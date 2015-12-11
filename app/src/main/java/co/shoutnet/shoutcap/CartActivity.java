package co.shoutnet.shoutcap;

import android.app.DialogFragment;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import co.shoutnet.shoutcap.adapter.CartAdapter;
import co.shoutnet.shoutcap.model.ModelAdapterCart;
import co.shoutnet.shoutcap.model.ModelOnlyResult;
import co.shoutnet.shoutcap.model.ModelQty;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.Parser;
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

        modelAdapterCarts = new DBCapsHelper(getApplicationContext()).getCartData();
        total = 0;
        capName = new String[modelAdapterCarts.size()];
        for (int i = 0; i < modelAdapterCarts.size(); i++) {
            capName[i] = modelAdapterCarts.get(i).getName();
            total += modelAdapterCarts.get(i).getPrice();
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

        RecyclerSwipeTouchListener touchListener = new RecyclerSwipeTouchListener(getApplicationContext(), recyclerView, R.id.main_view, R.id.background_view, new RecyclerSwipeTouchListener.SwipeListener() {
            @Override
            public boolean canSwipe(int position) {
                return true;
            }

            @Override
            public void onDismiss(RecyclerView recyclerView, int[] reversePositions) {
                for (int position : reversePositions) {
                    String id = String.valueOf(modelAdapterCarts.get(position).getId());
                    new DeleteCap().deleteData("https://api.shoutnet.co/shoutcap/delete_cart.php", id, position, new CartListenter() {
                        @Override
                        public void OnSuccess(String response, int pos) {
                            ModelOnlyResult modelOnlyResult = new ModelOnlyResult();
                            try {
                                modelOnlyResult = Parser.getResult(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (modelOnlyResult.getResult().equals("success") && modelOnlyResult != null) {
                                new DBCapsHelper(getApplicationContext()).deleteCartData(modelAdapterCarts.get(pos).getId());
                                Log.i("id remove", String.valueOf(modelAdapterCarts.get(pos).getId()));
                                adapter.notifyItemRemoved(pos);
                                modelAdapterCarts.remove(pos);
                                total = 0;
                                capName = new String[modelAdapterCarts.size()];
                                for (int i = 0; i < modelAdapterCarts.size(); i++) {
                                    capName[i] = modelAdapterCarts.get(i).getName();
                                    total += modelAdapterCarts.get(i).getPrice() * modelAdapterCarts.get(i).getQty();
                                    setTotal(total);
                                }
                            }
                        }

                        @Override
                        public void OnFaliure() {

                        }
                    });
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
                if (item != null) {
                    Log.i("item selected", item);
                    List<String> data = new DBCapsHelper(getApplicationContext()).getCart();
                    List<ModelQty> obj = Collections.synchronizedList(new ArrayList<ModelQty>());
                    ModelQty modelQty;
                    for (int i = 0; i < data.size(); i++) {
                        modelQty = new ModelQty();
                        modelQty.setId(data.get(i));
                        modelQty.setQty(i);
                        obj.add(modelQty);
                    }
                    try {
                        Log.i("String", Parser.getJsonCart(obj));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
                    startActivity(intent);
                }
            }

            @Override
            public void resultItemVoucher(String item, String voucherCode) {
                if (item != null && voucherCode != null) {
                    Log.i("item selected", item);
                    Log.i("voucher code", voucherCode);
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

    public interface CartListenter {
        void OnSuccess(String response, int pos);

        void OnFaliure();
    }

    private class DeleteCap {
        public void deleteData(String url, final String id, final int pos, final CartListenter listenter) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("response", response);
                    listenter.OnSuccess(response, pos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error", error.getMessage());
//                    capsResult.OnFailure(error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("shoutid", "hanswdd");
                    params.put("sessionid", "921782ced844a755128539a4c05e99cc");
                    params.put("id_cart", id);
                    return params;
                }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String,String> params=new HashMap<>();
//                    params.put("Content-Type", "application/x-www-form-urlencoded");
//                    return params;
//                }
            };

            RetryPolicy retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest);
        }
    }
}
