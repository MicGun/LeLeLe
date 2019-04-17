package com.hugh.lelele.invitation_sending;

import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class InvitationSendingPresenter implements InvitationSendingContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private InvitationSendingContract.View mInvitationSendingView;

    public InvitationSendingPresenter(LeLeLeRepository leLeLeRepository, InvitationSendingContract.View invitationSendingView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mInvitationSendingView = checkNotNull(invitationSendingView, "invitationSendingView cannot be null!");
        mInvitationSendingView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void setRoomData(Room room) {
        mInvitationSendingView.setRoomView(room);
    }

    @Override
    public void loadTenant(String email) {
        mLeLeLeRepository.getTenantProfile(email, new LeLeLeDataSource.GetTenantProfileCallback() {
            @Override
            public void onCompleted(Tenant tenant) {
                mInvitationSendingView.setTenant(tenant);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    @Override
    public void updateTenant(Tenant tenant) {
        mLeLeLeRepository.uploadTenant(tenant);
    }

    @Override
    public void updateRoom(Room room) {
        mLeLeLeRepository.updateRoom(room, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow());
    }
}
