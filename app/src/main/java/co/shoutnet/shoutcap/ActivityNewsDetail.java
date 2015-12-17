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

    private ImageView imageView;
    private TextView textTitle;
    private TextView textDate;
    private TextView textInfo;
    private ArrayList<ModelNewsDetail> list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
        initToolbar();

        list = new Dummy().getNewsDetail();
        imageView.setImageResource(list.get(0).getImage());
        textTitle.setText(list.get(0).getTitle());
        textDate.setText(list.get(0).getDate());
        textInfo.setText(list.get(0).getInfo());
    }

    private void initToolbar() {
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
}
