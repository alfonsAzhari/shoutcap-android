package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.ModelAdapterCart;
import co.shoutnet.shoutcap.utility.ListCallback;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements ListCallback.ItemTouchHelperAdapter {

    List<ModelAdapterCart> carts;
    Context context;
    int count;
    long price=3000000;

    public CartAdapter(Context context, List<ModelAdapterCart> carts) {
        this.carts = carts;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position) {
        holder.imgCart.setImageResource(carts.get(position).getImgCart());
        holder.txtProduct.setText(carts.get(position).getTxtProduct());
        holder.txtPrice.setText(carts.get(position).getTxtPrice());
        holder.txtSubTotal.setText(carts.get(position).getTxtSubTotal());
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(String.valueOf(holder.edtCount.getText()));
                if (count < 999) {
                    count += 1;
                    holder.edtCount.setText(Integer.toString(count));
                    price*=count;
                    Log.i("total", String.valueOf(price));
                    holder.txtSubTotal.setText("Rp. "+Long.toString(price));
                }
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(String.valueOf(holder.edtCount.getText()));
                if (count >1) {
                    holder.edtCount.setText(Integer.toString(count));
                    price/=count;
                    count -= 1;
                    Log.i("total", String.valueOf(price));
                    holder.txtSubTotal.setText("Rp. "+Long.toString(price));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    @Override
    public void onItemDismiss(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create();
        builder.setTitle("Confirmation Delete").setMessage("Are you sure to delete this item?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onRemoveList(position);
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private void onRemoveList(int position){
        carts.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onPendingDismiss(final int position) {


    }

    public static class CartViewHolder extends RecyclerView.ViewHolder implements ListCallback.ItemTouchHelperViewHolder{
        ImageView imgCart;
        TextView txtProduct;
        TextView txtPrice;
        TextView txtSubTotal;
        ImageView btnPlus;
        ImageView btnMinus;
        EditText edtCount;

        CartViewHolder(View itemView) {
            super(itemView);
            imgCart = (ImageView) itemView.findViewById(R.id.img_product_cart);
            btnPlus = (ImageView) itemView.findViewById(R.id.btn_plus_cart);
            btnMinus = (ImageView) itemView.findViewById(R.id.btn_minus_cart);
            txtProduct = (TextView) itemView.findViewById(R.id.txt_product_cart);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price_cart);
            txtSubTotal = (TextView) itemView.findViewById(R.id.txt_total_cart);
            edtCount = (EditText) itemView.findViewById(R.id.edt_count_cart);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }
}
