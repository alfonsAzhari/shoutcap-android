package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;

import co.shoutnet.shoutcap.model.CapsModel;
import co.shoutnet.shoutcap.utility.DBCapsHelper;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class FragmentCreateShout extends Fragment {

    private WebView webView;
    private Context context;
    private CapsModel capsModel;

    public FragmentCreateShout() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_shout, container, false);

        getActivity().setTitle("Create Shout");

        initView(rootView);
        context = getActivity();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        webView.addJavascriptInterface(this, "Android");
        webView.loadUrl("file:///android_asset/create_page/index.html");

        return rootView;
    }

    @JavascriptInterface
    public void capData(String data) {
        capsModel = new CapsModel();
        SecureRandom random=new SecureRandom();
        try {
            String name =new BigInteger(130,random).toString(32);

            JSONObject object = new JSONObject(data);
            JSONArray jsonArray = object.optJSONArray("cap");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            capsModel.setName(name);
            capsModel.setText(jsonObject.optString("text"));
            capsModel.setModel(Integer.parseInt(jsonObject.optString("model")));
            capsModel.setType(Integer.parseInt(jsonObject.optString("type")));
            capsModel.setSize(Integer.parseInt(jsonObject.optString("size")));
            capsModel.setFont(jsonObject.optString("font"));
            capsModel.setColor(jsonObject.optString("color"));
            capsModel.setPrice(Integer.parseInt(jsonObject.optString("price")));
            capsModel.setBaseImage(jsonObject.optString("image"));

            DBCapsHelper dbCapsHelper = new DBCapsHelper(context);
            dbCapsHelper.addCap(capsModel);

            int id=dbCapsHelper.getLatestId();

            Fragment fragment=FragmentPreviewShout.newInstance(id,capsModel.getBaseImage(),capsModel.getPrice(),capsModel.getName());
            FragmentManager fragmentManager=getActivity().getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_content_main,fragment).commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView(View v) {
        webView = (WebView) v.findViewById(R.id.web_create_shout);
    }

}
