package com.hugh.lelele.messaging_list;

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

import static com.google.android.gms.internal.firebase_messaging.zzg.checkNotNull;

public class MessagingListFragment extends Fragment implements MessagingListContract.View {

    private MessagingListContract.Presenter mPresenter;
    private MessagingListAdapter mMessagingListAdapter;

    private ArrayList<Room> mRooms;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessagingListAdapter = new MessagingListAdapter(mPresenter);

        mPresenter.hideBottomNavigation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messaging_list, container, false);

        mPresenter.loadMessagingList();

        RecyclerView recyclerView = root.findViewById(R.id.recycler_messaging_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mMessagingListAdapter);

        return root;
    }

    public static MessagingListFragment newInstance() {
        return new MessagingListFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showBottomNavigation();
        mPresenter.updateToolbar(getResources().getString(R.string.application));
    }

    @Override
    public void setPresenter(MessagingListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setRoomsMessagesData(ArrayList<Room> rooms) {
        mRooms = checkNotNull(rooms);

        if (mMessagingListAdapter == null) {
            mMessagingListAdapter = new MessagingListAdapter(mPresenter);
            mMessagingListAdapter.updateData(rooms);
        } else {
            mMessagingListAdapter.updateData(rooms);
        }
    }
}
