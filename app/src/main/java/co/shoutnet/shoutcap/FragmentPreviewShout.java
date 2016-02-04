package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.model.ModelCart;
import co.shoutnet.shoutcap.model.ModelRack;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.InternetConnection;
import co.shoutnet.shoutcap.utility.Loading;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.VolleyRequest;

/**
 * Created by Alfons on 29/8/2015.
 */
public class FragmentPreviewShout extends Fragment {

    private static CapsModel capsModel;
    private ImageView imgPreview;
    private DBCapsHelper dbCapsHelper;
    private Button btnAddRack;
    private Button btnAddCart;
    private Button btnSignIn;
    private LinearLayout layoutBtnAdd;
    private Uri uri;
    private boolean both=false;
    private Map<String, String> params;
    private ProgressDialog loading;
    private HashMap<String, String> user;
    private SessionManager manager;

    public FragmentPreviewShout() {

    }

    public static FragmentPreviewShout newInstance(String name, int idModel, String size, String font, int color, int fontSize, String text, int line, String image) {

        capsModel = new CapsModel(name, text, idModel, size, font, color, fontSize, line, 0, image, "rack");
        FragmentPreviewShout fragment = new FragmentPreviewShout();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preview_shout, container, false);
        initView(rootView);
        getActivity().setTitle("Preview ShoutCap");
        dbCapsHelper=new DBCapsHelper(getActivity());

        loading = Loading.newInstance(getActivity());
        manager = new SessionManager(getActivity());
        user = manager.getUserDetails();

        checkUserSign();

        byte[] decodeImage = Base64.decode(capsModel.getBaseImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);

