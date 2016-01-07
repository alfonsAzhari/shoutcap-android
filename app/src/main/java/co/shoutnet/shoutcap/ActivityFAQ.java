package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;

import java.util.ArrayList;

/**
 * Created by Adam MB on 10/21/2015.
 */
public class ActivityFAQ extends AppCompatActivity{
    private FAQAdapter faqAdapter;
    private ArrayList<ModelQuestionFAQ> questionListItems;
    private ExpandableListView ExpandList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        initView();
        initToolbar();

        questionListItems = new FAQ().getData();
        faqAdapter = new FAQAdapter(ActivityFAQ.this, questionListItems);
        ExpandList.setAdapter(faqAdapter);
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar_faq);
        ExpandList = (ExpandableListView) findViewById(R.id.expandable_list_view);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FAQ");
    }
}
