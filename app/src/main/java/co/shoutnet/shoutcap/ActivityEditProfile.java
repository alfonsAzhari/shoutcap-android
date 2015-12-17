package co.shoutnet.shoutcap;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
        adapter = ArrayAdapter.createFromResource(
                this, R.array.provinsi,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        adapter = ArrayAdapter.createFromResource(
                this, R.array.kota,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        adapter = ArrayAdapter.createFromResource(
                this, R.array.kecamatan,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKecamatan.setAdapter(adapter);
        spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v,
                                       int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
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
}