package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class ActivityChangePassword extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText passwordLama;
    private EditText passwordBaru;
    private EditText passwordKonfirmasi;
    private TextInputLayout layoutKonfirmasi;
    private Button simpan;
    private boolean match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
        initToolbar();
        validasi();

    }

    private void validasi() {
        passwordLama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(match&&passwordLama.length()>0&&passwordBaru.length()>0&&passwordKonfirmasi.length()>0){
                    simpan.setEnabled(true);
                }else {
                    simpan.setEnabled(false);
                }
            }
        });
        passwordBaru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(passwordBaru.getText().toString().equals(passwordKonfirmasi.getText().toString())){
                    layoutKonfirmasi.setErrorEnabled(false);
                    match=true;
                } else {
                    layoutKonfirmasi.setError("Password tidak sama");
                    match=false;
                }
                if(match&&passwordLama.length()>0&&passwordBaru.length()>0&&passwordKonfirmasi.length()>0){
                    simpan.setEnabled(true);
                }else {
                    simpan.setEnabled(false);
                }
            }
        });
        passwordKonfirmasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(passwordBaru.getText().toString().equals(passwordKonfirmasi.getText().toString())){
                    layoutKonfirmasi.setErrorEnabled(false);
                    match=true;
                } else {
                    layoutKonfirmasi.setError("Password tidak sama");
                    match=false;
                }
                if(match&&passwordLama.length()>0&&passwordBaru.length()>0&&passwordKonfirmasi.length()>0){
                    simpan.setEnabled(true);
                }else {
                    simpan.setEnabled(false);
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar_change_password);
        passwordLama = (EditText)findViewById(R.id.edit_pass_lama_change_password);
        passwordBaru = (EditText)findViewById(R.id.edit_pass_baru_change_password);
        passwordKonfirmasi = (EditText)findViewById(R.id.edit_pass_konfirmasi_change_password);
        layoutKonfirmasi = (TextInputLayout)findViewById(R.id.layout_pass_konfirmasi_change_password);
        simpan = (Button)findViewById(R.id.button_simpan_change_password);
        simpan.setEnabled(false);
    }
}
