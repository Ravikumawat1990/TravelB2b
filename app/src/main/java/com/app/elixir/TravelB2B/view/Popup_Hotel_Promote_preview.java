package com.app.elixir.TravelB2B.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;

/**
 * Created by User on 14-Jul-2017.
 */
//Mohit
public class Popup_Hotel_Promote_preview extends Activity {

    ImageView imgHotel;
    String hotelName,hotelType,tarrifCheap,tarrifExpensive,hotelWebsite,hotelCity,hotelState,hotelDuration,hotelCharge;
    MtplTextView txtHotelName,txtHotelType,txtWebsite,txtTarrifValue,txtCity,txtState,txtDurationValue;
    Bitmap hotelPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_promote_hotel_preview);
        initView();
    }

    public void initView() {

        imgHotel=(ImageView)findViewById(R.id.img_hotel);
        txtHotelName=(MtplTextView)findViewById(R.id.txtHotelName);
        txtHotelType=(MtplTextView)findViewById(R.id.txtHotelType);
        txtTarrifValue=(MtplTextView)findViewById(R.id.txtTarrifValue);
        txtWebsite=(MtplTextView)findViewById(R.id.txtWebSite);
        txtCity=(MtplTextView)findViewById(R.id.txtCity);
        txtState=(MtplTextView)findViewById(R.id.txtState);
        txtDurationValue=(MtplTextView)findViewById(R.id.txtDuration_Charge_value);

        if( getIntent().getExtras() != null)
        {
            Intent in=getIntent();
            Bundle bu = in.getExtras();
            //decode image
            byte[] byteArray = in.getByteArrayExtra("promppic");
            hotelPic = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            hotelName=bu.getString("hname");
            hotelType=bu.getString("htype");
            tarrifCheap=bu.getString("ctarrifroom");
            tarrifExpensive=bu.getString("etarrifroom");
            hotelWebsite=bu.getString("hwebsite");
            hotelState=bu.getString("hsatate");
            // hotelCityintent.putExtra("hcity",byteArray);
            hotelDuration=bu.getString("hduration");
            hotelCharge=bu.getString("hcharge");

            //set views
            imgHotel.setImageBitmap(hotelPic);
            txtHotelName.setText(hotelName);
            txtHotelType.setText(hotelType);
            txtWebsite.setText(hotelWebsite);
            txtTarrifValue.setText(tarrifCheap+" - "+tarrifExpensive);
            txtCity.setText(hotelState);
            txtState.setText(hotelState);
            txtDurationValue.setText(hotelDuration+" - "+hotelCharge);

        }
    }
}
