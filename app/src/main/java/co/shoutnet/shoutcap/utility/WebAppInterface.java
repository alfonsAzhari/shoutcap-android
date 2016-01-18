package co.shoutnet.shoutcap.utility;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by Codelabs on 11/5/2015.
 */
public class WebAppInterface {

    private Context mContext;
    private float fontSize;
    private String fontStyle;
    private String fontColor;
    private String[] shout;

    public WebAppInterface(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void getData(float fontSize) {
        this.fontSize = fontSize;
    }

    public float getFontSize() {
        return fontSize;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public String getFontColor() {
        return fontColor;
    }

    public String[] getShout() {
        return shout;
    }
}
