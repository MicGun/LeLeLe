package com.hugh.lelele.application_landlord;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

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
                if (groups != null) {
                    //ToDo: to think about if the data is already been saving to landlord, should passing the data through dependency injection?
                    UserManager.getInstance().getLandlord().setGroups(groups);
                }
                Log.v(TAG, "Group Number: " + groups.size());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void openGroupList(ArrayList<Group> groups) {

    }
}
