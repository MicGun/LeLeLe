package com.hugh.lelele.invitation_sending;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

public interface InvitationSendingContract {

    interface View extends BaseView<InvitationSendingContract.Presenter> {

        void setRoomView(Room room);

        void setupTenantInfoView();
        void showTenantUi();
    }

    interface Presenter extends BasePresenter {

        void setRoomData(Room room);
    }
}
