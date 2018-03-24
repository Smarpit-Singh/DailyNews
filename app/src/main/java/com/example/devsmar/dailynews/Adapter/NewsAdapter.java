package com.example.devsmar.dailynews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.devsmar.dailynews.Model.NewsFeed;
import com.example.devsmar.dailynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev Smar on 3/23/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final NewsClickListener newsClickListener;

    public interface NewsClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    private Context context;

    private List<NewsFeed> newsFeeds;

    public NewsAdapter(Context context, List<NewsFeed> newsFeeds , NewsClickListener newsClickListener) {
        this.context = context;
        this.newsFeeds = newsFeeds;
        this.newsClickListener = newsClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        NewsFeed item = newsFeeds.get(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.name.setText(item.getSectioName());
        viewHolder.date.setText(item.getPublicationDate());
        viewHolder.url.setText(item.getWebUrl());
        Glide.with(context)
                .load(item.getImgUrl())
                .into(viewHolder.img);

    }

    @Override
    public int getItemCount() {
        return newsFeeds.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView date;
        TextView title;
        TextView url;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.publicationDate);
            name = itemView.findViewById(R.id.sectionName);
            url = itemView.findViewById(R.id.weburl);
            img = itemView.findViewById(R.id.newsimage1);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            newsClickListener.onClick(position);
        }
    }
}
