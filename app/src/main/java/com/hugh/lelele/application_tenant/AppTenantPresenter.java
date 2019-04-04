package com.hugh.lelele.application_tenant;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.LeLeLeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
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
        //給假資料
        int scale = 7788;
//        HashMap<String, Electricity> electricityYearly = new LinkedHashMap<>();
        ArrayList<Electricity> electricityYearly = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Electricity electricity = new Electricity();
            Random random = new Random();
            int range = random.nextInt(100);
            int consumption = range + 100;
            electricity.setScaleLast(String.valueOf(scale));
            scale += consumption;
            electricity.setScale(String.valueOf(scale));
            electricity.setPrice(String.valueOf(consumption*5));
            electricity.setTotalConsumption(String.valueOf(consumption));
            electricity.setTime("2019/" + i+1 + "/01");
            electricityYearly.add(electricity);
            if (electricityYearly.size() == 11) {
                mAppTenantView.showElectricityUi(electricityYearly);
            }
        }
        Log.v("electricityYearly", "" + electricityYearly);

    }

    @Override
    public void openElectricity(ArrayList<Electricity> electricityYearly) {

    }
}
