package com.hugh.lelele.application_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppTenantFragment extends Fragment implements AppTenantContract.View {

    private AppTenantContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_app_tenant, container, false);

        return root;
    }

    public static AppTenantFragment newInstance() {
        return new AppTenantFragment();
    }

    @Override
    public void setPresenter(AppTenantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
