package com.app.elixir.TravelB2B.volly;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.TravelB2B;
import com.app.elixir.TravelB2B.utils.CM;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by jaimin on 15-12-2015.
 */
public class VolleyIntialization {
    private Activity mActivity;

    private boolean mIsShowPopup;
    private boolean mIsDismissPopup;
    private MtplProgressDialog mtplDialog;

    public VolleyIntialization(Activity activity, boolean showpopup, boolean dismisspopup) {

        mActivity = activity;
        mIsShowPopup = showpopup;
        mIsDismissPopup = dismisspopup;

    }

    public Activity getActivity() {
        return mActivity;
    }

    public boolean getShowPopup() {
        return mIsShowPopup;
    }

    public boolean getDismissPopup() {
        return mIsDismissPopup;
    }

//Volly Webservice Related Methods //

    /**
     * Webservice call with Map Key pair value and after response not
     * any ws call use this method(single boolean) for dialog dismiss
     *
     * @param url
     * @param requestMethod
     * @param json
     * @param vollyHandler
     */
    public void vollyStringRequestCall(String url, final int requestMethod, final Map<String, String> params, final OnVolleyHandler vollyHandler) throws JSONException {
        //AS we have to pass Security key in ever webservice we have
//        if (json != null) {
//            json.put("strSecurityKey", CV.SECURITY_KEY);
//        }
         if (!CM.isInternetAvailable(mActivity)) {
            vollyHandler.onVollyError(mActivity.getResources().getString(R.string.msg_internet_unavailable_msg));
            return;
        }
        if (mIsShowPopup) {
            showMtplDialog(mActivity);
        }
        Log.i("WebCalls", url);
        StringRequest stringRequest = new StringRequest(requestMethod,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
                        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
                            dismissMtplDialog(mActivity);
                        } else {
                            if (mIsDismissPopup) {
                                dismissMtplDialog(mActivity);
                            }
                        }
                        vollyHandler.onVollySuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Webcalls", "error=" + error.getMessage());
                dismissMtplDialog(mActivity);
                String errorSet = getActivity().getResources().getString(R.string.msg_networkerror);
                vollyHandler.onVollyError(errorSet);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Log.i("Webcalls", "params=" + params.toString());
                return params;
            }
        };
        ((TravelB2B) mActivity.getApplicationContext()).volley.addToRequestQueue(stringRequest);

    }


    //Volly Webservice Related Methods End //
    public void showMtplDialog(Activity mActivity) {
        if (mActivity.isFinishing()) {
            return;
        }
        if (mtplDialog == null)
            mtplDialog = new MtplProgressDialog(mActivity, "", false);
        if (!mtplDialog.isShowing())
            mtplDialog.show();
    }

    private void dismissMtplDialog(Activity activity) {

        if (mtplDialog != null && mtplDialog.isShowing())
            mtplDialog.dismiss();
    }


}
