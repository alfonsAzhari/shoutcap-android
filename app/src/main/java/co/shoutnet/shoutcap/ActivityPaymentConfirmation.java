package co.shoutnet.shoutcap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import co.shoutnet.shoutcap.model.ModelCaraBayar;
import co.shoutnet.shoutcap.model.ModelMessage;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.VolleyRequest;

/**
 * Created by Adam MB on 10/16/2015.
 */
public class ActivityPaymentConfirmation extends AppCompatActivity {

    private static String ID_ORDER;
    private Context mContext = this;

    private ModelCaraBayar caraBayar;
    private ArrayList<ModelCaraBayar.Item> caraBayars;
    private ModelMessage modelMessage;

    private Toolbar toolbar;
    private EditText idOrder;
    private EditText namaPemesan;
    private EditText nomorHP;
    private EditText email;
    private EditText tanggalPembayaran;
    private RadioButton rBankBCA;
    private EditText eBankLain;
    private RadioButton rBankLain;
    private RadioButton[] rb;
    private int indexRadio;
    private EditText pemilikRekening;
    private EditText jumlahUang;
    private Button send;
    private RadioGroup rgCaraBayar;
    private String bankAsal;
    private TextInputLayout lytNamaPemesan;
    private TextInputLayout lytNomorHP;
    private TextInputLayout lytEmail;
    private TextInputLayout lytIdOrder;
    private TextInputLayout lytTanggalPembayaran;
    private TextInputLayout lytPemilikRekening;
    private TextInputLayout lytJumlahUang;
    private LinearLayout linProgress;

    private Bundle bundle;
    private SessionManager manager;
    private HashMap<String, String> user;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        manager = new SessionManager(mContext);
        user = manager.getUserDetails();
        bundle = getIntent().getExtras();

        initView();
        setToolbar();

        setDateTimeField();
        fetchCaraBayar(ApiReferences.getCaraBayar());

        initViewAction();
    }

    private void initViewAction() {
        idOrder.setText(bundle.getString(ID_ORDER));

        namaPemesan.addTextChangedListener(new Watcher(namaPemesan));
        nomorHP.addTextChangedListener(new Watcher(nomorHP));
        email.addTextChangedListener(new Watcher(email));
        idOrder.addTextChangedListener(new Watcher(idOrder));
        tanggalPembayaran.addTextChangedListener(new Watcher(tanggalPembayaran));
        pemilikRekening.addTextChangedListener(new Watcher(pemilikRekening));
        jumlahUang.addTextChangedListener(new Watcher(jumlahUang));

        rBankLain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rBankLain.isChecked()) {
                    eBankLain.setEnabled(true);
                    eBankLain.requestFocus();
                } else {
                    eBankLain.setText("");
                    eBankLain.setEnabled(false);
                }
            }
        });

        rBankBCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankAsal = "BCA";
            }
        });
        rBankLain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankAsal = eBankLain.getText().toString().trim();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < caraBayars.size(); i++) {
                    if (rb[i].isChecked()) {
                        indexRadio = i + 1;
                    }
                }
                validate();
