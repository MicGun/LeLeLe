package com.hugh.lelele.application_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppTenantFragment extends Fragment implements AppTenantContract.View {

    private AppTenantContract.Presenter mPresenter;

    private Button mElectricityButton;
    private Button mMessageButton;

    private boolean mIsLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_app_tenant, container, false);

        mElectricityButton = root.findViewById(R.id.button_electricity_tenant);
        mElectricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isLoading()) {
                    if (UserManager.getInstance().getTenant().isBinding()) {
                        mPresenter.loadElectricityData();
                        setLoading(true);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.not_bind_yet), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        mMessageButton = root.findViewById(R.id.button_message_tenant);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoading()) {
                    if (UserManager.getInstance().getTenant().isBinding()) {
                        mPresenter.loadRoomMessage();
                        setLoading(true);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.not_bind_yet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        setLoading(false);

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
    public void openRoomMessageView(ArrayList<Message> messages) {
        mPresenter.openMessage(messages, UserManager.getInstance().getTenant());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public void setLoading(boolean loading) {
        mIsLoading = loading;
    }
}
