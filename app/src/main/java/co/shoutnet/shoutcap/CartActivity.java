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
import android.widget.EditText;

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
import co.shoutnet.shoutcap.model.ModelVoucher;
import co.shoutnet.shoutcap.utility.DBCapsHelper;
import co.shoutnet.shoutcap.utility.Parser;
import co.shoutnet.shoutcap.utility.RecyclerSwipeTouchListener;
import co.shoutnet.shoutcap.utility.VolleyRequest;
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
    private String jsonQty;
    private String jsonVoucher;
    private List<String> data;
    private int[] qtyItem;

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
        if (modelAdapterCarts.size() > 0) {
            btnTotal.setEnabled(true);
        }
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
                    new DBCapsHelper(getApplicationContext()).deleteData(modelAdapterCarts.get(position).getId());
                    modelAdapterCarts.remove(position);
                    total = 0;
                    capName = new String[modelAdapterCarts.size()];
                    Log.i("size", String.valueOf(modelAdapterCarts.size()));
                    if (modelAdapterCarts.size() > 0) {
                        for (int i = 0; i < modelAdapterCarts.size(); i++) {
                            capName[i] = modelAdapterCarts.get(i).getName();
                            total += modelAdapterCarts.get(i).getPrice() * modelAdapterCarts.get(i).getQty();
                            setTotal(total);
                        }
                    } else {
                        capName = null;
                        total = 0;
                        setTotal(total);
                        btnTotal.setEnabled(false);
                    }
                    adapter.notifyItemRemoved(position);
                    recyclerView.removeViewAt(position);

                    String url = "https://api.shoutnet.co/shoutcap/delete_cart.php";
                    Map<String, String> params = new HashMap<>();
                    params.put("shoutid", "devtest");
                    params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
                    params.put("id_cart", id);
//                    new DeleteCap().deleteData("https://api.shoutnet.co/shoutcap/delete_cart.php", params, new CartListenter() {
//                        @Override
//                        public void OnSuccess(String response) {
//
//                        }
//
//                        @Override
//                        public void OnFaliure() {
//
//                        }
//                    });

                    new VolleyRequest().request(getApplicationContext(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
                        @Override
                        public void OnSuccess(String response) {

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
                int size = recyclerView.getChildCount();
                View child;
                EditText editText;
                ModelQty modelQty;
                data = new DBCapsHelper(getApplicationContext()).getCart();
                List<ModelQty> obj = Collections.synchronizedList(new ArrayList<ModelQty>());
                for (int i = 0; i < size; i++) {
                    child = recyclerView.getChildAt(i);
                    editText = (EditText) child.findViewById(R.id.edt_count_cart);

                    modelQty = new ModelQty();
                    modelQty.setId(data.get(i));
                    modelQty.setQty(Integer.parseInt(editText.getText().toString()));
                    obj.add(modelQty);

                    qtyItem = new int[size];
                    qtyItem[i] = Integer.parseInt(editText.getText().toString());
                }
                dialogResult();
//                View v=recyclerView.getChildAt(0);
//                EditText editText=(EditText)v.findViewById(R.id.edt_count_cart);
                try {
                    Log.i("new json", Parser.getJsonCart(obj));
                    jsonQty = Parser.getJsonCart(obj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        SharedPreferences preferences=getApplicationContext().getSharedPreferences("cart",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        editor.putStringSet()
    }

    private void dialogResult() {
        DialogFragment dialogFragment = VoucherDialog.newInstance(capName, new VoucherDialog.DialogListener() {
            @Override
            public void skipVoucher() {
                submitData();
//                Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
//                startActivity(intent);
            }

            @Override
            public void resultItemVoucher(int item, String voucherCode) {
//                if (item != null && voucherCode != null) {
                Log.i("item selected", String.valueOf(item));
                Log.i("voucher code", voucherCode);
                String id = data.get(item);
                Log.i("id", id);
                List<ModelVoucher> toJson = Collections.synchronizedList(new ArrayList<ModelVoucher>());

                ModelVoucher modelVoucher = new ModelVoucher();
                modelVoucher.setId(id);
                modelVoucher.setVoucherCode(voucherCode.trim());

                toJson.add(modelVoucher);

                try {
                    Log.i("json", Parser.getJsonVoucher(toJson));
                    jsonVoucher = Parser.getJsonVoucher(toJson);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                submitData();
//                }
            }
        });
        dialogFragment.show(getFragmentManager(), "Voucher");
    }

    private void submitData() {
        Map<String, String> params = new HashMap<>();
        params.put("shoutid", "devtest");
        params.put("sessionid", "fab19834f4aac1c399b1273245d7b648");
        if (jsonQty != null) {
            Log.i("jsonQty", "not null");
            params.put("qty", jsonQty);
        }
        if (jsonVoucher != null) {
            Log.i("jsonVoucher", "not null");
            params.put("voucher", jsonVoucher);
        } else {
            params.put("voucher", "");
        }

        String url = "https://api.shoutnet.co/shoutcap/update_qty_voucher_cart.php";
//        new DeleteCap().deleteData(url, params, new CartListenter() {
//            @Override
//            public void OnSuccess(String response) {
//                ModelOnlyResult result = new ModelOnlyResult();
//                try {
//                    result = Parser.getResult(response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (result.getResult().equals("success")) {
//                    Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
//                    intent.putExtra("qtyItems", qtyItem);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void OnFaliure() {
//                Toast.makeText(getApplicationContext(), "Upload data is failed", Toast.LENGTH_SHORT).show();
//            }
//        });
        new VolleyRequest().request(getApplicationContext(), Request.Method.POST, url, params, new VolleyRequest.RequestListener() {
            @Override
            public void OnSuccess(String response) {
                ModelOnlyResult result = new ModelOnlyResult();
                try {
                    result = Parser.getResult(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (result.getResult().equals("success")) {
                    Intent intent = new Intent(getApplicationContext(), OrderConfirmation.class);
                    intent.putExtra("qtyItems", qtyItem);
                    startActivity(intent);
                }
            }

            @Override
            public void OnFaliure() {

            }
        });
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
        void OnSuccess(String response);

        void OnFaliure();
    }

    private class DeleteCap {
        public void deleteData(String url, final Map<String, String> param, final CartListenter listenter) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("response", response);
                    listenter.OnSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error", error.getMessage());
                    listenter.OnFaliure();
//                    capsResult.OnFailure(error.getMessage());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Log.i("param", param.get("shoutid"));
                    return param;
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
