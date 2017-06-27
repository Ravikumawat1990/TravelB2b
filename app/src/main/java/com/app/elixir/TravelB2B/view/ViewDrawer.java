package com.app.elixir.TravelB2B.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.fragment.FragBlockUser;
import com.app.elixir.TravelB2B.fragment.FragContactUs;
import com.app.elixir.TravelB2B.fragment.FragFaq;
import com.app.elixir.TravelB2B.fragment.FragFinalizedRequest;
import com.app.elixir.TravelB2B.fragment.FragFinalizedResponses;
import com.app.elixir.TravelB2B.fragment.FragFollowers;
import com.app.elixir.TravelB2B.fragment.FragHome;
import com.app.elixir.TravelB2B.fragment.FragMyRequest;
import com.app.elixir.TravelB2B.fragment.FragMyResponse;
import com.app.elixir.TravelB2B.fragment.FragPaceRequest;
import com.app.elixir.TravelB2B.fragment.FragPromoteHotel;
import com.app.elixir.TravelB2B.fragment.FragRemoveRequest;
import com.app.elixir.TravelB2B.fragment.FragRespondToRequest;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.CustomTypefaceSpan;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.lang.reflect.Field;

public class ViewDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, ActionBarTitleSetter {

    private DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    private NavigationView navigationView, navigationViewSec;
    private String TAG = "ViewDrawer";
    private boolean isOpen = false;
    Toolbar toolbar;
    private String mTitle = "";
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setSubtitle(getString(R.string.tb));
        setSupportActionBar(toolbar);


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
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_DroidSerif_Bold));
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
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.placeReq, R.drawable.ic_send_black_24dp, R.color.colorLightGray);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.MyReq, R.drawable.ic_drafts_black_24dp, R.color.colorLightGray);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.respondToReq, R.drawable.ic_reply_black_24dp, R.color.colorLightGray);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.MyResponse, R.drawable.ic_unarchive_black_24dp, R.color.colorLightGray);


        Menu nav_Menu = navigationView.getMenu();
        if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("1")) {   // Travel Agent

            nav_Menu.findItem(R.id.nav_pro_hotel).setVisible(false);
            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);
            bottomNavigation.addItem(item3);
            bottomNavigation.addItem(item4);
            bottomNavigation.setNotification("365", 0);
            bottomNavigation.setNotification("8", 1);
            bottomNavigation.setNotification("2", 2);
            bottomNavigation.setNotification("1", 3);

        } else if (CM.getSp(ViewDrawer.this, CV.PrefRole_Id, "").toString().equals("2")) {   //Event Planner

            nav_Menu.findItem(R.id.nav_pro_hotel).setVisible(false);

            bottomNavigation.addItem(item3);
            bottomNavigation.addItem(item4);

            bottomNavigation.setNotification("2", 2);
            bottomNavigation.setNotification("1", 3);

        } else {                 //Hotelier
            nav_Menu.findItem(R.id.nav_fin_req).setVisible(false);
            nav_Menu.findItem(R.id.nav_fin_res).setVisible(false);
            nav_Menu.findItem(R.id.nav_follower).setVisible(false);
            nav_Menu.findItem(R.id.nav_block_user).setVisible(false);
            nav_Menu.findItem(R.id.nav_remove_req).setVisible(false);
            nav_Menu.findItem(R.id.nav_pro_hotel).setVisible(true);
            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);
            bottomNavigation.setNotification("365", 0);
            bottomNavigation.setNotification("8", 1);


        }


        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#ffff8800"));
        bottomNavigation.setAccentColor(Color.parseColor("#ffff8800"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        bottomNavigation.setBackgroundResource(R.color.appTextColor);

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        //bottomNavigation.setColored(true);
        bottomNavigation.setForceTint(true);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment;
                switch (position) {
                    case 0:
                        fragment = new FragPaceRequest();
                        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
                        fm.popBackStack();
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragment = new FragMyRequest();
                        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("FragHome");
                        fm.popBackStack();
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        fragment = new FragRespondToRequest();
                        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("FragHome");
                        fm.popBackStack();
                        fragmentTransaction.commit();
                        break;
                    case 3:
                        fragment = new FragMyResponse();
                        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("FragHome");
                        fm.popBackStack();
                        fragmentTransaction.commit();
                        break;
                    default:
                        fragment = new FragPaceRequest();
                        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("FragHome");
                        fm.popBackStack();
                        fragmentTransaction.commit();
                        break;


                }


                return true;
            }
        });

        View headerLayout = navigationViewSec.getHeaderView(0);
        MtplTextView navHeaderTitle = (MtplTextView) headerLayout.findViewById(R.id.txtUserName);
        MtplTextView navHeaderEamil = (MtplTextView) headerLayout.findViewById(R.id.textUserEmail);
        try {
            navHeaderTitle.setText(CM.getSp(ViewDrawer.this, CV.Preffirst_name, "").toString() + " " + CM.getSp(ViewDrawer.this, CV.Preflast_name, "").toString());
        } catch (Exception e) {

        }
        navHeaderEamil.setText(CM.getSp(ViewDrawer.this, CV.PrefEmail, "").toString());


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
        if (isOpen) {
            menu.getItem(0).setIcon(R.drawable.ic_arrow_back_white_24dp);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_dehaze_white_24dp);
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
                fragment = new FragHome();
                ft.replace(R.id.container, fragment);
                //fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.VISIBLE);
                break;
            case 1:
                fragment = new FragFinalizedRequest();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 2:
                fragment = new FragFinalizedResponses();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 3:
                fragment = new FragFollowers();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 4:
                fragment = new FragBlockUser();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
                fm.popBackStack();
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 5:
                fragment = new FragRemoveRequest();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 6:
                fragment = new FragPromoteHotel();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 7:
                fragment = new FragContactUs();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
                ft.commit();
                bottomNavigation.setVisibility(View.GONE);
                break;
            case 8:
                fragment = new FragFaq();
                ft.replace(R.id.container, fragment).addToBackStack("FragHome");
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
                        CM.startActivity(ViewDrawer.this, ViewLoginActivity.class);
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
}
