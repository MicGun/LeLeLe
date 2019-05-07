package com.hugh.lelele.application_tenant;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppTenantPresenter implements AppTenantContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final AppTenantContract.View mAppTenantView;

    public AppTenantPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                              @NonNull AppTenantContract.View appTenantView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mAppTenantView = checkNotNull(appTenantView, "appTenantView cannot be null!");
        mAppTenantView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadElectricityData() {
        Tenant tenant = UserManager.getInstance().getTenant();
        mLeLeLeRepository.getElectricityList(tenant.getLandlordEmail(),
                tenant.getGroup(), String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                tenant.getRoomNumber(), new LeLeLeDataSource.GetElectricityCallback() {
                    @Override
                    public void onCompleted(ArrayList<Electricity> electricities) {
                        mAppTenantView.showElectricityUi(electricities);
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });

    }

    @Override
    public void openElectricity(ArrayList<Electricity> electricityYearly) {

    }

    @Override
    public void loadRoomMessage() {
        Tenant tenant = UserManager.getInstance().getTenant();
        String email = tenant.getLandlordEmail();
        String groupName = tenant.getGroup();
        String roomName = tenant.getRoomNumber();
        mLeLeLeRepository.getMessagesFromRoom(email, groupName, roomName, new LeLeLeDataSource.GetMessagesCallback() {
            @Override
            public void onCompleted(ArrayList<Message> messages) {
                mAppTenantView.openRoomMessageView(messages);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }

    @Override
    public void openMessage(ArrayList<Message> messages, Tenant tenant) {

    }
}
