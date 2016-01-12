package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.model.ModelAdapterVoucher;
import co.shoutnet.shoutcap.model.ModelVoucher;
import co.shoutnet.shoutcap.R;

/**
 * Created by Adam MB on 12/24/2015.
 */
public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    ArrayList<ModelAdapterVoucher> vouchers;
    private Context context;

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {

        TextView voucherCode;
        TextView discount;
        TextView expire;
        TextView useAtOrder;
        TextView status;

        public VoucherViewHolder(View itemView) {
            super(itemView);
            voucherCode = (TextView) itemView.findViewById(R.id.text_voucher_code_voucher);
            discount = (TextView) itemView.findViewById(R.id.text_discount_voucher);
            expire = (TextView) itemView.findViewById(R.id.text_expire_voucher);
            useAtOrder = (TextView) itemView.findViewById(R.id.text_use_at_order_voucher);
            status = (TextView) itemView.findViewById(R.id.text_status_voucher);
        }
    }

    public VoucherAdapter(Context context, ArrayList<ModelAdapterVoucher> vouchers) {
        this.context = context;
        this.vouchers = vouchers;
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }

    @Override
    public VoucherAdapter.VoucherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_voucher, parent, false);
        VoucherViewHolder viewHolder = new VoucherViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VoucherAdapter.VoucherViewHolder holder, int position) {

        holder.voucherCode.setText(vouchers.get(position).getVoucherCode());
        holder.discount.setText(vouchers.get(position).getDiscount() + " to " + vouchers.get(position).getDiscountTo());
        holder.expire.setText(vouchers.get(position).getExpire().toString());
        if (vouchers.get(position).getUseAtOrder().isEmpty()) {
            holder.useAtOrder.setText("Belum digunakan");
        } else {
            holder.useAtOrder.setText(vouchers.get(position).getUseAtOrder());
        }
        holder.status.setText(vouchers.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }
}
