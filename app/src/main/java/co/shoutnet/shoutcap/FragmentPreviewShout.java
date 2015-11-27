package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.utility.DBCapsHelper;

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
        //check connection
        //post data
        //get price
        //save price

        if (uri == null) {
            uri = saveImageToStorage(capsModel.getBaseImage(), capsModel.getName());
        }
        capsModel.setBaseImage(uri.toString());
        capsModel.setStatus("cart");
        capsModel.setPrice(10000);
        dbCapsHelper.addCap(capsModel);
    }

    private void addToRack() {
        //check connection
        //post data
        //get price
        //save price
        if (uri == null) {
            uri = saveImageToStorage(capsModel.getBaseImage(), capsModel.getName());
        }
        capsModel.setBaseImage(uri.toString());
        dbCapsHelper.addCap(capsModel);
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

}