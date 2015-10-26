package co.shoutnet.shoutcap;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import co.shoutnet.shoutcap.utility.AutoResizeEditText;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class FragmentCreateShout extends Fragment {

    private WebView webView;
    private Context context;

    public FragmentCreateShout() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_shout, container, false);

        getActivity().setTitle("Create Shout");

        initView(rootView);
        context = getActivity();

        javacriptInterface javascript = new javacriptInterface(context);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        webView.addJavascriptInterface(javascript, "Android");
        webView.loadUrl("file:///android_asset/create_page/index.html");

        return rootView;
    }

    private void initView(View v) {
        webView = (WebView) v.findViewById(R.id.web_create_shout);
    }

    public class javacriptInterface{

        private Context context;

        public javacriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void sendCapData(String image){
            Log.i("image ", image);

            Intent intent=new Intent(context,PreviewActivity.class);
            String[] base64Image=image.split(",");
            Log.i("split",base64Image[1]);
            byte[] newImage= Base64.decode(base64Image[1],Base64.DEFAULT);
            intent.putExtra("image",newImage);
            startActivity(intent);
        }
    }
}
