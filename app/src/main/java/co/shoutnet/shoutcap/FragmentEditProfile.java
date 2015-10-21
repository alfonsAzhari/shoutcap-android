package co.shoutnet.shoutcap;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Adam MB on 9/14/2015.
 */
public class FragmentEditProfile extends Fragment {

    private ArrayAdapter<CharSequence> adapter;
    private Spinner spinnerProvinsi;
    private Spinner spinnerKota;
    private Spinner spinnerKecamatan;
    private String[] provinsi;
    private String[] kota;
    private String[] kecamatan;
    private EditText dateEditText;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    public static FragmentEditProfile newInstance() {

        Bundle args = new Bundle();

        FragmentEditProfile fragment = new FragmentEditProfile();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        initView(rootView);
        setSpinner();
        setDateTimeField();

        return rootView;
    }

    private void initView(View v) {
        dateEditText = (EditText) v.findViewById(R.id.tanggal_lahir_edit);
        dateEditText.setInputType(InputType.TYPE_NULL);
        spinnerProvinsi = (Spinner) v.findViewById(R.id.spinner_provinsi);
        spinnerKota = (Spinner) v.findViewById(R.id.spinner_kota);
        spinnerKecamatan = (Spinner) v.findViewById(R.id.spinner_kecamatan);
    }

    private void setSpinner() {
        /*adapter = ArrayAdapter.createFromResource(
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
        });*/
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
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateEditText.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
}
