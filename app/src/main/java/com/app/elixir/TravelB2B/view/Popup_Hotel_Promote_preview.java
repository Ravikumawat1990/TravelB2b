package com.app.elixir.TravelB2B.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by User on 14-Jul-2017.
 */
//Mohit
public class Popup_Hotel_Promote_preview extends Activity implements View.OnClickListener {

    public String TAG = "Promote Hotel Preview";
    ImageView imgHotel;
    String userId, userName, hotelName, hotelType, tarrifCheap, tarrifExpensive, hotelWebsite, hotelCity, hotelcityids, hcityprice, hotelState, hotelDuration, hotelCharge;
    MtplTextView txtHotelName, txtHotelType, txtWebsite, txtTarrifValue, txtCity, txtState, txtDurationValue;
    MtplButton btnPromote;
    Bitmap hotelPic;
    String hotelImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_promote_hotel_preview);
        initView();
    }

    public void initView() {

        imgHotel = (ImageView) findViewById(R.id.img_hotel);
        txtHotelName = (MtplTextView) findViewById(R.id.txtHotelName);
        txtHotelType = (MtplTextView) findViewById(R.id.txtHotelType);
        txtTarrifValue = (MtplTextView) findViewById(R.id.txtTarrifValue);
        txtWebsite = (MtplTextView) findViewById(R.id.txtWebSite);
        txtCity = (MtplTextView) findViewById(R.id.txtCity);
        txtState = (MtplTextView) findViewById(R.id.txtState);
        txtDurationValue = (MtplTextView) findViewById(R.id.txtDuration_Charge_value);
        btnPromote = (MtplButton) findViewById(R.id.buttonPromote);

        if (getIntent().getExtras() != null) {
            Intent in = getIntent();
            Bundle bu = in.getExtras();
            //decode image
            //byte[] byteArray = in.getByteArrayExtra("promppic");
            //  hotelPic = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            userName = bu.getString("username");
            userId = bu.getString("userid");
            hotelName = bu.getString("hname");
            hotelType = bu.getString("htype");
            tarrifCheap = bu.getString("ctarrifroom");
            tarrifExpensive = bu.getString("etarrifroom");
            hotelWebsite = bu.getString("hwebsite");
            hotelState = bu.getString("hsatate");
            hotelCity = bu.getString("hcity");
            hotelcityids = bu.getString("hcityid");
            hcityprice = bu.getString("hcityprice");
            hotelDuration = bu.getString("hduration");
            hotelCharge = bu.getString("hcharge");

            //hotelImage = Base64.encodeToString(byteArray, Base64.DEFAULT);;
            byte[] decodedString = Base64.decode(CM.getSp(Popup_Hotel_Promote_preview.this, "img", "").toString(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //set views
            imgHotel.setImageBitmap(decodedByte);

            hotelImage = BASE64String(imgHotel);
            txtHotelName.setText(hotelName);
            txtHotelType.setText(hotelType);
            txtWebsite.setText(hotelWebsite);
            txtTarrifValue.setText(tarrifCheap + " - " + tarrifExpensive);
            txtCity.setText(hotelCity);
            txtState.setText(hotelState);
            txtDurationValue.setText(hotelDuration + " - " + hotelCharge);

        }

        btnPromote.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonPromote:

                webCallHotelPromotion(userId, hotelName, hotelType, tarrifCheap, tarrifExpensive, hotelWebsite, hotelcityids, hcityprice, hotelDuration, hotelCharge, hotelImage);
                break;


        }
    }

    // String userId, String hname, String hcategories, String ctariff, String etariff, String website, String cityid, String citycharge,  String duration,  String charges,
    public void webCallHotelPromotion(String userId, String hname, String hcategories, String ctariff, String etariff, String website, String cityid, String citycharge, String duration, String charges, String hotelimage) {
        try {
            VolleyIntialization v = new VolleyIntialization(Popup_Hotel_Promote_preview.this, true, true);
            WebService.getHotelPromotion(v, userId, hname, hcategories, ctariff, etariff, website, cityid, citycharge, duration, charges, hotelimage, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (Popup_Hotel_Promote_preview.this.isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getResponseForHotelPromo(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(Popup_Hotel_Promote_preview.this)) {
                        CM.showPopupCommonValidation(Popup_Hotel_Promote_preview.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getResponseForHotelPromo(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(Popup_Hotel_Promote_preview.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":

                    break;
                case "202":
                    break;
                case "402":
                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(Popup_Hotel_Promote_preview.this, e.getMessage(), false);
        }
    }

    public String BASE64String(ImageView image) {
        String encodedImage = "";
        try {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            Log.i(TAG, "initView: " + encodedImage);

        } catch (Exception e) {
            encodedImage = "";
        }


        return encodedImage;
    }
}
