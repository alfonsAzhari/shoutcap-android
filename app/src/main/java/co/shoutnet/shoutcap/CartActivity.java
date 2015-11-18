package co.shoutnet.shoutcap;

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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.shoutnet.shoutcap.adapter.CartAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterCart;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.ListCallback;
import co.shoutnet.shoutcap.utility.RecyclerSwipeTouchListener;


public class CartActivity extends AppCompatActivity {

    private static Button btnTotal;
    private static long total;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;

    public static long getTotal() {
        return total;
    }

    public static void setTotal(long mTotal) {
        btnTotal.setText(getCurrency(mTotal));
        total = mTotal;
        Log.i("total ", String.valueOf(total));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initToolbar();
        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<ModelAdapterCart> modelAdapterCarts;
        modelAdapterCarts=new DBCapsHelper(getApplicationContext()).getCartData();

        for (int i=0;i<modelAdapterCarts.size();i++){
            total+=modelAdapterCarts.get(i).getPrice();
        }
        setTotal(total);
        getCurrency(total);

        CartAdapter adapter = new CartAdapter(this,modelAdapterCarts);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        ItemTouchHelper.Callback callback = new ListCallback(getApplicationContext(), adapter);
//        itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        RecyclerSwipeTouchListener touchListener=new RecyclerSwipeTouchListener(getApplicationContext(), recyclerView, R.id.main_view, R.id.background_view, new RecyclerSwipeTouchListener.SwipeListener() {
            @Override
            public boolean canSwipe(int position) {
                return true;
            }

            @Override
            public void onDismiss(RecyclerView recyclerView, int[] reversePositions) {

            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });

        recyclerView.addOnItemTouchListener(touchListener);
        recyclerView.setAdapter(adapter);

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
        btnTotal = (Button) findViewById(R.id.btn_purchase_cart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static String getCurrency(long value){
        NumberFormat currency=NumberFormat.getCurrencyInstance(Locale.US);
        String strCurrency=currency.format(value).replace("$","Rp. ");
        Log.i("info currency",strCurrency);
        return strCurrency;
    }
}
