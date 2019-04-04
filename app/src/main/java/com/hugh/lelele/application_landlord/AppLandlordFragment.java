package com.hugh.lelele.application_landlord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hugh.lelele.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppLandlordFragment extends Fragment implements AppLandlordContract.View {

    private AppLandlordContract.Presenter mPresenter;

    private Button mElectricityButton;
    private Button mMessageButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_app_landlord, container, false);

        mElectricityButton = root.findViewById(R.id.button_electricity_landlord);
        mElectricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadRoomList();
            }
        });
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
