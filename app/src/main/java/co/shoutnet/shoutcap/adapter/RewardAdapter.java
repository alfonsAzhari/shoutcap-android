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
import co.shoutnet.shoutcap.model.ModelAdapterReward;

/**
 * Created by Codelabs on 9/3/2015.
 */
public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {

    private Context context;
    private List<ModelAdapterReward> rewards;

    public RewardAdapter(Context context, ArrayList<ModelAdapterReward> rewards) {
        this.context = context;
        this.rewards = rewards;
    }

    @Override
    public RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reward, parent, false);
        RewardViewHolder rewardViewHolder = new RewardViewHolder(view);

        return rewardViewHolder;
    }

    @Override
    public void onBindViewHolder(RewardViewHolder holder, int position) {
        holder.txtTitle.setText(rewards.get(position).getTitle());
        holder.txtDate.setText(rewards.get(position).getDate());
        holder.txtCoin.setText(rewards.get(position).getCoin());
        holder.txtPoint.setText(rewards.get(position).getPoint());
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    public static class RewardViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDate;
        TextView txtCoin;
        TextView txtPoint;

        public RewardViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_reward_title);
            txtDate = (TextView) itemView.findViewById(R.id.txt_reward_date);
            txtCoin = (TextView) itemView.findViewById(R.id.txt_reward_coin);
            txtPoint = (TextView) itemView.findViewById(R.id.txt_reward_point);
        }
    }
}
