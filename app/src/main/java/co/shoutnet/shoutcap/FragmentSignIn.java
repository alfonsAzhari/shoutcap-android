package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import co.shoutnet.shoutcap.model.ModelProfile;
import co.shoutnet.shoutcap.model.ModelSignIn;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentSignIn extends Fragment {

    private Context mContext;

    private EditText edtShoutId;
    private EditText edtpassword;
    private Button btnSignIn;

    private ModelSignIn modelSignIn = null;
    private ModelProfile modelProfile = null;
    private ProgressDialog progressDialog;

    SharedPreferences sharedPreferences;
    SessionManager sessionManager;

    public FragmentSignIn() {

    }

    public static FragmentSignIn newInstance() {

        Bundle args = new Bundle();

        FragmentSignIn fragment = new FragmentSignIn();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        mContext = getActivity();

        sessionManager = new SessionManager(mContext);

        initView(rootView);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtShoutId.getText().toString().equals("") && edtpassword.getText().toString().equals("")) {
                    Toast.makeText(mContext, "ShoutID and Password are empty", Toast.LENGTH_SHORT).show();
                } else if (edtShoutId.getText().toString().equals("")) {
                    Toast.makeText(mContext, "ShoutID is empty", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Password is empty", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });

        return rootView;
    }

    private void initView(View v) {
        edtShoutId = (EditText) v.findViewById(R.id.edt_signin_shoutid);
        edtpassword = (EditText) v.findViewById(R.id.edt_signin_pass);
        btnSignIn = (Button) v.findViewById(R.id.btn_signin_sign);
    }

    private void fetchData(final String shoutid, final String password) {
        sharedPreferences = mContext.getSharedPreferences("prefLogin", Context.MODE_PRIVATE);

        final StringRequest requestLogin = new StringRequest(Request.Method.POST, ApiReferences.getUrlLogin(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response sign in", response.toString());

                try {
                    modelSignIn = Parser.getReturnSignIn(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (modelSignIn.getResult().equals("success")) {
                    sessionManager.createLoginSession(modelSignIn.getShoutId(), modelSignIn.getSessionId(), modelSignIn.getPoint(), modelSignIn.getCoin(), modelSignIn.getUrlAvatar(), modelSignIn.getShoutcapQuota(),
                            modelSignIn.getScreamShirtQuota(), modelSignIn.getPictocapQuota());
                } else {
                    Toast.makeText(mContext, "Your ShoutId or Password are wrong", Toast.LENGTH_SHORT).show();

                    btnSignIn.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error sign in", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("shoutid", shoutid);
                params.put("password", password);
                params.put("from", "app");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        final StringRequest requestProfile = new StringRequest(Request.Method.POST, ApiReferences.getUrlProfile(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response profile", response.toString());

                try {
                    modelProfile = Parser.getProfile(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (modelProfile.getResult().equals("success")) {
                    sessionManager.addProfile(modelProfile.getItem().getNama(), modelProfile.getItem().getEmail(), modelProfile.getItem().getHp(),
                            modelProfile.getItem().getGender(), modelProfile.getItem().getAlamat(), modelProfile.getItem().getKecamatan(), modelProfile.getItem().getKota(), modelProfile.getItem().getProvinsi(),
                            modelProfile.getItem().getKodePos(), modelProfile.getItem().getTglLahir(), modelProfile.getItem().getMinat(), modelProfile.getItem().getStatusKerja());

                    progressDialog.dismiss();

                    Intent i = new Intent(mContext, MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error profile", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("shoutid", shoutid);
                params.put("sessionid", modelSignIn.getSessionId());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RetryPolicy retryPolicy = new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        requestLogin.setRetryPolicy(retryPolicy);
        final RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(requestLogin);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(requestProfile);
            }
        }, (retryPolicy.getCurrentRetryCount() + 1) * retryPolicy.getCurrentTimeout());
    }

    private void login() {
        final String shoutid = edtShoutId.getText().toString();
        final String password = edtpassword.getText().toString();

        Log.i("TAG", "Login Start");

        btnSignIn.setEnabled(false);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing In");
        progressDialog.show();
        fetchData(shoutid, password);
    }
}
