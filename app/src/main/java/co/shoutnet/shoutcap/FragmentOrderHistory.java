package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.OrderHistoryAdapter;
import co.shoutnet.shoutcap.adapter.VoucherAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterOrderHistory;
import co.shoutnet.shoutcap.model.ModelAdapterVoucher;
import co.shoutnet.shoutcap.model.ModelOrderHistory;
import co.shoutnet.shoutcap.model.ModelVoucher;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.RecyclerItemClickListener;
import co.shoutnet.shoutcap.utility.SessionManager;

public class FragmentOrderHistory extends Fragment {

    private Context mContext;
    private ArrayList<ModelAdapterOrderHistory> orderHistories;
    private RecyclerView recyclerView;
    private ModelOrderHistory orderHistory = null;
    private OrderHistoryAdapter adapter;
    private LinearLayout linProgress;
    private SessionManager manager;
    private HashMap<String, String> user;

    public FragmentOrderHistory() {

    }

    public static FragmentOrderHistory newInstance() {

        Bundle args = new Bundle();

        FragmentOrderHistory fragment = new FragmentOrderHistory();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_history, container, false);
        mContext = getActivity();

        manager = new SessionManager(mContext);
        user = manager.getUserDetails();

        initView(rootView);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(itemClick);

        fetchData(ApiReferences.getUrlOrderHistory());

        return rootView;
    }

    private void fetchData(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("json", response.toString());

                try {
                    orderHistory = Parser.getOrderHistory(response.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("result", orderHistory.getResult());
                if (orderHistory.getResult().equals("success")) {
                    orderHistories = new ArrayList<>();
                    for (ModelOrderHistory.Item item : orderHistory.getItem()) {
                        orderHistories.add(new ModelAdapterOrderHistory(item.getId_order(), item.getOrder_date(), item.getDue_date(), item.getPrice(), item.getPayment_status(), item.getProduction_status(), item.getE_connote()));
                    }

                    adapter = new OrderHistoryAdapter(mContext, orderHistories);
                    recyclerView.setAdapter(adapter);
                    linProgress.setVisibility(View.GONE);

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

    private RecyclerItemClickListener itemClick = new RecyclerItemClickListener(mContext, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);


            FragmentInvoice invoice = FragmentInvoice.newInstance(orderHistory.getItem().get(position).getId_order());

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.frame_content_main, invoice).commit();
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    });

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_order_history);
        linProgress = (LinearLayout) v.findViewById(R.id.lin_order_history_progress);
    }
}
