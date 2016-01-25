package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.DestinationModel;
import co.shoutnet.shoutcap.model.ModelProvince;
import co.shoutnet.shoutcap.utility.Loading;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.VolleyRequest;

public class FragmentDestination extends Fragment {

    private static DestinationModel destModel;
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
    private String result;
    private boolean[] emptyField;
    private ProgressDialog loading;

    private FetchData fetchData;
    private ArrayAdapter<String> adapter;

    private SessionManager manager;
    private HashMap<String, String> user;

    public static FragmentDestination newInstance(String param1, String param2) {
        FragmentDestination fragment = new FragmentDestination();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destination, container, false);

        if (destModel == null) {
            destModel = new DestinationModel();
        }

        loading = Loading.newInstance(getActivity());
        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();

        initView(view);
        initViewAction();

        return view;
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

        getData("prov", "prov", "https://api.shoutnet.co/shoutid/get_provinsi.php", spnProvince);

        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = spnProvince.getSelectedItem().toString();
                String url = "https://api.shoutnet.co/shoutid/get_kota.php";
                getData(selectedItem, "provinsi", url, spnCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = spnCity.getSelectedItem().toString();
                String url = "https://api.shoutnet.co/shoutid/get_kecamatan.php";
                getData(selectedItem, "kota", url, spnDistrict);
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

//    private boolean validateProvince(){
//        if (spnProvince.getSelectedItem().toString().isEmpty()){
//            lyZip.setError("Please insert zip code");
//            return false;
//        }else {
//            lyZip.setErrorEnabled(false);
//        }
//        return true;
//    }
//    private boolean validateCity(){
//        if (edtZipCode.getText().toString().trim().isEmpty()){
//            lyZip.setError("Please insert zip code");
//            return false;
//        }else {
//            lyZip.setErrorEnabled(false);
//        }
//        return true;
//    }
//    private boolean validateDistrict(){
//        if (edtZipCode.getText().toString().trim().isEmpty()){
//            lyZip.setError("Please insert zip code");
//            return false;
//        }else {
//            lyZip.setErrorEnabled(false);
//        }
//        return true;
//    }

    private void edtValidate() {
        if (!validateName()) {
            Toast.makeText(getActivity(), "Name field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validatePhone()) {
            Toast.makeText(getActivity(), "Phone field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateEmail()) {
            Toast.makeText(getActivity(), "E-Mail field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateAddress()) {
            Toast.makeText(getActivity(), "Address field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateZip()) {
            Toast.makeText(getActivity(), "Zip code field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> params = mappingData();

        String url = "https://api.shoutnet.co/shoutcap/order_tujuan.php";
        new VolleyRequest().request(getActivity(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                Log.i("json", response);
                loading.dismiss();
            }

            @Override
            public void OnFaliure() {
                loading.dismiss();
                Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Map<String, String> mappingData() {
        Map<String, String> params = new HashMap<>();
        params.put("shoutid", user.get("shoutId"));
        params.put("sessionid", user.get("sessionId"));
//        params.put("shoutid", "devtest");
//        params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
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

    private void getData(String param, String key, String url, final Spinner spinner) {
        fetchData = new FetchData(param, key, url, new RequestListener() {
            @Override
            public void OnDataLoaded(String result) {
                ModelProvince dataAddrs = null;
                try {
                    dataAddrs = Parser.getDataAddrs(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (dataAddrs != null) {
                    String[] data = dataAddrs.getItem();
                    if (data != null) {
                        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, data);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spnProvince.setAdapter(adapter);
                        spinner.setAdapter(adapter);
                        spinner.setEnabled(true);
                    }
                }
            }
        });
    }

    private void initView(View view) {
        //Consignee
        edtName = (EditText) view.findViewById(R.id.edt_name_destination);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone_destination);
        edtEmail = (EditText) view.findViewById(R.id.edt_email_destination);
        lyName = (TextInputLayout) view.findViewById(R.id.ly_name_dest);
        lyPhone = (TextInputLayout) view.findViewById(R.id.ly_phone_dest);
        lyEmail = (TextInputLayout) view.findViewById(R.id.ly_email_dest);
        rbMale = (RadioButton) view.findViewById(R.id.rb_male_destination);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female_destination);

        //Address
        spnProvince = (Spinner) view.findViewById(R.id.spn_province_destination);
        spnCity = (Spinner) view.findViewById(R.id.spn_city_destination);
        spnDistrict = (Spinner) view.findViewById(R.id.spn_district_destination);
        spnCity.setEnabled(false);
        spnDistrict.setEnabled(false);
        edtAddress = (EditText) view.findViewById(R.id.edt_address_destination);
        edtZipCode = (EditText) view.findViewById(R.id.edt_zip_destination);
        lyAddrs = (TextInputLayout) view.findViewById(R.id.ly_addrs_dest);
        lyZip = (TextInputLayout) view.findViewById(R.id.ly_zip_dest);

        btnSubmit = (Button) view.findViewById(R.id.btn_submit_destination);
//        ProgressBar progressBar=(ProgressBar)
    }

    public interface RequestListener {
        void OnDataLoaded(String result);
    }

    private class FetchData {
        private String param;
        private String keyParam;
        private String url;
        private String requestResult = null;
        private RequestListener listener;

        public FetchData(String param, String keyParam, String url, RequestListener listener) {
            this.param = param;
            this.keyParam = keyParam;
            this.url = url;
            this.listener = listener;
            getData();
        }

        public String getData() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    requestResult = response;
                    Log.i("json", response);
                    listener.OnDataLoaded(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("error", error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(keyParam, param);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };

            RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

            return requestResult;
        }

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
