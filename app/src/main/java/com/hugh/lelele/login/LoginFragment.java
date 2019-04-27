package com.hugh.lelele.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private Spinner mUserTypeSelector;
    private Button mFacebookLoginButton;
    private int mUserType;
    private final String LANDLORD = "房東";
    private RadioButton mLandlordRadioButton;
    private RadioButton mTenantRadioButton;
    private RadioGroup mLoginUserTypeButtonGroup;

    private ProgressBar mProgressBar;

    private final String TAG = LoginFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        mFacebookLoginButton = root.findViewById(R.id.button_facebook_login);
        mFacebookLoginButton.setOnClickListener(new CustomOnClickListener());

        mProgressBar = root.findViewById(R.id.progress_bar_logging_in);
        showProgressBar(false);

        mLoginUserTypeButtonGroup = root.findViewById(R.id.radios_group_login_user_type);
        mLoginUserTypeButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: " + checkedId);

                RadioButton userType = group.findViewById(checkedId);

                if (userType.getTag().toString().equals(LANDLORD)) {
                    mUserType = R.string.landlord;
                    mPresenter.setUserType(R.string.landlord);
                    UserManager.getInstance().setUserType(R.string.landlord);
                    Log.v(TAG, "UserType" + mUserType);
                } else {
                    mUserType = R.string.tenant;
                    mPresenter.setUserType(R.string.tenant);
                    UserManager.getInstance().setUserType(R.string.tenant);
                    Log.v(TAG, "UserType" + mUserType);
                }

                Toast.makeText(group.getContext(),
                        "You have selected : " + userType.getTag().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    public class CustomOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_facebook_login) {
                if (mUserType == R.string.landlord || mUserType == R.string.tenant) {
                    showProgressBar(true);
                    UserManager.getInstance().loginByFacebook(getActivity(), new UserManager.LoadUserProfileCallback() {
                        @Override
                        public void onSuccess() {
                            UserManager.getInstance().setupUserEnvironment(new UserManager.EnvironmentSetupCallback() {
                                @Override
                                public void onSuccess() {
                                    mPresenter.openHome();
                                    showProgressBar(false);
                                    mPresenter.showToolbarAndBottomNavigation();
                                    if (mUserType == R.string.landlord) {
                                        mPresenter.loadGroupListDrawerMenu();
                                    } else {
                                        //to clear submenu, avoid there's landlord data showing
                                        mPresenter.resetDrawer();
                                    }
                                }

                                @Override
                                public void onError(String errorMessage) {

                                }
                            });

//                            Log.v(TAG, "UserManager is Null: " + (UserManager.getInstance().getUserData() == null));
//                            Log.v(TAG, "User Name: " + (UserManager.getInstance().getUserData().getName()));

                        }

                        @Override
                        public void onFail(String errorMessage) {

                        }

                        @Override
                        public void onInvalidToken(String errorMessage) {

                        }
                    });
                } else {
                    Toast.makeText(LeLeLe.getAppContext(),
                            "Please select a type before click.", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void showProgressBar(boolean showProgress) {
        if (showProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

//    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
//
//        String firstItem = String.valueOf(mUserTypeSelector.getSelectedItem());
//
//        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//
//            if (firstItem.equals(String.valueOf(mUserTypeSelector.getSelectedItem()))) {
//                mUserType = -1;
//                Log.v(TAG, "UserType" + mUserType);
//                Toast.makeText(parent.getContext(),
//                        "You have to select a user type!",
//                        Toast.LENGTH_LONG).show();
//            } else {
//                String selectedUserType = parent.getItemAtPosition(pos).toString();
//                if (selectedUserType.equals(LANDLORD)) {
//                    mUserType = R.string.landlord;
//                    mPresenter.setUserType(R.string.landlord);
//                    UserManager.getInstance().setUserType(R.string.landlord);
//                    Log.v(TAG, "UserType" + mUserType);
//                } else {
//                    mUserType = R.string.tenant;
//                    mPresenter.setUserType(R.string.tenant);
//                    UserManager.getInstance().setUserType(R.string.tenant);
//                    Log.v(TAG, "UserType" + mUserType);
//                }
//
//                Toast.makeText(parent.getContext(),
//                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> arg) {
//
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.hideToolbarAndBottomNavigation();
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.showToolbarAndBottomNavigation();
    }
}
