package com.hugh.lelele.messaging_list;

import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.data.source.RoomsMessagesRecursive;
import com.hugh.lelele.data.source.RoomsMessagesRecursiveCallback;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

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

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void updateToolbar(String title) {

    }

    @Override
    public void loadMessagingList() {

        final String email = UserManager.getInstance().getUserData().getEmail();
        final String groupName = UserManager.getInstance().getUserData().getGroupNow();

        mLeLeLeRepository.getRoomList(email, groupName, new LeLeLeDataSource.GetRoomListCallback() {
            @Override
            public void onCompleted(ArrayList<Room> rooms) {
                new RoomsMessagesRecursive(rooms, email, groupName, mLeLeLeRepository, new RoomsMessagesRecursiveCallback() {
                    @Override
                    public void onCompleted(ArrayList<Room> roomArrayList) {
                        mMessagingListView.setRoomsMessagesData(roomArrayList);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }

    @Override
    public void openMessage(ArrayList<Message> messages, Tenant tenant) {

    }
}
