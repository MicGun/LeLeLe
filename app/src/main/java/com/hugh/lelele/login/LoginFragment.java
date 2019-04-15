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

    private final String TAG = LoginFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        mUserTypeSelector = root.findViewById(R.id.spinner_user_type_selector);
        mUserTypeSelector.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        mFacebookLoginButton = root.findViewById(R.id.button_facebook_login);
        mFacebookLoginButton.setOnClickListener(new CustomOnClickListener());

        return root;
    }

    public class CustomOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_facebook_login) {
                if (mUserType == R.string.landlord || mUserType == R.string.tenant) {
                    UserManager.getInstance().loginByFacebook(getActivity(), new UserManager.LoadUserProfileCallback() {
                        @Override
                        public void onSuccess() {
                            mPresenter.openHome();
                            mPresenter.showToolbarAndBottomNavigation();
                            Log.v(TAG, "UserManager is Null: " + (UserManager.getInstance().getUserData() == null));
//                            mPresenter.loadGroupListDrawerMenu();
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

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        String firstItem = String.valueOf(mUserTypeSelector.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            if (firstItem.equals(String.valueOf(mUserTypeSelector.getSelectedItem()))) {
                mUserType = -1;
                Log.v(TAG, "UserType" + mUserType);
                Toast.makeText(parent.getContext(),
                        "You have to select a user type!",
                        Toast.LENGTH_LONG).show();
            } else {
                String selectedUserType = parent.getItemAtPosition(pos).toString();
                if (selectedUserType.equals(LANDLORD)) {
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

                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }

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
