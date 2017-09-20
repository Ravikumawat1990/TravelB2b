package com.app.elixir.TravelB2B.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.pojos.pojoCity;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.volly.MtplProgressDialog;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by User on 19-Sep-2017.
 */

public class ViewCustomWebView extends AppCompatActivity {
    private static final String TAG = "WebView";
    Toolbar toolbar;
    WebView webView;
    ProgressBar progressBar;
    String url, pId;
    MtplProgressDialog mtplDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_webview);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        pId = intent.getStringExtra("pid");
        webCallPromoCount(pId);
        initView();
    }

    private void initView() {

        webView = (WebView) findViewById(R.id.webView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mtplDialog = new MtplProgressDialog(ViewCustomWebView.this, "", false);
        webView.getSettings().setJavaScriptEnabled(true);
      //  webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Do something on page loading started
                // Visible the progressbar
                mtplDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Do something when page loading finished
                mtplDialog.dismiss();
                // Toast.makeText(mContext,"Page Loaded.",Toast.LENGTH_SHORT).show();
            }

        });


    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    public void webCallPromoCount(String PromoId) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewCustomWebView.this, true, true);
            WebService.getPromoCount(v, PromoId, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (ViewCustomWebView.this.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForCount(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewCustomWebView.this)) {
                        CM.showPopupCommonValidation(ViewCustomWebView.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getResponseForCount(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewCustomWebView.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {


                case "200":
                    if (jsonObject.optString("ResponseObject") != null) {


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
            CM.showPopupCommonValidation(ViewCustomWebView.this, e.getMessage(), false);
        }
    }
}
