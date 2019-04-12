package com.hugh.lelele.group_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupDetailsFragment extends Fragment implements GroupDetailsContract.View {

    private GroupDetailsContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_details, container, false);

        return root;
    }

    public static GroupDetailsFragment newInstance() {
        return new GroupDetailsFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.updateToolbar(getResources().getString(R.string.application_groups_list));
    }

    @Override
    public void setPresenter(GroupDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
