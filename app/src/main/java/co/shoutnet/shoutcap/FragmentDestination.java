package co.shoutnet.shoutcap;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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
    private Button spnProvince;
    private Button spnCity;
    private Button spnDistrict;
    private EditText edtAddress;
    private EditText edtZipCode;
    private Button btnSubmit;
    private String result;
    private boolean[] emptyField;

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

        return view;
    }

    private void initViewAction() {
        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (edtName.getText().equals("") || edtName.getText() == null)) {
                    showToast("Name field is empty, insert name");
                    emptyField[0] = true;
                } else {
                    emptyField[0] = false;
                }
            }
        });
        edtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (edtPhone.getText().equals("") || edtPhone.getText() == null)) {
                    showToast("Phone field is empty, insert phone");
                    emptyField[1] = true;
                } else {
                    emptyField[1] = false;
                }
            }
        });
        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (edtEmail.getText().equals("") || edtEmail.getText() == null)) {
                    showToast("Email field is empty, insert email");
                    emptyField[2] = true;
                } else {
                    emptyField[2] = false;
                }
            }
        });
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

        emptyField[3] = true;
        emptyField[4] = true;
        emptyField[5] = true;

        spnProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(name, "prov");
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
        edtAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (edtAddress.getText().equals("") || edtAddress.getText() == null)) {
                    showToast("Address field is empty, insert address");
                    emptyField[6] = true;
                } else {
                    emptyField[6] = false;
                }
            }
        });
        edtZipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && (edtZipCode.getText().equals("") || edtZipCode.getText() == null)) {
                    showToast("Zip code field is empty, insert zip code");
                    emptyField[7] = true;
                } else {
                    emptyField[7] = false;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < emptyField.length; i++) {
                    if (emptyField[i]) {
                        showToast("There is field empty");
                        break;
                    } else {
                        getData();
                    }
                }
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
                    case "prov":
                        spnProvince.setText(value);
                        destModel.setProvince(value);
                        emptyField[3] = false;
                        break;
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
        spnProvince = (Button) view.findViewById(R.id.spn_province_destination);
        spnCity = (Button) view.findViewById(R.id.spn_city_destination);
        spnDistrict = (Button) view.findViewById(R.id.spn_district_destination);
        edtAddress = (EditText) view.findViewById(R.id.edt_address_destination);
        edtZipCode = (EditText) view.findViewById(R.id.edt_zip_destination);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
