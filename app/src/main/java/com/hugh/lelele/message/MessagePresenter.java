package com.hugh.lelele.message;

import android.support.annotation.NonNull;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessagePresenter implements MessageContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private MessageContract.View mMessageView;

    private Tenant mTenant;
    private String mUserType;
    private String mLandlordEmail;
    private String mGroupName;
    private String mRoomName;

    public MessagePresenter(@NonNull LeLeLeRepository leLeLeRepository, @NonNull MessageContract.View messageView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mMessageView = checkNotNull(messageView, "messageView cannot be null!");
        mMessageView.setPresenter(this);

        if (UserManager.getInstance().getUserType() == R.string.landlord) {
            mUserType = Constants.LANDLORD;
        } else if (UserManager.getInstance().getUserType() == R.string.tenant){
            mUserType = Constants.TENANT;
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void updateToolbar(String title) {

    }

    @Override
    public void sendMessage(String content) {
        Message message = getMessageWithContent(content);

        if (!mLandlordEmail.equals("") && !mGroupName.equals("") && !mRoomName.equals("")) {
            mLeLeLeRepository.sendMessageToRoom(message, mLandlordEmail, mGroupName, mRoomName);
        }
    }

    private Message getMessageWithContent(String content) {
        Message message = new Message();

        long time = System.currentTimeMillis();
        String senderType = "";

        if (UserManager.getInstance().getUserType() == R.string.landlord) {
            senderType = Constants.LANDLORD;
        } else if (UserManager.getInstance().getUserType() == R.string.tenant){
            senderType = Constants.TENANT;
        }

        message.setContent(content);
        message.setSenderName(UserManager.getInstance().getUserData().getName());
        message.setSenderEmail(UserManager.getInstance().getUserData().getEmail());
        message.setSenderPicture(UserManager.getInstance().getUserData().getPictureUrl());
        message.setSenderType(senderType);
        message.setTimeMillisecond(time);
        return message;
    }

    @Override
    public void setTenant(Tenant tenant) {
        mTenant = checkNotNull(tenant);
        mMessageView.setTenantView(tenant);

        mLandlordEmail = mTenant.getLandlordEmail();
        mGroupName = mTenant.getGroup();
        mRoomName = mTenant.getRoomNumber();
    }

    @Override
    public void setMessages(ArrayList<Message> messages) {
        //ToDo: 往後在這處理最多一百則訊息的上限，處理完畢再讓view去更新
        mMessageView.setMessagesView(messages);
    }

    @Override
    public void setupMessageListener(boolean switchOn) {

        String email = mTenant.getLandlordEmail();
        String groupName = mTenant.getGroup();
        String roomName = mTenant.getRoomNumber();

        mLeLeLeRepository.messageListener(email, groupName, roomName, switchOn, new LeLeLeDataSource.MessageCallback() {
            @Override
            public void onCompleted() {
                loadMessages();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void loadMessages() {
        String email = mTenant.getLandlordEmail();
        String groupName = mTenant.getGroup();
        String roomName = mTenant.getRoomNumber();
        mLeLeLeRepository.getMessagesFromRoom(email, groupName, roomName, new LeLeLeDataSource.GetMessagesCallback() {
            @Override
            public void onCompleted(ArrayList<Message> messages) {
                mMessageView.setMessagesView(messages);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void setMessagesAreRead(ArrayList<Message> messages) {

        for (Message message:messages) {

            if (!message.getSenderType().equals(mUserType) && !message.isRead()) {
                message.setRead(true);
                mLeLeLeRepository.updateMessageToRoom(message, mLandlordEmail, mGroupName, mRoomName);
            }
        }
    }

    @Override
    public void moveMessageItemToPosition(int position) {
        mMessageView.moveMessageItemToPositionView(position);
    }
}
