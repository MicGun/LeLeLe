package com.hugh.lelele.messaging_list;

import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessagingListPresenter implements MessagingListContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private MessagingListContract.View mMessagingListView;

    public MessagingListPresenter(LeLeLeRepository leLeLeRepository, MessagingListContract.View messagingListView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mMessagingListView = checkNotNull(messagingListView, "messagingListView cannot be null!");
        messagingListView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
