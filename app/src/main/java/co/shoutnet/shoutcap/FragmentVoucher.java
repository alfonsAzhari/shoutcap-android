package co.shoutnet.shoutcap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.shoutnet.shoutcap.adapter.VoucherAdapter;
import co.shoutnet.shoutcap.model.ModelVoucher;

public class FragmentVoucher extends Fragment {

    private Context mContext;

    private List<ModelVoucher> vouchers;
    private RecyclerView recyclerView;

    private VoucherAdapter adapter;

    public FragmentVoucher(){

    }

    public static FragmentVoucher newInstance() {

        Bundle args = new Bundle();

        FragmentVoucher fragment = new FragmentVoucher();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voucher, container, false);
        mContext = getActivity();

        initView(rootView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_voucher);
    }

    private void initData() {
        vouchers = new Dummy().getVoucher();
    }

    private void initAdapter() {
        adapter = new VoucherAdapter(vouchers);
        recyclerView.setAdapter(adapter);
    }
}
