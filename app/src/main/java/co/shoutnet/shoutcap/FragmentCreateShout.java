package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class FragmentCreateShout extends Fragment {

    private int imagePosition;
    private int colorPosition;
    private String[] fontCollection;
    private Resources res;
    private TypedArray imgCapId;
    private TypedArray colorId;

    private String[] shout;
    private int[] shoutWidth;
    private int[] shoutHeight;

    private ImageView imgHat;
    private EditText edtTextInput;
    private Button btnCreate;
    private Button back;
    private Button next;
    private Spinner spinModel;
    private Spinner spinCategory;
    private Spinner spinFontStyle;
    private Spinner spinFontColor;

    private Context context;

    public FragmentCreateShout() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_shout, container, false);

        initView(rootView);
        context = getActivity();

        getActivity().setTitle("Create Shout");

        res = context.getResources();

        initResources();
        initImage();

        edtTextInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    if (((EditText) view).getLineCount() >= 3) {
                        return true;
                    }
                }
                return false;
            }
        });

        edtTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Rect bounds = new Rect();
                shout = (edtTextInput.getText().toString() + " ").split("\n");
                shoutWidth = new int[shout.length];
                shoutHeight = new int[shout.length];

                Paint textPaint = edtTextInput.getPaint();

                for (int i = 0; i < shout.length; i++) {
                    textPaint.getTextBounds(shout[i], 0, shout[i].length(), bounds);

                    shoutWidth[i] = bounds.width();
                    shoutHeight[i] = bounds.height();
                }
                textPaint.getTextBounds(charSequence.toString(), 0, edtTextInput.length(), bounds);

                int width = bounds.width();
                int height = (bounds.height() + edtTextInput.getPaddingBottom() + 45) * shout.length;

                float textSize = edtTextInput.getTextSize();

                Log.i("Text", String.valueOf(charSequence));
                Log.i("editText Width", String.valueOf(edtTextInput.getWidth()));
                Log.i("editText Height", String.valueOf(edtTextInput.getHeight()));

                /*Log.i("editText Width", String.valueOf(edtTextInput.getWidth()));
                Log.i("editText Height", String.valueOf(edtTextInput.getHeight()));
                Log.i("Text Size", String.valueOf(edtTextInput.getTextSize()));
                Log.i("Text Height", String.valueOf(height));
                Log.i("editText Line", String.valueOf(edtTextInput.getLineCount()));*/

                if ((width > edtTextInput.getWidth()) || (height > edtTextInput.getHeight())) {
                    do {
                        textSize -= 1;
                        if (textSize <= 30) {
                            textSize = 30;
                            edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(textSize));
                            break;
                        }
                        Log.i("Text Width before", String.valueOf(width));

                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(textSize));

                        textPaint.getTextBounds(charSequence.toString(), 0, edtTextInput.length(), bounds);
                        width = bounds.width();
                        height = (bounds.height() + edtTextInput.getPaddingBottom() + 45) * shout.length;
                        Log.i("Text Width after", String.valueOf(width));
                    }
                    while (width > edtTextInput.getWidth() || height > edtTextInput.getHeight());
                }
                /*
                if ((width < edtTextInput.getWidth()) || (height < edtTextInput.getHeight())) {
                    do {
                        textSize += 1;

                        Log.i("Text Width before", String.valueOf(width));

                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(textSize));

                        textPaint.getTextBounds(charSequence.toString(), 0, edtTextInput.length(), bounds);
                        width = bounds.width();
                        height = (bounds.height() + edtTextInput.getPaddingBottom() + 45) * shout.length;
                        Log.i("Text Width after", String.valueOf(width));
                    }
                    while (width <= edtTextInput.getWidth() || height <= edtTextInput.getHeight());
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinModel.setOnItemSelectedListener(selectModel);
        spinCategory.setOnItemSelectedListener(selectCategory);
        spinFontStyle.setOnItemSelectedListener(selectFontStyle);
        spinFontColor.setOnItemSelectedListener(selectFontColor);

        back.setOnClickListener(prevHat);
        next.setOnClickListener(nextHat);
        btnCreate.setOnClickListener(createHat);

        return rootView;
    }

    private void initView(View v) {
        imgHat = (ImageView) v.findViewById(R.id.img_create_hat);
        edtTextInput = (EditText) v.findViewById(R.id.edt_create_input);
        btnCreate = (Button) v.findViewById(R.id.btn_create);
        back = (Button) v.findViewById(R.id.btn_create_back);
        next = (Button) v.findViewById(R.id.btn_create_next);
        spinModel = (Spinner) v.findViewById(R.id.spin_create_model);
        spinCategory = (Spinner) v.findViewById(R.id.spin_create_cat);
        spinFontStyle = (Spinner) v.findViewById(R.id.spin_create_font);
        spinFontColor = (Spinner) v.findViewById(R.id.spin_create_font_color);
    }

    private void initImage() {
        imgHat.setImageResource(imgCapId.getResourceId(0, 0));
        imgHat.setTag(imgCapId.getResourceId(0, 0));
        imagePosition = 0;
    }

    private void initResources() {
        imgCapId = res.obtainTypedArray(R.array.cap_trucker_classic_collection);
        colorId = res.obtainTypedArray(R.array.color_collection);
        colorPosition = 0;
    }

    View.OnClickListener createHat = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Toast.makeText(context, String.valueOf(edtTextInput.getTextSize()), Toast.LENGTH_SHORT).show();

            FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
            FragmentPreviewShout previewShout = FragmentPreviewShout.newInstance((int) imgHat.getTag(), colorId.getColor(colorPosition, 0), edtTextInput.getTextSize(), shout, fontCollection[spinFontStyle.getSelectedItemPosition()], edtTextInput.getWidth(), edtTextInput.getHeight(), shoutWidth, shoutHeight);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(R.id.frame_content_main, previewShout);
            ft.addToBackStack(null);
            ft.commit();
        }
    };

    View.OnClickListener prevHat = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (imagePosition > 0) {
                imagePosition -= 1;
                imgHat.setImageResource(imgCapId.getResourceId(imagePosition, 0));
                imgHat.setTag(imgCapId.getResourceId(imagePosition, 0));
            }
        }
    };

    View.OnClickListener nextHat = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (imagePosition <= imgCapId.getIndexCount()) {
                imagePosition += 1;
                imgHat.setImageResource(imgCapId.getResourceId(imagePosition, 0));
                imgHat.setTag(imgCapId.getResourceId(imagePosition, 0));
            }
        }
    };

    AdapterView.OnItemSelectedListener selectModel = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 1) {
                spinCategory.setEnabled(false);
                updateImageResource(R.array.cap_baseball_collection);
                imagePosition = 0;
                imgHat.setImageResource(imgCapId.getResourceId(0, 0));
                imgHat.setTag(imgCapId.getResourceId(0, 0));
            } else {
                spinCategory.setEnabled(true);
                spinCategory.setSelection(0);
                updateImageResource(R.array.cap_trucker_classic_collection);
                imagePosition = 0;
                imgHat.setImageResource(imgCapId.getResourceId(0, 0));
                imgHat.setTag(imgCapId.getResourceId(0, 0));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener selectCategory = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i) {
                case 0:
                    updateImageResource(R.array.cap_trucker_classic_collection);
                    imagePosition = 0;
                    imgHat.setImageResource(imgCapId.getResourceId(0, 0));
                    imgHat.setTag(imgCapId.getResourceId(0, 0));
                    break;

                case 1:
                    updateImageResource(R.array.cap_trucker_color_collection);
                    imagePosition = 0;
                    imgHat.setImageResource(imgCapId.getResourceId(0, 0));
                    imgHat.setTag(imgCapId.getResourceId(0, 0));
                    break;

                case 2:
                    updateImageResource(R.array.cap_trucker_mixed_collection);
                    imagePosition = 0;
                    imgHat.setImageResource(imgCapId.getResourceId(0, 0));
                    imgHat.setTag(imgCapId.getResourceId(0, 0));
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener selectFontStyle = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            fontCollection = res.getStringArray(R.array.fonts_name_array);
            Typeface tf;
            switch (i) {
                case 0:
                    tf = Typeface.createFromAsset(context.getAssets(), fontCollection[0]);
                    edtTextInput.setTypeface(tf);
                    if (edtTextInput.getText().length() == 0)
                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(152));
                    break;

                case 1:
                    tf = Typeface.createFromAsset(context.getAssets(), fontCollection[1]);
                    edtTextInput.setTypeface(tf);
                    if (edtTextInput.getText().length() == 0)
                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(163));
                    break;

                case 2:
                    tf = Typeface.createFromAsset(context.getAssets(), fontCollection[2]);
                    edtTextInput.setTypeface(tf);
                    if (edtTextInput.getText().length() == 0)
                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(151));
                    break;

                case 3:
                    tf = Typeface.createFromAsset(context.getAssets(), fontCollection[3]);
                    edtTextInput.setTypeface(tf);
                    if (edtTextInput.getText().length() == 0)
                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(154));
                    break;

                case 4:
                    tf = Typeface.createFromAsset(context.getAssets(), fontCollection[4]);
                    edtTextInput.setTypeface(tf);
                    if (edtTextInput.getText().length() == 0)
                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(153));
                    break;

                case 5:
                    tf = Typeface.createFromAsset(context.getAssets(), fontCollection[5]);
                    edtTextInput.setTypeface(tf);
                    if (edtTextInput.getText().length() == 0)
                        edtTextInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, getSpFromPixels(155));
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener selectFontColor = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) {
                edtTextInput.setTextColor(colorId.getColor(0, 0));
                colorPosition = 0;
            } else {
                edtTextInput.setTextColor(colorId.getColor(1, 0));
                colorPosition = 1;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void updateImageResource(int id) {
        imgCapId = res.obtainTypedArray(id);
    }

    private double getDpFromPixels(double pixels) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return pixels;

            case DisplayMetrics.DENSITY_HIGH:
                return pixels * 1.5;

            case DisplayMetrics.DENSITY_XHIGH:
                return pixels * 2;

            default:
                return pixels;
        }
    }

    private float getSpFromPixels(double pixels) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return (float) pixels;

            case DisplayMetrics.DENSITY_HIGH:
                return (float) (pixels / 1.5);

            case DisplayMetrics.DENSITY_XHIGH:
                return (float) (pixels / 2);

            default:
                return (float) pixels;
        }
    }
}
