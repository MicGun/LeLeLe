package com.hugh.lelele.home;

import android.support.annotation.NonNull;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;
import java.util.Collections;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final HomeContract.View mHomeView;

    private ArrayList<Article> mUserArticles;
    private ArrayList<Article> mGroupArticles;

    private boolean mUserArticlesDownloaded;
    private boolean mGroupArticlesDownloaded;

    private final static String TAG = HomePresenter.class.getSimpleName();

    public HomePresenter(@NonNull LeLeLeRepository leLeLeRepository,
                         @NonNull HomeContract.View homeView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mHomeView = checkNotNull(homeView, "homeView cannot be null!");
        mHomeView.setPresenter(this);

        mUserArticles = new ArrayList<>();
        mGroupArticles = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadArticles() {
        mUserArticlesDownloaded = false;
        mGroupArticlesDownloaded = false;

        loadUserArticles();

        if (!UserManager.getInstance().getUserData().getGroupNow().equals("")) {
            loadGroupArticles();
        } else {
            mGroupArticlesDownloaded = true;
        }
    }

    @Override
    public void cancelInvitation(Article article) {
        mLeLeLeRepository.deleteUserArticle(article,
                UserManager.getInstance().getUserData().getEmail(),
                UserManager.getInstance().getUserData().getUserType());
        loadArticles();
        resetRoom();
    }

    @Override
    public void agreeInvitation(Article article) {
        mLeLeLeRepository.deleteUserArticle(article,
                UserManager.getInstance().getUserData().getEmail(),
                UserManager.getInstance().getUserData().getUserType());
        loadArticles();
        bindingWithTenant();
    }

    @Override
    public void setPostingButton() {
        mHomeView.setPostingButtonUi();
    }

    @Override
    public void openPosting() {

    }

    @Override
    public void loadUserArticles() {
        mLeLeLeRepository.getUserArticles(UserManager.getInstance().getUserData().getEmail(), new LeLeLeDataSource.GetArticlesCallback() {
            @Override
            public void onCompleted(ArrayList<Article> articles) {
                mUserArticlesDownloaded = true;
                mUserArticles = articles;
                combineAllArticles();
//                mHomeView.setArticleList(articles);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void loadGroupArticles() {
        String email;
        String groupName = UserManager.getInstance().getUserData().getGroupNow();

        if (UserManager.getInstance().getUserData().getUserType() == R.string.landlord) {
            email = UserManager.getInstance().getUserData().getEmail();
        } else {
            email = UserManager.getInstance().getTenant().getLandlordEmail();
        }

        mLeLeLeRepository.getGroupArticles(email, groupName, new LeLeLeDataSource.GetArticlesCallback() {
            @Override
            public void onCompleted(ArrayList<Article> articles) {
                mGroupArticlesDownloaded = true;
                mGroupArticles = articles;
                combineAllArticles();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void combineAllArticles() {

        if (mGroupArticlesDownloaded && mUserArticlesDownloaded) {
            ArrayList allArticles = new ArrayList();
            allArticles.addAll(mGroupArticles);
            allArticles.addAll(mUserArticles);

            mHomeView.setArticleList(allArticles);
        }

    }

    private void resetRoom() {
        Room emptyRoom = new Room();
        emptyRoom.setRoomName(UserManager.getInstance().getTenant().getRoomNumber());
        emptyRoom.getTenant().setRoomNumber(UserManager.getInstance().getTenant().getRoomNumber());
        mLeLeLeRepository.updateRoom(emptyRoom, UserManager.getInstance().getTenant().getLandlordEmail(),
                UserManager.getInstance().getTenant().getGroup());
        resetTenant(UserManager.getInstance().getTenant());
    }

    private void resetTenant(Tenant tenant) {
        tenant.setRoomNumber(Constants.EMPTY);
        tenant.setLandlordEmail(Constants.EMPTY);
        tenant.setGroup(Constants.EMPTY);
        tenant.setBinding(false);
        tenant.setInviting(false);
        mLeLeLeRepository.uploadTenant(tenant);
        UserManager.getInstance().setTenant(tenant);
        UserManager.getInstance().setupUserEnvironment();
    }

    private void bindingWithTenant() {
        Tenant tenant = UserManager.getInstance().getTenant();
        tenant.setInviting(false);
        tenant.setBinding(true);
        mLeLeLeRepository.uploadTenant(tenant);
        UserManager.getInstance().setTenant(tenant);
        UserManager.getInstance().setupUserEnvironment();
        bindingWithRoom(tenant);
    }

    private void bindingWithRoom(Tenant tenant) {
        Room boundRoom = new Room();
        boundRoom.setTenant(tenant);
        boundRoom.setRoomName(tenant.getRoomNumber());
        mLeLeLeRepository.updateRoom(boundRoom, UserManager.getInstance().getTenant().getLandlordEmail(),
                UserManager.getInstance().getTenant().getGroup());
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
