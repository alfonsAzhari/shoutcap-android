package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.ModelAdapterCart;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<ModelAdapterCart> carts;
    Context context;
    public CartAdapter(Context context,List<ModelAdapterCart> carts) {
        this.carts=carts;
        this.context=context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        CartViewHolder viewHolder=new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.imgCart.setImageResource(carts.get(position).getImgCart());
        holder.txtProduct.setText(carts.get(position).getTxtProduct());
        holder.txtPrice.setText(carts.get(position).getTxtPrice());
        holder.txtSubTotal.setText(carts.get(position).getTxtSubTotal());
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCart;
         TextView txtProduct;
        TextView txtPrice;
        TextView txtSubTotal;
         CartViewHolder(View itemView) {
            super(itemView);
             imgCart=(ImageView)itemView.findViewById(R.id.img_product_cart);
             txtProduct=(TextView)itemView.findViewById(R.id.txt_product_cart);
             txtPrice=(TextView)itemView.findViewById(R.id.txt_price_cart);
             txtSubTotal=(TextView)itemView.findViewById(R.id.txt_total_cart);
        }
    }
}
