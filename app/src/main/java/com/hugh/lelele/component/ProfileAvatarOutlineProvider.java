package com.hugh.lelele.component;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;


/**
 * Created by Wayne Chen on Feb. 2019.
 */
public class ProfileAvatarOutlineProvider extends ViewOutlineProvider {
    @Override
    public void getOutline(View view, Outline outline) {
        view.setClipToOutline(true);
//        int radius = LeLeLe.getAppContext().getResources().getDimensionPixelSize(R.dimen.radius_profile_avatar);
        int radius = 6;
        outline.setOval(0, 0, radius, radius);
    }
}
