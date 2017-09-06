package com.app.elixir.TravelB2B;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.app.elixir.TravelB2B.database.DbHelper;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.volly.VolleySingleton;

import java.io.IOException;


public class TravelB2B extends Application {

    //initialize font typeface
    public Typeface mTypeFaceRalewayLight;
    public Typeface mTypeFaceRobotoBlack;
    public Typeface mTypeFaceRobotoBold;
    public Typeface mTypeFaceRobotoCondensed;
    public Typeface mTypeFaceRobotoCondensedBold;
    public Typeface mTypeFaceRobotoLight;
    public Typeface mTypeFaceRobotoLightItalic;
    public Typeface mTypeFaceRobotoMedium;
    public Typeface mTypeFaceRobotoRegular;
    public Typeface mTypeFaceRobotoThin;


    public Typeface mTypeFaceDroidSerifBold;
    public Typeface mTypeFaceDroidSerifBoldItalic;
    public Typeface mTypeFaceDroidSerifItalic;
    public Typeface mTypeFaceDroidSerifRegular;


    public Context mContext;
    public VolleySingleton volley;
    private DbHelper dbHelper;

    public static SQLiteDatabase sqLiteDatabase;
    private static TravelB2B Instance;
    public static volatile Handler applicationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize font typeface and accessing instance font wise
        initializeTypeFace();
        Instance = this;
        mContext = getApplicationContext();
        volley = new VolleySingleton(mContext);

        DbHelper dbHelper = new DbHelper(mContext);
        try {
            dbHelper.createDataBase();
            sqLiteDatabase = dbHelper.openDataBase();
            CM.copyDataBase(mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        applicationHandler = new Handler(getInstance().getMainLooper());

    }


    /**
     * Access direct font-face instance  in settypeface method
     */
    private void initializeTypeFace() {
        mTypeFaceRalewayLight = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_ralway_light));

        mTypeFaceRobotoBlack = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_black));
        mTypeFaceRobotoBold = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_bold));
        mTypeFaceRobotoCondensed = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_condensed));
        mTypeFaceRobotoCondensedBold = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_condensed_bold));
        mTypeFaceRobotoLight = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_light));
        mTypeFaceRobotoMedium = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_medium));
        mTypeFaceRobotoRegular = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_regular));

        mTypeFaceRobotoThin = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_thin));
        mTypeFaceRobotoLightItalic = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_lightitalic));
        mTypeFaceDroidSerifBold = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_bold));

        mTypeFaceDroidSerifBoldItalic = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_DroidSerif_BoldItalic));
        mTypeFaceDroidSerifItalic = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_DroidSerif_Italic));
        mTypeFaceDroidSerifRegular = Typeface.createFromAsset(
                getAssets(),
                getResources().getString(
                        R.string.fontface_roboto_regular));


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static TravelB2B getInstance() {
        return Instance;
    }
}
