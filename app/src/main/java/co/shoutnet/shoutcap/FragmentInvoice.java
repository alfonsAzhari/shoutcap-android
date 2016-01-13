package co.shoutnet.shoutcap;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.ModelInvoice;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;

public class FragmentInvoice extends Fragment {
    private Context mContext;
    private static String ID_ORDER = "id";
    private ImageView imageInvoice;
    private LinearLayout linProgress;
    SessionManager sessionManager;
    HashMap<String, String> user;

    public FragmentInvoice(){

    }

    public static FragmentInvoice newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id", id);

        FragmentInvoice fragment = new FragmentInvoice();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invoice, container, false);

        mContext = getActivity();

        initView(rootView);

        sessionManager = new SessionManager(mContext);
        user = sessionManager.getUserDetails();

        Bundle bundle = getArguments();
        Log.i("user", user.get("shoutid"));

//        fetchData(ApiReferences.getInvoice(), bundle.getString(ID_ORDER));

        return rootView;
    }

    private void fetchData(String url, final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("result", response.toString());
                ModelInvoice invoice = new ModelInvoice();
                try {
                    invoice = Parser.getInvoice(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (invoice.getResult().equals("success")) {
                    Glide.with(mContext).load(invoice.getUrlInvoice()).asBitmap().into(new BitmapImageViewTarget(imageInvoice));
                }

                linProgress.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Log.i("shoutid",user.get("shoutId"));
                Log.i("sessionid",user.get("sessionid"));
                params.put("shoutid", user.get("shoutId"));
                params.put("sessionid", user.get("sessionId"));
                params.put("id", ID_ORDER);

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


    private void initView(View v) {
        imageInvoice = (ImageView)v.findViewById(R.id.img_invoice_invoice);
        linProgress = (LinearLayout)v.findViewById(R.id.lin_invoice_progress);
    }
}