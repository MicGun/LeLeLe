package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final HomeContract.View mHomeView;

    private final static String TAG = HomePresenter.class.getSimpleName();

    public HomePresenter(@NonNull LeLeLeRepository leLeLeRepository,
                         @NonNull HomeContract.View homeView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mHomeView = checkNotNull(homeView, "homeView cannot be null!");
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadArticles() {

        mLeLeLeRepository.getUserArticles(UserManager.getInstance().getUserData().getEmail(), new LeLeLeDataSource.GetUserArticlesCallback() {
            @Override
            public void onCompleted(ArrayList<Article> articles) {
                mHomeView.setArticleList(articles);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
//
//    @Override
//    public void uploadLandlord(String email) {
//        mLeLeLeRepository.updateLandlordUser(email, new LeLeLeDataSource.LandlordUserCallback() {
//            @Override
//            public void onCompleted(Landlord landlord) {
//                UserManager.getInstance().setLandlord(landlord);
//                Log.v("HomePresenter", "Landlord: " + UserManager.getInstance().getLandlord().getIdCardNumber());
//                Log.v("HomePresenter", "Landlord: " + landlord.getIdCardNumber());
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//
//            }
//        });
//    }
}
