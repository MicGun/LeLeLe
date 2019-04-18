package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hugh.lelele.Constants;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Article;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeAdapter extends RecyclerView.Adapter {

    private static final int INVITATION = 0x01;

    private HomeContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;

    public HomeAdapter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class HomeInvitationViewHolder extends RecyclerView.ViewHolder {

        ImageView mAuthorPicture;

        public HomeInvitationViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mArticles.get(position).getType().equals(Constants.INVITATION)) {
            return INVITATION;
        } else {
            return INVITATION;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View recyclerView;

        switch (viewType) {
            case INVITATION:
                recyclerView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_home_invitation, viewGroup, false);
                viewHolder = new HomeInvitationViewHolder(recyclerView);
                break;
            default:
                return null;

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        if (mArticles != null) {
            return mArticles.size();
        } else {
            return 0;
        }
    }

    public void updateArticles(ArrayList<Article> articles) {
        mArticles = checkNotNull(articles);
        notifyDataSetChanged();
    }
}
