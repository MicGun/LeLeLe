package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.LeLeLe;
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

    private final static String TAG = LeLeLe.class.getSimpleName();

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
    public void uploadLandlord(String email) {
        mLeLeLeRepository.updateLandlordUser(email, new LeLeLeDataSource.LandlordUserCallback() {
            @Override
            public void onCompleted(Landlord landlord) {
                UserManager.getInstance().setLandlord(landlord);
                Log.v("HomePresenter", "Landlord: " + UserManager.getInstance().getLandlord().getIdCardNumber());
                Log.v("HomePresenter", "Landlord: " + landlord.getIdCardNumber());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void loadRoomList(String email, String groupName) {
        mLeLeLeRepository.getRoomList(email, groupName, new LeLeLeDataSource.GetRoomListCallback() {
            @Override
            public void onCompleted(ArrayList<Room> rooms) {
                for (int i = 0; i < rooms.size(); i++) {
                    Log.v("HomePresenter", "Room Name: " + rooms.get(i).getRoomName());
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void loadGroupList(String email) {
        mLeLeLeRepository.getGroupList(email, new LeLeLeDataSource.GetGroupListCallback() {
            @Override
            public void onCompleted(ArrayList<Group> groups) {
                Log.v(TAG, "Group Number: " + groups.size());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void loadTenant(String email) {
        mLeLeLeRepository.getTenantProfile(email, new LeLeLeDataSource.GetTenantProfileCallback() {
            @Override
            public void onCompleted(Tenant tenant) {
                UserManager.getInstance().setTenant(tenant);
                Log.v("HomePresenter", "Tenant: " + UserManager.getInstance().getTenant().getIdCardNumber());
                Log.v("HomePresenter", "Tenant: " + tenant.getIdCardNumber());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
