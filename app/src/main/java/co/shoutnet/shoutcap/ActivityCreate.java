package co.shoutnet.shoutcap;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActivityCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_create);

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentCreateShout createShout = new FragmentCreateShout();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.frame_content_create, createShout).commit();
    }
}
