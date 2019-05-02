package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;

import java.util.ArrayList;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class LeLeLeRepository implements LeLeLeDataSource {

    private static LeLeLeRepository INSTANCE = null;

    private LeLeLeDataSource mLeLeLeRemoteDataSource;

    private final String TAG = LeLeLeRepository.class.getSimpleName();

    public LeLeLeRepository(@NonNull LeLeLeDataSource leLeLeRemoteDataSource) {
        mLeLeLeRemoteDataSource = checkNotNull(leLeLeRemoteDataSource);
    }

    public static LeLeLeRepository getInstance(LeLeLeDataSource remoteDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new LeLeLeRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void updateLandlordUser(@NonNull String email, @NonNull final LandlordUserCallback callback) {
        mLeLeLeRemoteDataSource.updateLandlordUser(email, new LandlordUserCallback() {
            @Override
            public void onCompleted(Landlord landlord) {
                callback.onCompleted(landlord);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void updateTenantUser(@NonNull String email, @NonNull final TenantUserCallback callback) {
        mLeLeLeRemoteDataSource.updateTenantUser(email, new TenantUserCallback() {
            @Override
            public void onCompleted(Tenant tenant) {
                callback.onCompleted(tenant);
                Log.v(TAG, "Repository updateTenant");
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getRoomList(@NonNull String email, @NonNull String groupName, @NonNull final GetRoomListCallback callback) {
        mLeLeLeRemoteDataSource.getRoomList(email, groupName, new GetRoomListCallback() {
            @Override
            public void onCompleted(ArrayList<Room> rooms) {
                callback.onCompleted(rooms);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getGroupList(@NonNull String email, @NonNull final GetGroupListCallback callback) {
        mLeLeLeRemoteDataSource.getGroupList(email, new GetGroupListCallback() {
            @Override
            public void onCompleted(ArrayList<Group> groups) {
                callback.onCompleted(groups);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void updateGroupList(@NonNull Group group, @NonNull String email, @NonNull final UpdateGroupListCallback callback) {
        mLeLeLeRemoteDataSource.updateGroupList(group, email, new UpdateGroupListCallback() {
            @Override
            public void onCompleted(Group group) {
                callback.onCompleted(group);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getElectricityList(@NonNull String email, @NonNull String groupName,
                                   @NonNull String year, @NonNull String roomName,
                                   @NonNull final GetElectricityCallback callback) {
        mLeLeLeRemoteDataSource.getElectricityList(email, groupName, year, roomName, new GetElectricityCallback() {
            @Override
            public void onCompleted(ArrayList<Electricity> electricities) {
                callback.onCompleted(electricities);
                Log.v("Repository", "electricity size: " + electricities.size());
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void uploadElectricityData(String landlordEmail, String groupName, String roomName,
                                      String year, String month, Electricity electricity) {
        mLeLeLeRemoteDataSource.uploadElectricityData(landlordEmail, groupName, roomName, year, month, electricity);
    }

    @Override
    public void initialElectricityMonthData(String landlordEmail, String groupName, String roomName, String year, String month) {
        mLeLeLeRemoteDataSource.initialElectricityMonthData(landlordEmail, groupName, roomName, year, month);
    }

    @Override
    public void getLandlordProfile(@NonNull String email, @NonNull final GetLandlordProfileCallback callback) {
        mLeLeLeRemoteDataSource.getLandlordProfile(email, new GetLandlordProfileCallback() {
            @Override
            public void onCompleted(Landlord landlord) {
                callback.onCompleted(landlord);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getTenantProfile(@NonNull String email, @NonNull final GetTenantProfileCallback callback) {
        mLeLeLeRemoteDataSource.getTenantProfile(email, new GetTenantProfileCallback() {
            @Override
            public void onCompleted(Tenant tenant) {
                callback.onCompleted(tenant);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void updateRoom(@NonNull Room room, @NonNull String email, @NonNull String groupName) {
        mLeLeLeRemoteDataSource.updateRoom(room, email, groupName);
    }

    @Override
    public void deleteRoom(@NonNull Room room, @NonNull String email, @NonNull String groupName) {
        mLeLeLeRemoteDataSource.deleteRoom(room, email, groupName);
    }

    @Override
    public void getGroupData(@NonNull String email, @NonNull String groupName, @NonNull final GetGroupDataCallback callback) {
        mLeLeLeRemoteDataSource.getGroupData(email, groupName, new GetGroupDataCallback() {
            @Override
            public void onCompleted(Group group) {
                callback.onCompleted(group);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void uploadTenant(@NonNull Tenant tenant) {
        mLeLeLeRemoteDataSource.uploadTenant(tenant);
    }

    @Override
    public void sendLandlordArticle(@NonNull Article article, @NonNull String email) {
        mLeLeLeRemoteDataSource.sendLandlordArticle(article, email);
    }

    @Override
    public void sendTenantArticle(@NonNull Article article, @NonNull String email) {
        mLeLeLeRemoteDataSource.sendTenantArticle(article, email);
    }

    @Override
    public void sendGroupArticle(@NonNull Article article, @NonNull String landlordEmail,
                                 @NonNull String groupName, @NonNull final SendGroupArticleCallback callback) {
        mLeLeLeRemoteDataSource.sendGroupArticle(article, landlordEmail, groupName, new SendGroupArticleCallback() {
            @Override
            public void onCompleted() {
                callback.onCompleted();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getUserArticles(@NonNull String email, @NonNull final GetArticlesCallback callback) {
        mLeLeLeRemoteDataSource.getUserArticles(email, new GetArticlesCallback() {
            @Override
            public void onCompleted(ArrayList<Article> articles) {
                callback.onCompleted(articles);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getGroupArticles(@NonNull String email, @NonNull String groupName, @NonNull final GetArticlesCallback callback) {
        mLeLeLeRemoteDataSource.getGroupArticles(email, groupName, new GetArticlesCallback() {
            @Override
            public void onCompleted(ArrayList<Article> articles) {
                callback.onCompleted(articles);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void deleteUserArticle(@NonNull Article article, @NonNull String email, @NonNull int userType) {
        mLeLeLeRemoteDataSource.deleteUserArticle(article, email, userType);
    }

    @Override
    public void deleteGroupArticle(@NonNull Article article, @NonNull String email, @NonNull String groupName) {
        mLeLeLeRemoteDataSource.deleteGroupArticle(article, email, groupName);
    }

    @Override
    public void queryUserArticleByAuthorAndType(@NonNull String email, @NonNull String authorName,
                                                @NonNull String articleType,  @NonNull int userType,
                                                @NonNull final QueryArticleByAuthorAndTypeCallback callback) {
        mLeLeLeRemoteDataSource.queryUserArticleByAuthorAndType(email, authorName, articleType, userType, new QueryArticleByAuthorAndTypeCallback() {
            @Override
            public void onCompleted(ArrayList<Article> articles) {
                callback.onCompleted(articles);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void uploadLandlord(@NonNull Landlord landlord) {
        mLeLeLeRemoteDataSource.uploadLandlord(landlord);
    }

    @Override
    public void groupArticlesListener(@NonNull String email, @NonNull String groupName, final ArticlesCallback callback) {
        mLeLeLeRemoteDataSource.groupArticlesListener(email, groupName, new ArticlesCallback() {
            @Override
            public void onCompleted() {
                callback.onCompleted();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void userArticlesListener(@NonNull String email, @NonNull int userType, final ArticlesCallback callback) {
        mLeLeLeRemoteDataSource.userArticlesListener(email, userType, new ArticlesCallback() {
            @Override
            public void onCompleted() {
                callback.onCompleted();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

//    @Override
//    public void pushNotificationToTenant(@NonNull Map<String, Object> notificationMessage, @NonNull String email, @NonNull final PushNotificationCallback callback) {
//        mLeLeLeRemoteDataSource.pushNotificationToTenant(notificationMessage, email, new PushNotificationCallback() {
//            @Override
//            public void onCompleted() {
//                callback.onCompleted();
//            }
//
//            @Override
//            public void onError(String errorMessage) {
//                callback.onError(errorMessage);
//            }
//        });
//    }

    @Override
    public void pushNotificationToTenant(@NonNull Notification notification, @NonNull String email, @NonNull final PushNotificationCallback callback) {
        mLeLeLeRemoteDataSource.pushNotificationToTenant(notification, email, new PushNotificationCallback() {
            @Override
            public void onCompleted() {
                callback.onCompleted();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void pushNotificationToLandlord(@NonNull Notification notification, @NonNull String email, @NonNull final PushNotificationCallback callback) {
        mLeLeLeRemoteDataSource.pushNotificationToLandlord(notification, email, new PushNotificationCallback() {
            @Override
            public void onCompleted() {
                callback.onCompleted();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getGroupInfo(@NonNull String email, @NonNull String groupName, @NonNull final GetGroupInfoCallback callback) {
        mLeLeLeRemoteDataSource.getGroupInfo(email, groupName, new GetGroupInfoCallback() {
            @Override
            public void onCompleted(Group group) {
                callback.onCompleted(group);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void updateGroupInfo(@NonNull Group group,@NonNull String email, @NonNull String groupName) {
        mLeLeLeRemoteDataSource.updateGroupInfo(group, email, groupName);
    }

    @Override
    public void getUserNotifications(@NonNull String email, @NonNull final GetUserNotificationsCallback callback) {
        mLeLeLeRemoteDataSource.getUserNotifications(email, new GetUserNotificationsCallback() {
            @Override
            public void onCompleted(ArrayList<Notification> notifications) {
                callback.onCompleted(notifications);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void updateNotificationRead(@NonNull Notification notification, @NonNull String email) {
        mLeLeLeRemoteDataSource.updateNotificationRead(notification, email);
    }
}
