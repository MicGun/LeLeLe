package com.hugh.lelele.room_list;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

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

public class RoomListPresenter implements RoomListContract.Presenter {

    private static final String TAG = "RoomListPresenter";

    private final LeLeLeRepository mLeLeLeRepository;
    private RoomListContract.View mRoomListView;

    public RoomListPresenter(LeLeLeRepository leLeLeRepository, RoomListContract.View roomListView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mRoomListView = checkNotNull(roomListView, "roomListView cannot be null!");
        mRoomListView.setPresenter(this);
    }

    @Override
    public void loadGroupData() {
        mLeLeLeRepository.getGroupData(UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(), new LeLeLeDataSource.GetGroupDataCallback() {
                    @Override
                    public void onCompleted(Group group) {
                        mRoomListView.showGroupData(group);
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    @Override
    public void cancelInvitingAction(Room room) {
        resetRoom(room);
        resetTenant(room.getTenant());
        loadGroupData();
    }

    @Override
    public void removeTenantAction(final Room room) {

        sendRemoveFromRoomNotificationToTenant(room, new SendNotificationCallback() {
            @Override
            public void onCompleted() {
                resetRoom(room);
                resetTenant(room.getTenant());
                loadGroupData();
                decreaseNumberOfTenantInGroup();
            }
        });
    }

    private void resetRoom(Room room) {
        Room emptyRoom = new Room();
        emptyRoom.setRoomName(room.getRoomName());
        emptyRoom.getTenant().setRoomNumber(room.getRoomName());
        mLeLeLeRepository.updateRoom(emptyRoom, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow());
    }

    private void resetTenant(Tenant tenant) {
        deleteInvitationFromTenantArticles(tenant);
        tenant.setRoomNumber(Constants.EMPTY);
        tenant.setLandlordEmail(Constants.EMPTY);
        tenant.setGroup(Constants.EMPTY);
        tenant.setBinding(false);
        tenant.setInviting(false);
        mLeLeLeRepository.uploadTenant(tenant);
    }

    private void deleteInvitationFromTenantArticles(final Tenant tenant) {
        mLeLeLeRepository.queryUserArticleByAuthorAndType(tenant.getEmail(),
                UserManager.getInstance().getLandlord().getName(),
                Constants.INVITATION, R.string.tenant, new LeLeLeDataSource.QueryArticleByAuthorAndTypeCallback() {
                    @Override
                    public void onCompleted(ArrayList<Article> articles) {

                        for (Article article:articles) {
                            mLeLeLeRepository.deleteUserArticle(article,
                                    tenant.getEmail(), R.string.tenant);
                        }

                        Log.d(TAG, "Articles Size: " + articles.size());
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    private void decreaseNumberOfTenantInGroup() {

        mLeLeLeRepository.getGroupInfo(UserManager.getInstance().getUserData().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(), new LeLeLeDataSource.GetGroupInfoCallback() {
                    @Override
                    public void onCompleted(Group group) {
                        int numberBefore = Integer.valueOf(group.getGroupTenantNumber());
                        int numberAfter = numberBefore - 1;
                        group.setGroupTenantNumber(String.valueOf(numberAfter));

                        mLeLeLeRepository.updateGroupInfo(group, UserManager.getInstance().getUserData().getEmail(),
                                UserManager.getInstance().getUserData().getGroupNow());
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    private void sendRemoveFromRoomNotificationToTenant(Room room, final SendNotificationCallback callback) {
        Notification notification = getRemoveFromRoomNotification(room);

        mLeLeLeRepository.pushNotificationToTenant(notification, room.getTenant().getEmail(), new LeLeLeDataSource.PushNotificationCallback() {
            @Override
            public void onCompleted() {
                callback.onCompleted();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private Notification getRemoveFromRoomNotification(Room room) {
        Notification notification = new Notification();
        long time= System.currentTimeMillis();

        notification.setContent(LeLeLe.getAppContext().getString(R.string.remove_from_room_content,
                room.getTenant().getGroup(),
                room.getTenant().getRoomNumber()));
        notification.setTitle(LeLeLe.getAppContext().getString(R.string.remove_from_room_title));
        notification.setSenderEmail(UserManager.getInstance().getUserData().getEmail());
        notification.setTimeMillisecond(time);
        notification.setNotificationType(Constants.REMOVE_FROM_GROUP);
        return notification;
    }

    @Override
    public void openInvitationSending(Room room) {

    }

    @Override
    public void openInvitationActionDialog(View view, Room room) {

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
    public void updateToolbar(String title) {

    }

    interface SendNotificationCallback {

        void onCompleted();
    }
}
