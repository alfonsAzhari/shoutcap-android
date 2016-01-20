package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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

import co.shoutnet.shoutcap.adapter.RewardAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterReward;
import co.shoutnet.shoutcap.model.ModelHistoryReward;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.SimpleDividerItemDecoration;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentReward extends Fragment {

    private Context mContext;

    private RecyclerView recyclerView;
    private RewardAdapter adapter;
    private ArrayList<ModelAdapterReward> rewards;
    private ModelHistoryReward model = null;

    private LinearLayout linProgress;

    SessionManager manager;
    HashMap<String, String> user;

    public FragmentReward() {

    }

    public static FragmentReward newInstance() {

        Bundle args = new Bundle();

        FragmentReward fragment = new FragmentReward();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reward, container, false);

        mContext = getActivity();

        manager = new SessionManager(mContext);
        user = manager.getUserDetails();

        initView(rootView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mContext));

        fetchData(ApiReferences.getUrlGetRewardHistory());

        return rootView;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_reward);
        linProgress = (LinearLayout) v.findViewById(R.id.lin_reward_progress);
    }

    private void fetchData(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("json", response.toString());

                try {
                    model = Parser.getRewardHistory(response.toString());
                } catch (IOException e) {
                    Log.e("IOException", e.toString());
                }

                Log.i("result", model.getResult());

                if (model.getResult().equals("success")) {
                    rewards = new ArrayList<>();

                    for (ModelHistoryReward.Item item : model.getItem()) {
                        Log.i("bobot coin", item.getBobotCoin());
                        Log.i("coin", item.getJumlahCoin());
                        Log.i("bobot poin", item.getBobotPoint());
                        Log.i("poin", item.getJumlahPoint());

                        rewards.add(new ModelAdapterReward(item.getHistory(), item.getDate(), item.getBobotCoin() + item.getJumlahCoin(), item.getBobotPoint() + item.getJumlahPoint()));
                    }

                    adapter = new RewardAdapter(mContext, rewards);
                    recyclerView.setAdapter(adapter);
                    linProgress.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
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
}