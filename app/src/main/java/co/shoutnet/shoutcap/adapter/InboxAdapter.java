package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.ModelAdapterInbox;

/**
 * Created by Codelabs on 8/26/2015.
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {

    private List<ModelAdapterInbox> inbox;
    private Context context;

    public InboxAdapter(Context context, ArrayList<ModelAdapterInbox> inbox) {
        this.context = context;
        this.inbox = inbox;
    }

    @Override
    public InboxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inbox, parent, false);
        InboxViewHolder viewHolder = new InboxViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InboxViewHolder holder, int position) {
        holder.title.setText(inbox.get(position).getTitle());
        holder.date.setText(inbox.get(position).getDate());
        holder.desc.setText(inbox.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return inbox.size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;
        private TextView desc;

        public InboxViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_inbox_title);
            date = (TextView) itemView.findViewById(R.id.txt_inbox_date);
            desc = (TextView) itemView.findViewById(R.id.txt_inbox_desc);
        }
    }
}
