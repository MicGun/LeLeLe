package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

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
        sendDisagreeNotificationToLandlord();
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

        UserManager.getInstance().setupUserEnvironment(new UserManager.EnvironmentSetupCallback() {
            @Override
            public void onSuccess() {

                //確認使用者環境都設置完畢才可以進行下一步
                bindingWithTenant();
                increaseNumberOfTenantInGroup();
                sendAgreeNotificationToLandlord();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
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

    @Override
    public void deleteElectricityNotification(Article article) {
        mLeLeLeRepository.deleteUserArticle(article,
                UserManager.getInstance().getUserData().getEmail(),
                UserManager.getInstance().getUserData().getUserType());
        loadArticles();
    }

    @Override
    public void setupArticleListener() {

        setupUserArticlesListener();

        setupGroupArticlesListener();
    }

    @Override
    public void deleteGroupArticle(Article article) {

        if (UserManager.getInstance().getUserType() == R.string.landlord) {
            mLeLeLeRepository.deleteGroupArticle(article,
                    UserManager.getInstance().getLandlord().getEmail(),
                    UserManager.getInstance().getUserData().getGroupNow());
        } else {
            mLeLeLeRepository.deleteGroupArticle(article,
                    UserManager.getInstance().getTenant().getEmail(),
                    UserManager.getInstance().getUserData().getGroupNow());
        }
    }

    private void setupUserArticlesListener() {

        mLeLeLeRepository.userArticlesListener(UserManager.getInstance().getUserData().getEmail(),
                UserManager.getInstance().getUserData().getUserType(), new LeLeLeDataSource.ArticlesCallback() {
                    @Override
                    public void onCompleted() {
                        loadArticles();
                        //需重新去firestore同步使用者資料，否則會有不同步的狀況
                        UserManager.getInstance().refreshUserEnvironment();
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    private void setupGroupArticlesListener() {

        Log.d(TAG, "setupGroupArticlesListener: ");
        if (UserManager.getInstance().getUserData().getUserType() == R.string.landlord) {

            if (!UserManager.getInstance().getUserData().getGroupNow().equals("")) {
                mLeLeLeRepository.groupArticlesListener(UserManager.getInstance().getLandlord().getEmail(),
                        UserManager.getInstance().getUserData().getGroupNow(), new LeLeLeDataSource.ArticlesCallback() {
                            @Override
                            public void onCompleted() {
                                loadArticles();
                                Log.d(TAG, "loadArticles");
                            }

                            @Override
                            public void onError(String errorMessage) {

                            }
                        });
            }
        } else {
            if (UserManager.getInstance().getTenant().isBinding()) {
                mLeLeLeRepository.groupArticlesListener(UserManager.getInstance().getTenant().getEmail(),
                        UserManager.getInstance().getTenant().getGroup(), new LeLeLeDataSource.ArticlesCallback() {
                            @Override
                            public void onCompleted() {
                                loadArticles();
                                Log.d(TAG, "loadArticles");
                            }

                            @Override
                            public void onError(String errorMessage) {

                            }
                        });
            }
        }
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
        UserManager.getInstance().refreshUserEnvironment();
    }

    private void bindingWithTenant() {
        Tenant tenant = UserManager.getInstance().getTenant();
        tenant.setInviting(false);
        tenant.setBinding(true);
        mLeLeLeRepository.uploadTenant(tenant);
        UserManager.getInstance().setTenant(tenant);
        UserManager.getInstance().refreshUserEnvironment();
        bindingWithRoom(tenant);
    }

    private void bindingWithRoom(Tenant tenant) {
        Room boundRoom = new Room();
        boundRoom.setTenant(tenant);
        boundRoom.setRoomName(tenant.getRoomNumber());
        mLeLeLeRepository.updateRoom(boundRoom, UserManager.getInstance().getTenant().getLandlordEmail(),
                UserManager.getInstance().getTenant().getGroup());
    }

    private void increaseNumberOfTenantInGroup() {

        mLeLeLeRepository.getGroupInfo(UserManager.getInstance().getTenant().getLandlordEmail(),
                UserManager.getInstance().getTenant().getGroup(), new LeLeLeDataSource.GetGroupInfoCallback() {
                    @Override
                    public void onCompleted(Group group) {
                        int numberBefore = Integer.valueOf(group.getGroupTenantNumber());
                        int numberAfter = numberBefore + 1;
                        group.setGroupTenantNumber(String.valueOf(numberAfter));

                        mLeLeLeRepository.updateGroupInfo(group, UserManager.getInstance().getTenant().getLandlordEmail(),
                                UserManager.getInstance().getTenant().getGroup());
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    private void sendAgreeNotificationToLandlord() {
        Notification notification = new Notification();
        long time= System.currentTimeMillis();

        notification.setContent(LeLeLe.getAppContext().getString(R.string.agree_invitation_content,
                UserManager.getInstance().getTenant().getName(),
                UserManager.getInstance().getTenant().getGroup(),
                UserManager.getInstance().getTenant().getRoomNumber()));
        notification.setTitle(LeLeLe.getAppContext().getString(R.string.invitation_result_title));
        notification.setSenderEmail(UserManager.getInstance().getUserData().getEmail());
        notification.setTimeMillisecond(time);
        notification.setNotificationType(Constants.INVITATION_AGREE);

        sendNotificationToLandlord(notification);
    }

    private void sendDisagreeNotificationToLandlord() {
        Notification notification = new Notification();
        long time= System.currentTimeMillis();

        notification.setContent(LeLeLe.getAppContext().getString(R.string.disagree_invitation_content,
                UserManager.getInstance().getTenant().getName()));
        notification.setTitle(LeLeLe.getAppContext().getString(R.string.invitation_result_title));
        notification.setSenderEmail(UserManager.getInstance().getUserData().getEmail());
        notification.setTimeMillisecond(time);
        notification.setNotificationType(Constants.INVITATION_DISAGREE);

        sendNotificationToLandlord(notification);
    }

    private void sendNotificationToLandlord(Notification notification) {
        String landlordEmail = UserManager.getInstance().getTenant().getLandlordEmail();

        if (!landlordEmail.equals("")) {
            mLeLeLeRepository.pushNotificationToLandlord(notification, landlordEmail, new LeLeLeDataSource.PushNotificationCallback() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(String errorMessage) {

                }
            });
        }
    }
}
