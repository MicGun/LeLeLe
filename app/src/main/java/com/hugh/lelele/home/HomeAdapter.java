package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.ImageManager;

import java.util.ArrayList;
import java.util.Collections;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeAdapter extends RecyclerView.Adapter {

    private static final int INVITATION = 0x01;
    private static final int GENERAL = 0x02;
    private static final int ELECTRICITY = 0x03;
    private static final int SYSTEM = 0x04;

    private HomeContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;

    public HomeAdapter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {

        if (mArticles.get(position).getType().equals(Constants.INVITATION)) {
            return INVITATION;
        } else if (mArticles.get(position).getType().equals(Constants.GENERAL)){
            return GENERAL;
        } else if (mArticles.get(position).getType().equals(Constants.ELECTRICITY)){
            return ELECTRICITY;
        } else if (mArticles.get(position).getType().equals(Constants.SYSTEM)) {
            return SYSTEM;
        } else {
            return GENERAL;
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
            case GENERAL:
                recyclerView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_home_general, viewGroup, false);
                viewHolder = new HomeGeneralViewHolder(recyclerView);
                break;
            case ELECTRICITY:
                recyclerView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_home_electricity, viewGroup, false);
                viewHolder = new HomeElectricityViewHolder(recyclerView);
                break;
            case SYSTEM:
                recyclerView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_home_system, viewGroup, false);
                viewHolder = new HomeSystemViewHolder(recyclerView);
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
        } else if (viewHolder instanceof HomeGeneralViewHolder) {

            bindHomeGeneralViewHolder((HomeGeneralViewHolder) viewHolder, mArticles.get(i));
        } else if (viewHolder instanceof HomeElectricityViewHolder) {

            bindHomeElectricityViewHolder((HomeElectricityViewHolder) viewHolder, mArticles.get(i));
        } else if (viewHolder instanceof HomeSystemViewHolder) {
            bindHomeSystemViewHolder((HomeSystemViewHolder) viewHolder, mArticles.get(i));
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

    private void bindHomeGeneralViewHolder(HomeGeneralViewHolder viewHolder, final Article article) {

        ImageManager.getInstance().setImageByUrl(viewHolder.getAuthorPicture(), article.getAuthorPicture());

        viewHolder.getTitle().setText(article.getTitle());

        viewHolder.getContent().setText(article.getContent());

        viewHolder.getTime().setText(article.getTime());
    }

    private void bindHomeElectricityViewHolder(HomeElectricityViewHolder viewHolder, final Article article) {

        ImageManager.getInstance().setImageByUrl(viewHolder.getAuthorPicture(), article.getAuthorPicture());

        viewHolder.getTitle().setText(article.getTitle());

        viewHolder.getContent().setText(article.getContent());

        viewHolder.getTime().setText(article.getTime());

        viewHolder.getUnderstandButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteElectricityNotification(article);
            }
        });
    }

    private void bindHomeSystemViewHolder(HomeSystemViewHolder viewHolder, final Article article) {

        viewHolder.getTitle().setText(article.getTitle());

        viewHolder.getContent().setText(article.getContent());

        viewHolder.getTime().setText(article.getTime());

        viewHolder.getAuthorPicture().setBackground(LeLeLe.getAppContext().getDrawable(R.drawable.ic_notification));

        viewHolder.getUnderstandButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteElectricityNotification(article);
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

    public class HomeGeneralViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAuthorPicture;
        private TextView mTitle;
        private TextView mContent;
        private TextView mTime;

        public HomeGeneralViewHolder(@NonNull View itemView) {
            super(itemView);

            mAuthorPicture = itemView.findViewById(R.id.image_author_picture_posting);
            mAuthorPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(LeLeLe.getAppContext().
                    getResources().getDimensionPixelSize(R.dimen.radius_general_post_avatar)));
            mTitle = itemView.findViewById(R.id.item_text_view_general_title_posting);
            mContent = itemView.findViewById(R.id.item_text_view_general_content);
            mTime = itemView.findViewById(R.id.item_text_view_time_general);
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

        public TextView getTime() {
            return mTime;
        }
    }

    public class HomeElectricityViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAuthorPicture;
        private TextView mTitle;
        private TextView mContent;
        private TextView mTime;
        private ImageView mUnderstandButton;

        public HomeElectricityViewHolder(@NonNull View itemView) {
            super(itemView);

            mAuthorPicture = itemView.findViewById(R.id.image_author_picture_posting);
            mAuthorPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(LeLeLe.getAppContext().
                    getResources().getDimensionPixelSize(R.dimen.radius_general_post_avatar)));
            mTitle = itemView.findViewById(R.id.item_text_view_electricity_title_posting);
            mContent = itemView.findViewById(R.id.item_text_view_electricity_content);
            mTime = itemView.findViewById(R.id.item_text_view_time_electricity);
            mUnderstandButton = itemView.findViewById(R.id.item_image_view_understand_electricity_fee_article);
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

        public TextView getTime() {
            return mTime;
        }

        public ImageView getUnderstandButton() {
            return mUnderstandButton;
        }
    }

    public class HomeSystemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAuthorPicture;
        private TextView mTitle;
        private TextView mContent;
        private TextView mTime;
        private ImageView mUnderstandButton;

        public HomeSystemViewHolder(@NonNull View itemView) {
            super(itemView);

            mAuthorPicture = itemView.findViewById(R.id.image_notify_icon);
            mTitle = itemView.findViewById(R.id.item_text_view_system_posting);
            mContent = itemView.findViewById(R.id.item_text_view_system_content);
            mTime = itemView.findViewById(R.id.item_text_view_time_system);
            mUnderstandButton = itemView.findViewById(R.id.item_image_view_understand_system_button);
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

        public TextView getTime() {
            return mTime;
        }

        public ImageView getUnderstandButton() {
            return mUnderstandButton;
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
        Collections.reverse(mArticles);
        notifyDataSetChanged();
    }
}
