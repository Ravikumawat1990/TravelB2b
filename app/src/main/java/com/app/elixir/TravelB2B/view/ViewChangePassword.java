package com.app.elixir.TravelB2B.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class ViewChangePassword extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ViewChangePassword";
    MtplButton login_btn;
    Activity thisActivity;
    String[] validation = {"Email ", "Old Password", "New Password"};
    private EditText edtemail, edtoldPass, edtNewPass;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_changepassword);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.cngPassword));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewChangePassword.this);

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        TextView titleTextView = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_DroidSerif_Bold));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(18);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        init();
    }

    public void init() {
        edtemail = (EditText) findViewById(R.id.edtemail);

        edtemail.setText(CM.getSp(ViewChangePassword.this, CV.PrefEmail, "").toString());


        edtemail.setSelection(edtemail.getText().length());
        edtoldPass = (EditText) findViewById(R.id.edtoldPass);
        edtNewPass = (EditText) findViewById(R.id.edtNewPass);
        login_btn = (MtplButton) findViewById(R.id.changePassword_btn);
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == login_btn) {
            String valid = CM.Validation(validation, edtemail, edtoldPass, edtNewPass);
            if (!valid.equals(CV.Valid)) {
                CM.showToast(valid, ViewChangePassword.this);
            } else {
                if (CM.isEmailValid(edtemail.getText().toString())) {
                    webChangePassword(edtoldPass.getText().toString(), edtNewPass.getText().toString(), CM.getSp(ViewChangePassword.this, CV.PrefID, "").toString());
                } else {
                    CM.showToast(getString(R.string.entvemail), ViewChangePassword.this);
                }

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewChangePassword.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewChangePassword.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void webChangePassword(String password, String newpassword, String userId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewChangePassword.this, true, true);
            WebService.getChagePassword(v, password, newpassword, userId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForRegister(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewChangePassword.this)) {
                        CM.showPopupCommonValidation(ViewChangePassword.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForRegister(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewChangePassword.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    CM.showToast(jsonObject.getString("msg").toString(), ViewChangePassword.this);
                    edtemail.setText("");
                    edtoldPass.setText("");
                    edtNewPass.setText("");
                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.getString("msg").toString(), ViewChangePassword.this);
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewChangePassword.this, e.getMessage(), false);
        }
    }


}
