package com.app.elixir.TravelB2B.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.TravelB2B;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jaimin on 6/24/2015.
 */
public class CM {


    /**
     * Getting Typeface Passing attributes(Font name)
     *
     * @param ctx
     * @param attrs
     * @return
     */
    public static Typeface getTypeFace(Context ctx, AttributeSet attrs) {
        Typeface myTypeface = null;
        if (attrs != null) {
            TypedArray a = ctx.obtainStyledAttributes(attrs,
                    R.styleable.mtplviews);
            String fontName = a.getString(R.styleable.mtplviews_TypeFace);


            if (fontName != null) {
//Checking fontname if match with MainApplication set type as fontname otherwise RobotoMedium font set
                if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_ralway_light))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRalewayLight;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_black))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoBlack;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_bold))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoBold;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_condensed))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoCondensed;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_condensed_bold))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoCondensedBold;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_light))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoLight;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_medium))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoMedium;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_regular))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoRegular;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_thin))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoThin;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_roboto_lightitalic))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoLightItalic;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_DroidSerif_Bold))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceDroidSerifBold;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_DroidSerif_Regular))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceDroidSerifRegular;
                } else if (fontName.equalsIgnoreCase(ctx.getString(R.string.fontface_DroidSerif_BoldItalic))) {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceDroidSerifBoldItalic;
                } else {
                    myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoMedium;
                }
            } else {
                myTypeface = ((TravelB2B) ctx.getApplicationContext()).mTypeFaceRobotoMedium;
            }

            a.recycle();
        }
        return myTypeface;
    }

    /**
     * Checking Internet is available or not
     *
     * @param context
     * @return
     */
    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void setSp(Context activity, String key, Object value) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getPackageName(), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }

        editor.commit();
        editor = null;
        prefs = null;

    }

    /**
     * returns object of specific type from SharedPrefs
     *
     * @param activity
     * @param key          name of data in SP
     * @param defaultValue used if no value available for this key
     * @return
     */
    public static Object getSp(Context activity, String key, Object defaultValue) {
        SharedPreferences prefs = activity.getSharedPreferences(
                activity.getPackageName(), Activity.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return prefs.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return prefs.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return prefs.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            return prefs.getLong(key, (Long) defaultValue);
        } else {
            return prefs.getFloat(key, (Float) defaultValue);
        }

    }

    // call this method when you don't have any data via intent
    public static void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_from_left,
                R.anim.push_out_to_right);

    }

    // call this method when you have to pass data in intent
    public static void startActivity(Intent intent, Activity activity) {

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.push_in_from_left,
                R.anim.push_out_to_right);

    }

    // call this method when you have to pass data in intent
    public static void startActivityForResult(Intent intent, int resultcode, Activity activity) {

        activity.startActivityForResult(intent, resultcode);
        activity.overridePendingTransition(R.anim.push_in_from_left,
                R.anim.push_out_to_right);

    }

    public static void finishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(0, R.anim.push_out_to_left);
    }


    public static String converDateFormate(String oldpattern,
                                           String newPattern, String dateString) {
        // SimpleDateFormat sdf = new SimpleDateFormat(oldpattern);
        SimpleDateFormat sdf = new SimpleDateFormat(oldpattern,
                CV.LOCALE_USE_DATEFORMAT);
        Date testDate = null;
        try {
            testDate = sdf.parse(dateString);
            SimpleDateFormat formatter = new SimpleDateFormat(newPattern,
                    CV.LOCALE_USE_DATEFORMAT);
            String newFormat = formatter.format(testDate);
            System.out.println("" + newFormat);
            return newFormat;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public static int getDeviceWidth(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    /**
     * @param activity
     * @return
     */
    public static Dialog showPopupCommonValidation(final Activity activity,
                                                   final String title, final boolean isActivityFinish) {
        if (activity.isFinishing()) {
            return null;
        }
        final Dialog dialog = new Dialog(activity
        );
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {


                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_commonmsg);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                RelativeLayout rltvMain = (RelativeLayout) dialog.findViewById(R.id.RelativeLayout1);
                MtplDrawables.setStrokeGrayandFillWhite(rltvMain, activity);
                final MtplTextView txtHeader = (MtplTextView) dialog
                        .findViewById(R.id.dialog_commonmsg_txtHeader);
                txtHeader.setText(title);
                final MtplTextView txtOk = (MtplTextView) dialog
                        .findViewById(R.id.dialog_commonmsg_txtOkBtn);
                final MtplTextView txtCancel = (MtplTextView) dialog
                        .findViewById(R.id.dialog_commonmsg_txtCancelBtn);
                MtplDrawables.setBlueLightBlue(txtOk, activity);

                // CM.setFontRobotoRegular(activity, txtHeader);
                txtOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        if (isActivityFinish) {
                            finishActivity(activity);
                        }
                    }
                });
                dialog.show();
            }
        });
        return dialog;
    }


    public static void showToast(String msg, Context context) {

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();


    }

    /**
     * Loading Image Progress
     */
    public static void loadImageProgess(ImageView imgProgress, Activity activity) {
        imgProgress.setVisibility(View.VISIBLE);
        Animation a = AnimationUtils.loadAnimation(activity, R.anim.scale);
        a.setDuration(1000);
        imgProgress.startAnimation(a);

        a.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
    }

    public static String Validation(String[] edittextName, TextView... edt) {
        String message = null;
        for (int i = 0; i < edt.length; i++) {
            if (edt[i].getText().length() > 0) {
                message = CV.Valid;
            } else {
                message = edittextName[i] + " is required.";
                break;
            }
        }
        return message;
    }

