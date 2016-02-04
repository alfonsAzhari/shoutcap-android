package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.model.ModelCapModel;
import co.shoutnet.shoutcap.model.ModelColor;
import co.shoutnet.shoutcap.utility.CustomScrollView;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.WebAppInterface;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class FragmentCreateShout extends Fragment {
    private static CapsModel capsModel = new CapsModel();
    private static ArrayList<CapsModel> caps;
    private Context context;
    private WebView webView;
    private Button btnCreate;
    private Spinner spinModel;
    private Spinner spinCategory;
    private Spinner spinFontStyle;
    private Spinner spinFontColor;
    private RadioButton rbAdult;
    private RadioButton rbJunior;

    //temp data
    private CustomScrollView scrollView;
    private ArrayList<ModelColor> color = null;
    private ArrayList<ModelCapModel> model = null;
    private String[] imgCap = null;
    private int indexCap;
    private int capPrice = 0;
    private int sizePrice = 0;
    private int colorPrice = 0;
    private View.OnClickListener clickCreate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            webView.loadUrl("javascript:drawTextToImage()");
//            webView.loadUrl("javascript:changeFont()");
        }
    };
    private AdapterView.OnItemSelectedListener selectModel = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String fileColor;
            String fileModel;
            int capArrayName;
            if (i == 1) {
                spinCategory.setEnabled(false);

                fileColor = "baseball_color.json";
                fileModel = "model_cap_baseball.json";
//                capArrayName = R.array.cap_img_baseball_list;
                indexCap = 0;
            } else {
                spinCategory.setEnabled(true);
                spinCategory.setSelection(0);

                fileColor = "classic_color.json";
                fileModel = "model_cap_trucker_classic.json";
//                capArrayName = R.array.cap_img_trucker_classic_list;
                indexCap = 0;
            }

            try {
                color = Parser.getColorFromJson(getFileFromAsset(fileColor));
                model = Parser.getModelFromJson(getFileFromAsset(fileModel));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] nameCap = new String[model.size()];
            int indexImage = 0;
            for (ModelCapModel cap : model) {
                nameCap[indexImage++] = cap.getImg_path();
            }
            imgCap = nameCap;
            webViewChangeCap(imgCap[0]);

            capsModel.setModel(model.get(0).getId());
            capsModel.setColor(color.get(0).getId());

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
            String fileModel = null;
            int capArrayName = 0;
            String[] colorList;
            ArrayAdapter<String> fontColorAdapter;
            switch (i) {
                case 0:
                    file = "classic_color.json";
                    fileModel = "model_cap_trucker_classic.json";
                    capArrayName = R.array.cap_img_trucker_classic_list;
                    indexCap = 0;
                    break;

                case 1:
                    file = "color_color.json";
                    fileModel = "model_cap_trucker_color.json";
                    capArrayName = R.array.cap_img_trucker_color_list;
                    indexCap = 0;
                    break;

                case 2:
                    file = "mixed_color.json";
                    fileModel = "model_cap_trucker_mixed.json";
                    capArrayName = R.array.cap_img_trucker_mixed_list;
                    indexCap = 0;
                    break;
            }

            try {
                color = Parser.getColorFromJson(getFileFromAsset(file));
                model = Parser.getModelFromJson(getFileFromAsset(fileModel));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] nameCap = new String[model.size()];
            int indexImage = 0;
            for (ModelCapModel cap : model) {
                nameCap[indexImage++] = cap.getImg_path();
            }
            imgCap = nameCap;
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
            capsModel.setFont(spinFontStyle.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    private AdapterView.OnItemSelectedListener selectFontColor = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            webView.loadUrl("javascript:changeFontColor('" + color.get(i).getCode() + "')");
            Log.i("code", color.get(i).getCode());
            capsModel.setColor(color.get(i).getId());
//            Log.i("id color", String.valueOf(color.get(i).getId()));

//            colorPrice = Integer.getInteger(color.get(i).getPrice());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public FragmentCreateShout() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_shout, container, false);

        getActivity().setTitle("Create Shout");

        initView(rootView);
        context = getActivity();

        WebSettings settings = webView.getSettings();
        final WebAppInterface webApp = new WebAppInterface(context);
        settings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        //webView.loadUrl("https://shoutcap.shoutnet.co/home");
        settings.setAllowFileAccessFromFileURLs(true);
        webView.addJavascriptInterface(this, "Android");
        webView.loadUrl("file:///android_asset/create_page/index.html");

//        webView.addJavascriptInterface(this, "Android");

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
                                webViewChangeCap(imgCap[indexCap]);
//                                Log.i("image choose",model.get(indexCap).getImg_path());
//                                scrollView.setScrollable(true);
                            }
                            return true;
                        } else if (e2.getX() - e1.getX() > 100) {
                            if (indexCap > 0) {
                                indexCap--;
                                webViewChangeCap(imgCap[indexCap]);
//                                Log.i("image choose", model.get(indexCap).getImg_path());
//                                scrollView.setScrollable(true);
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
                scrollView.setScrollable(false);
                gestureDetector.onTouchEvent(motionEvent);
                if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL || motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    scrollView.setScrollable(true);
                }
                return false;
            }

        });

        if (capsModel.getSize() == null) {
            capsModel.setSize("adult");
        }

        btnCreate.setOnClickListener(clickCreate);
        spinModel.setOnItemSelectedListener(selectModel);
        spinCategory.setOnItemSelectedListener(selectCat);
        spinFontStyle.setOnItemSelectedListener(selectFontStyle);
        spinFontColor.setOnItemSelectedListener(selectFontColor);
        rbAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capsModel.setSize("adult");
            }
        });
        rbJunior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capsModel.setSize("junior");
            }
        });

        return rootView;
    }

    @JavascriptInterface
    public void capData(String data) {
        SecureRandom random = new SecureRandom();
        try {
            String name = new BigInteger(130, random).toString(32);

            JSONObject object = new JSONObject(data);
            JSONArray jsonArray = object.optJSONArray("cap");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            capsModel.setName(name);
            capsModel.setText(jsonObject.optString("text"));
            capsModel.setBaseImage(jsonObject.optString("image"));
            capsModel.setLine(jsonObject.optInt("line"));
            capsModel.setFontsize(jsonObject.optInt("fontsize"));

//            DBCapsHelper dbCapsHelper = new DBCapsHelper(context);
//            dbCapsHelper.addCap(capsModel);
//
//            int id = dbCapsHelper.getLatestId();
//
            Fragment fragment = FragmentPreviewShout.newInstance(name, capsModel.getModel(),
                    capsModel.getSize(), capsModel.getFont(), capsModel.getColor(), capsModel.getFontsize(),
                    capsModel.getText(), capsModel.getLine(), capsModel.getBaseImage());
            FragmentManager fragmentManager = getActivity().getFragmentManager();
            SessionManager manager = new SessionManager(getActivity());
            // TODO ulah poho dipindahkeun
            if (manager.isLoggedIn()) {
                fragmentManager.beginTransaction().replace(R.id.frame_content_main, fragment).commit();
            } else {
                fragmentManager.beginTransaction().replace(R.id.frame_content_create, fragment).commit();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView(View v) {
        webView = (WebView) v.findViewById(R.id.web_create_shout);
        btnCreate = (Button) v.findViewById(R.id.btn_create);
        spinModel = (Spinner) v.findViewById(R.id.spin_create_model);
        spinCategory = (Spinner) v.findViewById(R.id.spin_create_cat);
        spinFontStyle = (Spinner) v.findViewById(R.id.spin_create_font_style);
        spinFontColor = (Spinner) v.findViewById(R.id.spin_create_font_color);
        scrollView = (CustomScrollView) v.findViewById(R.id.scroll);
        rbAdult = (RadioButton) v.findViewById(R.id.rb_adult_create);
        rbJunior = (RadioButton) v.findViewById(R.id.rb_junior_create);
    }

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
