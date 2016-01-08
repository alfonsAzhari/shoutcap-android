package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import co.shoutnet.shoutcap.adapter.HowToOrderAdapter;
import co.shoutnet.shoutcap.utility.CirclePageIndicator;

public class ActivityHowToOrder extends AppCompatActivity {

    private ViewPager viewPager;
    private HowToOrderAdapter adapter;
    private Toolbar toolbar;
    private CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_order);

        initView();
        initToolbar();

        adapter = new HowToOrderAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("How To Order");
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pager_how_to_order);
        toolbar = (Toolbar)findViewById(R.id.toolbar_how_to_order);
        indicator = (CirclePageIndicator)findViewById(R.id.indicator);
    }
}
