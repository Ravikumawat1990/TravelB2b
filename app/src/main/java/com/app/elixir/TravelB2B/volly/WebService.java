package com.app.elixir.TravelB2B.volly;


import com.android.volley.Request;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.URLS;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static com.app.elixir.TravelB2B.utils.CV.email;


/**
 * Created by jaimin on 15-12-2015.
 */
public class WebService {

    /**
     * @param vollyInit
     * @param vollyHanlder
     * @throws JSONException
     */
  /*  public static void Login(VolleyIntialization vollyInit, String strMobileNo, OnVolleyHandler vollyHanlder) throws JSONException {

        String url = URLS.GETPRODUCTCATEGORIES;
        Map<String, String> params = new HashMap<>();
        params.put(WebServiceTag.TAG_STR_APIKEY, CV.API_KEY);
        params.put(WebServiceTag.TAG_STR_UDID, "188488");
        params.put(WebServiceTag.TAG_STR_ACCESSTOKEN, "d6978");
        params.put(WebServiceTag.TAG_STR_MOBILENO, strMobileNo);
        params.put(WebServiceTag.TAG_STR_DEVICETYPE, CV.DEVICE_TYPE);
        params.put(WebServiceTag.TAG_STR_DEVICETOKEN, "sdvvdb234234");

        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }*/
    public static void getFaq(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FAQ;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);

    }

    public static void getCity(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.CITY;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);

    }

    public static void getState(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.STATE;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);

    }

    public static void getCountry(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.COUNTRY;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);

    }

    public static void getRegister(VolleyIntialization vollyInit, String catName, String comName, String fname, String lname, String email, String pass, String confPass, String contact, String address, String locality, String city, String state, String pincode, String country, String selectedCity, String checkStatus, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.REGISTER;
        Map<String, String> params = new HashMap<>();
        params.put(CV.company_name, comName);
        params.put(CV.role_id, catName);
        params.put(CV.first_name, fname);
        params.put(CV.last_name, lname);
        params.put(CV.email, email);
        params.put(CV.password, pass);
        params.put(CV.cpassword, confPass);
        params.put(CV.mobile_number, contact);
        params.put(CV.locality, locality);
        params.put(CV.address, address);
        params.put(CV.address1, "");
        params.put(CV.city_name, "");
        params.put(CV.city_id, city);
        params.put(CV.state_name, "");
        params.put(CV.state_id, state);
        params.put(CV.pincode, pincode);
        params.put(CV.country_name, "");
        params.put(CV.country_id, country);
        params.put(CV.preference, "1,2");
        params.put(CV.term_n_cond, checkStatus);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getLogin(VolleyIntialization vollyInit, String email, String password, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.LOGIN;
        Map<String, String> params = new HashMap<>();
        params.put(CV.email, email);
        params.put(CV.password, password);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }


    public static void getFP(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FORGOTPASSWORD;
        Map<String, String> params = new HashMap<>();
        params.put(CV.email, email);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getAdv(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FORGOTPASSWORD;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);

    }

    public static void getChagePassword(VolleyIntialization vollyInit, String oldPass, String pass, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.CHANGEPASSSORD;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.OLD_PASSWORD, oldPass);
        params.put(CV.password, pass);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getEditProfile(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FORGOTPASSWORD;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);

    }

    public static void getBloackUser(VolleyIntialization vollyInit, String blockuser, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.BLOCKUSER;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, blockuser);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }


}

