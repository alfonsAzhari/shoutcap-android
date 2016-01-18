package co.shoutnet.shoutcap.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.ModelAdapterRack;
import co.shoutnet.shoutcap.utility.RackItemDialog;

/**
 * Created by Codelabs on 9/14/2015.
 */
public class RackAdapter extends RecyclerView.Adapter<RackAdapter.RackViewHolder> {

    ArrayList<ModelAdapterRack> racks;
    Context context;
    RackListener listener;

    public RackAdapter(Context context, ArrayList<ModelAdapterRack> racks, RackListener listener) {
        this.context = context;
        this.racks = racks;
        this.listener = listener;
    }

    @Override
    public RackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rack, parent, false);
        RackViewHolder viewHolder = new RackViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RackViewHolder holder, final int position) {
        byte[] decoded = Base64.decode(racks.get(position).getImgRack(), Base64.DEFAULT);
        final Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
        holder.imgRack.setImageBitmap(bitmap);
        holder.imgRack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = RackItemDialog.newInstance(bitmap, new RackItemDialog.RackDialogListener() {
                    @Override
                    public void onAction(char action) {
                        listener.testing(racks.get(position).getId(), action);
                    }
                });
                Activity activity = (Activity) context;
                dialogFragment.show(activity.getFragmentManager(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return racks.size();
    }

    public interface RackListener {
        void testing(String id, char act);
    }

    public static class RackViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRack;

        public RackViewHolder(View itemView) {
            super(itemView);
            imgRack = (ImageView) itemView.findViewById(R.id.img_item_rack);
        }
    }
}
