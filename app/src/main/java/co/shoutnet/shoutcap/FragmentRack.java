package co.shoutnet.shoutcap;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.RackAdapter;
import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.model.ModelAdapterRack;
import co.shoutnet.shoutcap.model.ModelRack;
import co.shoutnet.shoutcap.model.ModelSyncRack;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.DialogConfirm;
import co.shoutnet.shoutcap.utility.Loading;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.VolleyRequest;

/**
 * Created by Codelabs on 9/2/2015.
 */

public class FragmentRack extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    Target target;
    List<Integer> idRack;
    List<Integer> idRackServer;
    private SessionManager manager;
    private HashMap<String, String> user;
    private RackAdapter adapter;
    private DialogFragment dialogFragment;
    private ArrayList<ModelAdapterRack> racks;
    private DBCapsHelper dbCapsHelper;
    private ProgressDialog loading;

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

        setHasOptionsMenu(true);

        loading = Loading.newInstance(getActivity());
        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();

        dbCapsHelper = new DBCapsHelper(getActivity());
        racks = dbCapsHelper.getRackData();
        Log.i("racks", String.valueOf(racks.size()));
        if (racks.size() < 1) {
            //sync from server
            loading.setMessage("Checking data");
            loading.show();
            fetchUrlRack();
        } else {
//            Log.i("rack", "null");
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
                if (items.size() > 0) {
                    loading.dismiss();
                    loading.setMessage("Fetching data");
                    loading.show();

                    ModelAdapterRack adapterRack;
                    idRackServer = new ArrayList<>();
                    idRack = new DBCapsHelper(getActivity()).fetchingIDRack();
                    for (ModelSyncRack.Item itemId : items) {
                        Log.i("id", itemId.getId());
                        idRackServer.add(Integer.valueOf(itemId.getId()));
                    }

                    for (int i = 0; i < idRack.size(); i++) {
                        if (!idRackServer.contains(idRack.get(i))) {
                            Log.i("id", idRack.get(i) + " deleted");
                            racks.remove(i);
                            new DBCapsHelper(getActivity()).deleteData(idRack.get(i));
                            changeData('d', i);
                        }
                    }

                    for (ModelSyncRack.Item item : items) {
                        if (idRack.contains(Integer.valueOf(item.getId()))) {
                            Log.i("idrack", String.valueOf(idRack.get(0)));
                            Log.i("id", "isthere");
                        } else {
                            adapterRack = new ModelAdapterRack();
                            adapterRack.setId(item.getId());
                            adapterRack.setImgRack(MappingImage(item.getImage()));
                            addToDatabase(item, MappingImage(item.getImage()));
                            racks.add(adapterRack);
                        }
                    }

                    changeData('a', RecyclerView.NO_POSITION);
                } else {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "No rack data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnFailure() {

            }
        });
    }

    private void changeData(char a, int position) {
        if (a == 'd') {
            recyclerView.removeViewAt(position);
        }
        adapter.notifyDataSetChanged();
        loading.dismiss();
    }

    private void addToDatabase(ModelSyncRack.Item item, String base) {
        Log.i("process", "adding to db");
        CapsModel capsModel = new CapsModel();
        capsModel.setBaseImage(base);
        capsModel.setId(item.getId());
        capsModel.setStatus("rack");
        capsModel.setText(item.getShout());
        new DBCapsHelper(getActivity()).addCapSync(capsModel);
    }

    private String MappingImage(String url) {
        Log.i("process", "mapping");
        Bitmap bitmap = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            if (bitmap != null) {
                return convertBitmapToBase64(bitmap);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        Log.i("process", "converting");
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte[] byteBitmap = byteArray.toByteArray();
        String bitmapEncode = Base64.encodeToString(byteBitmap, Base64.DEFAULT);
        return bitmapEncode;
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
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_rack, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                loading.setMessage("Synchronizing");
                loading.show();
                fetchUrlRack();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
