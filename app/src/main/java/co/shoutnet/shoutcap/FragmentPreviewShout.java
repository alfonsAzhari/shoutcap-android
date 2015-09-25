package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Alfons on 29/8/2015.
 */
public class FragmentPreviewShout extends Fragment {

    private static String IMAGE_ID = "IMAGE_ID";
    private static String FONT_COLOR = "FONT_COLOR";
    private static String FONT_SIZE = "FONT_SIZE";
    private static String TEXT = "TEXT";
    private static String FONT_STYLE = "FONT_STYLE";
    private static String EDITTEXT_WIDTH = "EDITTEXT_WIDTH";
    private static String EDITTEXT_HEIGHT = "EDITTEXT_HEIGHT";
    private static String TEXT_WIDTH = "TEXT_WIDTH";
    private static String TEXT_HEIGHT = "TEXT_HEIGHT";
    private static String IMAGEVIEW_WIDTH = "IMAGEVIEW_WIDTH";
    private static String IMAGEVIEW_HEIGHT = "IMAGEVIEW_HEIGHT";

    private Context context;

    private ImageView imgPreview;

    public FragmentPreviewShout() {

    }

    public static FragmentPreviewShout newInstance(int imageId, int imageViewWidth, int imageViewHeight, int fontColor, float fontSize, String[] text, String fontStyle, float editTextWidth, float editTextHeight, int[] textWidth, int[] textHeight) {

        Bundle args = new Bundle();
        args.putInt(IMAGE_ID, imageId);
        args.putInt(FONT_COLOR, fontColor);
        args.putFloat(FONT_SIZE, fontSize);
        args.putStringArray(TEXT, text);
        args.putString(FONT_STYLE, fontStyle);
        args.putFloat(EDITTEXT_WIDTH, editTextWidth);
        args.putFloat(EDITTEXT_HEIGHT, editTextHeight);
        args.putIntArray(TEXT_WIDTH, textWidth);
        args.putIntArray(TEXT_HEIGHT, textHeight);
        args.putInt(IMAGEVIEW_WIDTH, imageViewWidth);
        args.putInt(IMAGEVIEW_HEIGHT, imageViewHeight);
        FragmentPreviewShout fragment = new FragmentPreviewShout();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_preview_shout, container, false);

        getActivity().setTitle("Preview ShoutCap");
        context = getActivity();

        Bundle bundle = getArguments();
        int imageId = bundle.getInt(IMAGE_ID);
        String[] shout = bundle.getStringArray(TEXT);
        String font = bundle.getString(FONT_STYLE);
        int color = bundle.getInt(FONT_COLOR);
        float size = bundle.getFloat(FONT_SIZE);
        float edtWidth = bundle.getFloat(EDITTEXT_WIDTH);
        float edtHeight = bundle.getFloat(EDITTEXT_HEIGHT);
        int[] textWidth = bundle.getIntArray(TEXT_WIDTH);
        int[] textHeight = bundle.getIntArray(TEXT_HEIGHT);
        int imageWidth = bundle.getInt(IMAGEVIEW_WIDTH);
        int imageHeight = bundle.getInt(IMAGEVIEW_HEIGHT);

        Log.i("image_id", String.valueOf(imageId));
        Log.i("shout", shout[0]);
        Log.i("font", font);
        Log.i("color", String.valueOf(color));
        Log.i("size", String.valueOf(size));
        Log.i("edt_width", String.valueOf(edtWidth));
        Log.i("txt_width", String.valueOf(textWidth));

        initView(rootView);

        imgPreview.setImageDrawable(writeOnDrawable(context, shout, size, imageId, imageWidth, imageHeight, color, font, edtWidth, edtHeight, textWidth, textHeight));
        return rootView;
    }

    private void initView(View v) {
        imgPreview = (ImageView) v.findViewById(R.id.img_preview_hat);
    }

    private BitmapDrawable writeOnDrawable(Context context, String[] shout, float textSize, int imageId, int imageViewWidth, int imageViewHeight, int fontColor, String fontStyle, float editTextWidth, float editTextHeight, int[] textWidth, int[] textHeight) {

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), imageId).copy(Bitmap.Config.ARGB_8888, true);

        AssetManager assetManager = context.getAssets();
        Typeface style = Typeface.createFromAsset(assetManager, fontStyle);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fontColor);
        paint.setTypeface(style);
        paint.setTextSize(textSize);

        Canvas canvas = new Canvas(bm);
        float startY = ((bm.getHeight() - editTextHeight) / 2) + ((editTextHeight - textHeight[0]) / 2);
        for (int i = 0; i < shout.length; i++) {
            Log.i("Width", String.valueOf(imgPreview.getHeight()));
            Log.i("Width", String.valueOf(editTextWidth));
            float startX = ((imageViewWidth - editTextWidth) / 2) + ((editTextWidth - textWidth[i]) / 2);

            canvas.drawText(shout[i], startX, startY, paint);
            startY += textHeight[i] + textSize / 4;
        }

        return new BitmapDrawable(context.getResources(), bm);
    }
}
