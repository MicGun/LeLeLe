package com.hugh.lelele.post;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class PostPresenter implements PostContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private PostContract.View mPostView;

    public PostPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                         @NonNull  PostContract.View postView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mPostView = checkNotNull(postView, "postView cannot be null!");;
    }

    @Override
    public void start() {

    }

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void releaseGroupArticle(Article article) {
        mLeLeLeRepository.sendGroupArticle(article, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(), new LeLeLeDataSource.SendGroupArticleCallback() {
                    @Override
                    public void onCompleted() {
                        mPostView.toBackStack();
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    @Override
    public void showLastFragment() {

    }

    @Override
    public void updateToolbar(String title) {

    }
}
