package co.shoutnet.shoutcap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.DestinationModel;
import co.shoutnet.shoutcap.model.ModelProvince;
import co.shoutnet.shoutcap.model.ModelResponseCheckout;
import co.shoutnet.shoutcap.utility.Loading;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.VolleyRequest;

public class OrderConfirmation extends AppCompatActivity {

    private static DestinationModel destModel;
    private Toolbar toolbar;
    //Consignee
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtEmail;
    private TextInputLayout lyName;
    private TextInputLayout lyPhone;
    private TextInputLayout lyEmail;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    //address
    private Spinner spnProvince;
    private Spinner spnCity;
    private Spinner spnDistrict;
    private EditText edtAddress;
    private EditText edtZipCode;
    private TextInputLayout lyAddrs;
    private TextInputLayout lyZip;
    private Button btnSubmit;

    //    private FetchData fetchData;
    private ArrayAdapter<String> adapter;
    private Map<String, String> params;

    private int[] qtyItems;
    private Context context;

    private String urlProvince = "https://api.shoutnet.co/shoutid/get_provinsi.php";
    private String urlCity = "https://api.shoutnet.co/shoutid/get_kota.php";
    private String urlDistrict = "https://api.shoutnet.co/shoutid/get_kecamatan.php";

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_destination);

        toolbar = (Toolbar) findViewById(R.id.toolbar_orderconf);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        destModel = new DestinationModel();
        context = this;

        initView();
        initViewAction();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            qtyItems = bundle.getIntArray("qtyItems");
            Log.i("qty", "ayaan tah manggan mamam");
        } else {
            Log.i("bun", "null");
        }

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentDestination destination = FragmentDestination.newInstance("dasdas", "dasdasd");
//        fragmentManager.beginTransaction().add(R.id.frame_content_order, destination).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int[] tes = data.getIntArrayExtra("qty");
                Log.i("qty", String.valueOf(tes[0]));
            }
        }
    }

    private void initViewAction() {
        edtName.addTextChangedListener(new Watcher(edtName));
        edtPhone.addTextChangedListener(new Watcher(edtPhone));
        edtEmail.addTextChangedListener(new Watcher(edtEmail));
        destModel.setGender("laki-laki");
        rbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destModel.setGender("laki-laki");
            }
        });
        rbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destModel.setGender("perempuan");
            }
        });

