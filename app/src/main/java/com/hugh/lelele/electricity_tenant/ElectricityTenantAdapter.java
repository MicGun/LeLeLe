package com.hugh.lelele.electricity_tenant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Electricity;

import java.util.ArrayList;
import java.util.Calendar;

public class ElectricityTenantAdapter extends RecyclerView.Adapter {

    private ElectricityTenantContract.Presenter mPresenter;
    private ArrayList<Electricity> mElectricities;

    public static final int LAST_MONTH = 12;

    public ElectricityTenantAdapter(ElectricityTenantContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class ElectricityTenantItemViewHolder extends RecyclerView.ViewHolder {

        TextView month;
        TextView scaleLest;
        TextView scaleThis;
        TextView scaleTotal;
        TextView price;

        public ElectricityTenantItemViewHolder(@NonNull View itemView) {
            super(itemView);

            month = itemView.findViewById(R.id.text_tenant_item_view_month);
            scaleLest = itemView.findViewById(R.id.text_tenant_item_view_scale_last);
            scaleThis = itemView.findViewById(R.id.text_tenant_item_view_scale_this);
            scaleTotal = itemView.findViewById(R.id.text_tenant_item_view_scale_total);
            price = itemView.findViewById(R.id.text_tenant_item_view_price);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_electricity_fee_tenant, viewGroup, false);

        return new ElectricityTenantItemViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        //有一個 base month 在位置 0 ，所以第一個月從位置 1 算起，因此需要 +1
        Electricity electricity = mElectricities.get(i+1);

        if ( i < 9) {
            String month = "0" + (i + 1);
            ((ElectricityTenantItemViewHolder) viewHolder).month.setText(month);
        } else {
            ((ElectricityTenantItemViewHolder) viewHolder).month.setText(String.valueOf(i+1));
        }
        ((ElectricityTenantItemViewHolder) viewHolder).scaleLest.setText(electricity.getScaleLast());
        ((ElectricityTenantItemViewHolder) viewHolder).scaleThis.setText(electricity.getScale());
        ((ElectricityTenantItemViewHolder) viewHolder).scaleTotal.setText(electricity.getTotalConsumption());
        ((ElectricityTenantItemViewHolder) viewHolder).price.setText(electricity.getPrice());

    }

    @Override
    public int getItemCount() {
        if (mElectricities != null) {
            return getMonth();
        } else {
            return 0;
        }
    }

    private int getMonth() {
        if (Calendar.getInstance().get(Calendar.MONTH) != 0) {
            return Calendar.getInstance().get(Calendar.MONTH);
        } else {
            return LAST_MONTH;
        }
    }

    public void updateData(ArrayList<Electricity> electricities) {
        if (electricities != null) {
            mElectricities = electricities;
            notifyDataSetChanged();
        }
    }
}
