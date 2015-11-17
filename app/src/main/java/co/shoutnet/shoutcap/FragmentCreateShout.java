package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import co.shoutnet.shoutcap.model.ModelColor;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.WebAppInterface;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class FragmentCreateShout extends Fragment {

    private Context context;

    private WebView webView;
    private Button btnCreate;
    private Spinner spinModel;
    private Spinner spinCategory;
    private Spinner spinFontStyle;
    private Spinner spinFontColor;

    private ArrayList<ModelColor> color = null;
    private String[] imgCap = null;
    private int indexCap;

    private int capPrice = 0;
    private int sizePrice = 0;
    private int colorPrice = 0;

    public FragmentCreateShout() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_shout, container, false);

        getActivity().setTitle("Create Shout");

        initView(rootView);
        context = getActivity();

        WebSettings settings = webView.getSettings();
        WebAppInterface webApp = new WebAppInterface(context);
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        //webView.loadUrl("https://shoutcap.shoutnet.co/home");
        webView.loadUrl("file:///android_asset/create_page/index.html");

        webView.addJavascriptInterface(webApp, "Android");

        //Set Gesture WebView
        final GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null) return false;
                if (e1.getPointerCount() > 1 || e2.getPointerCount() > 1) return false;
                else {
                    try {
                        if (e1.getX() - e2.getX() > 100) {
                            if (indexCap < imgCap.length) {
                                indexCap++;
                                webViewChangeCap(imgCap[indexCap]);
                            }
                            return true;
                        } else if (e2.getX() - e1.getX() > 100) {
                            if (indexCap > 0) {
                                indexCap--;
                                webViewChangeCap(imgCap[indexCap]);
                            }
                            return true;
                        }
                    } catch (Exception e) {
                        Log.e("Swipe Exception", e.toString());
                    }
                    return false;
                }
            }
        });

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });

        btnCreate.setOnClickListener(clickCreate);
        spinModel.setOnItemSelectedListener(selectModel);
        spinCategory.setOnItemSelectedListener(selectCat);
        spinFontStyle.setOnItemSelectedListener(selectFontStyle);
        spinFontColor.setOnItemSelectedListener(selectFontColor);

        return rootView;
    }

    private void initView(View v) {
        webView = (WebView) v.findViewById(R.id.web_create_shout);
        btnCreate = (Button) v.findViewById(R.id.btn_create);
        spinModel = (Spinner) v.findViewById(R.id.spin_create_model);
        spinCategory = (Spinner) v.findViewById(R.id.spin_create_cat);
        spinFontStyle = (Spinner) v.findViewById(R.id.spin_create_font_style);
        spinFontColor = (Spinner) v.findViewById(R.id.spin_create_font_color);
    }

    private View.OnClickListener clickCreate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            webView.loadUrl("javascript:changeFont()");
        }
    };

    private AdapterView.OnItemSelectedListener selectModel = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String file;
            int capArrayName;
            if (i == 1) {
                spinCategory.setEnabled(false);

                file = "baseball_color.json";
                capArrayName = R.array.cap_img_baseball_list;
                indexCap = 0;
            } else {
                spinCategory.setEnabled(true);
                spinCategory.setSelection(0);

                file = "classic_color.json";
                capArrayName = R.array.cap_img_trucker_classic_list;
                indexCap = 0;
            }

            try {
                color = Parser.getColorFromJson(getFileFromAsset(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgCap = getResources().getStringArray(capArrayName);
            webViewChangeCap(imgCap[0]);

            String[] colorList = new String[color.size()];
            int index = 0;
            for (ModelColor code : color) {
                colorList[index++] = code.getName();
            }

            ArrayAdapter<String> fontColorAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, colorList);
            fontColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinFontColor.setAdapter(fontColorAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener selectCat = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String file = "";
            int capArrayName = 0;
            String[] colorList;
            ArrayAdapter<String> fontColorAdapter;
            switch (i) {
                case 0:
                    file = "classic_color.json";
                    capArrayName = R.array.cap_img_trucker_classic_list;
                    indexCap = 0;
                    break;

                case 1:
                    file = "color_color.json";
                    capArrayName = R.array.cap_img_trucker_color_list;
                    indexCap = 0;
                    break;

                case 2:
                    file = "mixed_color.json";
                    capArrayName = R.array.cap_img_trucker_mixed_list;
                    indexCap = 0;
                    break;
            }

            try {
                color = Parser.getColorFromJson(getFileFromAsset(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgCap = getResources().getStringArray(capArrayName);
            webViewChangeCap(imgCap[0]);

            colorList = new String[color.size()];
            int index = 0;

            for (ModelColor code : color) {
                colorList[index++] = code.getName();
            }

            fontColorAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, colorList);
            fontColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinFontColor.setAdapter(fontColorAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener selectFontStyle = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            webView.loadUrl("javascript:changeFont('" + spinFontStyle.getSelectedItem() + "')");
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener selectFontColor = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            webView.loadUrl("javascript:changeFontColor('" + color.get(i).getCode() + "')");
            //Log.i("code", color.get(i).getCode());

            colorPrice = Integer.getInteger(color.get(i).getPrice());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private String getFileFromAsset(String fileName) throws IOException {
        InputStream stream = context.getAssets().open(fileName);
        int size = stream.available();

        byte[] buffer = new byte[size];
        stream.read(buffer);
        stream.close();

        return new String(buffer);
    }

    private void webViewChangeCap(String img) {
        webView.loadUrl("javascript:changeCap('" + img + "')");
    }
}
