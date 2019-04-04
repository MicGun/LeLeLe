package com.hugh.lelele.electricity_tenant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hugh.lelele.data.Electricity;

import java.util.ArrayList;

public class ElectricityTenantAdapter extends RecyclerView.Adapter {

    private ElectricityTenantContract.Presenter mPresenter;
    private ArrayList<Electricity> mElectricities;

    public ElectricityTenantAdapter(ElectricityTenantContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
//        if (mElectricities != null) {
//            return mElectricities.size();
//        } else {
//            return 0;
//        }
        return 0;
    }

    public void updateData(ArrayList<Electricity> electricities) {
        if (electricities != null) {
            mElectricities = electricities;
        }
    }
}
