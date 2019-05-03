package com.hugh.lelele.message;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Tenant;

import java.util.ArrayList;

public interface MessageContract {

    interface View extends BaseView<Presenter> {

        void setTenantView(Tenant tenant);

        void setMessagesView(ArrayList<Message> messages);

    }

    interface Presenter extends BasePresenter {

        void showBottomNavigation();

        void hideBottomNavigation();

        void updateToolbar(String title);

        void sendMessage(String content);

        void setTenant(Tenant tenant);

        void setMessages(ArrayList<Message> messages);

        void setupMessageListener(boolean switchOn);

        void loadMessages();
    }
}
