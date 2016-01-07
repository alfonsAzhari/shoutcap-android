package co.shoutnet.shoutcap;

import android.app.DownloadManager;
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

import co.shoutnet.shoutcap.model.ModelSignIn;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;

/**
 * Created by Adam MB on 1/7/2016.
 */
public class FragmentSignIn extends Fragment {
    private Context mContext;

    private EditText edtShoutId;
    private EditText edtpassword;
    private Button btnSignIn;

    private ModelSignIn modelSignIn;

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
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        StringRequest stringRequest = new StringRequest(DownloadManager.Request.Method.POST, ApiReferences.getUrlLogin(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response volley", response.toString());

                try {
                    modelSignIn = Parser.getReturnSignIn(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (modelSignIn.getResult().equals("success")) {

                    sessionManager.createLoginSession(modelSignIn.getShoutId(), modelSignIn.getSessionId(), modelSignIn.getPoint(), modelSignIn.getCoin(), modelSignIn.getUrlAvatar(), modelSignIn.getShoutcapQuota(), modelSignIn.getScreamShirtQuota(), modelSignIn.getPictocapQuota());

                    Intent i = new Intent(mContext, MainActivity.class);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    Toast.makeText(mContext, "Your ShoutId or Password are wrong", Toast.LENGTH_SHORT).show();

                    btnSignIn.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error volley", error.toString());
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

        RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    private void getProfile(String url) {

    }

    private void login() {
        final String shoutid = edtShoutId.getText().toString();
        final String password = edtpassword.getText().toString();

        Log.i("TAG", "Login Start");

        btnSignIn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing In");
        progressDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData(shoutid, password);
                progressDialog.dismiss();
            }
        }, 3000);
    }
}