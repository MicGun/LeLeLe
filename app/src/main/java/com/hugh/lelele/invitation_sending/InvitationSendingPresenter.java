package com.hugh.lelele.invitation_sending;

import android.annotation.SuppressLint;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.UserManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
                if (tenant.getName().equals("")) {
                    mInvitationSendingView.showNoTenantNotifyTextView(true);
                } else {
                    mInvitationSendingView.showNoTenantNotifyTextView(false);
                }
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

    @Override
    public void updateRoomListStatus() {

    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void sendInvitationToTenant(Room room) {
        Article article = getInvitationArticle(room);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        article.setTime(String.valueOf(formatter.format(Calendar.getInstance().getTime())));

        mLeLeLeRepository.sendTenantArticle(article, room.getTenant().getEmail());
    }

    private Article getInvitationArticle(Room room) {
        Article article = new Article();
        article.setTitle(LeLeLe.getAppContext().getString(R.string.inviting_notification));
        article.setContent(LeLeLe.getAppContext().getString(R.string.invitation_content,
                UserManager.getInstance().getLandlord().getName(),
                UserManager.getInstance().getUserData().getGroupNow(),
                room.getRoomName()));
        article.setType(Constants.INVITATION);
        article.setAuthor(UserManager.getInstance().getLandlord().getName());
        article.setAuthorEmail(UserManager.getInstance().getLandlord().getEmail());
        article.setAuthorPicture(UserManager.getInstance().getLandlord().getPicture());
        return article;
    }

    @Override
    public void hideKeyBoard() {

    }
}
