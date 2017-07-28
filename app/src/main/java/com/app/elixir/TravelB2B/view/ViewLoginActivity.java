package com.app.elixir.TravelB2B.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
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


/**
 * A login screen that offers login via email/password.
 */
public class ViewLoginActivity extends AppCompatActivity implements OnClickListener {


    private static final String TAG = "ViewLoginActivity";
    private TextInputLayout mEmailView;

    Toolbar toolbar;
    private EditText edtEmail, editPassword;
    private LinearLayout txtForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.login_btnlogin);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backicnwht);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        mEmailView = (TextInputLayout) findViewById(R.id.login_edtEmail);


        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewLoginActivity.this);

            }
        });
        TextView titleTextView = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_roboto_black));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(18);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        initView();


    }

    private void initView() {
        MtplButton btnSinup = (MtplButton) findViewById(R.id.login_btnSignup);
        MtplButton btnLogin = (MtplButton) findViewById(R.id.login_btnLogin);
        txtForgotPassword = (LinearLayout) findViewById(R.id.txtForgotPassword);
        edtEmail = (EditText) findViewById(R.id.login_Email);
        edtEmail.setSelection(edtEmail.getText().toString().length());
        editPassword = (EditText) findViewById(R.id.login_edtPassword);
        Typeface face = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_roboto_light));
        editPassword.setTypeface(face);
        editPassword.setTransformationMethod(new PasswordTransformationMethod());


        btnSinup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewLoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btnSignup:
                CM.startActivity(ViewLoginActivity.this, ViewRegisterAs.class);
                break;
            case R.id.login_btnLogin:

                if (!edtEmail.getText().toString().equals("")) {
                    if (!editPassword.getText().toString().equals("")) {
                        webLogin(edtEmail.getText().toString(), editPassword.getText().toString());
                    } else {
                        CM.showToast(getString(R.string.entvpass), ViewLoginActivity.this);
                    }
                } else {
                    CM.showToast(getString(R.string.entvemail), ViewLoginActivity.this);
                }
                break;
            case R.id.txtForgotPassword:
                showPopup();
                break;


        }

    }


    private void showPopup() {
        final Dialog dialog = new Dialog(ViewLoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        final MtplEditText edtemail = (MtplEditText) dialog.findViewById(R.id.edtEmail);
        Button dialogButtonCancle = (Button) dialog.findViewById(R.id.btnCancel);
        Button dialogButtonSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        dialogButtonCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButtonSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtemail.getText().toString().equals("") && CM.isEmailValid(edtemail.getText().toString())) {
                    dialog.dismiss();
                    webForgotPassword();

                } else {
                    CM.showToast(getString(R.string.entvemail), ViewLoginActivity.this);
                }
            }
        });

        dialog.show();
    }


    public void webLogin(String email, String password) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewLoginActivity.this, true, true);
            WebService.getLogin(v, email, password, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForLogin(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewLoginActivity.this)) {
                        CM.showPopupCommonValidation(ViewLoginActivity.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForLogin(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewLoginActivity.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONObject jsonObject1 = new JSONObject(jsonObject.getString("response_object"));
                    CM.showToast("Logged In Successfully.", ViewLoginActivity.this);
                    CM.setSp(ViewLoginActivity.this, CV.PrefID, String.valueOf(jsonObject1.getInt("id")));
                    CM.setSp(ViewLoginActivity.this, CV.PrefEmail, jsonObject1.optString("email"));
                    CM.setSp(ViewLoginActivity.this, CV.PrefRole_Id, String.valueOf(jsonObject1.getInt("role_id")));
                    CM.setSp(ViewLoginActivity.this, CV.PrefMobile_number, jsonObject1.optString("mobile_number"));
                    CM.setSp(ViewLoginActivity.this, CV.Preffirst_name, jsonObject1.optString("first_name"));
                    CM.setSp(ViewLoginActivity.this, CV.Preflast_name, jsonObject1.optString("last_name"));
                    CM.setSp(ViewLoginActivity.this, CV.PrefPreference, jsonObject1.optString("preference"));
                    CM.setSp(ViewLoginActivity.this, CV.PrefState_id, jsonObject1.optString("state_id"));
                    CM.setSp(ViewLoginActivity.this, CV.PrefCity_id, jsonObject1.optString("city_id"));


                    CM.setSp(ViewLoginActivity.this, CV.PrefIsLogin, "1");
                    CM.startActivity(ViewLoginActivity.this, ViewDrawer.class);
                    finish();
                    break;
                case "202":
                    break;
                case "402":
                    break;
                case "501":
                    CM.showToast(getString(R.string.loginmsg), ViewLoginActivity.this);
                    break;

                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewLoginActivity.this, e.getMessage(), false);
        }
    }


    public void webForgotPassword() {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewLoginActivity.this, true, true);
            WebService.getFP(v, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForForgotPass(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewLoginActivity.this)) {
                        CM.showPopupCommonValidation(ViewLoginActivity.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForForgotPass(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewLoginActivity.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    CM.showToast(jsonObject.optString("msg"), ViewLoginActivity.this);

                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewLoginActivity.this);

                    finish();
                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewLoginActivity.this, e.getMessage(), false);
        }
    }

}

