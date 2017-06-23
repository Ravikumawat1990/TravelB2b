package com.app.elixir.TravelB2B.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mtpl on 12/15/2015.
 */
public class Model_Login {
    @SerializedName("userID")
    public String userID;
    @SerializedName("userrole")
    public String userrole;
    @SerializedName("fullName")
    public String fullName;
    @SerializedName("qrcode")
    public String qrcode;
    @SerializedName("mobileNo")
    public String mobileNo;
    @SerializedName("email")
    public String email;
    @SerializedName("address")
    public String address;
    @SerializedName("city")
    public String city;
    @SerializedName("country")
    public String country;
    @SerializedName("postCode")
    public String postCode;
    @SerializedName("gender")
    public String gender;
    @SerializedName("profilePic")
    public String profilePic;
}
