package co.shoutnet.shoutcap;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Adam MB on 12/24/2015.
 */
public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView voucherCode;
        TextView discount;
        TextView expire;
        TextView useAtOrder;
        SwitchCompat status;

        public VoucherViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_voucher);
            voucherCode = (TextView) itemView.findViewById(R.id.text_voucher_code_voucher);
            discount = (TextView) itemView.findViewById(R.id.text_discount_voucher);
            expire = (TextView) itemView.findViewById(R.id.text_expire_voucher);
            useAtOrder = (TextView) itemView.findViewById(R.id.text_use_at_order_voucher);
            status = (SwitchCompat) itemView.findViewById(R.id.switch_status_voucher);
        }
    }

    List<ModelVoucher> vouchers;

    VoucherAdapter(List<ModelVoucher> vouchers) {
        this.vouchers = vouchers;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public VoucherAdapter.VoucherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher, parent, false);
        VoucherViewHolder viewHolder = new VoucherViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VoucherAdapter.VoucherViewHolder holder, int position) {
        holder.voucherCode.setText(vouchers.get(position).getVoucherCode());
        holder.discount.setText(vouchers.get(position).getDiscount());
        holder.expire.setText(vouchers.get(position).getExpire().toString());
        holder.useAtOrder.setText(vouchers.get(position).getUseAtOrder());
        holder.status.setChecked(vouchers.get(position).isStatus());
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }
}
