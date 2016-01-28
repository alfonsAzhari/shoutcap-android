package co.shoutnet.shoutcap;

import android.app.DialogFragment;
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
import co.shoutnet.shoutcap.model.ModelSyncRack;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.DialogConfirm;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.VolleyRequest;

/**
 * Created by Codelabs on 9/2/2015.
 */
public class FragmentRack extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    private SessionManager manager;
    private HashMap<String, String> user;
    private RackAdapter adapter;
    private DialogFragment dialogFragment;
    private ArrayList<ModelAdapterRack> racks;
    private DBCapsHelper dbCapsHelper;

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

        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();

        dbCapsHelper = new DBCapsHelper(getActivity());
        racks = dbCapsHelper.getRackData();
        if (racks.size() > 0) {
            //sync from server
            fetchUrlRack();
        } else {
            Log.i("rack", "null");
        }
        layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        adapter = new RackAdapter(getActivity(), racks, new RackAdapter.RackListener() {
            @Override
            public void linstener(final String id, final char act, final int position) {
                switch (act) {
                    case 'a': {
                        act("https://api.shoutnet.co/shoutcap/add_to_cart_from_rack.php", id, act, position);
                        break;
                    }
                    case 'd': {
                        dialogFragment = DialogConfirm.newInstance(new DialogConfirm.ConfirmDialogListener() {
                            @Override
                            public void onYesAction() {
                                dialogFragment.dismiss();
                                act("https://api.shoutnet.co/shoutcap/delete_rack.php", id, act, position);
                            }

                            @Override
                            public void onNoAction() {
                                dialogFragment.dismiss();
                            }
                        });
                        dialogFragment.show(getFragmentManager(), null);
                        break;
                    }
                }
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_rack);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void fetchUrlRack() {
        Map<String, String> params = new HashMap<>();
        String url = "https://api.shoutnet.co/shoutcap/get_rack.php";
        params.put("shoutid", user.get("shoutId"));
        params.put("sessionid", user.get("sessionId"));

        new VolleyRequest().request(getActivity(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                ModelSyncRack syncRack = new ModelSyncRack();
                try {
                    syncRack = Parser.getSyncRack(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ArrayList<ModelSyncRack.Item> items = syncRack.getItem();
                MappingImage(items);
            }

            @Override
            public void OnFailure() {

            }
        });
    }

    private void MappingImage(ArrayList<ModelSyncRack.Item> items) {

    }

    private void act(String url, final String id, final char act, final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("shoutid", user.get("shoutId"));
        params.put("sessionid", user.get("sessionId"));
        params.put("from", "app");
        params.put("id_rack", id);

        new VolleyRequest().request(getActivity(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                if (act == 'a') {

                    ModelRack modelRack = new ModelRack();
                    try {
                        modelRack = Parser.getRackResponse(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ModelRack.Item items = modelRack.getItem();
                    CapsModel capsModel;
                    capsModel = new DBCapsHelper(getActivity()).getRack(Integer.parseInt(id));
                    capsModel.setId(items.getId_rack());
                    new DBCapsHelper(getActivity()).addCap(capsModel);
                } else if (act == 'd') {
                    new DBCapsHelper(getActivity()).deleteData(Integer.parseInt(id));
                    racks.remove(position);
                    adapter.notifyItemRemoved(position);
                    recyclerView.removeViewAt(position);
                }
            }

            @Override
            public void OnFailure() {

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
