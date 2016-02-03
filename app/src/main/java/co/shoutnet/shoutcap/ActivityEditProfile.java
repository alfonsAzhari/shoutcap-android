package co.shoutnet.shoutcap;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import co.shoutnet.shoutcap.utility.SessionManager;

/**
 * Created by Adam MB on 9/14/2015.
 */
public class ActivityEditProfile extends AppCompatActivity {

    private Context mContext;

    private EditText edtName;
    private EditText edtAddress;
    private EditText edtPostalCode;
    private EditText edtPhone;
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
    private RadioGroup radioGroupStatus;
    private RadioButton radioButtonStatus;
    private RadioGroup radioGroupJK;
    private RadioButton radioButtonJK;
    private CheckBox checkArt;
    private CheckBox checkMusic;
    private CheckBox checkBook;
    private CheckBox checkPhoto;
    private CheckBox checkCulinary;
    private CheckBox checkSport;
    private CheckBox checkCulture;
    private CheckBox checkStandUp;
    private CheckBox checkFashion;
    private CheckBox checkTech;
    private CheckBox checkGames;
    private CheckBox checkTravel;
    private CheckBox checkMovie;
    private CheckBox checkOther;

    private List<String> minat;

    SessionManager sessionManager;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mContext = this;

        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();

        ArrayList<String> minats = new ArrayList<>(sessionManager.getUserMinat());
        minat = new ArrayList<>();
        minat.add(minats.get(0));

