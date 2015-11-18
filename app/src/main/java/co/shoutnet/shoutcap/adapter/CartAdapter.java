package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import co.shoutnet.shoutcap.CartActivity;
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
    long subTotal;
    long total;

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
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
//        price = carts.get(position).getPrice();
//        Log.i("price " + position, String.valueOf(price));
//        total = CartActivity.getTotal();
//        total += subTotal;
//        CartActivity.setTotal(total);
        subTotal = carts.get(position).getSubTotal();
        holder.imgCart.setImageURI(Uri.parse(carts.get(position).getImage()));
        holder.txtProduct.setText(carts.get(position).getName());
        holder.txtPrice.setText(Integer.toString(carts.get(position).getPrice()));
        holder.txtSubTotal.setText(CartActivity.getCurrency(subTotal));
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long subTotal = carts.get(position).getSubTotal();
                count = Integer.parseInt(String.valueOf(holder.edtQty.getText()));
                if (count < 999) {
                    count += 1;
                    subTotal += carts.get(position).getPrice() ;
                    carts.get(position).setSubTotal(subTotal);
                    holder.edtQty.setText(String.valueOf(count));
                    holder.txtSubTotal.setText(CartActivity.getCurrency(subTotal));
                    total = CartActivity.getTotal();
                    total += carts.get(position).getPrice();
                    CartActivity.setTotal(total);
                }
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long subTotal = carts.get(position).getSubTotal();
                count = Integer.parseInt(String.valueOf(holder.edtQty.getText()));
                if (count > 1) {
                    count -= 1;
                    subTotal -= carts.get(position).getPrice() ;
                    carts.get(position).setSubTotal(subTotal);
                    holder.edtQty.setText(String.valueOf(count));
                    holder.txtSubTotal.setText(CartActivity.getCurrency(subTotal));
                    total = CartActivity.getTotal();
                    total -= carts.get(position).getPrice();
                    CartActivity.setTotal(total);
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

    private void onRemoveList(int position) {
        carts.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onPendingDismiss(final int position) {


    }

    public static class CartViewHolder extends RecyclerView.ViewHolder implements ListCallback.ItemTouchHelperViewHolder {
        ImageView imgCart;
        TextView txtProduct;
        TextView txtPrice;
        TextView txtSubTotal;
        ImageView btnPlus;
        ImageView btnMinus;
        EditText edtQty;

        CartViewHolder(View itemView) {
            super(itemView);
            imgCart = (ImageView) itemView.findViewById(R.id.img_product_cart);
            btnPlus = (ImageView) itemView.findViewById(R.id.btn_plus_cart);
            btnMinus = (ImageView) itemView.findViewById(R.id.btn_minus_cart);
            txtProduct = (TextView) itemView.findViewById(R.id.txt_product_cart);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price_cart);
            txtSubTotal = (TextView) itemView.findViewById(R.id.txt_total_cart);
            edtQty = (EditText) itemView.findViewById(R.id.edt_count_cart);
        }

        @Override
        public void onItemSelected() {

        }

        @Override
        public void onItemClear() {

        }
    }
}
