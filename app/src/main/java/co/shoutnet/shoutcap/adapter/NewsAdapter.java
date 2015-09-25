package co.shoutnet.shoutcap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.shoutnet.shoutcap.R;
import co.shoutnet.shoutcap.model.ModelAdapterNews;

/**
 * Created by Codelabs on 8/24/2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<ModelAdapterNews> news;
    private Context context;

    public NewsAdapter(Context context, ArrayList<ModelAdapterNews> news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.title.setText(news.get(position).getTitle());
        holder.date.setText(news.get(position).getDate());
        holder.photo.setImageResource(news.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        ImageView photo;

        public NewsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_news_title);
            date = (TextView) itemView.findViewById(R.id.txt_news_date);
            photo = (ImageView) itemView.findViewById(R.id.img_news_photo);
        }
    }
}
