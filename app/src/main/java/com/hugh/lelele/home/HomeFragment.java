package com.hugh.lelele.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;

import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;
    private HomeAdapter mHomeAdapter;

    private final String TAG = LeLeLe.class.getSimpleName();

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        mPresenter.uploadLandlord("n1553330708@yahoo.com.tw");
        mPresenter.loadRoomList("n1553330708@yahoo.com.tw", "新明路287號");
        mPresenter.loadGroupList("n1553330708@yahoo.com.tw");
//        mPresenter.loadTenant("n1553330708@yahoo.com.tw");

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        Date time = Calendar.getInstance().getTime();
        Log.v(TAG, "Year Now: " + year + "/" + month);
        Log.v(TAG, "Time Now: " + time);

        return root;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
