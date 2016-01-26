package co.shoutnet.shoutcap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SignActivity extends AppCompatActivity {

    private boolean exitCounter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        FragmentSignIn signIn = new FragmentSignIn();
        getFragmentManager().beginTransaction().replace(R.id.frame_content_sign, signIn).commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            if(exitCounter){
                super.onBackPressed();
                return;
            }
            exitCounter = true;
            Toast.makeText(getApplicationContext(), "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitCounter = false;
                }
            }, 2500);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign, menu);
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