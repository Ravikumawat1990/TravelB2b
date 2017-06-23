package com.app.elixir.TravelB2B.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mtpl on 12/15/2015.
 */
public class Model_Country {
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
    @SerializedName("responsecode")
    public String responsecode;
    @SerializedName("login")
    public ArrayList<Model_Login> mListLogin = new ArrayList<Model_Login>();
}
