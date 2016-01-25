package co.shoutnet.shoutcap.utility;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by codelabs on 1/21/16.
 */
public class Loading {

    public static ProgressDialog newInstance(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        progressDialog.setCancelable(false);
        return progressDialog;
    }
//    public static ProgressDialog newInstance(Context context) {
//        ProgressDialog progressDialog = new ProgressDialog(context, R.style.LoadignDialog);
//        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading");
//        return progressDialog;
//    }

}
