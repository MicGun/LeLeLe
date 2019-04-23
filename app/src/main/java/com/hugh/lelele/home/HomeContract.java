package com.hugh.lelele.home;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Article;

import java.util.ArrayList;

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void setArticleList(ArrayList<Article> articleList);

        void setPostingButtonUi();

    }

    interface Presenter extends BasePresenter {

        void loadArticles();

        void cancelInvitation(Article article);

        void agreeInvitation(Article article);

        void setPostingButton();

        void openPosting();

        void loadUserArticles();

        void loadGroupArticles();

        void deleteElectricityNotification(Article article);

//        void uploadLandlord(String email);
    }
}
