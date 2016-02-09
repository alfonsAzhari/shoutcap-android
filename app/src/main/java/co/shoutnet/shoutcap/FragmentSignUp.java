package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.ModelRegister;
import co.shoutnet.shoutcap.model.ModelRegisterError;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Loading;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.VolleyRequest;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentSignUp extends Fragment {
    private ModelRegisterError modelRegisterError;
    private ModelRegister modelRegister;
    private EditText edtShoutId;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtEmail;
    private TextInputLayout lytShoutId;
    private TextInputLayout lytPassword;
    private TextInputLayout lytConfirm;
    private TextInputLayout lytEmail;
    private Button btnSignUp;
    private ProgressDialog loading;

    public FragmentSignUp() {

    }

    public static FragmentSignUp newInstance() {

        Bundle args = new Bundle();

        FragmentSignUp fragment = new FragmentSignUp();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        loading = Loading.newInstance(getActivity());
        initView(rootView);
        initViewAction();

        return rootView;
    }

    private void initViewAction() {
        edtShoutId.addTextChangedListener(new Watcher(edtShoutId));
        edtPassword.addTextChangedListener(new Watcher(edtPassword));
        edtConfirmPassword.addTextChangedListener(new Watcher(edtConfirmPassword));
        edtEmail.addTextChangedListener(new Watcher(edtEmail));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {
        if (!validateShoutId()) {
            Toast.makeText(getActivity(), "Nama belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validatePass()) {
            Toast.makeText(getActivity(), "Password belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateConfirm()) {
            Toast.makeText(getActivity(), "Password konfirmasi belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validateEmail()) {
            Toast.makeText(getActivity(), "E-mail belum diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        loading.setMessage("Signing up");
        loading.show();
        post(ApiReferences.getUrlRegister());


//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Signing Up");
//        progressDialog.show();
//
//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.dismiss();
//            }
//        }, 3000);

//        Log.i("click", "OK");
    }

    private void post(String url) {
        Map<String, String> params = mappingData();
        modelRegister = new ModelRegister();
        modelRegisterError = new ModelRegisterError();

        new VolleyRequest().request(getActivity(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
//                Log.i("json", response);
//                Log.i("char", response.substring(11, 12));
                if (response.substring(11, 12).equals("s")) {
                    try {
                        modelRegister = Parser.getRegister(response.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    loading.dismiss();
                    Toast.makeText(getActivity(), modelRegister.getResult(), Toast.LENGTH_SHORT).show();
                    signIn();
                } else {
                    try {
                        modelRegisterError = Parser.getRegisterError(response.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String space = "";
                    if (modelRegisterError.getItem().getShoutid_error().trim().equals("") || modelRegisterError.getItem().getEmail_error().trim().equals("")) {
                        space = "";
                    } else {
                        space = "\n";
                    }
                    loading.dismiss();
                    Toast.makeText(getActivity(), modelRegisterError.getItem().getShoutid_error() + space + modelRegisterError.getItem().getEmail_error(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void OnFailure() {
                loading.dismiss();
                Toast.makeText(getActivity(), "Pengiriman data gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {
        FragmentSignIn signIn = new FragmentSignIn();
        getFragmentManager().beginTransaction().replace(R.id.frame_content_sign, signIn).commit();
    }

    private Map<String, String> mappingData() {
        Map<String, String> params = new HashMap<>();
        params.put("shoutid", edtShoutId.getText().toString().trim());
        params.put("password", edtPassword.getText().toString().trim());
        params.put("email", edtEmail.getText().toString().trim());
        return params;
    }

    private void initView(View v) {
        edtShoutId = (EditText) v.findViewById(R.id.edt_signup_shoutid);
        edtPassword = (EditText) v.findViewById(R.id.edt_signup_pass);
        edtConfirmPassword = (EditText) v.findViewById(R.id.edt_signup_confirm);
        edtEmail = (EditText) v.findViewById(R.id.edt_signup_email);
        lytShoutId = (TextInputLayout) v.findViewById(R.id.lyt_signup_shoutid);
        lytPassword = (TextInputLayout) v.findViewById(R.id.lyt_signup_pass);
        lytConfirm = (TextInputLayout) v.findViewById(R.id.lyt_signup_confirm);
        lytEmail = (TextInputLayout) v.findViewById(R.id.lyt_signup_email);
        btnSignUp = (Button) v.findViewById(R.id.btn_signup_sign);
    }

    private boolean validateEmail() {
        if (edtEmail.getText().toString().trim().isEmpty()) {
            lytEmail.setError("Masukkan e-mail");
            edtEmail.requestFocus();
            return false;
        } else {
            lytEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateConfirm() {
        if (edtConfirmPassword.getText().toString().trim().isEmpty()) {
            lytConfirm.setError("Masukkan password konfirmasi");
            edtConfirmPassword.requestFocus();
            return false;
        } else {
            if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                lytConfirm.setError("Password tidak sama");
                return false;
            } else {
                lytConfirm.setErrorEnabled(false);
            }
            lytPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePass() {
        if (edtPassword.getText().toString().trim().isEmpty()) {
            lytPassword.setError("Masukkan password");
            edtPassword.requestFocus();
            return false;
        } else {
            if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                lytConfirm.setError("Password tidak sama");
                return false;
            } else {
                lytConfirm.setErrorEnabled(false);
            }
            lytPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateShoutId() {
        if (edtShoutId.getText().toString().trim().isEmpty()) {
            lytShoutId.setError("Masukkan Shout ID");
            edtShoutId.requestFocus();
            return false;
        } else {
            lytShoutId.setErrorEnabled(false);
        }
        return true;
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
                case R.id.edt_signup_shoutid:
                    validateShoutId();
                    break;
                case R.id.edt_signup_pass:
                    validatePass();
                    break;
                case R.id.edt_signup_confirm:
                    validateConfirm();
                    break;
                case R.id.edt_signup_email:
                    validateEmail();
                    break;
            }
        }
    }

}
