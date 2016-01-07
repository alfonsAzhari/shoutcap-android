package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adam MB on 9/10/2015.
 */
public class ActivityInboxDetail extends AppCompatActivity{
    private TextView title;
    private TextView date;
    private TextView content;
    private ArrayList<ModelInboxDetail> list;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail);
        initView();
        initToolbar();

        list = new Dummy().getInboxDetail();
        title.setText(list.get(0).getTitle());
        date.setText(list.get(0).getDate());
        content.setText(list.get(0).getContent());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inbox");
    }


    private void initView(){
        title = (TextView)findViewById(R.id.txt_title_inbox_detail);
        date = (TextView)findViewById(R.id.txt_date_inbox_detail);
        content = (TextView)findViewById(R.id.txt_content_inbox_detail);
        toolbar = (Toolbar)findViewById(R.id.toolbar_inbox_detail);
    }
}
