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

import static com.google.android.gms.internal.firebase_messaging.zzg.checkNotNull;

public class MessagingListFragment extends Fragment implements MessagingListContract.View {

    private MessagingListContract.Presenter mPresenter;
    private MessagingListAdapter mMessagingListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messaging_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_messaging_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    public static MessagingListFragment newInstance() {
        return new MessagingListFragment();
    }

    @Override
    public void setPresenter(MessagingListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
