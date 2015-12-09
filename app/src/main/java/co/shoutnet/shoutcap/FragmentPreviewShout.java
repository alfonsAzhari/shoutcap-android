package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.model.ModelCart;
import co.shoutnet.shoutcap.model.ModelRack;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.InternetConnection;
import co.shoutnet.shoutcap.utility.Parser;

/**
 * Created by Alfons on 29/8/2015.
 */
public class FragmentPreviewShout extends Fragment {

    private static CapsModel capsModel;
    private ImageView imgPreview;
    private DBCapsHelper dbCapsHelper;
    private Button btnAddRack;
    private Button btnAddCart;
    private Uri uri;
    private boolean both=false;

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

    private void addToCart() {
        new AddCaps().sendData("https://api.shoutnet.co/shoutcap/add_to_cart.php", capsModel, new CapsResult() {
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
                if (!both) {
                    both = true;
                    capsModel.setStatus("cart");
                    dbCapsHelper.addCap(capsModel);
                } else {
                    dbCapsHelper.updateStatus("both", item.getId());
                }
            }

            @Override
            public void OnFailure(String message) {
                btnAddCart.setEnabled(true);
                Toast.makeText(getActivity(), "Add to cart failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToRack() {
        new AddCaps().sendData("https://api.shoutnet.co/shoutcap/add_to_cart.php", capsModel, new CapsResult() {
            @Override
            public void OnSuccess(String response) {
                ModelRack modelRack = null;
                try {
                    modelRack = Parser.getRackResponse(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ModelRack.Item item = modelRack.getItem().get(0);
                capsModel.setPrice(Integer.parseInt(item.getPrice()));
                capsModel.setId(item.getId());
                if (!both) {
                    both = true;
                    capsModel.setStatus("rack");
                    dbCapsHelper.addCap(capsModel);
                } else {
                    dbCapsHelper.updateStatus("both", item.getId());
                }
            }

            @Override
            public void OnFailure(String message) {

            }
        });
    }

    private Uri saveImageToStorage(String image, String name) {

        String fileName = "/" + name + ".png";
        Uri uri = null;
        byte[] decoded = Base64.decode(image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);

        if (bitmap != null) {
            File file = new File(Environment.getExternalStorageDirectory() + "/shoutcap");
            if (!file.isDirectory()) {
                file.mkdir();
            }
            uri = Uri.parse(file.toURI() + fileName);
            FileOutputStream out;
            try {
                out = new FileOutputStream(file.getAbsolutePath() + fileName.toString());
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        dbCapsHelper.updateUri(id,uri.toString());
        return uri;
    }

    private void initView(View v) {
        imgPreview = (ImageView) v.findViewById(R.id.img_preview_hat);
        btnAddCart = (Button) v.findViewById(R.id.btn_addcart_preview);
        btnAddRack = (Button) v.findViewById(R.id.btn_addrack_preview);
    }

    public interface CapsResult {
        void OnSuccess(String response);

        void OnFailure(String message);
    }

    private class AddCaps {
        public void sendData(String url, final CapsModel capsModel, final CapsResult capsResult) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("response", response);
                    capsResult.OnSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error", error.getMessage());
                    capsResult.OnFailure(error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("shoutid", "hanswdd");
                    params.put("sessionid", "71a12b569a717c8a582e929ac5a8da49");
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

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String,String> params=new HashMap<>();
//                    params.put("Content-Type", "application/x-www-form-urlencoded");
//                    return params;
//                }
            };

            RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(stringRequest);
        }
    }
}