package com.app.elixir.TravelB2B.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.fragment.FragBlockUser;
import com.app.elixir.TravelB2B.fragment.FragContactUs;
import com.app.elixir.TravelB2B.fragment.FragFaq;
import com.app.elixir.TravelB2B.fragment.FragFinalizedRequest;
import com.app.elixir.TravelB2B.fragment.FragFinalizedResponses;
import com.app.elixir.TravelB2B.fragment.FragFollowers;
import com.app.elixir.TravelB2B.fragment.FragHome;
import com.app.elixir.TravelB2B.fragment.FragHotelierHome;
import com.app.elixir.TravelB2B.fragment.FragMyRequest;
import com.app.elixir.TravelB2B.fragment.FragMyResponse;
import com.app.elixir.TravelB2B.fragment.FragPaceRequest;
import com.app.elixir.TravelB2B.fragment.FragPrivacyPolicys;
import com.app.elixir.TravelB2B.fragment.FragPromoteHotel;
import com.app.elixir.TravelB2B.fragment.FragPromotionReports;
import com.app.elixir.TravelB2B.fragment.FragRemoveRequest;
import com.app.elixir.TravelB2B.fragment.FragRespondToRequest;
import com.app.elixir.TravelB2B.fragment.FragTermsandCondions;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnApiDataChange;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.model.Model_Profile;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.utils.BadgeDrawable;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.CustomTypefaceSpan;
import com.app.elixir.TravelB2B.utils.messageListionerService;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import static com.app.elixir.TravelB2B.utils.CV.city_id;