        try {
            imgPreview.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (new InternetConnection().IsConnected(getActivity())) {
            Log.i("conn", "connected");
        } else {
            Log.i("conn", "disconnect");
            btnAddCart.setEnabled(false);
            btnAddRack.setEnabled(false);
            Toast.makeText(getActivity(), "Pastikan smartphone anda terkoneksi dengan internet", Toast.LENGTH_SHORT).show();
        }

        btnAddRack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToRack();
                btnAddRack.setEnabled(false);
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
                btnAddCart.setEnabled(false);
            }
        });

        return rootView;
    }

    private void checkUserSign() {
        if (manager.isLoggedIn()) {
            btnSignIn.setVisibility(View.GONE);
            layoutBtnAdd.setVisibility(View.VISIBLE);
        } else {

        }
    }

    private void addToCart() {
        String url = "https://api.shoutnet.co/shoutcap/add_to_cart.php";
        params = mapping(capsModel);
        loading.setMessage("Adding to cart");
        loading.show();
//        new AddCaps().sendData("https://api.shoutnet.co/shoutcap/add_to_cart.php", capsModel, new CapsResult() {
//            @Override
//            public void OnSuccess(String response) {
//                ModelCart modelCart = null;
//                try {
//                    modelCart = Parser.getCartResponse(response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                ModelCart.Item item = modelCart.getItem().get(0);
//                capsModel.setPrice(Integer.parseInt(item.getPrice()));
//                capsModel.setId(item.getId());
//                if (!both) {
//                    both = true;
//                    capsModel.setStatus("cart");
//                    dbCapsHelper.addCap(capsModel);
//                } else {
//                    dbCapsHelper.updateStatus("both", item.getId());
//                }
//            }
//
//            @Override
//            public void OnFailure(String message) {
//                btnAddCart.setEnabled(true);
//                Toast.makeText(getActivity(), "Add to cart failed", Toast.LENGTH_SHORT).show();
//            }
//        });
        new VolleyRequest().request(getActivity(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                ModelCart modelCart = null;
                try {
                    modelCart = Parser.getCartResponse(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ModelCart.Item item = modelCart.getItem().get(0);
                capsModel.setPrice(Integer.parseInt(item.getPrice()));
                capsModel.setId(item.getId());
//                if (!both) {
//                    both = true;
                    capsModel.setStatus("cart");
                    dbCapsHelper.addCap(capsModel);
//                } else {
//                    dbCapsHelper.updateStatus("both", item.getId());
//                }
                loading.dismiss();
            }

            @Override
            public void OnFailure() {
                loading.dismiss();
            }
        });
//        String url=null;
//        Map<String,String> params=new HashMap<>();
//        sendData(url,params);
    }

    private void addToRack() {
        String url = "https://api.shoutnet.co/shoutcap/add_to_rack.php";
        params = mapping(capsModel);
        loading.setMessage("Adding to rack");
        loading.show();
//        new AddCaps().sendData("https://api.shoutnet.co/shoutcap/add_to_cart.php", capsModel, new CapsResult() {
//            @Override
//            public void OnSuccess(String response) {
//                ModelRack modelRack = null;
//                try {
//                    modelRack = Parser.getRackResponse(response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                ModelRack.Item item = modelRack.getItem().get(0);
//                capsModel.setPrice(Integer.parseInt(item.getPrice()));
//                capsModel.setId(item.getId());
//                if (!both) {
//                    both = true;
//                    capsModel.setStatus("rack");
//                    dbCapsHelper.addCap(capsModel);
//                } else {
//                    dbCapsHelper.updateStatus("both", item.getId());
//                }
//            }
//
//            @Override
//            public void OnFailure(String message) {
//
//            }
//        });
        new VolleyRequest().request(getActivity(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {

                String[] split = response.split("\"");
                if (split[3].equals("success")) {
                    ModelRack modelRack = null;
                    try {
                        modelRack = Parser.getRackResponse(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ModelRack.Item item = modelRack.getItem();
                    capsModel.setId(item.getId_rack());
//                if (!both) {
//                    both = true;
                    capsModel.setStatus("rack");
                    dbCapsHelper.addCap(capsModel);
//                } else {
//                    dbCapsHelper.updateStatus("both", item.getId());
//                }
                    loading.dismiss();
                } else if (split[3].equals("error")) {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Isi rack penuh! maksimum 6 desain", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnFailure() {
                loading.dismiss();
            }
        });
//        String url=null;
//        Map<String,String> params=new HashMap<>();
//        sendData(url,params);
    }

    private Map<String, String> mapping(CapsModel capsModel) {
        params = new HashMap<>();
        params.put("shoutid", user.get("shoutId"));
        params.put("sessionid", user.get("sessionId"));
//        params.put("shoutid", "devtest");
//        params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
        params.put("from", "app");
        params.put("id_model", String.valueOf(capsModel.getModel()));
        params.put("size", capsModel.getSize());
        params.put("font_type", capsModel.getFont());
        params.put("id_font_color", String.valueOf(capsModel.getColor()));
        params.put("font_size", String.valueOf(capsModel.getFontsize()));
        params.put("shout", capsModel.getText());
        params.put("jum_baris", String.valueOf(capsModel.getLine()));
        params.put("shout_caption", "n");
        params.put("caption", "n");
        params.put("mirror", "n");
        params.put("flip", "n");
        params.put("image", capsModel.getBaseImage());
        return params;
    }

//    private Uri saveImageToStorage(String image, String name) {
//
//        String fileName = "/" + name + ".png";
//        Uri uri = null;
//        byte[] decoded = Base64.decode(image, Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
//
//        if (bitmap != null) {
//            File file = new File(Environment.getExternalStorageDirectory() + "/shoutcap");
//            if (!file.isDirectory()) {
//                file.mkdir();
//            }
//            uri = Uri.parse(file.toURI() + fileName);
//            FileOutputStream out;
//            try {
//                out = new FileOutputStream(file.getAbsolutePath() + fileName.toString());
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                out.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
////        dbCapsHelper.updateUri(id,uri.toString());
//        return uri;
//    }

    private void initView(View v) {
        imgPreview = (ImageView) v.findViewById(R.id.img_preview_hat);
        btnAddCart = (Button) v.findViewById(R.id.btn_addcart_preview);
        btnAddRack = (Button) v.findViewById(R.id.btn_addrack_preview);
        btnSignIn = (Button) v.findViewById(R.id.btn_signin_preview);
        layoutBtnAdd = (LinearLayout) v.findViewById(R.id.lin_btnadd_preview);
    }

//    public interface CapsResult {
//        void OnSuccess(String response);
//
//        void OnFailure(String message);
//    }
//
//    private class AddCaps {
//        public void sendData(String url, final CapsModel capsModel, final CapsResult capsResult) {
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("response", response);
//                    capsResult.OnSuccess(response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.i("error", error.getMessage());
//                    capsResult.OnFailure(error.getMessage());
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("shoutid", "devtest");
//                    params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
//                    params.put("from", "app");
//                    params.put("id_model", String.valueOf(capsModel.getModel()));
//                    params.put("size", capsModel.getSize());
//                    params.put("font_type", capsModel.getFont());
//                    params.put("id_font_color", String.valueOf(capsModel.getColor()));
//                    params.put("font_size", String.valueOf(capsModel.getFontsize()));
//                    params.put("shout", capsModel.getText());
//                    params.put("jum_baris", String.valueOf(capsModel.getLine()));
//                    params.put("shout_caption", "n");
//                    params.put("caption", "n");
//                    params.put("mirror", "n");
//                    params.put("flip", "n");
//                    params.put("image", capsModel.getBaseImage());
//                    return params;
//                }
//
////                @Override
////                public Map<String, String> getHeaders() throws AuthFailureError {
////                    Map<String,String> params=new HashMap<>();
////                    params.put("Content-Type", "application/x-www-form-urlencoded");
////                    return params;
////                }
//            };
//
//            RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//            stringRequest.setRetryPolicy(retryPolicy);
//            RequestQueue queue = Volley.newRequestQueue(getActivity());
//            queue.add(stringRequest);
//        }
//    }
}