        initView();
        setSpinner();
        getProvince(ApiReferences.getUrlGetProvince());
        setDateTimeField();
        initToolbar();
        //setTwitter();
        setValue();

        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                radioButtonStatus = (RadioButton) radioGroup.findViewById(id);
                //Log.i("radio", radioButtonStatus.getText().toString());
            }
        });

        radioGroupJK.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                radioButtonJK = (RadioButton) radioGroup.findViewById(id);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProfile(ApiReferences.getUrlUpdateProfile());
            }
        });
    }

    private void initView() {
        edtBirth = (EditText) findViewById(R.id.edt_edit_profile_birth);
        edtBirth.setInputType(InputType.TYPE_NULL);
        radioGroupJK = (RadioGroup) findViewById(R.id.radio_group_edit_jk);
        radioGroupStatus = (RadioGroup) findViewById(R.id.radio_group_edit_status);
        spinnerProvince = (Spinner) findViewById(R.id.spinner_edit_province);
        spinnerCity = (Spinner) findViewById(R.id.spinner_edit_city);
        spinnerKecamatan = (Spinner) findViewById(R.id.spinner_edit_kecamatan);
        edtName = (EditText) findViewById(R.id.edt_edit_profile_name);
        edtAddress = (EditText) findViewById(R.id.edt_edit_address);
        edtPostalCode = (EditText) findViewById(R.id.edt_edit_postal_kode);
        edtPhone = (EditText) findViewById(R.id.edt_edit_phone);
        //edtTwitter = (EditText) findViewById(R.id.edt_edit_twitter);

        checkArt = (CheckBox)findViewById(R.id.checkbox_edit_art);
        checkMusic = (CheckBox)findViewById(R.id.checkbox_edit_music);
        checkBook = (CheckBox)findViewById(R.id.checkbox_edit_book);
        checkPhoto = (CheckBox)findViewById(R.id.checkbox_edit_photography);
        checkCulinary = (CheckBox)findViewById(R.id.checkbox_edit_culinary);
        checkSport = (CheckBox)findViewById(R.id.checkbox_edit_sport);
        checkCulture = (CheckBox)findViewById(R.id.checkbox_edit_culture);
        checkStandUp = (CheckBox)findViewById(R.id.checkbox_edit_standup);
        checkFashion = (CheckBox)findViewById(R.id.checkbox_edit_fashion);
        checkTech = (CheckBox)findViewById(R.id.checkbox_edit_technology);
        checkGames = (CheckBox)findViewById(R.id.checkbox_edit_games);
        checkTravel = (CheckBox)findViewById(R.id.checkbox_edit_travel);
        checkMovie = (CheckBox)findViewById(R.id.checkbox_edit_movie);
        checkOther = (CheckBox)findViewById(R.id.checkbox_edit_other);

        btnSave = (Button) findViewById(R.id.btn_edit_save);
    }

    private void setValue() {
        edtName.setText(user.get(SessionManager.KEY_NAME));
        edtBirth.setText(user.get(SessionManager.KEY_DATE_BIRTH));
        edtPhone.setText(user.get(SessionManager.KEY_PHONE));
        edtAddress.setText(user.get(SessionManager.KEY_ADDRESS));
        edtPostalCode.setText(user.get(SessionManager.KEY_POSTAL_CODE));

        switch (user.get(SessionManager.KEY_WORK_STATUS).toLowerCase()) {
            case "sekolah":
                radioGroupStatus.check(R.id.radio_edit_school);
                break;
            case "kuliah":
                radioGroupStatus.check(R.id.radio_edit_college);
                break;
            case "kerja":
                radioGroupStatus.check(R.id.radio_edit_work);
                break;
            case "kuliah + kerja":
                radioGroupStatus.check(R.id.radio_edit_college_work);
                break;
            case "other":
                radioGroupStatus.check(R.id.radio_edit_other);
                break;
        }

        switch (user.get(SessionManager.KEY_GENDER).toLowerCase()) {
            case "pria":
                radioGroupJK.check(R.id.radio_edit_male);
                break;
            case "laki-laki":
                radioGroupJK.check(R.id.radio_edit_male);
                break;
            case "wanita":
                radioGroupJK.check(R.id.radio_edit_female);
                break;
            case "perempuan":
                radioGroupJK.check(R.id.radio_edit_female);
                break;
        }

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

    private void setCheckBoxes() {
        checkArt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkArt.getText().toString());
            }
        });
        checkMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkMusic.getText().toString());
            }
        });
        checkBook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkBook.getText().toString());
            }
        });
        checkPhoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkPhoto.getText().toString());
            }
        });
        checkCulinary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkCulinary.getText().toString());
            }
        });
        checkSport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkSport.getText().toString());
            }
        });
        checkCulture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkCulture.getText().toString());
            }
        });
        checkStandUp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkStandUp.getText().toString());
            }
        });
        checkFashion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkFashion.getText().toString());
            }
        });
        checkTech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkTech.getText().toString());
            }
        });
        checkGames.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkGames.getText().toString());
            }
        });
        checkTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkTravel.getText().toString());
            }
        });
        checkMovie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkMovie.getText().toString());
            }
        });
        checkOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                minat.add(checkOther.getText().toString());
            }
        });
    }

    private void setDateTimeField() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

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
                //Log.i("tgl", edtBirth.getText().toString());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                edtBirth.clearFocus();
            }
        });

    }

    private void getProvince(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.get("result").equals("success")) {
                        int id = 0;
                        JSONArray jsonArray = response.getJSONArray("item");
                        List<String> listProvince = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listProvince.add(jsonArray.get(i).toString());
                            if (jsonArray.get(i).toString().toLowerCase().equals(user.get(SessionManager.KEY_PROVINCE).toLowerCase())) {
                                id = i;
                            }
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ActivityEditProfile.this, android.R.layout.simple_spinner_item, listProvince);
                        spinnerProvince.setAdapter(arrayAdapter);
                        spinnerProvince.setSelection(id);

                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.i("volley error", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        objectRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(objectRequest);
    }

    private void getCity(String url, final String province) {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.get("result").equals("success")) {
                        int id = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("item");
                        List<String> listCity = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listCity.add(jsonArray.get(i).toString());
                            if (jsonArray.get(i).toString().toLowerCase().equals(user.get(SessionManager.KEY_CITY).toLowerCase())) {
                                id = i;
                            }
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ActivityEditProfile.this, android.R.layout.simple_spinner_item, listCity);
                        spinnerCity.setAdapter(arrayAdapter);
                        spinnerCity.setSelection(id);

                        progressDialog.dismiss();
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

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
    }

    private void getKecamatan(String url, final String city) {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                    if (jsonObject.get("result").equals("success")) {
                        int id = 0;
                        JSONArray jsonArray = jsonObject.getJSONArray("item");
                        List<String> listKecamatan = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            listKecamatan.add(jsonArray.get(i).toString());
                            if (jsonArray.get(i).toString().toLowerCase().equals(user.get(SessionManager.KEY_KEC).toLowerCase())) {
                                id = i;
                            }
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ActivityEditProfile.this, android.R.layout.simple_spinner_item, listKecamatan);
                        spinnerKecamatan.setAdapter(arrayAdapter);
                        spinnerKecamatan.setSelection(id);

                        progressDialog.dismiss();
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

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
    }

    private void postProfile(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                    if (jsonObject.get("result").equals("success")) {
                        progressDialog.dismiss();

                        Toast.makeText(mContext, "Profile Sudah Terupdate", Toast.LENGTH_SHORT).show();

                        ActivityEditProfile.this.finish();
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
                data.put("shoutid", user.get(SessionManager.KEY_SHOUTID));
                data.put("sessionid", user.get(SessionManager.KEY_SESSIONID));
                data.put("nama", edtName.getText().toString());
                data.put("gender", radioButtonJK.getText().toString());
                data.put("tgl_lahir", edtBirth.getText().toString());
                data.put("alamat", edtAddress.getText().toString());
                data.put("provinsi", spinnerProvince.getSelectedItem().toString());
                data.put("kota", spinnerCity.getSelectedItem().toString());
                data.put("kecamatan", spinnerKecamatan.getSelectedItem().toString());
                data.put("kodepos", edtPostalCode.getText().toString());
                data.put("hp", edtPhone.getText().toString());
                data.put("email", "");
                data.put("status_kerja", radioButtonStatus.getText().toString());
                data.put("minat", minat.toString());

                return data;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);
        requestQueue.add(request);
    }
}