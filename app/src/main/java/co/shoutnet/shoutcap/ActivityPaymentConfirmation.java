package co.shoutnet.shoutcap;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Adam MB on 10/16/2015.
 */
public class ActivityPaymentConfirmation extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText idOrder;
    private EditText namaPemesan;
    private EditText nomorHP;
    private EditText email;
    private EditText tanggalPembayaran;
    private EditText bankLain;
    private EditText pemilikRekening;
    private EditText jumlahUang;
    private Button send;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        initView();
        initToolbar();
        setDateTimeField();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment Confirmation");
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar_payment_confirmation);
        idOrder = (EditText)findViewById(R.id.edit_id_order_payment_confirmation);
        namaPemesan = (EditText)findViewById(R.id.edit_nama_pemesan_payment_confirmation);
        nomorHP = (EditText)findViewById(R.id.edit_no_hp_payment_confirmation);
        email = (EditText)findViewById(R.id.edit_email_payment_confirmation);
        tanggalPembayaran = (EditText)findViewById(R.id.edit_tanggal_pembayaran_payment_confirmation);
        bankLain = (EditText)findViewById(R.id.edit_bank_lain_payment_confirmation);
        pemilikRekening = (EditText)findViewById(R.id.edit_pemilik_rekening_payment_confirmation);
        jumlahUang = (EditText)findViewById(R.id.edit_jumlah_uang_payment_confirmation);
        send = (Button)findViewById(R.id.button_send_payment_confirmation);
    }

    private void setDateTimeField() {

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        tanggalPembayaran.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(tanggalPembayaran.hasFocus()) {
                    if (view == tanggalPembayaran) {
                        datePickerDialog.show();
                    }
                }
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggalPembayaran.setText(simpleDateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
}
