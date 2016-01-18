package co.shoutnet.shoutcap.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.ItemCartModel;

/**
 * Created by Henra SN on 12/13/2015.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private ArrayList<ItemCartModel> caps;
    private int[] qtyItem;
    private byte[] decoded;
    private Bitmap bitmap;

    public CheckoutAdapter(ArrayList<ItemCartModel> capsModels, int[] qtyItem) {
        this.caps = capsModels;
        this.qtyItem = qtyItem;
    }

    @Override
    public CheckoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckoutViewHolder holder, int position) {
        decoded = Base64.decode(caps.get(position).getImage(), Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        String qtyPrice = qtyItem[position] + " x " + convertFormat(caps.get(position).getPrice());
        int subtotal = qtyItem[position] * caps.get(position).getPrice();

        holder.imgCap.setImageBitmap(bitmap);
        holder.txtName.setText(caps.get(position).getName());
        holder.txtSize.setText(caps.get(position).getSize());
        holder.txtQtyPrice.setText(qtyPrice);
        holder.txtSubtotal.setText(convertFormat(subtotal));
    }

    @Override
    public int getItemCount() {
        return caps.size();
    }

    private String convertFormat(int price) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        String strCurrency = currency.format(price).replace("$", "Rp. ");
        return strCurrency;
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCap;
        private TextView txtName;
        private TextView txtSize;
        private TextView txtQtyPrice;
        private TextView txtSubtotal;

        public CheckoutViewHolder(View itemView) {
            super(itemView);
            imgCap = (ImageView) itemView.findViewById(R.id.img_item_checkout);
            txtName = (TextView) itemView.findViewById(R.id.txt_itemname_checkout);
            txtSize = (TextView) itemView.findViewById(R.id.txt_size_checkout);
            txtQtyPrice = (TextView) itemView.findViewById(R.id.txt_qtyprice_checkout);
            txtSubtotal = (TextView) itemView.findViewById(R.id.txt_subtotal_checkout);
        }
    }
}
