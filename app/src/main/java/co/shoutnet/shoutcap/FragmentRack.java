package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.RackAdapter;
import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.model.ModelAdapterRack;
import co.shoutnet.shoutcap.model.ModelRack;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.VolleyRequest;

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
            public void testing(String id, char act) {
                Log.i("rack", id);
                switch (act) {
                    case 'a': {
                        act("https://api.shoutnet.co/shoutcap/add_to_cart_from_rack.php", id, act);
                        break;
                    }
                    case 'd': {
                        act("https://api.shoutnet.co/shoutcap/delete_rack.php", id, act);
                        new DBCapsHelper(getActivity()).deleteData(Integer.parseInt(id));
                        break;
                    }
                }
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_rack);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void act(String url, final String id, final char act) {
        Map<String, String> params = new HashMap<>();
        params.put("shoutid", "devtest");
        params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
        params.put("from", "app");
        params.put("id_rack", id);

        new VolleyRequest().request(getActivity(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                Log.i("json", response);
                if (act == 'a') {

                    ModelRack modelRack = new ModelRack();
                    try {
                        modelRack = Parser.getRackResponse(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ArrayList<ModelRack.Item> items = modelRack.getItem();
                    CapsModel capsModel;
                    capsModel = new DBCapsHelper(getActivity()).getRack(Integer.parseInt(id));
                    capsModel.setId(items.get(0).getId());
                    capsModel.setPrice(Integer.parseInt(items.get(0).getPrice()));
                    new DBCapsHelper(getActivity()).addCap(capsModel);
                }
            }

            @Override
            public void OnFaliure() {

            }
        });
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.i("json", response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("shoutid", "hanswdd");
//                params.put("sessionid", "71a12b569a717c8a582e929ac5a8da49");
//                params.put("from", "app");
//                params.put("id_racl", id);
//                return params;
//            }
//        };
    }
}
