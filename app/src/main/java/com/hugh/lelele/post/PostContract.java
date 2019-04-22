package com.hugh.lelele.post;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Article;

public interface PostContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void hideBottomNavigation();

        void showBottomNavigation();

        void releaseGroupArticle(Article article);
    }
}
