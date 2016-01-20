package co.shoutnet.shoutcap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import co.shoutnet.shoutcap.utility.SessionManager;

public class HelpActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ListView listView;
    private String[] items;

    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        manager = new SessionManager(this);

        initToolbar();
        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items = getResources().getStringArray(R.array.help_item_array);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(HelpActivity.this, ActivityHowToOrder.class);
                        startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(HelpActivity.this, ActivityFAQ.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview_help);
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
}
