package com.example.newsupdates;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>{

    List<Article> articleList;
    NewsRecyclerAdapter(List<Article> articleList){
        this.articleList = articleList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.article_title.setText(article.getTitle());
        holder.article_source.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.no_image_icon)
                .placeholder(R.drawable.no_image_icon)
                .into(holder.article_image_view);

        holder.itemView.setOnClickListener((view -> {
            Intent intent = new Intent(view.getContext(),NewsFullActivity.class);
            intent.putExtra("url",article.getUrl());
            view.getContext().startActivity(intent);
        }));

    }

    void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);

    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView article_title,article_source;
        ImageView article_image_view;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            article_title=itemView.findViewById(R.id.article_title);
            article_source=itemView.findViewById(R.id.article_source);
            article_image_view=itemView.findViewById(R.id.article_image_view);
        }
    }
}
