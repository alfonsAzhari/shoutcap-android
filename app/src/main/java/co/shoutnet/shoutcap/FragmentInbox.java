package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.InboxAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterInbox;
import co.shoutnet.shoutcap.model.ModelInbox;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.RecyclerItemClickListener;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.SimpleDividerItemDecoration;

/**
 * Created by Codelabs on 8/26/2015.
 */
public class FragmentInbox extends Fragment {

    private Context mContext;

    private RecyclerView recyclerView;
    private InboxAdapter adapter;
    private ArrayList<ModelAdapterInbox> inboxes;
    private ModelInbox inbox = null;

    private LinearLayout linProgress;

    SessionManager manager;
    HashMap<String, String> user;

    public FragmentInbox() {

    }

    public static FragmentInbox newInstance() {

        Bundle args = new Bundle();

        FragmentInbox fragment = new FragmentInbox();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);

        mContext = getActivity();

        manager = new SessionManager(mContext);
        user = manager.getUserDetails();

        initView(rootView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        recyclerView.addOnItemTouchListener(itemClick);

        fetchData(ApiReferences.getUrlGetInbox());

        //inboxes = Dummy.getDataInbox();
        //adapter = new InboxAdapter(getActivity(), inboxes);

        //recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_inbox);
        linProgress = (LinearLayout) v.findViewById(R.id.lin_inbox_progress);
    }

    private void fetchData(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("json", response.toString());

                try {
                    inbox = Parser.getInbox(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("result", inbox.getResult());
                if (inbox.getResult().equals("success")) {
                    inboxes = new ArrayList<>();
                    for (ModelInbox.Item item : inbox.getItem()) {
                        inboxes.add(new ModelAdapterInbox(item.getTitle(), item.getDate(), item.getMessage()));
                    }

                    adapter = new InboxAdapter(mContext, inboxes);
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

            FragmentInboxDetail inboxDetail = FragmentInboxDetail.newInstance(inbox.getItem().get(position).getId());

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.frame_content_main, inboxDetail).commit();
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    });
}
