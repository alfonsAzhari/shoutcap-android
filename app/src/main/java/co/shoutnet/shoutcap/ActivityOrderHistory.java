package co.shoutnet.shoutcap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class ActivityOrderHistory extends AppCompatActivity {

    private List<ModelOrderHistory> orderHistories;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        initView();
        initToolbar();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        initData();
        initAdapter();
    }

    private void initView() {
        recyclerView = (RecyclerView)findViewById(R.id.recycler_order_history);
        toolbar = (Toolbar)findViewById(R.id.toolbar_order_history);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order History");
    }

    private void initData() {
        orderHistories = new Dummy().getOrderHistory();
    }

    private void initAdapter() {
        OrderHistoryAdapter adapter = new OrderHistoryAdapter(orderHistories);
        recyclerView.setAdapter(adapter);
    }
}
