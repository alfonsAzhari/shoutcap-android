package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import co.shoutnet.shoutcap.model.ModelInboxDetail;
import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;

/**
 * Created by Adam MB on 9/10/2015.
 */
public class FragmentInboxDetail extends Fragment {

    private Context mContext;

    private static String ID_INBOX;

    private TextView title;
    private TextView date;
    private TextView content;

    private LinearLayout linProgres;

    SessionManager sessionManager;
    HashMap<String, String> user;

    public FragmentInboxDetail() {

    }

    public static FragmentInboxDetail newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(ID_INBOX, id);

        FragmentInboxDetail fragment = new FragmentInboxDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox_detail, container, false);

        mContext = getActivity();

        initView(rootView);

        sessionManager = new SessionManager(mContext);
        user = sessionManager.getUserDetails();

        Bundle bundle = getArguments();

        fetchData(ApiReferences.getUrlGetInboxDetail(), bundle.getString(ID_INBOX));

        return rootView;
    }

    private void initView(View v) {
        title = (TextView) v.findViewById(R.id.txt_inbox_detail_title);
        date = (TextView) v.findViewById(R.id.txt_inbox_detail_date);
        content = (TextView) v.findViewById(R.id.txt_inbox_detail_content);
        linProgres = (LinearLayout) v.findViewById(R.id.lin_inbox_detail_progress);
    }

    private void fetchData(String url, final String id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.i("result", response.toString());
                ModelInboxDetail inboxDetail = new ModelInboxDetail();
                try {
                    inboxDetail = Parser.getInboxDetail(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (inboxDetail.getResult().equals("success")) {
                    title.setText(inboxDetail.getItem().getTitle());
                    date.setText(inboxDetail.getItem().getDate());
                    content.setText(inboxDetail.getItem().getMessage());
                }

                linProgres.setVisibility(View.GONE);
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
                params.put("id", id);

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