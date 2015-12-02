package co.shoutnet.shoutcap;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import co.shoutnet.shoutcap.utility.AddressDialog;

public class FragmentDestination extends Fragment {

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

        initView(view);


        spnProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("info", "accessed");
                showDialog(name);
            }
        });
        return inflater.inflate(R.layout.fragment_destination, container, false);
    }

    private void showDialog(String[] name) {
        DialogFragment dialogFragment = AddressDialog.newInstance(name, new AddressDialog.AddrDialogListener() {
            @Override
            public void result(String value) {

            }
        });
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

}
