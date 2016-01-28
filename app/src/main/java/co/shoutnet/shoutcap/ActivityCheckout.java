package co.shoutnet.shoutcap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.CheckoutAdapter;
import co.shoutnet.shoutcap.model.ItemCartModel;
import co.shoutnet.shoutcap.model.ModelResponseCheckout;
import co.shoutnet.shoutcap.utility.CustomLinearLayoutManager;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.Loading;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.SessionManager;
import co.shoutnet.shoutcap.utility.VolleyRequest;

public class ActivityCheckout extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView itemList;
    private CustomLinearLayoutManager layoutManager;

    private int[] qtyItem;
    private String jsonResponse;
    private ArrayList<ItemCartModel> caps;
    private ArrayList<ModelResponseCheckout> checkouts;
    private ModelResponseCheckout checkout;
    private Bundle consigneeBundle;

    private TextView txtName;
    private TextView txtphone;
    private TextView txtEmail;
    private TextView txtAddress;

    private TextView txtdiscQtyPersen;
    private TextView txtdiscQtyIDR;
    private TextView txtdiscVoucherPersen;
    private TextView txtdiscVoucherIDR;
    private TextView txtCostDelivery;
    private TextView txtTotal;

    private Button btnConfirm;
    private ProgressDialog loading;

    private SessionManager manager;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initView();
        manager = new SessionManager(getApplicationContext());
        user = manager.getUserDetails();

        //get data to view
        Bundle bundle = getIntent().getExtras();
        qtyItem = bundle.getIntArray("qtyItems");
        jsonResponse = bundle.getString("jsonResponse");
        consigneeBundle = bundle.getBundle("bundle");
        caps = new DBCapsHelper(getApplicationContext()).getItemCart();
        checkout = new ModelResponseCheckout();
        try {
            checkout = Parser.getResultCheckout(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkouts = new ArrayList<>();
        checkouts.add(checkout);

        //bind data
        bindData(checkouts);

        //requirement component for viewing data
        layoutManager = new CustomLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        CheckoutAdapter adapter = new CheckoutAdapter(caps, qtyItem);

        //set require item to container view
        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(layoutManager);
        itemList.setAdapter(adapter);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show();
                String url = "https://api.shoutnet.co/shoutcap/order_create.php";

                Map<String, String> params = new HashMap<>();
                params.put("shoutid", user.get("shoutId"));
                params.put("sessionid", user.get("sessionId"));
//                params.put("shoutid", "devtest");
//                params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
                params.put("from", "app");
                params.put("id_checkout", checkout.getItem().getId_checkout());

                new VolleyRequest().request(getApplicationContext(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
                    @Override
                    public void OnSuccess(String response) {
                        loading.dismiss();
                        new DBCapsHelper(getApplicationContext()).deleteCartData();
                        Intent intent = new Intent(ActivityCheckout.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void OnFailure() {
                        loading.dismiss();
                        Toast.makeText(ActivityCheckout.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("qty", qtyItem);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    private void bindData(ArrayList<ModelResponseCheckout> checkouts) {
        ModelResponseCheckout.Item item = checkouts.get(0).getItem();
        Log.i("bundle name", consigneeBundle.getString("phone"));

        txtName.setText(consigneeBundle.getString("name"));
        txtphone.setText(consigneeBundle.getString("phone"));
        txtEmail.setText(consigneeBundle.getString("email"));
        txtAddress.setText(consigneeBundle.getString("address"));

        txtdiscQtyPersen.setText(item.getDisc_qty_persen());
        txtdiscQtyIDR.setText(item.getDisc_qty_idr());
        txtdiscVoucherPersen.setText(item.getDisc_voucher_persen());
        txtdiscVoucherIDR.setText(item.getDisc_voucher_idr());
        txtCostDelivery.setText(item.getOngkos_kirim());
        txtTotal.setText(item.getTotal());
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.checkout_toolbar);
        itemList = (RecyclerView) findViewById(R.id.rv_items_checkout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = (TextView) findViewById(R.id.txt_name_checkout);
        txtphone = (TextView) findViewById(R.id.txt_phone_checkout);
        txtEmail = (TextView) findViewById(R.id.txt_email_checkout);
        txtAddress = (TextView) findViewById(R.id.txt_address_checkout);

        txtdiscQtyPersen = (TextView) findViewById(R.id.txt_discQtyPercent_checkout);
        txtdiscQtyIDR = (TextView) findViewById(R.id.txt_discQtyIDR_checkout);
        txtdiscVoucherPersen = (TextView) findViewById(R.id.txt_discVoucherPercent_checkout);
        txtdiscVoucherIDR = (TextView) findViewById(R.id.txt_discVoucherIDR_checkout);
        txtCostDelivery = (TextView) findViewById(R.id.txt_costDelivery_checkout);
        txtTotal = (TextView) findViewById(R.id.txt_total_checkout);

        btnConfirm = (Button) findViewById(R.id.btn_confirm_checkout);
        loading = Loading.newInstance(ActivityCheckout.this);
        loading.setMessage("Sending confirmation");
        this.setTitle("Checkout");
    }
}
