package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hugh.lelele.data.Article;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter {

    private ArrayList<Article> mArticles;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void updateArticles(ArrayList<Article> articles) {
        mArticles = articles;
    }
}
