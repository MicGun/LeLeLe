package com.hugh.lelele.notify;

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
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.loco_data.NotificationDAO;
import com.hugh.lelele.data.loco_data.NotificationDatabase;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotifyFragment extends Fragment implements NotifyContract.View {

    private NotifyContract.Presenter mPresenter;
    private NotifyAdapter mNotifyAdapter;

    private ArrayList<Notification> mNotifications;

    private TextView mNoNotifyText;

    private static final String TAG = "NotifyFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotifyAdapter = new NotifyAdapter(mPresenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.loadNotifications();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notify, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_notify);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mNotifyAdapter);

//        recyclerViewReadyCallback = new RecyclerViewReadyCallback() {
//            @Override
//            public void onLayoutReady() {
//                mPresenter.changeReadStatus();
//            }
//        };
//
//        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (recyclerViewReadyCallback != null) {
//                    recyclerViewReadyCallback.onLayoutReady();
//                }
//            }
//        });

        mNoNotifyText = root.findViewById(R.id.text_view_notification);

        setNoNotifyTextStatus();

        return root;
    }

    private void setNoNotifyTextStatus() {

        if (mNotifications == null || mNotifications.size() == 0) {
            mNoNotifyText.setVisibility(View.VISIBLE);
        } else {
            mNoNotifyText.setVisibility(View.INVISIBLE);
        }
    }

    public static NotifyFragment newInstance() {
        return new NotifyFragment();
    }

    @Override
    public void setPresenter(NotifyContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showNotifications(ArrayList<Notification> notifications) {
        mNotifications = notifications;
        setNoNotifyTextStatus();
        if (mNotifyAdapter == null) {
            mNotifyAdapter = new NotifyAdapter(mPresenter);
            mNotifyAdapter.updateData(notifications);
        } else {
            mNotifyAdapter.updateData(notifications);
        }
    }

    private RecyclerViewReadyCallback recyclerViewReadyCallback;

    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }
}
