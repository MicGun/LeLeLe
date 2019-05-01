package com.hugh.lelele.invitation_sending;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.util.ImageManager;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class InvitationSendingFragment extends Fragment implements InvitationSendingContract.View {

    private InvitationSendingContract.Presenter mPresenter;
    private Room mRoom;
    private Tenant mTenant;

    private EditText mTenantEmail;
    private ImageView mSearchButton;
    private ImageView mTenantPicture;
    private TextView mTenantName;
    private Button mSendInvitationButton;
    private String mSearchEmail;

    public InvitationSendingFragment() {
        mRoom = new Room();
        mTenant = new Tenant();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inviting_tenant, container, false);

        root.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mPresenter.hideKeyBoard();
                }
            }
        });

        mTenantEmail = root.findViewById(R.id.edit_text_tenant_email);
        mTenantEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchEmail = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchButton = root.findViewById(R.id.image_view_search_tenant_email);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadTenant(mSearchEmail);
            }
        });

        mTenantPicture = root.findViewById(R.id.image_tenant_picture_inviting);
        mTenantPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(getResources().
                getDimensionPixelSize(R.dimen.radius_profile_avatar)));

        mTenantName = root.findViewById(R.id.text_view_tenant_name_inviting);

        mSendInvitationButton = root.findViewById(R.id.button_inviting);
        mSendInvitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTenant.setInviting(true);
                mTenant.setLandlordEmail(UserManager.getInstance().getLandlord().getEmail());
                mTenant.setRoomNumber(mRoom.getRoomName());
                mTenant.setGroup(UserManager.getInstance().getUserData().getGroupNow());
                mPresenter.updateTenant(mTenant);
                mRoom.setTenant(mTenant);
                mPresenter.updateRoom(mRoom);
                mPresenter.sendInvitationToTenant(mRoom);
                setupTenantInfoView();
            }
        });

        setupTenantInfoView();

        return root;
    }

    @Override
    public void setupTenantInfoView() {

        if (mTenant.getName().equals("")) {
            //還沒有對象時，不顯示
            mTenantPicture.setVisibility(View.GONE);
            mTenantName.setVisibility(View.GONE);
            mSendInvitationButton.setVisibility(View.GONE);
        } else if (mTenant.isBinding() || mTenant.isInviting()) {
            //有對象，但對方已經被綁定，或是正被邀請，無法邀請
            mTenantPicture.setVisibility(View.VISIBLE);
            mTenantName.setVisibility(View.VISIBLE);
            mSendInvitationButton.setVisibility(View.GONE);
            showTenantUi();
        } else {
            //有對象，可以被邀請
            mTenantPicture.setVisibility(View.VISIBLE);
            mTenantName.setVisibility(View.VISIBLE);
            mSendInvitationButton.setVisibility(View.VISIBLE);
            showTenantUi();
        }
    }

    @Override
    public void showTenantUi() {
        ImageManager.getInstance().setImageByUrl(mTenantPicture,
                mTenant.getPicture());
        mTenantName.setText(mTenant.getName());
    }

    @Override
    public void setTenant(Tenant tenant) {
        mTenant = tenant;
        setupTenantInfoView();
    }

    public static InvitationSendingFragment newInstance() {
        return new InvitationSendingFragment();
    }

    @Override
    public void setPresenter(InvitationSendingContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.updateRoomListStatus();
        mPresenter.hideKeyBoard();
    }

    @Override
    public void setRoomView(Room room) {
        mRoom = room;
    }
}
