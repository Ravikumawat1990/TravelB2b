package com.app.elixir.TravelB2B.view;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;


/**
 * Created by mtpl on 12/15/2015.
 */
public class ViewSplashScreen extends AppCompatActivity implements Runnable {
    private Handler mHandler;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_spalshscreen);
        prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        mHandler = new Handler();
        mHandler.postDelayed(this, 5000);

    }

    @Override
    public void run() {

        if (prefs.getBoolean("firstrun", true)) {
            prefs.edit().putBoolean("firstrun", false).commit();
            CM.startActivity(this, ViewIntroActivity.class);
            finish();
        } else {

            if (CM.getSp(ViewSplashScreen.this, CV.PrefIsLogin, "").equals("1")) {

                CM.startActivity(this, ViewDrawer.class);
                finish();

            } else {
                CM.startActivity(this, ViewLoginActivity.class);
                finish();
            }


        }

    }
}
