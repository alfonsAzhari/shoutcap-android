package co.shoutnet.shoutcap;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import co.shoutnet.shoutcap.utility.ApiReferences;

/**
 * Created by Adam MB on 9/14/2015.
 */
public class ActivityEditProfile extends AppCompatActivity {

    private EditText edtName;
    private EditText edtAddress;
    private EditText edtPostalCode;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtTwitter;
    private ArrayAdapter<CharSequence> adapter;
    private Spinner spinnerProvince;
    private Spinner spinnerCity;
    private Spinner spinnerKecamatan;
    private Toolbar toolbar;
    private EditText edtBirth;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initView();
        setSpinner();
        getProvice(ApiReferences.getUrlGetProvince());
        setDateTimeField();
        initToolbar();
        setTwitter();
    }

    private void setTwitter() {
        edtTwitter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edtTwitter.hasFocus()) {
                    if (edtTwitter.length() <= 1) {
                        edtTwitter.setText("@");
                    }
                } else {
                    if (edtTwitter.length() <= 1) {
                        edtTwitter.setText("");
                    }
                }
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_edit_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");
    }

    private void setSpinner() {
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCity(ApiReferences.getUrlGetCity(), spinnerProvince.getSelectedItem().toString());
                spinnerCity.setEnabled(true);
                spinnerKecamatan.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getKecamatan(ApiReferences.getUrlGetKec(), spinnerCity.getSelectedItem().toString());
                spinnerKecamatan.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCity.setEnabled(false);
        spinnerKecamatan.setEnabled(false);
    }

    private void initView() {
        edtBirth = (EditText) findViewById(R.id.edt_edit_profile_birth);
        edtBirth.setInputType(InputType.TYPE_NULL);
        spinnerProvince = (Spinner) findViewById(R.id.spinner_edit_province);
        spinnerCity = (Spinner) findViewById(R.id.spinner_edit_city);
        spinnerKecamatan = (Spinner) findViewById(R.id.spinner_edit_kecamatan);
        edtName = (EditText) findViewById(R.id.edt_edit_profile_name);
        edtAddress = (EditText) findViewById(R.id.edt_edit_address);
        edtPostalCode = (EditText) findViewById(R.id.edt_edit_postal_kode);
        edtPhone = (EditText) findViewById(R.id.edt_edit_phone);
        edtEmail = (EditText) findViewById(R.id.edt_edit_email);
        edtTwitter = (EditText) findViewById(R.id.edt_edit_twitter);
        btnSave = (Button) findViewById(R.id.btn_edit_save);
    }

    private void setDateTimeField() {
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        edtBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edtBirth.hasFocus()) {
                    if (view == edtBirth) {
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
                edtBirth.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void getProvice(String url) {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                    if (jsonObject.get("result").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("item");
                        List<String> listProvince = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listProvince.add(jsonArray.get(i).toString());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ActivityEditProfile.this, android.R.layout.simple_spinner_item, listProvince);
                        spinnerProvince.setAdapter(arrayAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.i("volley error", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        objectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(objectRequest);
    }

    private void getCity(String url, final String province) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                    if (jsonObject.get("result").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("item");
                        List<String> listCity = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listCity.add(jsonArray.get(i).toString());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ActivityEditProfile.this, android.R.layout.simple_spinner_item, listCity);
                        spinnerCity.setAdapter(arrayAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("provinsi", province);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
    }

    private void getKecamatan(String url, final String city) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                    if (jsonObject.get("result").equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("item");
                        List<String> listKecamatan = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listKecamatan.add(jsonArray.get(i).toString());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ActivityEditProfile.this, android.R.layout.simple_spinner_item, listKecamatan);
                        spinnerKecamatan.setAdapter(arrayAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("kota", city);

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
    }
}