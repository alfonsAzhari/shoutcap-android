package co.shoutnet.shoutcap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Preview Cap");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        byte[] image = bundle.getByteArray("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);


        ImageView imageView = (ImageView) findViewById(R.id.img_preview);

        if (bitmap != null) {
            File file = new File(Environment.getExternalStorageDirectory() + "/shoutcap");
            if (!file.isDirectory()) {
                file.mkdir();
            }
            FileOutputStream out = null;
            try {
                if (file.isDirectory()) {
                    out = new FileOutputStream(file.getAbsolutePath() + "/image.png".toString());
                    if (out != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.close();
//                                Uri uri=new URI(file.toURI()+"/image.png");
                        imageView.setImageURI(Uri.parse(file.toURI() + "/image.png"));
                    }
//                    Log.i("directory", "exist");
                } else {
                    if (out != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.close();
                        imageView.setImageURI(Uri.parse(file.toURI() + "/image.png"));
                    }
//                    Log.i("directory", "doesn't exist");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        Log.i("path", Environment.getExternalStorageDirectory().toString() + "/shoutcap");
    }


}
