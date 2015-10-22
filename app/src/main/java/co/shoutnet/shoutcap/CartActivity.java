package co.shoutnet.shoutcap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import co.shoutnet.shoutcap.adapter.CartAdapter;
import co.shoutnet.shoutcap.utility.ListCallback;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    private static Button btnTotal;
    private static long total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initToolbar();
        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CartAdapter adapter = new CartAdapter(this, Dummy.getDataCart());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback=new ListCallback(getApplicationContext(),adapter);
        itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

//        SharedPreferences preferences=getApplicationContext().getSharedPreferences("cart",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        editor.putStringSet()
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_cart);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_cart);
        btnTotal=(Button)findViewById(R.id.btn_purchase_cart);
    }

    public static void setTotal(long mTotal){
        btnTotal.setText(Long.toString(mTotal));
        total=mTotal;
        Log.i("total ", String.valueOf(total));
    }

    public static long getTotal(){
        return total;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
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
