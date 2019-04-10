package com.hugh.lelele.electricity_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Electricity;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityTenantFragment extends Fragment implements ElectricityTenantContract.View {

    private final String TAG = getClass().getSimpleName();

    private ElectricityTenantContract.Presenter mPresenter;
    private ElectricityTenantAdapter mAdapter;
    ArrayList<Electricity> mElectricities;

    private BarChart mBarChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_electricity_tenant, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_electricities_tenant);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mPresenter.hideBottomNavigation();

        mBarChart = root.findViewById(R.id.bar_chart_electricity);
        mBarChart.getDescription().setEnabled(false);

//        setData(12);
        setData(mElectricities);
        mBarChart.setFitBars(true);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setData(int count) {

        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random()*100);
            yVals.add(new BarEntry(i+1, value));
        }

        BarDataSet set = new BarDataSet(yVals, getResources().getString(R.string.electricity_fee));
        set.setColor(getResources().getColor(R.color.black_3f3a3a)); //bar的顏色
        set.setDrawValues(true); //bar上面有數字

        BarData data = new BarData(set);

        mBarChart.setData(data);
        mBarChart.invalidate();
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//Z軸顯示於下方
        mBarChart.getAxisRight().setEnabled(false);//右邊不要有Y軸
        mBarChart.getAxisLeft().setAxisMinValue(0.0f); //下方不要有空隙(介於0到X軸間)
        mBarChart.animateY(500);
    }

    private void setData(ArrayList<Electricity> electricities) {

        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 1; i < electricities.size(); i++) {
            Electricity electricity = electricities.get(i);
            int value;
            if (electricity.getPrice().equals("")) {
                value = 0;
            } else {
                value = (int) Integer.valueOf(electricity.getPrice());
            }
            yVals.add(new BarEntry(i, value));
        }

        BarDataSet set = new BarDataSet(yVals, "電費");
        set.setColor(getResources().getColor(R.color.black_3f3a3a)); //bar的顏色
        set.setDrawValues(true); //bar上面有數字

        BarData data = new BarData(set);

        mBarChart.setData(data);
        mBarChart.invalidate();
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//Z軸顯示於下方
        mBarChart.getAxisRight().setEnabled(false);//右邊不要有Y軸
        mBarChart.getAxisLeft().setAxisMinValue(0.0f); //下方不要有空隙(介於0到X軸間)
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

    @Override
    public void showElectricityUi(ArrayList<Electricity> electricityYearly) {

        Log.v(TAG, "electricityYearly Size: " + electricityYearly.size());
        mElectricities = electricityYearly;
        Log.v(TAG, "mElectricities Size: " + mElectricities.size());

        Log.v(TAG, "is Null" + (mAdapter == null));
        if (mAdapter == null) {
            mAdapter = new ElectricityTenantAdapter(mPresenter);
            mAdapter.updateData(electricityYearly);
        } else {
            mAdapter.updateData(electricityYearly);
        }


    }

    public ArrayList<Electricity> parseElectricityArray(HashMap<String, Electricity> electricityYearly) {

        ArrayList<Electricity> electricities = new ArrayList<>();

        for (int i = 0; i < electricityYearly.size(); i++) {
            electricities.add(electricityYearly.get(String.valueOf(i+1)));
        }
        Log.v(TAG, "electricities Size: " + electricities.size());
        return electricities;
    }
}
