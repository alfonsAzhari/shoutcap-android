package co.shoutnet.shoutcap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.SessionManager;

public class ActivityChangePassword extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private TextInputLayout layoutConfirm;
    private Button btnSave;
    private boolean match;

    private Context mContext;

    private HashMap<String, String> user;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mContext = getApplicationContext();

        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();

        initToolbar();
        initView();
        validasi();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(ApiReferences.getUrlChangePass(), oldPassword.getText().toString(), newPassword.getText().toString());
            }
        });
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
                if (match && oldPassword.length() > 0 && newPassword.length() > 0 && confirmPassword.length()>0) {
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
                if (match && oldPassword.length() > 0 && newPassword.length() > 0 && confirmPassword.length()>0) {
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
                if (match && oldPassword.length() > 0 && newPassword.length() > 0 && confirmPassword.length()>0) {
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

    private void changePassword(String url, final String oldPass, final String newPass) {

        final ProgressDialog dialog = new ProgressDialog(ActivityChangePassword.this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response.toString());
                    if (object.getString("result").equals("success")) {
                        dialog.dismiss();
                        Toast.makeText(mContext, "Password Changed", Toast.LENGTH_SHORT).show();
                        ActivityChangePassword.this.finish();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(mContext, "Old Password you entered is invalid", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    Toast.makeText(mContext, "Connection Lost, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e("voleyChangePass", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("shoutid", user.get(SessionManager.KEY_SHOUTID));
                params.put("sessionid", user.get(SessionManager.KEY_SESSIONID));
                params.put("new_password", newPass);
                params.put("old_password", oldPass);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RetryPolicy policy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}