package com.hugh.lelele.profile_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ProfileTenantFragment extends Fragment implements ProfileTenantContract.View {

    private ProfileTenantContract.Presenter mPresenter;

    private EditText mUserNameEditText;
    private EditText mAddressEditText;
    private EditText mCellphoneNumberEditText;
    private EditText mEmailEditText;
    private EditText mIdCardNumberEditText;
    private ImageView mProfileBackground;
    private ImageView mUserPicture;

    private Tenant mTenant;

    public ProfileTenantFragment() {
        mTenant = UserManager.getInstance().getTenant();
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
        mCellphoneNumberEditText = root.findViewById(R.id.edit_text_cellphone_number_profile);
        mEmailEditText = root.findViewById(R.id.edit_text_user_email_profile);
        mIdCardNumberEditText = root.findViewById(R.id.edit_text_id_card_number_profile);

        setupTenantProfileUi();

        return root;
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

    public static ProfileTenantFragment newInstance() {
        return new ProfileTenantFragment();
    }

    @Override
    public void setPresenter(ProfileTenantContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
