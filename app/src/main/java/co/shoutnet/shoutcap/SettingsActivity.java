package co.shoutnet.shoutcap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import co.shoutnet.shoutcap.utility.SessionManager;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ListView listView;
    private String[] items;

    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        manager = new SessionManager(this);

        initToolbar();
        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items = getResources().getStringArray(R.array.settings_item_array);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intentEdit = new Intent(SettingsActivity.this, ActivityEditProfile.class);
                        startActivity(intentEdit);
                        break;

                    case 1:
                        Intent intentChange = new Intent(SettingsActivity.this, ActivityChangePassword.class);
                        startActivity(intentChange);
                        break;

                    case 2:
                        Intent intentAbout = new Intent(SettingsActivity.this, ActivityAbout.class);
                        startActivity(intentAbout);
                        break;

                    case 3:
                        logout(3000);
                        break;
                }
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview_settings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(long time) {
        final ProgressDialog progressDialog = new ProgressDialog(SettingsActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing Out");
        progressDialog.show();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                manager.logoutUser();
            }
        }, time);
    }
}
