package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;

import java.util.ArrayList;
import java.util.Map;

public interface LeLeLeDataSource {

    interface LandlordUserCallback {

        void onCompleted(Landlord landlord);

        void onError(String errorMessage);

    }

    interface TenantUserCallback {

        void onCompleted(Tenant tenant);

        void onError(String errorMessage);

    }

    interface GetRoomListCallback {

        void onCompleted(ArrayList<Room> rooms);

        void onError(String errorMessage);

    }

    interface GetGroupListCallback {

        void onCompleted(ArrayList<Group> groups);

        void onError(String errorMessage);
    }

    interface UpdateGroupListCallback {

        void onCompleted(Group group);

        void onError(String errorMessage);
    }

    interface GetElectricityCallback {

        void onCompleted(ArrayList<Electricity> electricities);

        void onError(String errorMessage);
    }

    interface GetLandlordProfileCallback {

        void onCompleted(Landlord landlord);

        void onError(String errorMessage);
    }

    interface GetTenantProfileCallback {

        void onCompleted(Tenant tenant);

        void onError(String errorMessage);
    }

    interface GetGroupDataCallback {

        void onCompleted(Group group);

        void onError(String errorMessage);
    }

    interface GetArticlesCallback {

        void onCompleted(ArrayList<Article> articles);

        void onError(String errorMessage);
    }

    interface QueryArticleByAuthorAndTypeCallback {

        void onCompleted(ArrayList<Article> articles);

        void onError(String errorMessage);
    }

    interface SendGroupArticleCallback {

        void onCompleted();

        void onError(String errorMessage);
    }

    interface ArticlesCallback {

        void onCompleted();

        void onError(String errorMessage);
    }

    interface PushNotificationCallback {

        void onCompleted();

        void onError(String errorMessage);
    }

    interface GetGroupInfoCallback {

        void onCompleted(Group group);

        void onError(String errorMessage);
    }

    interface GetUserNotificationsCallback {

        void onCompleted(ArrayList<Notification> notifications);

        void onError(String errorMessage);
    }

    void updateLandlordUser(@NonNull String email, @NonNull LandlordUserCallback callback);

    void updateTenantUser(@NonNull String email, @NonNull TenantUserCallback callback);

    void getRoomList(@NonNull String email, @NonNull String groupName, @NonNull GetRoomListCallback callback);

    void getGroupList(@NonNull String email, @NonNull GetGroupListCallback callback);

    void updateGroupList(@NonNull Group group, @NonNull String email, @NonNull UpdateGroupListCallback callback);

    void getElectricityList(@NonNull String email, @NonNull String groupName, @NonNull String year,
                            @NonNull String roomName, @NonNull GetElectricityCallback callback);

    void uploadElectricityData(String landlordEmail, String groupName, String roomName,
                               String year, String month, Electricity electricity);

    void initialElectricityMonthData(String landlordEmail, String groupName, String roomName, String year, String month);

    void getLandlordProfile(@NonNull String email, @NonNull GetLandlordProfileCallback callback);

    void getTenantProfile(@NonNull String email, @NonNull GetTenantProfileCallback callback);

    void updateRoom(@NonNull Room room, @NonNull String email, @NonNull String groupName);

    void deleteRoom(@NonNull Room room, @NonNull String email, @NonNull String groupName);

    void getGroupData(@NonNull String email, @NonNull String groupName, @NonNull GetGroupDataCallback callback);

    void uploadTenant(@NonNull Tenant tenant);

    void sendLandlordArticle(@NonNull Article article, @NonNull String email);

    void sendTenantArticle(@NonNull Article article, @NonNull String email);

    void sendGroupArticle(@NonNull Article article, @NonNull String landlordEmail,
                          @NonNull String groupName, @NonNull SendGroupArticleCallback callback);

    void getUserArticles(@NonNull String email, @NonNull GetArticlesCallback callback);

    void getGroupArticles(@NonNull String email, @NonNull String groupName, @NonNull GetArticlesCallback callback);

    void deleteUserArticle(@NonNull Article article, @NonNull String email, @NonNull int userType);

    void deleteGroupArticle(@NonNull Article article, @NonNull String email, @NonNull String groupName);

    void queryUserArticleByAuthorAndType(@NonNull String email, @NonNull String authorName,
                                         @NonNull String articleType, @NonNull int userType,
                                         @NonNull QueryArticleByAuthorAndTypeCallback callback);

    void uploadLandlord(@NonNull Landlord landlord);

    void groupArticlesListener(@NonNull String email, @NonNull String groupName, @NonNull  ArticlesCallback callback);

    void userArticlesListener(@NonNull String email, @NonNull int userType, @NonNull  ArticlesCallback callback);

    void pushNotificationToTenant(@NonNull Map<String, Object> notificationMessage, @NonNull  String email, @NonNull  PushNotificationCallback callback);

    void getGroupInfo(@NonNull String email, @NonNull String groupName, @NonNull GetGroupInfoCallback callback);

    void updateGroupInfo(@NonNull Group group,@NonNull String email, @NonNull String groupName);

    void getUserNotifications(@NonNull String email, @NonNull GetUserNotificationsCallback callback);
}
