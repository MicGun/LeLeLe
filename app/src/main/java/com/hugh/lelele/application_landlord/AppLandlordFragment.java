package com.hugh.lelele.application_landlord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppLandlordFragment extends Fragment implements AppLandlordContract.View {

    private AppLandlordContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_app_landlord, container, false);

        return root;
    }

    public static AppLandlordFragment newInstance() {
        return new AppLandlordFragment();
    }

    @Override
    public void setPresenter(AppLandlordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}