//                mappingData();
//                postConfirmation(ApiReferences.getPaymentConfirmation());
            }
        });
    }

    private void validate() {
        if (!validateNama()) {
            namaPemesan.requestFocus();
            Toast.makeText(mContext, "Nama belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateHP()) {
            nomorHP.requestFocus();
            Toast.makeText(mContext, "Nomor HP belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateEmail()) {
            email.requestFocus();
            Toast.makeText(mContext, "E-mail belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateIdOrder()) {
            idOrder.requestFocus();
            Toast.makeText(mContext, "ID order belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateTanggalPembayaran()) {
            tanggalPembayaran.requestFocus();
            Toast.makeText(mContext, "Tanggal pembayaran belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validatePemilikRekening()) {
            pemilikRekening.requestFocus();
            Toast.makeText(mContext, "Pemilik rekening belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateJumlahUang()) {
            jumlahUang.requestFocus();
            Toast.makeText(mContext, "Jumlah uang belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending");
        progressDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postConfirmation(ApiReferences.getPaymentConfirmation());
                progressDialog.dismiss();
            }
        }, 3000);
    }

    private void postConfirmation(String url) {
        Map<String, String> params = mappingData();
        modelMessage = new ModelMessage();

        new VolleyRequest().request(mContext, Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                Log.i("json", response);
                try {
                    modelMessage = Parser.getMessage(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(mContext, modelMessage.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void OnFaliure() {
                Toast.makeText(mContext, "Sending data failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Map<String, String> mappingData() {
        Map<String, String> params = new HashMap<>();
        params.put("shoutid", user.get("shoutId"));
        params.put("sessionid", user.get("sessionId"));
        params.put("idorder", idOrder.getText().toString().trim());
        params.put("nama_pemesan", namaPemesan.getText().toString().trim());
        params.put("hp", nomorHP.getText().toString().trim());
        params.put("email", email.getText().toString().trim());
        params.put("tgl_bayar", tanggalPembayaran.getText().toString().trim());
        params.put("id_cara_bayar", String.valueOf(indexRadio));
        params.put("bank_asal", bankAsal);
        params.put("nama_rekening", pemilikRekening.getText().toString().trim());
        params.put("jumlah", jumlahUang.getText().toString().trim());
        return params;
    }

    private void fetchCaraBayar(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response.toString());
                try {
                    caraBayar = Parser.getCaraBayar(response.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("result", caraBayar.getResult());
                if (caraBayar.getResult().equals("success")) {
                    caraBayars = new ArrayList<>();
                    caraBayars = caraBayar.getItem();

                    rb = new RadioButton[caraBayars.size()];
                    for (int i = 0; i < caraBayars.size(); i++) {
                        rb[i] = new RadioButton(mContext);
                        rgCaraBayar.addView(rb[i]);
                        rb[i].setText(caraBayars.get(i).getCara_bayar());
                    }

                    linProgress.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error volley", error.toString());
            }
        });

        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment Confirmation");
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_payment_confirmation);
        idOrder = (EditText) findViewById(R.id.edit_id_order_payment_confirmation);
        namaPemesan = (EditText) findViewById(R.id.edit_nama_pemesan_payment_confirmation);
        nomorHP = (EditText) findViewById(R.id.edit_no_hp_payment_confirmation);
        email = (EditText) findViewById(R.id.edit_email_payment_confirmation);
        tanggalPembayaran = (EditText) findViewById(R.id.edit_tanggal_pembayaran_payment_confirmation);
        eBankLain = (EditText) findViewById(R.id.edit_bank_lain_payment_confirmation);
        pemilikRekening = (EditText) findViewById(R.id.edit_pemilik_rekening_payment_confirmation);
        jumlahUang = (EditText) findViewById(R.id.edit_jumlah_uang_payment_confirmation);
        send = (Button) findViewById(R.id.button_send_payment_confirmation);
        rBankLain = (RadioButton) findViewById(R.id.radio_bank_lain_payment_confirmation);
        rgCaraBayar = (RadioGroup) findViewById(R.id.radio_group_cara_pembayaran_payment_confirmation);
        rgCaraBayar.setOrientation(LinearLayout.VERTICAL);
        rBankBCA = (RadioButton) findViewById(R.id.radio_bca_payment_confirmation);

        lytNamaPemesan = (TextInputLayout)findViewById(R.id.lyt_nama_pemesan_payment_confirmation);
        lytNomorHP = (TextInputLayout)findViewById(R.id.lyt_no_hp_payment_confirmation);
        lytEmail = (TextInputLayout)findViewById(R.id.lyt_email_payment_confirmation);
        lytIdOrder = (TextInputLayout)findViewById(R.id.lyt_id_order_payment_confirmation);
        lytTanggalPembayaran = (TextInputLayout)findViewById(R.id.lyt_tanggal_pembayaran_payment_confirmation);
        lytPemilikRekening = (TextInputLayout)findViewById(R.id.lyt_pemilik_rekening_payment_confirmation);
        lytJumlahUang = (TextInputLayout)findViewById(R.id.lyt_jumlah_uang_payment_confirmation);
        linProgress = (LinearLayout)findViewById(R.id.lin_payment_confirmation_progress);
    }

    private void setDateTimeField() {

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        tanggalPembayaran.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (tanggalPembayaran.hasFocus()) {
                    if (view == tanggalPembayaran) {
                        datePickerDialog.setTitle("Tanggal Pembayaran");
                        datePickerDialog.show();
                    }
                }
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggalPembayaran.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private class Watcher implements TextWatcher {
        private View view;

        public Watcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.edit_nama_pemesan_payment_confirmation:
                    validateNama();
                    break;
                case R.id.edit_no_hp_payment_confirmation:
                    validateHP();
                    break;
                case R.id.edit_email_payment_confirmation:
                    validateEmail();
                    break;
                case R.id.edit_id_order_payment_confirmation:
                    validateIdOrder();
                    break;
                case R.id.edit_tanggal_pembayaran_payment_confirmation:
                    validateTanggalPembayaran();
                    break;
                case R.id.edit_pemilik_rekening_payment_confirmation:
                    validatePemilikRekening();
                    break;
                case R.id.edit_jumlah_uang_payment_confirmation:
                    validateJumlahUang();
                    break;
            }
        }
    }

    private boolean validateNama() {
        if (namaPemesan.getText().toString().trim().isEmpty()){
            lytNamaPemesan.setError("Masukan nama pemesan");
            return false;
        } else {
            lytNamaPemesan.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateHP() {
        if (nomorHP.getText().toString().trim().isEmpty()){
            lytNomorHP.setError("Masukan nomor HP");
            return false;
        } else {
            lytNomorHP.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty()){
            lytEmail.setError("Masukan e-mail");
            return false;
        } else {
            lytEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateIdOrder() {
        if (idOrder.getText().toString().trim().isEmpty()){
            lytIdOrder.setError("Masukan ID order");
            return false;
        } else {
            lytIdOrder.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateTanggalPembayaran() {
        if (tanggalPembayaran.getText().toString().trim().isEmpty()){
            lytTanggalPembayaran.setError("Masukan tanggal pembayaran");
            return false;
        } else {
            lytTanggalPembayaran.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePemilikRekening() {
        if (pemilikRekening.getText().toString().trim().isEmpty()){
            lytPemilikRekening.setError("Masukan nama pemilik rekening");
            return false;
        } else {
            lytPemilikRekening.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateJumlahUang() {
        if (jumlahUang.getText().toString().trim().isEmpty()){
            lytJumlahUang.setError("Masukan jumlah uang");
            return false;
        } else {
            lytJumlahUang.setErrorEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
