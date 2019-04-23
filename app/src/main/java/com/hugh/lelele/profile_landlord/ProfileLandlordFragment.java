package com.hugh.lelele.profile_landlord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileLandlordFragment extends Fragment implements ProfileLandlordContract.View {

    private ProfileLandlordContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_landlord, container, false);

        return root;
    }

    public static ProfileLandlordFragment newInstance() {
        return new ProfileLandlordFragment();
    }

    @Override
    public void setPresenter(ProfileLandlordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
