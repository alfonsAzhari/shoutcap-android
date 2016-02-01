package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * Created by Adam MB on 9/10/2015.
 */
public class FragmentInvoice extends Fragment {

    private Context mContext;

    private static String ID_ORDER;

    private ImageView imgInvoice;
    private Button confirmation;

    private LinearLayout linProgres;

    SessionManager sessionManager;
    HashMap<String, String> user;

    public FragmentInvoice() {

    }

    public static FragmentInvoice newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(ID_ORDER, id);

        FragmentInvoice fragment = new FragmentInvoice();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invoice, container, false);

        mContext = getActivity();

        initView(rootView);

        sessionManager = new SessionManager(mContext);

        user = sessionManager.getUserDetails();

        final Bundle bundle = getArguments();
//        Log.i("id invoice", user.get("shoutId"));
//        Log.i("session invoice", user.get("sessionId"));
//        Log.i("id order invoice", bundle.getString(ID_ORDER));

        fetchData(ApiReferences.getInvoice(), bundle.getString(ID_ORDER));

        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ActivityPaymentConfirmation.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        return rootView;
    }

    private void initView(View v) {
        imgInvoice = (ImageView)v.findViewById(R.id.img_invoice_invoice);
        linProgres = (LinearLayout) v.findViewById(R.id.lin_invoice_progress);
        confirmation = (Button)v.findViewById(R.id.button_payment_confirmation_invoice);
    }

    private void fetchData(String url, final String id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i("result", response.toString());
                ModelInvoice invoice = new ModelInvoice();
                try {
                    invoice = Parser.getInvoice(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (invoice.getResult().equals("success")) {
//                    Log.i("img", invoice.getItem());
                    imgInvoice.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(invoice.getItem()).into(imgInvoice);
                }

                linProgres.setVisibility(View.GONE);
                confirmation.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("shoutid", user.get("shoutId"));
                params.put("sessionid", user.get("sessionId"));
                params.put("idorder", id);

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

}