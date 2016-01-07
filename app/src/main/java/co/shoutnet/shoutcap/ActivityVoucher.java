package co.shoutnet.shoutcap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class ActivityVoucher extends AppCompatActivity {

    private List<ModelVoucher> vouchers;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        initView();
        initToolbar();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        initData();
        initAdapter();
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar_voucher);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_voucher);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Voucher");
    }

    private void initData() {
        vouchers = new Dummy().getVoucher();
    }

    private void initAdapter() {
        VoucherAdapter adapter = new VoucherAdapter(vouchers);
        recyclerView.setAdapter(adapter);
    }
}
