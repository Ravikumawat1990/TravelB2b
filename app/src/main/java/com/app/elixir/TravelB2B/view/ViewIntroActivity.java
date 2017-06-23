package com.app.elixir.TravelB2B.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.IntroAdapter;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.utils.CM;


public class ViewIntroActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "ViewIntroActivity";
    AppCompatActivity thisActivity;
    private ViewPager mPager;
    private LinearLayout mDotsLayout;
    private LinearLayout linearLayout;
    static ImageView mDotsText[];
    private int mDotsCount = 7;
    Toolbar toolbar;
    private MtplTextView title;
    private LinearLayout layoutBottom;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_intro);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);
        title = (MtplTextView) toolbar.findViewById(R.id.toolbar_title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);


        init();
    }

    private void init() {
        thisActivity = this;
        MtplButton buttonSubmit = (MtplButton) findViewById(R.id.btnLogin);
        MtplButton buttonRegister = (MtplButton) findViewById(R.id.btnRegister);
        buttonSubmit.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);


        mPager = (ViewPager) findViewById(R.id.mainActivity_viewPager);
        mDotsLayout = (LinearLayout) findViewById(R.id.mainActivity_lns_count);
        linearLayout = (LinearLayout) findViewById(R.id.mainActivity_frm_footer);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));
            }
        });

        mPager.addOnPageChangeListener(this);
        mDotsText = new ImageView[mDotsCount];


        for (int i = 0; i < mDotsCount; i++) {
            mDotsText[i] = new ImageView(thisActivity);
            mDotsText[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(4, 0, 4, 0);
            mDotsText[i].setLayoutParams(lp);
            mDotsLayout.addView(mDotsText[i]);
        }
        mDotsText[0].setImageDrawable(getResources().getDrawable(R.drawable.mainselecteditem_dot));


    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.i(TAG, "onPageScrollStateChanged: ");
    }

    @Override
    public void onPageSelected(int position) {
        setSelectedDotColor(position);
        Log.i(TAG, "onPageSelected: " + position);
        if (position == 0) {
            layoutBottom.setVisibility(View.VISIBLE);
            title.setText(R.string.app_name);
        } else if (position == 6) {
            title.setText(R.string.register);
            layoutBottom.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            title.setText(R.string.app_name);
            layoutBottom.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        Log.i(TAG, "onPageScrollStateChanged: ");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CM.startActivity(ViewIntroActivity.this, ViewLoginActivity.class);
                        finish();

                    }
                });


                break;

            case R.id.btnRegister:

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CM.startActivity(ViewIntroActivity.this, ViewRegisterAs.class);
                    }
                });

                break;


        }

    }


    private void setSelectedDotColor(int position) {
        // setting all dot colors to default color
        try {

            for (int i = 0; i < 7; i++) {
                mDotsText[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }
            mDotsText[position].setImageDrawable(getResources().getDrawable(R.drawable.mainselecteditem_dot));

        } catch (Exception e) {

            e.getMessage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getSupportActionBar().show();
    }
}