public class ViewDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, ActionBarTitleSetter, messageListionerService.ServiceCallbacks, OnApiDataChange {

    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    private NavigationView navigationView, navigationViewSec;
    private String TAG = "ViewDrawer";
    private boolean isOpen = false;
    Toolbar toolbar;
    private String mTitle = "";
    private AHBottomNavigation bottomNavigation;
    TextView txtCount;
    ProgressBar progressBar;
    ImageView icon1, icon2, icon3, icon4;
    private boolean mBounded;
    private int count = 0;
    ImageView imgUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("1")) {
            toolbar.setSubtitle(getString(R.string.tb));
        } else if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("2")) {
            toolbar.setSubtitle(getString(R.string.ep));
        } else {
            toolbar.setSubtitle(getString(R.string.ht));
        }
        setSupportActionBar(toolbar);

        try {
            CM.setSp(ViewDrawer.this, "regId", FirebaseInstanceId.getInstance().getToken().toString());
            Log.e("FCM_ID", FirebaseInstanceId.getInstance().getToken().toString());
        } catch (Exception e) {
            e.getMessage();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationViewSec = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);
        navigationViewSec.setNavigationItemSelectedListener(this);

        TextView titleTextView = null;
        TextView subtitleTextView = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            Field f1 = toolbar.getClass().getDeclaredField("mSubtitleTextView");
            f1.setAccessible(true);
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            subtitleTextView = (TextView) f1.get(toolbar);
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_roboto_black));
            titleTextView.setTypeface(font);
            subtitleTextView.setTypeface(font);
            titleTextView.setTextSize(18);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        /**
         * Drawer MenuItem Font Set
         */
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        Menu menu1 = navigationViewSec.getMenu();
        for (int i = 0; i < menu1.size(); i++) {
            MenuItem mi = menu1.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }


        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (drawerView.getId() == R.id.nav_view) {

                    Log.i(TAG, "onDrawerSlide: ");

                } else {

                    isOpen = true;
                    invalidateOptionsMenu();

                }


            }

            @Override
            public void onDrawerClosed(View drawerView) {

                if (drawerView.getId() == R.id.nav_view) {

                    Log.i(TAG, "onDrawerSlide: ");

                } else {

                    isOpen = false;

                    invalidateOptionsMenu();
                }

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.i(TAG, "onDrawerStateChanged: ");
                // Called when the drawer motion state changes. The new state will be one of STATE_IDLE, STATE_DRAGGING or STATE_SETTLING.
            }
        });


        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        if (CM.isInternetAvailable(ViewDrawer.this)) {
            getCounter(CM.getSp(ViewDrawer.this, CV.PrefID, "").toString(), CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString(), CM.getSp(ViewDrawer.this, CV.PrefState_id, "").toString(), CM.getSp(ViewDrawer.this, CV.PrefPreference, "").toString(), CM.getSp(ViewDrawer.this, city_id, "").toString());

        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewDrawer.this);
        }


        Menu nav_Menu = navigationView.getMenu();
        if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("1")) {   // Travel Agent

            AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.placeReq, R.drawable.ic_send_black_24dp, R.color.colorLightGray);
            AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.MyReq, R.drawable.ic_drafts_black_24dp, R.color.colorLightGray);
            AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.respondToReq, R.drawable.ic_reply_black_24dp, R.color.colorLightGray);
            AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.MyResponse, R.drawable.ic_unarchive_black_24dp, R.color.colorLightGray);
            //   AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.app_name, R.drawable.ic_unarchive_black_24dp, R.color.colorLightGray);
            nav_Menu.findItem(R.id.nav_pro_hotel).setVisible(false);
            nav_Menu.findItem(R.id.nav_pro_report).setVisible(false);
            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);
            bottomNavigation.addItem(item3);
            bottomNavigation.addItem(item4);
            // bottomNavigation.addItem(item5);
            // bottomNavigation.setNotification("365", 0);
            //  bottomNavigation.setNotification("8", 1);
            //   bottomNavigation.setNotification("2", 2);
            //   bottomNavigation.setNotification("1", 3);

        } else if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("2")) {   //Event Planner

            nav_Menu.findItem(R.id.nav_pro_hotel).setVisible(false);
            nav_Menu.findItem(R.id.nav_fin_res).setVisible(false);
            nav_Menu.findItem(R.id.nav_remove_req).setVisible(false);
            nav_Menu.findItem(R.id.nav_pro_report).setVisible(false);
            nav_Menu.findItem(R.id.nav_follower).setVisible(true);
            AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.placeReq, R.drawable.ic_send_black_24dp, R.color.colorLightGray);
            AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.MyReq, R.drawable.ic_drafts_black_24dp, R.color.colorLightGray);

            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);

            //    bottomNavigation.setNotification("2", 0);
            //    bottomNavigation.setNotification("1", 1);

        } else {                 //Hotelier


            AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.respondToReq, R.drawable.ic_reply_black_24dp, R.color.colorLightGray);
            AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.MyResponse, R.drawable.ic_unarchive_black_24dp, R.color.colorLightGray);

            nav_Menu.findItem(R.id.nav_fin_req).setVisible(false);
            nav_Menu.findItem(R.id.nav_fin_res).setVisible(false);
            nav_Menu.findItem(R.id.nav_follower).setVisible(true);
            nav_Menu.findItem(R.id.nav_pro_report).setVisible(true);
            nav_Menu.findItem(R.id.nav_block_user).setVisible(false);
            nav_Menu.findItem(R.id.nav_remove_req).setVisible(false);
            nav_Menu.findItem(R.id.nav_pro_hotel).setVisible(true);
            bottomNavigation.addItem(item3);
            bottomNavigation.addItem(item4);
            //   bottomNavigation.setNotification("365", 0);
            //   bottomNavigation.setNotification("8", 1);


        }


        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#ffff8800"));
        bottomNavigation.setAccentColor(Color.parseColor("#ffff8800"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setBackgroundResource(R.color.appTextColor);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //bottomNavigation.setColored(true);
        bottomNavigation.setForceTint(true);
        bottomNavigation.setTitleTextSizeInSp(10, 8);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment;
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("1")) {
                    switch (position) {
                        case 0:
                            fragment = new FragPaceRequest();
                            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
                            // fragmentTransaction.setCustomAnimations(0, R.anim.push_in_from_top);
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                        case 1:
                            fragment = new FragMyRequest();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack("FragHome");
                            //fragmentTransaction.setCustomAnimations(0, R.anim.push_in_from_top);
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                        case 2:
                            fragment = new FragRespondToRequest();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack("FragHome");
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                        case 3:
                            fragment = new FragMyResponse();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack("FragHome");
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                        default:
                            fragment = new FragPaceRequest();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack("FragHome");
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                    }


                } else if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("2")) {
                    switch (position) {
                        case 0:
                            fragment = new FragPaceRequest();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack(null);
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                        case 1:
                            fragment = new FragMyRequest();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack("FragHome");
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;

                    }

                } else {
                    switch (position) {
                        case 0:
                            fragment = new FragRespondToRequest();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack("FragHome");
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                        case 1:
                            fragment = new FragMyResponse();
                            fragmentTransaction.add(R.id.container, fragment).addToBackStack("FragHome");
                            fm.popBackStack();
                            fragmentTransaction.commit();
                            break;
                    }

                }
                return true;
            }

        });

        View headerLayout = navigationViewSec.getHeaderView(0);
        MtplTextView navHeaderTitle = (MtplTextView) headerLayout.findViewById(R.id.txtUserName);
        MtplTextView navHeaderEamil = (MtplTextView) headerLayout.findViewById(R.id.textUserEmail);

        icon1 = (ImageView) headerLayout.findViewById(R.id.icon1);
        icon2 = (ImageView) headerLayout.findViewById(R.id.icon2);
        icon3 = (ImageView) headerLayout.findViewById(R.id.icon3);
        icon4 = (ImageView) headerLayout.findViewById(R.id.icon4);

        imgUserProfile = (ImageView) headerLayout.findViewById(R.id.imgUserProfile);


        txtCount = (TextView) headerLayout.findViewById(R.id.totRating);
        progressBar = (ProgressBar) headerLayout.findViewById(R.id.progressRating);


        try

        {
            navHeaderTitle.setText(CM.getSp(ViewDrawer.this, CV.Preffirst_name, "").toString() + " " + CM.getSp(ViewDrawer.this, CV.Preflast_name, "").toString());
        } catch (
                Exception e)

        {

        }
        navHeaderEamil.setText(CM.getSp(ViewDrawer.this, CV.PrefEmail, "").

                toString());

        webUserProfile(CM.getSp(ViewDrawer.this, CV.PrefID, "").toString());
        getSupportFragmentManager().addOnBackStackChangedListener(getListener());
        setFragment(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.barg));
        bottomNavigation.setVisibility(View.VISIBLE);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                super.onBackPressed();
                final Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
                if (f instanceof FragHome) {
                    bottomNavigation.setVisibility(View.VISIBLE);
                    setTitle(getString(R.string.app_name));
                    int size = navigationView.getMenu().size();
                    for (int i = 0; i < size; i++) {
                        navigationView.getMenu().getItem(i).setChecked(false);
                    }
                    int size1 = navigationViewSec.getMenu().size();
                    for (int i = 0; i < size1; i++) {
                        navigationViewSec.getMenu().getItem(i).setChecked(false);
                    }
                }

            } else {
                showPopup(ViewDrawer.this);
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_drawer, menu);
        menu.getItem(1).setIcon(R.drawable.ic_filter_list_white_24dp);
        if (isOpen) {
            menu.getItem(2).setIcon(R.drawable.userhome);
        } else {
            menu.getItem(2).setIcon(R.drawable.userhome);
        }

        MenuItem menuItem = menu.findItem(R.id.noti);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion == 15) {
            LayerDrawable icon = null;
            try {
                menuItem.setIcon(getResources().getDrawable(R.drawable.ic_notifications_white_24dp));
                BitmapDrawable iconBitmap = (BitmapDrawable) menuItem.getIcon();
                icon = new LayerDrawable(new Drawable[]{iconBitmap});
            } catch (Exception e) {
                e.getMessage();
            }
            setBadgeCount(ViewDrawer.this, icon, String.valueOf(0));
        } else {
            LayerDrawable icon = null;
            try {
                icon = (LayerDrawable) menuItem.getIcon();
            } catch (Exception e) {
                e.getMessage();
            }
            setBadgeCount(ViewDrawer.this, icon, String.valueOf(0));
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cartMenu) {

            drawer.openDrawer(navigationViewSec);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_homw) {
            setFragment(0);
        } else if (id == R.id.nav_fin_req) {
            setFragment(1);
        } else if (id == R.id.nav_fin_res) {
            setFragment(2);
        } else if (id == R.id.nav_follower) {
            setFragment(3);
        } else if (id == R.id.nav_block_user) {
            setFragment(4);
        } else if (id == R.id.nav_remove_req) {
            setFragment(5);
        } else if (id == R.id.nav_pro_hotel) {
            setFragment(6);

        } else if (id == R.id.nav_contact_us) {
            setFragment(7);

        } else if (id == R.id.nav_faq) {
            setFragment(8);
        } else if (id == R.id.nav_share) {
            setFragment(9);
        } else if (id == R.id.nav_logout) {
            setFragment(10);
        } else if (id == R.id.nav_editProfile) {
            setFragment(11);
        } else if (id == R.id.nav_changePassword) {
            setFragment(12);
        } else if (id == R.id.nav_myprofile) {
            setFragment(13);
        } else if (id == R.id.nav_tandc) {
            setFragment(14);
        } else if (id == R.id.nav_privpolicy) {
            setFragment(15);
        } else if (id == R.id.nav_pro_report) {
            setFragment(16);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void setFragment(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = null;
        switch (i) {
            case 0:
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("3")) {   // Travel Agent
                    fragment = new FragHotelierHome();
                    ft.add(R.id.container, fragment);
                    ft.commit();
                    bottomNavigation.setVisibility(View.VISIBLE);
                } else {
                    fragment = new FragHome();
                    ft.add(R.id.container, fragment);
                    ft.commit();
                    fm.popBackStack();
                    bottomNavigation.setVisibility(View.VISIBLE);
                }

                break;
            case 1:
                fragment = new FragFinalizedRequest();
                ft.add(R.id.container, fragment).addToBackStack("FragHome");
                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 2:
                fragment = new FragFinalizedResponses();
                ft.add(R.id.container, fragment).addToBackStack("FragHome");
                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 3:
                fragment = new FragFollowers();
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("3")) {
                    ft.add(R.id.container, fragment).addToBackStack("FragHotelierHome");
                } else {
                    ft.add(R.id.container, fragment).addToBackStack("FragHome");
                }

                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 4:
                fragment = new FragBlockUser();
                ft.add(R.id.container, fragment).addToBackStack("FragHome");
                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 5:
                fragment = new FragRemoveRequest();
                ft.add(R.id.container, fragment).addToBackStack("FragHome");
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 6:
                fragment = new FragPromoteHotel();
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("3")) {
                    ft.add(R.id.container, fragment).addToBackStack("FragHotelierHome");
                } else {
                    ft.add(R.id.container, fragment).addToBackStack("FragHome");
                }
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 7:
                fragment = new FragContactUs();
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("3")) {
                    ft.add(R.id.container, fragment).addToBackStack("FragHotelierHome");
                } else {
                    ft.add(R.id.container, fragment).addToBackStack("FragHome");
                }

                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 8:
                fragment = new FragFaq();
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("3")) {
                    ft.add(R.id.container, fragment).addToBackStack("FragHotelierHome");
                } else {
                    ft.add(R.id.container, fragment).addToBackStack("FragHome");
                }
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 9:
                CM.shareApp(ViewDrawer.this);
                break;
            case 10:
                showPopupForLogOut(ViewDrawer.this);
                break;
            case 11:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        CM.startActivity(ViewDrawer.this, ViewEditProfile.class);
                    }
                });

                break;
            case 12:
                CM.startActivity(ViewDrawer.this, ViewChangePassword.class);
                break;
            case 13:
                CM.startActivity(ViewDrawer.this, ViewMyProfile.class);
                break;
            case 14:
                fragment = new FragTermsandCondions();
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("3")) {
                    ft.add(R.id.container, fragment).addToBackStack("FragHotelierHome");
                } else {
                    ft.add(R.id.container, fragment).addToBackStack("FragHome");
                }
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 15:
                fragment = new FragPrivacyPolicys();
                if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("3")) {
                    ft.add(R.id.container, fragment).addToBackStack("FragHotelierHome");
                } else {
                    ft.add(R.id.container, fragment).addToBackStack("FragHome");
                }
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 16:
                fragment = new FragPromotionReports();
                ft.add(R.id.container, fragment).addToBackStack("FragHotelierHome");
                ft.commit();
                // bottomNavigation.setVisibility(View.GONE);
                break;


            default:
                break;
        }
    }

    @Override
    public void showDrawerToggle(boolean showDrawerToggle) {
        final Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!showDrawerToggle) {
            toggle.setDrawerIndicatorEnabled(showDrawerToggle);
            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.backicnwht));
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fm = getSupportFragmentManager();
                    if (fm.getBackStackEntryCount() > 0) {
                        toggle.setDrawerIndicatorEnabled(true);
                        toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.barg));
                        fm.popBackStack();

                        if (f instanceof FragHome) {
                            bottomNavigation.setVisibility(View.VISIBLE);
                            setTitle(getString(R.string.app_name));
                            int size = navigationView.getMenu().size();
                            for (int i = 0; i < size; i++) {
                                navigationView.getMenu().getItem(i).setChecked(false);
                            }
                            int size1 = navigationViewSec.getMenu().size();
                            for (int i = 0; i < size1; i++) {
                                navigationViewSec.getMenu().getItem(i).setChecked(false);
                            }


                        } else if (f instanceof FragHotelierHome) {
                            bottomNavigation.setVisibility(View.VISIBLE);
                            setTitle(getString(R.string.app_name));
                            int size = navigationView.getMenu().size();
                            for (int i = 0; i < size; i++) {
                                navigationView.getMenu().getItem(i).setChecked(false);
                            }
                            int size1 = navigationViewSec.getMenu().size();
                            for (int i = 0; i < size1; i++) {
                                navigationViewSec.getMenu().getItem(i).setChecked(false);
                            }
                        }
                    } else {
                        showPopup(ViewDrawer.this);
                    }
                }
            });


        } else {
            toggle.setDrawerIndicatorEnabled(showDrawerToggle);
            toggle.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.barg));
            if (f instanceof FragHome) {
                bottomNavigation.setVisibility(View.VISIBLE);
                setTitle(getString(R.string.app_name));
                int size = navigationView.getMenu().size();
                for (int i = 0; i < size; i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }
                int size1 = navigationViewSec.getMenu().size();
                for (int i = 0; i < size1; i++) {
                    navigationViewSec.getMenu().getItem(i).setChecked(false);
                }
            } else if (f instanceof FragHotelierHome) {
                bottomNavigation.setVisibility(View.VISIBLE);
                setTitle(getString(R.string.app_name));
                int size = navigationView.getMenu().size();
                for (int i = 0; i < size; i++) {
                    navigationView.getMenu().getItem(i).setChecked(false);
                }
                int size1 = navigationViewSec.getMenu().size();
                for (int i = 0; i < size1; i++) {
                    navigationViewSec.getMenu().getItem(i).setChecked(false);
                }
            }


        }
    }

    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo3).show();
    }

    /**
     * Apply font in Drawer MenuItem
     *
     * @param mi
     */
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_regular));
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    @Override
    public void setTitle(String title) {
        try {
            mTitle = title;
            toolbar.setTitle(mTitle);
            Log.e("title", mTitle);
        } catch (Exception e) {

        }

    }

    public void showPopupForLogOut(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        CM.setSp(ViewDrawer.this, CV.PrefIsLogin, "0");
                        CM.setSp(ViewDrawer.this, CV.PrefRole_Id, "0");

                        CM.startActivity(ViewDrawer.this, ViewIntroActivity.class);
                        finish();


                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo1).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void getCounter(String userId, String rolId, String statID, String preference, String cityId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewDrawer.this, true, true);
            WebService.getCounter(v, userId, rolId, statID, preference, cityId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseCounter(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewDrawer.this)) {
                        CM.showPopupCommonValidation(ViewDrawer.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseCounter(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewDrawer.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object"));

                    if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("1")) {


                        bottomNavigation.setNotification(jsonObject1.optInt("placereq"), 0);
                        bottomNavigation.setNotification(jsonObject1.optInt("myRequestCount"), 1);
                        bottomNavigation.setNotification(jsonObject1.optInt("respondToRequestCount"), 2);
                        bottomNavigation.setNotification(jsonObject1.optInt("myReponseCount"), 3);

                    } else if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("2")) {


                        bottomNavigation.setNotification(jsonObject1.optInt("placereq"), 0);
                        bottomNavigation.setNotification(jsonObject1.optInt("myRequestCount"), 1);


                    } else {

                        bottomNavigation.setNotification(jsonObject1.optInt("respondToRequestCount"), 0);
                        bottomNavigation.setNotification(jsonObject1.optInt("myReponseCount"), 1);

                    }


                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewDrawer.this, e.getMessage(), false);
        }
    }


    public void webUserProfile(String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewDrawer.this, true, true);
            WebService.getUserProfile(v, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getUserProfile(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewDrawer.this)) {
                        CM.showPopupCommonValidation(ViewDrawer.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserProfile(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewDrawer.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.optString("response_object"));
                    Model_Profile model_main = CM.JsonParse(new Model_Profile(), jsonObject.getString("response_object"));

                    int i = 0;

                    if (model_main.pancard_pic != null && !model_main.pancard_pic.equals("")) {
                        icon2.setVisibility(View.VISIBLE);
                        icon2.setImageResource(R.drawable.pancard);
                        if (!model_main.role_id.toString().equals("null") && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2")) {
                                i += 16;
                            } else if (model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    } else {
                        icon2.setVisibility(View.GONE);
                    }
                    if (model_main.company_shop_registration_pic != null && !model_main.company_shop_registration_pic.toString().equals("")) {
                        icon4.setVisibility(View.VISIBLE);
                        icon4.setImageResource(R.drawable.conreg);
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2")) {
                                i += 15;
                            } else if (model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    } else {
                        icon4.setVisibility(View.GONE);
                    }
                    if (model_main.company_img_1_pic != null && !model_main.company_img_1_pic.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    }
                    if (model_main.id_card_pic != null && !model_main.id_card_pic.toString().equals("")) {
                        icon3.setVisibility(View.VISIBLE);
                        icon3.setImageResource(R.drawable.idcard);
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    } else {
                        icon3.setVisibility(View.GONE);
                    }

                    try {


                        Log.i("TAG", "onBindViewHolder: " + "http://www.travelb2bhub.com/b2b/img/user_docs/" + CM.getSp(ViewDrawer.this, CV.PrefID, "").toString() + "/" + model_main.profile_pic);
                        Picasso.with(ViewDrawer.this)
                                .load("http://www.travelb2bhub.com/b2b/img/user_docs/" + CM.getSp(ViewDrawer.this, CV.PrefID, "").toString() + "/" + jsonObject1.optString("profile_pic"))  //URLS.UPLOAD_IMG_URL + "" + dataSet.get(position).getHotel_pic()
                                .placeholder(R.drawable.logo1) // optional
                                .error(R.drawable.logo1)         // optional
                                .into(imgUserProfile);

                    } catch (Exception e) {

                        Log.i("TAG", "onBindViewHolder: " + e.getMessage());
                    }


                    if (model_main.profile_pic != null && !model_main.profile_pic.equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 10;
                            } else {
                                i += 5;
                            }
                        }
                    }
                    if (model_main.first_name != null && !model_main.first_name.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.company_name != null && !model_main.company_name.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.email != null && !model_main.email.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.mobile_number != null && !model_main.mobile_number.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 4;
                            } else {
                                i += 5;
                            }
                        }
                    }
                    if (model_main.p_contact != null && !model_main.p_contact.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.address != null && !model_main.address.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.locality != null && !model_main.locality.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.city_id != null && !model_main.city_id.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.state_id != null && !model_main.state_id.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.country_id != null && !model_main.country_id.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.pincode != null && !model_main.pincode.toString().equals("")) {
                        i += 3;
                    }
                    if (model_main.web_url != null && !model_main.web_url.toString().equals("")) {
                        i += 3;
                        icon1.setVisibility(View.VISIBLE);
                        icon1.setImageResource(R.drawable.weburl);
                    } else {
                        icon1.setVisibility(View.GONE);
                    }
                    if (model_main.description != null && !model_main.description.toString().equals("")) {
                        if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                            if (model_main.role_id.toString().equals("2") || model_main.role_id.toString().equals("3")) {
                                i += 3;
                            } else {
                                i += 2;
                            }
                        }
                    }
                    if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                        if (model_main.role_id.toString().equals("3")) {
                            if (model_main.hotel_rating != null && !model_main.hotel_rating.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.hotel_categories != null && !model_main.hotel_categories.toString().equals("")) {
                                i += 5;
                            }
                        } else {

                        }
                    }

                    if (model_main.role_id != null && !model_main.role_id.toString().equals("")) {
                        if (model_main.role_id.toString().equals("1")) {

                            if (model_main.iata_pic != null && !model_main.iata_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.tafi_pic != null && !model_main.tafi_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.taai_pic != null && !model_main.taai_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.iato_pic != null && !model_main.iato_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.adyoi_pic != null && !model_main.adyoi_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.iso9001_pic != null && !model_main.iso9001_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.uftaa_pic != null && !model_main.uftaa_pic.toString().equals("")) {
                                i += 5;
                            }
                            if (model_main.adtoi_pic != null && !model_main.adtoi_pic.toString().equals("")) {
                                i += 5;
                            }

                        } else {

                        }
                    }


                    txtCount.setText(String.valueOf(i) + " %");
                    progressBar.setProgress(i);


                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;


            }
        } catch (
                Exception e)

        {
            CM.showPopupCommonValidation(ViewDrawer.this, e.getMessage(), false);
        }

    }

    @Override
    public void ShowConnectionPopup(String status) {

        Log.i(TAG, "ShowConnectionPopup: ");

    }


    private messageListionerService myService;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            messageListionerService.LocalBinder binder = (messageListionerService.LocalBinder) service;
            myService = binder.getService();
            mBounded = true;
            myService.setCallbacks(ViewDrawer.this); // register
        }


        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBounded = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        //Intent mIntent = new Intent(this, messageListionerService.class);
        // bindService(mIntent, serviceConnection, BIND_AUTO_CREATE);

    }


    @Override
    protected void onStop() {
        super.onStop();
        // unbindService(serviceConnection);
        //  mBounded = false;
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public void onItemClick(boolean isChange) {

        if (isChange) {
            getCounter(CM.getSp(ViewDrawer.this, CV.PrefID, "").toString(), CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString(), CM.getSp(ViewDrawer.this, CV.PrefState_id, "").toString(), CM.getSp(ViewDrawer.this, CV.PrefPreference, "").toString(), CM.getSp(ViewDrawer.this, city_id, "").toString());

        }

    }


    private FragmentManager.OnBackStackChangedListener getListener() {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null) {
                    final Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
                    if (f instanceof FragHome) {
                        bottomNavigation.setVisibility(View.VISIBLE);
                        setTitle(getString(R.string.app_name));
                        int size = navigationView.getMenu().size();
                        for (int i = 0; i < size; i++) {
                            navigationView.getMenu().getItem(i).setChecked(false);
                        }
                        int size1 = navigationViewSec.getMenu().size();
                        for (int i = 0; i < size1; i++) {
                            navigationViewSec.getMenu().getItem(i).setChecked(false);
                        }


                    } else if (f instanceof FragHotelierHome) {
                        bottomNavigation.setVisibility(View.VISIBLE);
                        setTitle(getString(R.string.app_name));
                        int size = navigationView.getMenu().size();
                        for (int i = 0; i < size; i++) {
                            navigationView.getMenu().getItem(i).setChecked(false);
                        }
                        int size1 = navigationViewSec.getMenu().size();
                        for (int i = 0; i < size1; i++) {
                            navigationViewSec.getMenu().getItem(i).setChecked(false);
                        }
                    } else if (f instanceof FragPaceRequest) {
                        bottomNavigation.setVisibility(View.VISIBLE);

                    } else if (f instanceof FragMyRequest) {
                        bottomNavigation.setVisibility(View.VISIBLE);

                    } else if (f instanceof FragRespondToRequest) {
                        bottomNavigation.setVisibility(View.VISIBLE);

                    } else if (f instanceof FragMyResponse) {
                        bottomNavigation.setVisibility(View.VISIBLE);

                    } else {
                        bottomNavigation.setVisibility(View.GONE);

                    }
                }
            }
        };

        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
