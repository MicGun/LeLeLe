package com.hugh.lelele.electricity_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;

public class ElectricityTenantFragment extends Fragment implements ElectricityTenantContract.View {

    private ElectricityTenantContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_electricity_tenant, container, false);

        return root;
    }

    public static ElectricityTenantFragment newInstance() {
        return new ElectricityTenantFragment();
    }

    @Override
    public void setPresenter(ElectricityTenantContract.Presenter presenter) {
    }
}
