package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.Constants;
import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.util.ImageManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeAdapter extends RecyclerView.Adapter {

    private static final int INVITATION = 0x01;

    private HomeContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;

    public HomeAdapter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
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

        if (viewHolder instanceof HomeInvitationViewHolder) {

            bindHomeInvitationViewHolder((HomeInvitationViewHolder) viewHolder, mArticles.get(i));
        }
    }



    private void bindHomeInvitationViewHolder(HomeInvitationViewHolder viewHolder, final Article article) {

        ImageManager.getInstance().setImageByUrl(viewHolder.getAuthorPicture(), article.getAuthorPicture());

        viewHolder.getTitle().setText(article.getTitle());

        viewHolder.getContent().setText(article.getContent());

        viewHolder.getTime().setText(article.getTime());

        viewHolder.getCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cancelInvitation(article);
            }
        });

        viewHolder.getAgreeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.agreeInvitation(article);
            }
        });
    }

    public class HomeInvitationViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAuthorPicture;
        private TextView mTitle;
        private TextView mContent;
        private ImageView mCancelButton;
        private ImageView mAgreeButton;
        private TextView mTime;

        public HomeInvitationViewHolder(@NonNull View itemView) {
            super(itemView);

            mAuthorPicture = itemView.findViewById(R.id.image_author_picture_posting);
            mAuthorPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(LeLeLe.getAppContext().
                    getResources().getDimensionPixelSize(R.dimen.radius_profile_avatar)));
            mTitle = itemView.findViewById(R.id.item_text_view_invitation_title);
            mContent = itemView.findViewById(R.id.item_text_view_invitation_content);
            mTime = itemView.findViewById(R.id.item_text_view_time_invitation);
            mCancelButton = itemView.findViewById(R.id.item_image_view_cancel_button_invitation);
            mAgreeButton = itemView.findViewById(R.id.item_image_view_agree_button_invitation);
        }

        public ImageView getAuthorPicture() {
            return mAuthorPicture;
        }

        public TextView getTitle() {
            return mTitle;
        }

        public TextView getContent() {
            return mContent;
        }

        public ImageView getCancelButton() {
            return mCancelButton;
        }

        public ImageView getAgreeButton() {
            return mAgreeButton;
        }

        public TextView getTime() {
            return mTime;
        }
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