//        getData("prov", "prov", "https://api.shoutnet.co/shoutid/get_provinsi.php", spnProvince);
        getData(null, Request.Method.GET, urlProvince, spnProvince);

        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem;
                int item = spnProvince.getAdapter().getCount();
                if (item > 1) {
                    selectedItem = spnProvince.getSelectedItem().toString();

                    params = new HashMap<>();
                    params.put("provinsi", selectedItem);

                    getData(params, Request.Method.POST, urlCity, spnCity);
                } else {
                    getData(null, Request.Method.GET, urlProvince, spnProvince);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem;
                int item = spnCity.getAdapter().getCount();
                if (item > 1) {
                    selectedItem = spnCity.getSelectedItem().toString();
                    params = new HashMap<>();
                    params.put("kota", selectedItem);
                    getData(params, Request.Method.POST, urlDistrict, spnDistrict);
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtAddress.addTextChangedListener(new Watcher(edtAddress));
        edtZipCode.addTextChangedListener(new Watcher(edtZipCode));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                edtValidate();
            }
        });
    }

    private void initView() {
        //Consignee
        edtName = (EditText) findViewById(R.id.edt_name_destination);
        edtPhone = (EditText) findViewById(R.id.edt_phone_destination);
        edtEmail = (EditText) findViewById(R.id.edt_email_destination);
        lyName = (TextInputLayout) findViewById(R.id.ly_name_dest);
        lyPhone = (TextInputLayout) findViewById(R.id.ly_phone_dest);
        lyEmail = (TextInputLayout) findViewById(R.id.ly_email_dest);
        rbMale = (RadioButton) findViewById(R.id.rb_male_destination);
        rbFemale = (RadioButton) findViewById(R.id.rb_female_destination);

        //Address
        spnProvince = (Spinner) findViewById(R.id.spn_province_destination);
        spnCity = (Spinner) findViewById(R.id.spn_city_destination);
        spnDistrict = (Spinner) findViewById(R.id.spn_district_destination);
        spnCity.setEnabled(false);
        spnDistrict.setEnabled(false);
        edtAddress = (EditText) findViewById(R.id.edt_address_destination);
        edtZipCode = (EditText) findViewById(R.id.edt_zip_destination);
        lyAddrs = (TextInputLayout) findViewById(R.id.ly_addrs_dest);
        lyZip = (TextInputLayout) findViewById(R.id.ly_zip_dest);

        btnSubmit = (Button) findViewById(R.id.btn_submit_destination);
        loading = Loading.newInstance(OrderConfirmation.this);
        loading.setMessage("Sending consignee data");
    }

    private void getData(Map<String, String> param, int method, String url, final Spinner spinner) {
//        fetchData = new FetchData(param, key, url, new RequestListener() {
//            @Override
//            public void OnDataLoaded(String result) {
//                ModelProvince dataAddrs = null;
//                try {
//                    dataAddrs = Parser.getDataAddrs(result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (dataAddrs != null) {
//                    String[] data = dataAddrs.getItem();
//                    if (data != null) {
//                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, data);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                    spnProvince.setAdapter(adapter);
//                        spinner.setAdapter(adapter);
//                        spinner.setEnabled(true);
//                    }
//                }
//            }
//        });
        new VolleyRequest().request(getApplicationContext(), method, url, param, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                ModelProvince dataAddrs = null;
                try {
                    dataAddrs = Parser.getDataAddrs(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (dataAddrs != null) {
                    String[] data = dataAddrs.getItem();
                    if (data != null) {
                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, data);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setEnabled(true);
                    }
                }
            }

            @Override
            public void OnFailure() {

            }
        });
    }

    private void edtValidate() {
        if (!validateName()) {
            Toast.makeText(getApplicationContext(), "Name field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validatePhone()) {
            Toast.makeText(getApplicationContext(), "Phone field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateEmail()) {
            Toast.makeText(getApplicationContext(), "E-Mail field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateAddress()) {
            Toast.makeText(getApplicationContext(), "Address field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateZip()) {
            Toast.makeText(getApplicationContext(), "Zip code field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        final Bundle bundle = new Bundle();
        bundle.putString("name", edtName.getText().toString().trim());
        bundle.putString("phone", edtPhone.getText().toString().trim());
        bundle.putString("email", edtEmail.getText().toString().trim());
        String address = edtAddress.getText().toString() + " " +
                edtZipCode.getText().toString() + " " +
                spnDistrict.getSelectedItem().toString() + " " +
                spnCity.getSelectedItem().toString() + " " +
                spnProvince.getSelectedItem().toString();
        bundle.putString("address", address);

        //send data to server
        Log.i("info", "redeh to upload data");

        Map<String, String> params = mappingData();

        String url = "https://api.shoutnet.co/shoutcap/order_tujuan.php";
        new VolleyRequest().request(getApplicationContext(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                Log.i("json", response);
                ModelResponseCheckout modelResult = new ModelResponseCheckout();
                try {
                    modelResult = Parser.getResultCheckout(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (modelResult.getResult().equals("success")) {
                    Intent intent = new Intent(getApplicationContext(), ActivityCheckout.class);
                    intent.putExtra("qtyItems", qtyItems);
                    intent.putExtra("jsonResponse", response);
                    intent.putExtra("bundle", bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
//                    startActivityForResult(intent,1);
                }
            }

            @Override
            public void OnFailure() {

            }
        });
//
    }

    private boolean validateName() {
        if (edtName.getText().toString().trim().isEmpty()) {
            lyName.setError("Please insert consignee name");
            return false;
        } else {
            lyName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePhone() {
        if (edtPhone.getText().toString().trim().isEmpty()) {
            lyPhone.setError("Please insert phone number");
            return false;
        } else {
            lyPhone.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        if (edtEmail.getText().toString().trim().isEmpty()) {
            lyEmail.setError("Please insert e-email");
            return false;
        } else {
            lyEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateAddress() {
        if (edtAddress.getText().toString().trim().isEmpty()) {
            lyAddrs.setError("Please insert destination address");
            return false;
        } else {
            lyAddrs.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateZip() {
        if (edtZipCode.getText().toString().trim().isEmpty()) {
            lyZip.setError("Please insert zip code");
            return false;
        } else {
            lyZip.setErrorEnabled(false);
        }
        return true;
    }

    private Map<String, String> mappingData() {
        Map<String, String> params = new HashMap<>();
        params.put("shoutid", "devtest");
        params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
        params.put("nama", edtName.getText().toString().trim());
        params.put("hp", edtPhone.getText().toString().trim());
        params.put("email", edtEmail.getText().toString().trim());
        params.put("alamat", edtAddress.getText().toString().trim());
        params.put("kodepos", edtZipCode.getText().toString().trim());
        params.put("gender", destModel.getGender());
        params.put("provinsi", spnProvince.getSelectedItem().toString());
        params.put("kota", spnCity.getSelectedItem().toString());
        params.put("kecamatan", spnDistrict.getSelectedItem().toString());
        return params;
    }

//    public interface RequestListener {
//        void OnDataLoaded(String result);
//    }

//    private class FetchData {
//        private String param;
//        private String keyParam;
//        private String url;
//        private String requestResult = null;
//        private RequestListener listener;
//
//        public FetchData(String param, String keyParam, String url, RequestListener listener) {
//            this.param = param;
//            this.keyParam = keyParam;
//            this.url = url;
//            this.listener = listener;
//            getData();
//        }
//
//        public String getData() {
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    requestResult = response;
//                    Log.i("json", response);
//                    listener.OnDataLoaded(response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("error", error.getMessage());
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put(keyParam, param);
//                    return params;
//                }
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("Content-Type", "application/x-www-form-urlencoded");
//                    return params;
//                }
//            };
//
//            RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//            stringRequest.setRetryPolicy(retryPolicy);
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);
//
//            return requestResult;
//        }
//
//    }

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
                case R.id.edt_name_destination:
                    validateName();
                    break;
                case R.id.edt_phone_destination:
                    validatePhone();
                    break;
                case R.id.edt_email_destination:
                    validateEmail();
                    break;
                case R.id.edt_address_destination:
                    validateAddress();
                    break;
                case R.id.edt_zip_destination:
                    validateZip();
                    break;
            }
        }
    }
}
