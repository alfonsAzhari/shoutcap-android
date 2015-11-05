package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import co.shoutnet.shoutcap.utility.DBCapsHelper;

/**
 * Created by Alfons on 29/8/2015.
 */
public class FragmentPreviewShout extends Fragment {

    private static String ID="ID";
    private static String IMAGE = "IMAGE_BASE64";
    private static String NAME = "NAME";
    private static String PRICE = "PRICE";
    private ImageView imgPreview;
    private DBCapsHelper dbCapsHelper;

    public FragmentPreviewShout() {

    }

    public static FragmentPreviewShout newInstance(int id, String image, int price, String name) {

        Bundle args = new Bundle();
        args.putInt(ID,id);
        args.putString(IMAGE, image);
        args.putInt(PRICE, price);
        args.putString(NAME, name);
        FragmentPreviewShout fragment = new FragmentPreviewShout();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preview_shout, container, false);
        initView(rootView);
        getActivity().setTitle("Preview ShoutCap");
        dbCapsHelper=new DBCapsHelper(getActivity());

        Bundle bundle = getArguments();

        Uri uri = saveImageToStorage(bundle.getInt(ID),bundle.getString(IMAGE), bundle.getString(NAME));
        try {
            imgPreview.setImageURI(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private Uri saveImageToStorage(int id, String image, String name) {

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
        dbCapsHelper.updateUri(id,uri.toString());
        return uri;
    }

    private void initView(View v) {
        imgPreview = (ImageView) v.findViewById(R.id.img_preview_hat);
    }

}