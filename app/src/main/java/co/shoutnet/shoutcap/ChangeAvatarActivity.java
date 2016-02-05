package co.shoutnet.shoutcap;


import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import co.shoutnet.shoutcap.utility.ApiReferences;
import co.shoutnet.shoutcap.utility.SessionManager;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeAvatarActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;

    private Toolbar mToolbar;

    private Button btnChange;
    private ImageView imgPreview;

    private HashMap<String, String> user;

    SessionManager sessionManager;

    private final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_avatar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initToolbar();
        initView();

        sessionManager = new SessionManager(getApplicationContext());
        user = sessionManager.getUserDetails();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 1);
            }
        });

        Glide.with(this).load(user.get(SessionManager.KEY_URL_AVATAR)).into(imgPreview);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_change_ava);
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        btnChange = (Button)findViewById(R.id.btn_change_ava);
        imgPreview = (ImageView)findViewById(R.id.img_change_ava_preview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_ava, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();

            return true;
        } else if (id == R.id.action_avatar_ok) {
            changeAva(ApiReferences.getUrlChangeAvatar());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
//            Log.i("uri", getRealPathFromUriApiBelow19(this, uri));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imgPreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeAva(String url) {

        if (uri == null) {

        } else {

            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setMessage("Please Wait");
            dialog.show();

            try {

                client = new OkHttpClient();

                String path;
                if (Build.VERSION.SDK_INT < 19) {
                    path = getRealPathFromUriApiBelow19(getApplicationContext(), uri);
                } else {
                    path = getRealPathFromUriApi19(getApplicationContext(), uri);
                }

                File file = new File(path);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("shoutid", user.get(SessionManager.KEY_SHOUTID))
                        .addFormDataPart("sessionid", user.get(SessionManager.KEY_SESSIONID))
                        .addFormDataPart("avatar", file.getName(), RequestBody.create(MEDIA_TYPE_JPG, file))
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();

                Response response = client.newCall(request).execute();

                Log.i("Avatar Response", response.body().string());

                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String urlAva = "";
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("result").equalsIgnoreCase("success")) {
                            dialog.dismiss();
                            urlAva = object.getString("url_avatar");
                            Toast.makeText(this, "Avatar Changed", Toast.LENGTH_SHORT).show();
                            //Log.i("url ava baru",user.get(SessionManager.KEY_URL_AVATAR));
                            dialog.dismiss();
                            this.finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(this, "Connection failed, please try again later", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        dialog.dismiss();
                        Toast.makeText(this, "Avatar Changed", Toast.LENGTH_SHORT).show();
                        //Log.i("url ava baru",user.get(SessionManager.KEY_URL_AVATAR));
                        dialog.dismiss();
                        this.finish();
                        Log.e("Exception Response", e.toString());
                    }

                    sessionManager.updateAvatar(urlAva);
                    user = sessionManager.getUserDetails();
                }

            } catch (IOException e) {
                Log.e("Exception Change Avatar", e.toString());
            }
        }
    }

    private String getRealPathFromUriApiBelow19(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    private String getRealPathFromUriApi19(Context context, Uri contentUri) {
        if (isExternalStorageDocument(contentUri)) {
            final String docId = DocumentsContract.getDocumentId(contentUri);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(contentUri)) {
            final String id = DocumentsContract.getDocumentId(contentUri);
            final Uri content = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            return getDataColumn(context,content, null, null);
        } else if (isMediaDocument(contentUri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri content = null;
            if ("image".equals(type)) {
                content = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                content = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                content = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    split[1]
            };

            return getDataColumn(context, content, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(contentUri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            } else {
                getDataColumn(context, uri, null, null);
            }
        } else if ("file".equalsIgnoreCase(contentUri.getScheme())) {
            return contentUri.getPath();
        }

        return null;
    }

    /*private String getRealPathFromUriApiAbove19(Context context, Uri contentUri) {
        Uri docUri = DocumentsContract.buildDocumentUriUsingTree(contentUri, DocumentsContract.getTreeDocumentId(contentUri));

        String
    }*/

    public String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.android.apps.photos.content".equals(uri.getAuthority());
    }
}