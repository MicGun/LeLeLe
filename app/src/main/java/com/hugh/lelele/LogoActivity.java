package com.hugh.lelele;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class LogoActivity extends BaseActivivty {

    private int mTotalDuration = 2000;
    private ImageView mImageLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        final Intent intent = new  Intent(this, MainActivity.class);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(intent);
                LogoActivity.this.finish();
            }
        }, mTotalDuration / 2);
    }
}
