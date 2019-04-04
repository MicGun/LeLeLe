package com.hugh.lelele.application_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Electricity;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppTenantFragment extends Fragment implements AppTenantContract.View {

    private AppTenantContract.Presenter mPresenter;

    private Button mElectricityButton;
    private Button mMessageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_app_tenant, container, false);

        mElectricityButton = root.findViewById(R.id.button_electricity_tenant);
        mElectricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadElectricityData();
            }
        });

        mMessageButton = root.findViewById(R.id.button_message_tenant);

        return root;
    }

    public static AppTenantFragment newInstance() {
        return new AppTenantFragment();
    }

    @Override
    public void setPresenter(AppTenantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showElectricityUi(ArrayList<Electricity> electricityYearly) {
        mPresenter.openElectricity(electricityYearly);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
