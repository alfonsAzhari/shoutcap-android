package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class ActivityChangePassword extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private TextInputLayout layoutConfirm;
    private Button btnSave;
    private boolean match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initToolbar();
        initView();
        validasi();
    }

    private void validasi() {
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (match && oldPassword.length() > 0) {
                    btnSave.setEnabled(true);
                } else {
                    btnSave.setEnabled(false);
                }
            }
        });
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    layoutConfirm.setErrorEnabled(false);
                    match = true;
                } else {
                    layoutConfirm.setError("Password tidak sama");
                    match = false;
                }
                if (match && oldPassword.length() > 0) {
                    btnSave.setEnabled(true);
                } else {
                    btnSave.setEnabled(false);
                }
            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    layoutConfirm.setErrorEnabled(false);
                    match = true;
                } else {
                    layoutConfirm.setError("Password tidak sama");
                    match = false;
                }
                if (match && oldPassword.length() > 0) {
                    btnSave.setEnabled(true);
                } else {
                    btnSave.setEnabled(false);
                }
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_change_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
    }

    private void initView() {
        oldPassword = (EditText) findViewById(R.id.edt_change_old_pass);
        newPassword = (EditText) findViewById(R.id.edt_change_new_pass);
        confirmPassword = (EditText) findViewById(R.id.edt_change_new_pass_confirm);
        layoutConfirm = (TextInputLayout) findViewById(R.id.txtinputlayout_confirm_pass);
        btnSave = (Button) findViewById(R.id.btn_change_save);
        btnSave.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
