package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.shoutnet.shoutcap.model.ModelAdapterOrderHistory;
import co.shoutnet.shoutcap.R;

/**
 * Created by Adam MB on 12/24/2015.
 */
public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private ArrayList<ModelAdapterOrderHistory> orderHistories;
    private Context context;

    public static class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView orderID;
        TextView price;
        TextView paymentStatus;
        TextView productStatus;

        public OrderHistoryViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_order_history);
            orderID = (TextView) itemView.findViewById(R.id.text_order_id_order_history);
            price = (TextView) itemView.findViewById(R.id.text_price_order_history);
            paymentStatus = (TextView) itemView.findViewById(R.id.text_payment_status_order_history);
            productStatus = (TextView) itemView.findViewById(R.id.text_product_status_order_history);
        }
    }

    public OrderHistoryAdapter(Context context, ArrayList<ModelAdapterOrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
        this.context = context;
    }

//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }

    @Override
    public OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false);
        OrderHistoryViewHolder viewHolder = new OrderHistoryViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderHistoryViewHolder holder, int position) {
        holder.orderID.setText(orderHistories.get(position).getId_order());
        holder.price.setText(String.valueOf(orderHistories.get(position).getPrice()));
        holder.paymentStatus.setText(orderHistories.get(position).getPayment_status());
        holder.productStatus.setText(orderHistories.get(position).getProduction_status());
    }

    @Override
    public int getItemCount() {
        return orderHistories.size();
    }
}
