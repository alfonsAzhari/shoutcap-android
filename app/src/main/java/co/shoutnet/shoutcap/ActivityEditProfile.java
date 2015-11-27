package co.shoutnet.shoutcap;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Adam MB on 9/14/2015.
 */
public class ActivityEditProfile extends AppCompatActivity{

    private EditText editNama;
    private EditText editAlamat;
    private EditText editKodePos;
    private EditText editNomorHP;
    private EditText editEmail;
    private EditText editTwitter;
    private ArrayAdapter<CharSequence> adapter;
    private Spinner spinnerProvinsi;
    private Spinner spinnerKota;
    private Spinner spinnerKecamatan;
    private Toolbar toolbar;
    private EditText editTanggalLahir;
public class ActivityEditProfile extends AppCompatActivity {

    ArrayAdapter<CharSequence> adapter;
    Spinner spinnerProvinsi, spinnerKota, spinnerKecamatan;
    Toolbar toolbar;
    private String[] provinsi, kota, kecamatan;
    private EditText dateEditText;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        initView();
        setSpinner();
        setDateTimeField();
        initToolbar();
    }

    private void initToolbar() {


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");
    }

    private void setSpinner() {
        adapter = ArrayAdapter.createFromResource(
                this, R.array.provinsi,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvinsi.setAdapter(adapter);
        spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        spinnerKota.setAdapter(adapter);
        spinnerKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        editTanggalLahir = (EditText) findViewById(R.id.edit_tanggal_lahir_edit_profile);
        editTanggalLahir.setInputType(InputType.TYPE_NULL);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        spinnerProvinsi = (Spinner)findViewById(R.id.spinner_provinsi_edit_profile);
        spinnerKota = (Spinner)findViewById(R.id.spinner_kota_edit_profile);
        spinnerKecamatan = (Spinner)findViewById(R.id.spinner_kecamatan_edit_profile);
        editNama = (EditText)findViewById(R.id.edit_nama_edit_profile);
        editAlamat = (EditText)findViewById(R.id.edit_alamat_edit_profile);
        editKodePos = (EditText)findViewById(R.id.edit_kode_pos_edit_profile);
        editNomorHP = (EditText)findViewById(R.id.edit_nomor_hp_edit_profile);
        editEmail = (EditText)findViewById(R.id.edit_email_edit_profile);
        editTwitter = (EditText)findViewById(R.id.edit_twitter_edit_profile);
    }

    private void setDateTimeField() {
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        editTanggalLahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editTanggalLahir.hasFocus()) {
                    if (view == editTanggalLahir) {
        dateEditText = (EditText) findViewById(R.id.tanggal_lahir_edit);
        dateEditText.setInputType(InputType.TYPE_NULL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinnerProvinsi = (Spinner) findViewById(R.id.spinner_provinsi);
        spinnerKota = (Spinner) findViewById(R.id.spinner_kota);
        spinnerKecamatan = (Spinner) findViewById(R.id.spinner_kecamatan);
    }

    private void setDateTimeField() {
        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (dateEditText.hasFocus()) {
                    if (view == dateEditText) {
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
                editTanggalLahir.setText(simpleDateFormat.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dateEditText.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
}
