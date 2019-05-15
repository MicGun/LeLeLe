package com.hugh.lelele.application_landlord;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppLandlordPresenter implements AppLandlordContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final AppLandlordContract.View mAppLandlordView;

    private final String TAG = AppLandlordPresenter.class.getSimpleName();

    public AppLandlordPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                @NonNull AppLandlordContract.View appLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mAppLandlordView = checkNotNull(appLandlordView, "appLandlordView cannot be null!");
        mAppLandlordView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadRoomList(String email, String groupName) {

        mLeLeLeRepository.getRoomList(email, groupName, new LeLeLeDataSource.GetRoomListCallback() {
            @Override
            public void onCompleted(ArrayList<Room> rooms) {
                mAppLandlordView.showElectricityEditorUi(rooms);
                mAppLandlordView.setLoading(false);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void openElectricityEditor(ArrayList<Room> rooms) {

    }

    @Override
    public void loadGroupListFromApp(String email) {
        mLeLeLeRepository.getGroupList(email, new LeLeLeDataSource.GetGroupListCallback() {
            @Override
            public void onCompleted(ArrayList<Group> groups) {
                mAppLandlordView.showGroupListUi(groups);
                mAppLandlordView.showProgressBar(false);
                mAppLandlordView.setLoading(false);
                setUserManagerLandlordGroups(groups);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void setUserManagerLandlordGroups(ArrayList<Group> groups) {
        if (groups != null) {
            UserManager.getInstance().getLandlord().setGroups(groups);
        }
    }

    @Override
    public void openGroupList(ArrayList<Group> groups) {

    }

    @Override
    public void openRoomList() {

    }

    @Override
    public void openMessagingList() {

    }
}
