package com.hugh.lelele.electricity_landlord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityLandlordFragment extends Fragment implements ElectricityLandlordContract.View {

    private ElectricityLandlordContract.Presenter mPresenter;
    private ElectricityLandlordAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_electricity_landlord, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_electricity_editor_landlord);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mPresenter.hideBottomNavigation();

        return root;
    }

    @Override
    public void setPresenter(ElectricityLandlordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public static ElectricityLandlordFragment newInstance() {

        ElectricityLandlordFragment fragment = new ElectricityLandlordFragment();
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showBottomNavigation();
        mPresenter.updateToolbar(getResources().getString(R.string.application));
    }

    @Override
    public void showElectricityEditorUi(ArrayList<Room> rooms) {
        if (mAdapter == null) {
            mAdapter = new ElectricityLandlordAdapter(mPresenter);
            mAdapter.updateData(rooms);
        }
    }
}
