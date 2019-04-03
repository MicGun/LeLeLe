package com.hugh.lelele.electricity_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hugh.lelele.R;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityTenantFragment extends Fragment implements ElectricityTenantContract.View {

    private ElectricityTenantContract.Presenter mPresenter;

    private BarChart mBarChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_electricity_tenant, container, false);

        mPresenter.hideBottomNavigation();

        mBarChart = root.findViewById(R.id.bar_chart_electricity);
        mBarChart.getDescription().setEnabled(false);

        setData(12);
        mBarChart.setFitBars(true);

        return root;
    }

    private void setData(int count) {

        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random()*100);
            yVals.add(new BarEntry(i, value));
        }

        BarDataSet set = new BarDataSet(yVals, "金額");
        set.setColor(getResources().getColor(R.color.black_3f3a3a));
        set.setDrawValues(true);

        BarData data = new BarData(set);

        mBarChart.setData(data);
        mBarChart.invalidate();
        mBarChart.animateY(500);
    }

    public static ElectricityTenantFragment newInstance() {
        return new ElectricityTenantFragment();
    }

    @Override
    public void setPresenter(ElectricityTenantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showBottomNavigation();
        mPresenter.updateToolbar(getResources().getString(R.string.application));
    }
}
