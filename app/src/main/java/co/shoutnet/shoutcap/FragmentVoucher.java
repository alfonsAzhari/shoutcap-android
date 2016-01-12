package co.shoutnet.shoutcap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.VoucherAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterVoucher;
import co.shoutnet.shoutcap.model.ModelVoucher;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;

public class FragmentVoucher extends Fragment {

    private Context mContext;

    private ArrayList<ModelAdapterVoucher> vouchers;
    private RecyclerView recyclerView;
    private ModelVoucher voucher=null;
    private VoucherAdapter adapter;

    SessionManager manager;
    HashMap<String, String> user;

    public FragmentVoucher() {

    }

    public static FragmentVoucher newInstance() {

        Bundle args = new Bundle();

        FragmentVoucher fragment = new FragmentVoucher();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voucher, container, false);
        mContext = getActivity();

        manager = new SessionManager(mContext);
        user = manager.getUserDetails();

        initView(rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);

        fetchData(ApiReferences.getUrlGetVoucher());

        return rootView;
    }

    private void fetchData(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("json", response.toString());

                try {
                    voucher = Parser.getVoucher(response.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("result", voucher.getResult());
                if (voucher.getResult().equals("success")) {
                    vouchers = new ArrayList<>();
                    for (ModelVoucher.Item item : voucher.getItem()) {
                        Log.i("masuk", "OK");
                        Log.i("voucher code", item.getVoucherCode());
                        Log.i("discount", item.getDiscount());
                        Log.i("discount to", item.getDiscountTo());
                        Log.i("expire", item.getExpire());
                        Log.i("order", item.getUseAtOrder());
                        Log.i("status", item.getStatus());
                        vouchers.add(new ModelAdapterVoucher(item.getVoucherCode(), item.getDiscount(), item.getDiscountTo(), item.getExpire(), item.getUseAtOrder(), item.getStatus()));
                    }

                    adapter = new VoucherAdapter(mContext, vouchers);
                    recyclerView.setAdapter(adapter);

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
                params.put("shoutid", user.get("shoutId"));
                params.put("sessionid", user.get("sessionId"));

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
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_voucher);
    }
}
