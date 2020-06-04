package com.digger.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.R;
import com.digger.app.model.Alert;
import com.digger.app.model.News;
import com.digger.app.ui.news.ArticleClickListner;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArticleClickListner listener;




    private ArrayList<News> news = new ArrayList<>();

    public void setNews(ArrayList<News> news) {
        this.news = news;
    }

    public void setListener(ArticleClickListner listner) {
        this.listener = listner;
    }

    @NonNull
    @Override

    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        MaterialCardView alertViewItem = (MaterialCardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new NewsAdapter.ViewHolder(alertViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, final int position) {
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(news.get(position));
            }
        });
        holder.setAlertItemView(news.get(position));

    }

    @Override
    public int getItemCount() {
        return news.size();
    }



    class ViewHolder extends  RecyclerView.ViewHolder{

        private MaterialCardView newsItem;
        private View.OnClickListener clickListener;

        void setOnClickListener(View.OnClickListener onClickListener) {
            clickListener = onClickListener;
        }

        void setAlertItemView(News news) {

            TextView title = newsItem.findViewById(R.id.title);
            title.setText(news.getTitle());

            TextView source = newsItem.findViewById(R.id.source);
            source.setText(news.getSource());

            TextView description = newsItem.findViewById(R.id.description);
            description.setText(news.getDescription());


            ImageView image = newsItem.findViewById(R.id.image);

            Picasso.get().load(news.getImageUrl()).into(image);

            newsItem.setOnClickListener(clickListener);

        }
        ViewHolder(@NonNull MaterialCardView itemView) {
            super(itemView);
            this.newsItem = itemView;
        }

    }


}
