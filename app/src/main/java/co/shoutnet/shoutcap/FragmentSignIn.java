package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.ModelProfile;
import co.shoutnet.shoutcap.model.ModelSignIn;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Loading;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentSignIn extends Fragment {

    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    private Context mContext;
    private EditText edtShoutId;
    private EditText edtpassword;
    private Button btnSignIn;
    private TextView txtSignUp;
    private ModelSignIn modelSignIn = null;
    private ModelProfile modelProfile = null;
    private ProgressDialog loading;

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
                    Toast.makeText(mContext, "Shout ID dan Password belum diisi", Toast.LENGTH_SHORT).show();
                } else if (edtShoutId.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Shout ID belum diisi", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Password belum diisi", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentSignUp signUp = new FragmentSignUp();
                getFragmentManager().beginTransaction().replace(R.id.frame_content_sign, signUp).addToBackStack(null).commit();
            }
        });
        return rootView;
    }

    private void initView(View v) {
        edtShoutId = (EditText) v.findViewById(R.id.edt_signin_shoutid);
        edtpassword = (EditText) v.findViewById(R.id.edt_signin_pass);
        btnSignIn = (Button) v.findViewById(R.id.btn_signin_sign);
        txtSignUp = (TextView)v.findViewById(R.id.txt_signin_signup);
    }

    private void fetchData(final String shoutid, final String password) {
        sharedPreferences = mContext.getSharedPreferences("prefLogin", Context.MODE_PRIVATE);

        final StringRequest requestLogin = new StringRequest(Request.Method.POST, ApiReferences.getUrlLogin(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i("response sign in", response.toString());

                try {
                    modelSignIn = Parser.getReturnSignIn(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (modelSignIn.getResult().equals("success")) {
                    sessionManager.createLoginSession(modelSignIn.getShoutId(), modelSignIn.getSessionId(), modelSignIn.getPoint(), modelSignIn.getCoin(), modelSignIn.getUrlAvatar(), modelSignIn.getShoutcapQuota(),
                            modelSignIn.getScreamShirtQuota(), modelSignIn.getPictocapQuota());

                    loading.dismiss();
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(mContext, "Shout ID atau Password salah", Toast.LENGTH_SHORT).show();

                    btnSignIn.setEnabled(true);
                    loading.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("error sign in", error.toString());
                btnSignIn.setEnabled(true);
                loading.dismiss();
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

        RetryPolicy retryPolicy = new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        requestLogin.setRetryPolicy(retryPolicy);
        final RequestQueue queue = Volley.newRequestQueue(mContext);

        try {
            queue.add(requestLogin);
        } catch (Exception e) {
            Toast.makeText(mContext, "Koneksi gagal, coba lagi", Toast.LENGTH_LONG).show();
            edtShoutId.setEnabled(true);
            edtpassword.setEnabled(true);
            btnSignIn.setEnabled(true);
        }
    }

    private void login() {
        final String shoutid = edtShoutId.getText().toString();
        final String password = edtpassword.getText().toString();

//        Log.i("TAG", "Login Start");

        btnSignIn.setEnabled(false);

        loading = Loading.newInstance(mContext);
        loading.setMessage("Signing in");
        loading.setIndeterminate(true);
        loading.show();
        fetchData(shoutid, password);
    }
}
