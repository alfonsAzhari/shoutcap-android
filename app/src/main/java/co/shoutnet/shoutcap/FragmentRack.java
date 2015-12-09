package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.RackAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterRack;
import co.shoutnet.shoutcap.utility.DBCapsHelper;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentRack extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;

    public FragmentRack() {

    }

    public static FragmentRack newInstance() {

        Bundle args = new Bundle();

        FragmentRack fragment = new FragmentRack();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rack, container, false);


        DBCapsHelper dbCapsHelper = new DBCapsHelper(getActivity());
        ArrayList<ModelAdapterRack> racks = dbCapsHelper.getRackData();
        if (racks.size() > 0) {
            Log.i("rack", String.valueOf(racks.get(0).getImgRack()));
        } else {
            Log.i("rack", "null");
        }
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        RackAdapter adapter = new RackAdapter(getActivity(), racks, new RackAdapter.RackListener() {
            @Override
            public void testing(String id) {
                Log.i("rack", id);
                AddToCart("https://api.shoutnet.co/shoutcap/add_to_cart_from_rack.php", id);
                new DBCapsHelper(getActivity()).updateStatus("both", id);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_rack);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void AddToCart(String url, final String id) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("json", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("shoutid", "hanswdd");
                params.put("sessionid", "71a12b569a717c8a582e929ac5a8da49");
                params.put("from", "app");
                params.put("id_racl", id);
                return params;
            }
        };
    }
}
