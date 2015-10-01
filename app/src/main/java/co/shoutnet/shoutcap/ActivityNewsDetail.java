package co.shoutnet.shoutcap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.shoutnet.shoutcap.model.ModelNewsDetail;

public class ActivityNewsDetail extends AppCompatActivity {

    ImageView imageView;
    TextView textTitle, textDate, textInfo;
    ArrayList<ModelNewsDetail> list;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        list = new Dummy().getNewsDetail();

        imageView.setImageResource(list.get(0).getImage());
        textTitle.setText(list.get(0).getTitle());
        textDate.setText(list.get(0).getDate());
        textInfo.setText(list.get(0).getInfo());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News");
    }

    private void initView(){
        imageView = (ImageView)findViewById(R.id.img_news_detail);
        textTitle = (TextView)findViewById(R.id.txt_title_news_detail);
        textDate = (TextView)findViewById(R.id.txt_date_news_detail);
        textInfo = (TextView)findViewById(R.id.txt_info_news_detail);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
