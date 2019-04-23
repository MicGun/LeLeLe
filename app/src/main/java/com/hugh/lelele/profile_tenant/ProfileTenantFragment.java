package com.hugh.lelele.profile_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.util.ImageManager;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.hugh.lelele.R.drawable.ic_action_check;

public class ProfileTenantFragment extends Fragment implements ProfileTenantContract.View {

    private ProfileTenantContract.Presenter mPresenter;

    private EditText mUserNameEditText;
    private EditText mAddressEditText;
    private EditText mCellphoneNumberEditText;
    private EditText mEmailEditText;
    private EditText mIdCardNumberEditText;
    private EditText mGroupNameEditText;
    private EditText mRoomNumberEditText;
    private EditText mLandlordEmailEditText;
    private ImageView mProfileBackground;
    private ImageView mUserPicture;

    private FloatingActionButton mFloatingProfileEditButton;

    private Tenant mTenant;
    private boolean mIsEditable;

    public ProfileTenantFragment() {
        mTenant = UserManager.getInstance().getTenant();
        mIsEditable = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_tenant, container, false);

        mProfileBackground = root.findViewById(R.id.image_profile_picture_background);
        mUserPicture = root.findViewById(R.id.image_profile_avatar);
        mUserPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(LeLeLe.getAppContext().
                getResources().getDimensionPixelSize(R.dimen.radius_profile_avatar)));

        mUserNameEditText = root.findViewById(R.id.edit_text_user_name_profile);

        mAddressEditText = root.findViewById(R.id.edit_text_address_profile);
        mAddressEditText.setTag(mAddressEditText.getKeyListener());

        mCellphoneNumberEditText = root.findViewById(R.id.edit_text_cellphone_number_profile);
        mCellphoneNumberEditText.setTag(mCellphoneNumberEditText.getKeyListener());

        mEmailEditText = root.findViewById(R.id.edit_text_user_email_profile);

        mIdCardNumberEditText = root.findViewById(R.id.edit_text_id_card_number_profile);
        mIdCardNumberEditText.setTag(mIdCardNumberEditText.getKeyListener());

        mGroupNameEditText = root.findViewById(R.id.text_view_group_name_profile);
        mRoomNumberEditText = root.findViewById(R.id.text_view_room_number_profile);
        mLandlordEmailEditText = root.findViewById(R.id.text_view_landlord_email_profile);
        mFloatingProfileEditButton = root.findViewById(R.id.button_profile_edit);
        mFloatingProfileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsEditable) {
                    mIsEditable = !mIsEditable;
                    setEditability();
                    mFloatingProfileEditButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_check));
                } else {
                    mIsEditable = !mIsEditable;
                    setEditability();
                    mFloatingProfileEditButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
                }
            }
        });

        setEditability();
        setupTenantProfileUi();
        setupRentalInfo();

        return root;
    }

    private void setEditability() {

        if (mIsEditable) {
            mAddressEditText.setKeyListener((KeyListener) mAddressEditText.getTag());
            mCellphoneNumberEditText.setKeyListener((KeyListener) mCellphoneNumberEditText.getTag());
            mIdCardNumberEditText.setKeyListener((KeyListener) mIdCardNumberEditText.getTag());
        } else {
            mUserNameEditText.setKeyListener(null);
            mAddressEditText.setKeyListener(null);
            mCellphoneNumberEditText.setKeyListener(null);
            mEmailEditText.setKeyListener(null);
            mIdCardNumberEditText.setKeyListener(null);
            mGroupNameEditText.setKeyListener(null);
            mRoomNumberEditText.setKeyListener(null);
            mLandlordEmailEditText.setKeyListener(null);
        }

    }

    private void setupTenantProfileUi() {

        ImageManager.getInstance().setImageByUrl(mProfileBackground, mTenant.getPicture());
        ImageManager.getInstance().setImageByUrl(mUserPicture, mTenant.getPicture());

        mUserNameEditText.setText(mTenant.getName());
        mAddressEditText.setText(mTenant.getAddress());
        mCellphoneNumberEditText.setText(mTenant.getPhoneNumber());
        mEmailEditText.setText(mTenant.getEmail());
        mIdCardNumberEditText.setText(mTenant.getIdCardNumber());

    }

    private void setupRentalInfo() {

        if (mTenant.isBinding()) {
            mGroupNameEditText.setText(mTenant.getGroup());
            mRoomNumberEditText.setText(mTenant.getRoomNumber());
            mLandlordEmailEditText.setText(mTenant.getLandlordEmail());
        }
    }

    public static ProfileTenantFragment newInstance() {
        return new ProfileTenantFragment();
    }

    @Override
    public void setPresenter(ProfileTenantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
