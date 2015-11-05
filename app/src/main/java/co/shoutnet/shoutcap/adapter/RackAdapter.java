package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.ModelAdapterRack;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class RackAdapter extends RecyclerView.Adapter<RackAdapter.RackViewHolder> {

    List<ModelAdapterRack> racks;
    Context context;

    public RackAdapter(Context context, List<ModelAdapterRack> racks) {
        this.context = context;
        this.racks = racks;
    }

    @Override
    public RackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rack, parent, false);
        RackViewHolder viewHolder = new RackViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RackViewHolder holder, int position) {
        holder.imgRack.setImageURI(racks.get(position).getImgRack());
    }

    @Override
    public int getItemCount() {
        return racks.size();
    }

    public static class RackViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRack;

        public RackViewHolder(View itemView) {
            super(itemView);
            imgRack = (ImageView) itemView.findViewById(R.id.img_item_rack);
        }
    }
}
