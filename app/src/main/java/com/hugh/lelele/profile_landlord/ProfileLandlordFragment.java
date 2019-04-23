package com.hugh.lelele.profile_landlord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.util.ImageManager;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileLandlordFragment extends Fragment implements ProfileLandlordContract.View {

    private ProfileLandlordContract.Presenter mPresenter;

    private EditText mUserNameEditText;
    private EditText mAddressEditText;
    private EditText mCellphoneNumberEditText;
    private EditText mEmailEditText;
    private EditText mIdCardNumberEditText;
    private ImageView mProfileBackground;
    private ImageView mUserPicture;

    private FloatingActionButton mFloatingProfileEditButton;

    private Landlord mLandlord;
    private boolean mIsEditable;

    public ProfileLandlordFragment() {
        mLandlord = UserManager.getInstance().getLandlord();
        mIsEditable = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_landlord, container, false);

        mProfileBackground = root.findViewById(R.id.image_profile_picture_background);
        mUserPicture = root.findViewById(R.id.image_profile_avatar);
        mUserPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(LeLeLe.getAppContext().
                getResources().getDimensionPixelSize(R.dimen.radius_profile_avatar)));

        mUserNameEditText = root.findViewById(R.id.edit_text_user_name_profile);

        mAddressEditText = root.findViewById(R.id.edit_text_address_profile);
        mAddressEditText.setTag(mAddressEditText.getKeyListener());

        mAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String address = s.toString();
                mLandlord.setAddress(address);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCellphoneNumberEditText = root.findViewById(R.id.edit_text_cellphone_number_profile);
        mCellphoneNumberEditText.setTag(mCellphoneNumberEditText.getKeyListener());
        mCellphoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNumber = s.toString();
                mLandlord.setPhoneNumber(phoneNumber);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailEditText = root.findViewById(R.id.edit_text_user_email_profile);

        mIdCardNumberEditText = root.findViewById(R.id.edit_text_id_card_number_profile);
        mIdCardNumberEditText.setTag(mIdCardNumberEditText.getKeyListener());
        mIdCardNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String idCardNumber = s.toString();
                mLandlord.setIdCardNumber(idCardNumber);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFloatingProfileEditButton = root.findViewById(R.id.button_profile_edit);
        mFloatingProfileEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsEditable) {
                    mIsEditable = !mIsEditable;
                    setEditability();
                    mFloatingProfileEditButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_check));
                } else {
                    if (checkNoEmptyInfo()) {
                        mIsEditable = !mIsEditable;
                        setEditability();
                        mFloatingProfileEditButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
                        mPresenter.updateLandlordProfile(mLandlord);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.empty_info_not_allow), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        setEditability();
        setupLandlordProfileUi();

        return root;
    }

    private boolean checkNoEmptyInfo() {

        if (mLandlord.getIdCardNumber().equals("") ||
                mLandlord.getPhoneNumber().equals("") ||
                mLandlord.getAddress().equals("")) {
            return false;
        } else {
            return true;
        }

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
        }

    }

    private void setupLandlordProfileUi() {

        ImageManager.getInstance().setImageByUrl(mProfileBackground, mLandlord.getPicture());
        ImageManager.getInstance().setImageByUrl(mUserPicture, mLandlord.getPicture());

        mUserNameEditText.setText(mLandlord.getName());
        mAddressEditText.setText(mLandlord.getAddress());
        mCellphoneNumberEditText.setText(mLandlord.getPhoneNumber());
        mEmailEditText.setText(mLandlord.getEmail());
        mIdCardNumberEditText.setText(mLandlord.getIdCardNumber());

    }

    public static ProfileLandlordFragment newInstance() {
        return new ProfileLandlordFragment();
    }

    @Override
    public void setPresenter(ProfileLandlordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