//    public static <T> T JsonParse(T t, String response)
//            throws JsonSyntaxException, IOException, XmlPullParserException {
//        InputStream in = new ByteArrayInputStream(response.getBytes());
//        JsonReader reader;
//        reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
//        GsonBuilder b = new GsonBuilder();
//        b.registerTypeAdapter(boolean.class, new BooleanSerializer());
//        Gson gson = b.create();
//        t = (T) gson.fromJson(reader, t.getClass());
//        reader.close();
//        return t;
//    }

    /**
     * Json Response using key value
     *
     * @param Key
     * @param response
     * @return
     */
    public static String getValueFromJson(String Key, String response) {
        // if you use below code then it will throw null pointer exception when
        // key is not found in jsonResponse
        Gson gson = new Gson();
        ByteArrayInputStream io = new ByteArrayInputStream(
                response.getBytes());
        Reader reader = new InputStreamReader(io);
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        return jsonObject.get(Key).getAsString();
    }

    public static boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }


    // database Operation copy file asset to application database folder
    public static void copyDataBase(Context mActivity) throws IOException {

        InputStream myInput = new FileInputStream(new File("/data/data/"
                + mActivity.getPackageName() + "/databases/"
                + "sample.sqlite"));
        File files = new File(Environment.getExternalStorageDirectory()
                + "/files/");
        files.mkdirs();
        String outFileName = Environment.getExternalStorageDirectory()
                + "/files/sample.sqlite";
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int bufferLength;
        while ((bufferLength = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, bufferLength);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.d("tag", "Copy Database Done");
    }

    /**
     * @param intMonthAgo
     * @param dateFormat
     * @return
     */
    public static String getMonthAgoDate(int intMonthAgo, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat,
                CV.LOCALE_USE_DATEFORMAT);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, intMonthAgo);
        return formatter.format(calendar.getTime());
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        // DateFormat formatter = new SimpleDateFormat(dateFormat);
        DateFormat formatter = new SimpleDateFormat(dateFormat,
                CV.LOCALE_USE_DATEFORMAT);

        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String getCurrentDate(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        return getDate(calendar.getTimeInMillis(), dateFormat);
    }

    /**
     * @param TableName
     * @param dbfield
     * @param fieldValue
     * @return
     */
    public static boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String dbfield, String fieldValue) {
        SQLiteDatabase sqldb = TravelB2B.sqLiteDatabase;
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public static Date convertStringtodate(String strDate) {
        // SimpleDateFormat format = new SimpleDateFormat(
        // CommonVariable.DATABASE_DATE_FORMAT);

        SimpleDateFormat format = new SimpleDateFormat(
                CV.DATABASE_DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(strDate);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Http url parameter value divide in map value
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> splitUrlVariable(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public static String getDoubleTwoDecimal(double value)
            throws NumberFormatException {

        DecimalFormat decimalFormat = new DecimalFormat(
                "#0.00");

        return decimalFormat.format(value);
    }

    public static String getFormatedData(String unformatedData) {
        if (unformatedData != null) {
            try {
                //unformatedData.replaceAll(",", "");
                Double result = Double.valueOf(unformatedData);
                DecimalFormat myFormatter = new DecimalFormat("#,##,##0.00");
                //DecimalFormat myFormatter = new DecimalFormat("#,###,###");
                //If you don't want to show .00 format
                return myFormatter.format(result);
            } catch (NumberFormatException e) {
                return unformatedData;
            }

        } else {
            return "0.00";
        }
    }


    public static void shareApp(Context mContext) {
        String shareBody = "Check out the new  Android App " + mContext.getString(R.string.app_name);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Invite Using");
        String link = "https://play.google.com/store/apps/details?id=" + mContext.getPackageName();
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody + " " + link);
        mContext.startActivity(Intent.createChooser(sharingIntent,
                "Share Using..."));
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

}
