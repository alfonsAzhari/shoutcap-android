package co.shoutnet.shoutcap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import co.shoutnet.shoutcap.adapter.CheckoutAdapter;
import co.shoutnet.shoutcap.model.ItemCartModel;
import co.shoutnet.shoutcap.model.ModelResponseCheckout;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.Parser;

public class ActivityCheckout extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView itemList;
    private ssss layoutManager;

    private int[] qtyItem;
    private String jsonResponse;
    private ArrayList<ItemCartModel> caps;
    private ArrayList<ModelResponseCheckout> checkouts;
    private ModelResponseCheckout checkout;

    private TextView txtName;
    private TextView txtphone;
    private TextView txtEmail;
    private TextView txtAddress;

    private TextView txtdiscQtyPersen;
    private TextView txtdiscQtyIDR;
    private TextView txtdiscVoucherPersen;
    private TextView txtdiscVoucherIDR;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initView();

        //get data to view
        Bundle bundle = getIntent().getExtras();
        qtyItem = bundle.getIntArray("qtyItems");
        jsonResponse = bundle.getString("jsonResponse");
        caps = new DBCapsHelper(getApplicationContext()).getItemCart();
        checkout = new ModelResponseCheckout();
        try {
            checkout = Parser.getResultCheckout(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkouts = new ArrayList<>();
        checkouts.add(checkout);

        //requirement item for viewing data
        layoutManager = new ssss(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        CheckoutAdapter adapter = new CheckoutAdapter(caps, qtyItem);

        //set require item to container view
//        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(layoutManager);
        itemList.setAdapter(adapter);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.checkout_toolbar);
        itemList = (RecyclerView) findViewById(R.id.rv_items_checkout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public class ssss extends LinearLayoutManager {

        public ssss(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            Log.i("height", String.valueOf(heightSpec));
            Log.i("height", String.valueOf(View.MeasureSpec.getMode(heightSpec)));
            Log.i("height", String.valueOf(View.MeasureSpec.getSize(heightSpec)));

            int height = state.getItemCount() * View.MeasureSpec.getMode(heightSpec);
            Log.i("new ", String.valueOf(height));
            super.onMeasure(recycler, state, widthSpec, height);
        }
    }
}
