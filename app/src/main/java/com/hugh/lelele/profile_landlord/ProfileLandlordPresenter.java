package com.hugh.lelele.profile_landlord;

import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileLandlordPresenter implements ProfileLandlordContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private ProfileLandlordContract.View mProfileLandlordView;

    public ProfileLandlordPresenter(LeLeLeRepository leLeLeRepository, ProfileLandlordContract.View profileLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mProfileLandlordView = checkNotNull(profileLandlordView, "profileLandlordView cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void updateLandlordProfile(Landlord landlord) {
        mLeLeLeRepository.uploadLandlord(landlord);
        UserManager.getInstance().refreshUserEnvironment();
    }
}
