package com.hugh.lelele.component;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;


/**
 * Created by Wayne Chen on Feb. 2019.
 * Modified by Hugh Wang on April. 2019.
 */
public class ProfileAvatarOutlineProvider extends ViewOutlineProvider {

    private int mRadius;

    public ProfileAvatarOutlineProvider(int radius) {
        mRadius = radius;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        view.setClipToOutline(true);
        outline.setOval(0, 0, mRadius, mRadius);
    }
}
