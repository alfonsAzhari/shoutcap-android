package co.shoutnet.shoutcap;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.DestinationModel;
import co.shoutnet.shoutcap.utility.AddressDialog;

public class FragmentDestination extends Fragment {

    private static DestinationModel destModel;
    //Consignee
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtEmail;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    //address
    private Spinner spnProvince;
    private Button spnCity;
    private Button spnDistrict;
    private EditText edtAddress;
    private EditText edtZipCode;
    private Button btnSubmit;
    private String result;
    private boolean[] emptyField;

    private FetchData fetchData;
    private ArrayAdapter<String> adapter;

    private String[] name = {"name1", "name2", "name3", "name4", "name5", "name6", "name7", "name8"};

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

        initView(view);
        initViewAction();

        fetchData = new FetchData("prov", "prov", "https://api.shoutnet.co/shoutid/get_provinsi.php");
        fetchData.getData();

        return view;
    }

    private void initViewAction() {

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

        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(name, "city");
            }
        });
        spnDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(name, "district");
            }
        });

    }

    private void getData() {

    }

    private void showDialog(String[] name, final String init) {
        DialogFragment dialogFragment = AddressDialog.newInstance(name, new AddressDialog.AddrDialogListener() {
            @Override
            public void result(String value) {
                result = value;
                switch (init) {
//                    case "prov":
//                        spnProvince.setText(value);
//                        destModel.setProvince(value);
//                        emptyField[3] = false;
//                        break;
                    case "city":
                        spnCity.setText(value);
                        destModel.setCity(result);
                        emptyField[4] = false;
                        break;
                    case "district":
                        spnDistrict.setText(value);
                        destModel.setDistrict(result);
                        emptyField[5] = false;
                        break;
                }
            }
        });
        dialogFragment.setCancelable(false);
        dialogFragment.show(getFragmentManager(), "Address");
    }

    private void initView(View view) {
        //Consignee
        edtName = (EditText) view.findViewById(R.id.edt_name_destination);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone_destination);
        edtEmail = (EditText) view.findViewById(R.id.edt_email_destination);
        rbMale = (RadioButton) view.findViewById(R.id.rb_male_destination);
        rbFemale = (RadioButton) view.findViewById(R.id.rb_female_destination);

        //Address
        spnProvince = (Spinner) view.findViewById(R.id.spn_province_destination);
        spnCity = (Button) view.findViewById(R.id.spn_city_destination);
        spnDistrict = (Button) view.findViewById(R.id.spn_district_destination);
        edtAddress = (EditText) view.findViewById(R.id.edt_address_destination);
        edtZipCode = (EditText) view.findViewById(R.id.edt_zip_destination);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, name);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProvince.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private class FetchData {
        private String param;
        private String keyParam;
        private String url;
        private String requestResult = null;

        public FetchData(String keyParam, String param, String url) {
            this.keyParam = keyParam;
            this.param = param;
            this.url = url;
        }

        public String getData() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    requestResult = response;
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
}
