package com.hugh.lelele.electricity_tenant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Electricity;

import java.util.ArrayList;

public class ElectricityTenantAdapter extends RecyclerView.Adapter {

    private ElectricityTenantContract.Presenter mPresenter;
    private ArrayList<Electricity> mElectricities;

    public ElectricityTenantAdapter(ElectricityTenantContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class ElectricityTenantItemViewHolder extends RecyclerView.ViewHolder {

        public ElectricityTenantItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_electricity_fee_tenant, viewGroup, false);

        ElectricityTenantItemViewHolder viewHolder = new ElectricityTenantItemViewHolder(rootView);

        return viewHolder;
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
        return 3;
    }

    public void updateData(ArrayList<Electricity> electricities) {
        if (electricities != null) {
            mElectricities = electricities;
        }
    }
}
