package co.shoutnet.shoutcap;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import co.shoutnet.shoutcap.adapter.CartAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterCart;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.RecyclerSwipeTouchListener;
import co.shoutnet.shoutcap.utility.VoucherDialog;


public class CartActivity extends AppCompatActivity {

    private static Button btnTotal;
    private static long total;
    CartAdapter adapter;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    private String[] capName;
    private List<ModelAdapterCart> modelAdapterCarts;

    public static long getTotal() {
        return total;
    }

    public static void setTotal(long mTotal) {
        btnTotal.setText(getCurrency(mTotal));
        total = mTotal;
    }

    public static String getCurrency(long value) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        String strCurrency = currency.format(value).replace("$", "Rp. ");
        return strCurrency;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initToolbar();
        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        modelAdapterCarts=new DBCapsHelper(getApplicationContext()).getCartData();
        total = 0;
        capName = new String[modelAdapterCarts.size()];
        for (int i=0;i<modelAdapterCarts.size();i++){
            capName[i] = modelAdapterCarts.get(i).getName();
            total+=modelAdapterCarts.get(i).getPrice();
        }
//        List<String> strings=new ArrayList<>();
//        String[] newString = strings.toArray(new String[strings.size()]);

        setTotal(total);
        getCurrency(total);

        adapter = new CartAdapter(this, modelAdapterCarts, new CartAdapter.AdapterListener() {
            @Override
            public void onClickButton(int position, int qty) {
                modelAdapterCarts.get(position).setQty(qty);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerSwipeTouchListener touchListener=new RecyclerSwipeTouchListener(getApplicationContext(), recyclerView, R.id.main_view, R.id.background_view, new RecyclerSwipeTouchListener.SwipeListener() {
            @Override
            public boolean canSwipe(int position) {
                return true;
            }

            @Override
            public void onDismiss(RecyclerView recyclerView, int[] reversePositions) {
                for (int position : reversePositions) {

                    new DBCapsHelper(getApplicationContext()).deleteCartData(modelAdapterCarts.get(position).getId());
                    modelAdapterCarts.remove(position);
                    total = 0;
                    capName = new String[modelAdapterCarts.size()];
                    for (int i = 0; i < modelAdapterCarts.size(); i++) {
                        capName[i] = modelAdapterCarts.get(i).getName();
                        total += modelAdapterCarts.get(i).getPrice() * modelAdapterCarts.get(i).getQty();
                        setTotal(total);
                    }
                    adapter.notifyItemRemoved(position);
                }
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });

        recyclerView.addOnItemTouchListener(touchListener);
        recyclerView.setAdapter(adapter);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogResult();
            }
        });

//        SharedPreferences preferences=getApplicationContext().getSharedPreferences("cart",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        editor.putStringSet()
    }

    private void dialogResult() {
        DialogFragment dialogFragment = VoucherDialog.newInstance(capName, new VoucherDialog.DialogListener() {
            @Override
            public void resultItemOnly(String item) {
                if (item!=null||item.equals("")){
                    Log.i("item selected",item);
                }
            }

            @Override
            public void resultItemVoucher(String item, String voucherCode) {
                if ((item!=null&&voucherCode!=null)||(item.equals("")&&voucherCode.equals(""))){
                    Log.i("item selected",item);
                    Log.i("voucher code",voucherCode);
                }
            }
        });
        dialogFragment.show(getFragmentManager(), "Voucher");
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
}
