package co.shoutnet.shoutcap;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
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
            Log.i("uri", getRealPathFromUriApiBelow19(this, uri));

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

                if (!response.isSuccessful())
                    throw new IOException("Unexpected code " + response);

            } catch (IOException e) {
                Log.e("Exception Change Avatar", e.toString());
            }
        }
    }

    private String getRealPathFromUriApi19(Context context, Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
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
}
