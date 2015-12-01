package co.shoutnet.shoutcap;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class OrderConfirmation extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        toolbar = (Toolbar) findViewById(R.id.toolbar_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentDestination destination = FragmentDestination.newInstance("dasdas", "dasdasd");
        fragmentManager.beginTransaction().add(R.id.frame_content_order, destination).commit();
    }
}
