package co.shoutnet.shoutcap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import co.shoutnet.shoutcap.adapter.GetStartedAdapter;
import co.shoutnet.shoutcap.utility.CirclePageIndicator;
import co.shoutnet.shoutcap.utility.SessionManager;

public class GetStartedActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private ViewPager viewPager;
    private GetStartedAdapter adapter;
    private Button btnGetStarted;
    private CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

        initView();

        adapter = new GetStartedAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetStartedActivity.this, SignActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pager_started);
        btnGetStarted = (Button) findViewById(R.id.btn_get_started);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator_get_started);
    }

    public void btnCreate(View view) {
        Intent intent = new Intent(GetStartedActivity.this, ActivityCreate.class);
        startActivity(intent);
    }
}