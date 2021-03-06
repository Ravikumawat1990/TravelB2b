package com.app.elixir.TravelB2B.volly;


import com.android.volley.Request;
import com.app.elixir.TravelB2B.pojos.pojoPackage;
import com.app.elixir.TravelB2B.pojos.pojoStayReq;
import com.app.elixir.TravelB2B.pojos.pojoTransportReq;
import com.app.elixir.TravelB2B.pojos.transportPojo;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.utils.URLS;
import com.app.elixir.TravelB2B.view.ViewCheckResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.elixir.TravelB2B.utils.CV.city_id;
import static com.app.elixir.TravelB2B.utils.CV.email;
import static com.app.elixir.TravelB2B.utils.CV.role_id;


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

    public static void getRegister(VolleyIntialization vollyInit, String catName, String comName, String fname, String lname, String email, String pass, String confPass, String contact, String address, String locality, String city, String state, String pincode, String country, String selectedCity, String checkStatus, String deviceId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.REGISTER;
        Map<String, String> params = new HashMap<>();
        params.put(CV.company_name, comName);
        params.put(role_id, catName);
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
        params.put(city_id, city);
        params.put(CV.state_name, "");
        params.put(CV.state_id, state);
        params.put(CV.pincode, pincode);
        params.put(CV.country_name, "");
        params.put(CV.country_id, country);
        params.put(CV.preference, selectedCity);
        params.put(CV.term_n_cond, checkStatus);
        params.put(CV.DEVICEID, deviceId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getLogin(VolleyIntialization vollyInit, String email, String password, String regID, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.LOGIN;
        Map<String, String> params = new HashMap<>();
        params.put(CV.email, email);
        params.put(CV.password, password);
        params.put(CV.DEVICEID, regID);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }


    public static void getFP(VolleyIntialization vollyInit, String email, OnVolleyHandler vollyHanlder) throws JSONException {
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

    public static void getBloackUser(VolleyIntialization vollyInit, String blockuser, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.BLOCKUSER;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, blockuser);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getBlockUser(VolleyIntialization vollyInit, String userid, String blockuser, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.BLOCKUSERAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.BlockUser_Id, blockuser);
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getUnBloackUser(VolleyIntialization vollyInit, String userId, String blockuser, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.UNBLOCKUSERAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.BlockUser_Id, blockuser);
        params.put(CV.USER_ID, userId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getNoti(VolleyIntialization vollyInit, String userId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GETCHATNOTIFICATION;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getClearNoti(VolleyIntialization vollyInit, String userId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.CLEARREADCHATSAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getEditProfile(VolleyIntialization vollyInit, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.EDITPROFILE;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getSubmitEditProfile(VolleyIntialization vollyInit, Map<String, String> params, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.SUBMITEDITPROFILE;
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getTestimonial(VolleyIntialization vollyInit, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.TESTIMONIALAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getMyProfile(VolleyIntialization vollyInit, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.USERPROFILEAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getUserProfile(VolleyIntialization vollyInit, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.MY_PROFILE;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getMyResponse(VolleyIntialization vollyInit, String userid, String reqType, String reqId, String startDate, String endDate, String budget, String agentname, String pcity, String dcity, String member, String chatwith, String follow, String sharedetail, String sortItem, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.MYRESPONSE;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);

        if (!sortItem.isEmpty()) {
            params.put(CV.SORT, sortItem);
        } else {

        }

        if (!reqType.isEmpty()) {
            params.put(CV.REQ_TYPESEARCH, reqType);
        } else {
            params.put(CV.REQ_TYPESEARCH, "");
        }
        if (!reqId.isEmpty()) {
            params.put(CV.REFIDSEARCH, reqId);
        } else {
            params.put(CV.REFIDSEARCH, "");
        }
        if (!startDate.isEmpty()) {
            params.put(CV.STARTDATESEARCH, startDate);
        } else {
            params.put(CV.STARTDATESEARCH, "");
        }
        if (!endDate.isEmpty()) {
            params.put(CV.ENDDATESEARCH, endDate);
        } else {
            params.put(CV.ENDDATESEARCH, "");
        }
        if (!budget.isEmpty()) {
            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }
        } else {
            params.put(CV.BUDGETSEARCH, "");
        }

        if (!agentname.isEmpty()) {
            params.put(CV.AGENTNAMESEARCH, agentname);
        } else {
            params.put(CV.AGENTNAMESEARCH, "");
        }
        if (!pcity.isEmpty()) {
            params.put(CV.PICKUP_CITY, pcity);
        } else {
            params.put(CV.PICKUP_CITY, "");
        }
        if (!dcity.isEmpty()) {
            params.put(CV.DESTINATION_CITY, dcity);
        } else {
            params.put(CV.DESTINATION_CITY, "");
        }
        if (!member.isEmpty()) {
            params.put(CV.MEMBERS, member);
        } else {
            params.put(CV.MEMBERS, "");
        }
        if (!chatwith.isEmpty()) {
            params.put(CV.CHATWITH, chatwith);
        } else {
            params.put(CV.CHATWITH, "");
        }
        if (!follow.isEmpty()) {
            params.put(CV.FOLLOWSEARCH, follow);
        } else {
            params.put(CV.FOLLOWSEARCH, "");
        }
        if (!sharedetail.isEmpty()) {
            params.put(CV.SHARED_DETAILS, sharedetail);
        } else {
            params.put(CV.SHARED_DETAILS, "");
        }
        if (!member.isEmpty()) {
            params.put(CV.MEMBERS, member);
        } else {
            params.put(CV.MEMBERS, "");

        }

        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getMyResponseToReq(VolleyIntialization vollyInit, String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget, String agentName, String rating, String cityId, String pcity, String dcity, String member, String follow, String sortItem, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.RESPONDTOREQUESTAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.PrefRole_Id, roleId);

        if (sortItem.isEmpty()) {
            params.put(CV.SORT, sortItem);
        }

        if (roleId.equals("3")) {
            params.put(CV.city_id, cityId);
        }

        if (!reqType.isEmpty()) {
            params.put(CV.REQ_TYPESEARCH, reqType);
        } else {
            params.put(CV.REQ_TYPESEARCH, "");
        }
        if (!reqId.isEmpty()) {
            params.put(CV.REFIDSEARCH, reqId);
        } else {
            params.put(CV.REFIDSEARCH, "");
        }
        if (!startDate.isEmpty()) {
            params.put(CV.STARTDATESEARCH, startDate);
        } else {
            params.put(CV.STARTDATESEARCH, "");
        }
        if (!endDate.isEmpty()) {
            params.put(CV.ENDDATESEARCH, endDate);
        } else {
            params.put(CV.ENDDATESEARCH, "");
        }
        if (!budget.isEmpty()) {
            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }
        } else {
            params.put(CV.BUDGETSEARCH, "");
        }

        if (!pcity.isEmpty()) {
            params.put(CV.PICKUP_CITY, pcity);
        } else {
            params.put(CV.PICKUP_CITY, "");
        }
        if (!dcity.isEmpty()) {
            params.put(CV.DESTINATION_CITY, dcity);
        } else {
            params.put(CV.DESTINATION_CITY, "");
        }
        if (!member.isEmpty()) {
            params.put(CV.MEMBERS, member);
        } else {
            params.put(CV.MEMBERS, "");
        }
        if (!agentName.isEmpty()) {
            params.put(CV.AGENTNAMESEARCH, agentName);
        } else {
            params.put(CV.AGENTNAMESEARCH, "");
        }
        if (!follow.isEmpty()) {
            params.put(CV.FOLLOWSEARCH, follow);
        } else {
            params.put(CV.FOLLOWSEARCH, "");
        }
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getMyReq(VolleyIntialization vollyInit, String userid, String roleId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.MYREQUEST;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.PrefRole_Id, roleId);

        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void getRemReq(VolleyIntialization vollyInit, String userid, String reqid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.REMOVEREQUESTAPI;
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userid);
        params.put("request_id", reqid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void getDetail(VolleyIntialization vollyInit, String userid, String reqid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.RESPOSNEDETAIL;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.REQ_ID, reqid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

   /* public static void getFinalizeReq(VolleyIntialization vollyInit, String userid, String reqid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FINALIZEDREQUEST;
        Map<String, String> params = new HashMap<>();
        params.put(CV.PrefRole_Id, reqid);
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }*/

    public static void getFinalizeReq(VolleyIntialization vollyInit, String userid, String reqid, String reqType, String reqId, String budget, String startDate, String endDate, String member, String sort, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FINALIZEDREQUEST;
        Map<String, String> params = new HashMap<>();
        params.put(CV.PrefRole_Id, reqid);
        params.put(CV.USER_ID, userid);


        if (!sort.isEmpty()) {
            params.put(CV.SORT, sort);
        } else {
            // params.put(CV.REQ_TYPESEARCH, "");
        }
        if (!reqType.isEmpty()) {
            params.put(CV.REQ_TYPESEARCH, reqType);
        } else {
            params.put(CV.REQ_TYPESEARCH, "");
        }
        if (!reqId.isEmpty()) {
            params.put(CV.REFIDSEARCH, reqId);
        } else {
            params.put(CV.REFIDSEARCH, "");
        }
   /* if (!budget.isEmpty()) {
        params.put(CV.BUDGETSEARCH, budget);
    } else {
        params.put(CV.BUDGETSEARCH, "");
    }*/
        if (!budget.isEmpty()) {
            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }
        } else {
            params.put(CV.BUDGETSEARCH, "");
        }


        if (!startDate.isEmpty()) {
            params.put(CV.STARTDATESEARCH, startDate);
        } else {
            params.put(CV.STARTDATESEARCH, "");
        }
        if (!endDate.isEmpty()) {
            params.put(CV.ENDDATESEARCH, endDate);
        } else {
            params.put(CV.ENDDATESEARCH, "");
        }


        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void getFinalizeResponse(VolleyIntialization vollyInit, String userid, String reqId, String budget, String quotPrice, String name, String sort, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FINALRESPONSES;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);

        if (!sort.isEmpty()) {
            params.put(CV.SORT, sort); //CV.REFIDSEARCH
        } else {

        }

        if (!quotPrice.isEmpty()) {
            if (quotPrice.equals("100000+")) {
                params.put(CV.QUOTESEARCH, "100000-1000000000000"); //CV.QUOTATION_PRICE
            } else {
                params.put(CV.QUOTESEARCH, quotPrice);
            }

        } else {
            params.put(CV.QUOTESEARCH, "");
        }

        if (!budget.isEmpty()) {
            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }
        } else {
            params.put(CV.BUDGETSEARCH, "");

        }
        if (!name.isEmpty()) {
            params.put(CV.AGENTNAMESEARCH, name);
        } else {
            params.put(CV.AGENTNAMESEARCH, "");
        }

        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    /*public static void getRemoveReq(VolleyIntialization vollyInit, String userid, String roleId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.REMOVEDREQUEST;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.PrefRole_Id, roleId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }*/

    public static void getRemoveReq(VolleyIntialization vollyInit, String userid, String roleId, String reqType, String reqId, String budget, String startDate, String endDate, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.REMOVEDREQUEST;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.PrefRole_Id, roleId);
        if (!reqType.isEmpty()) {
            params.put(CV.REQ_TYPESEARCH, reqType);
        } else {
            params.put(CV.REQ_TYPESEARCH, "");
        }
        if (!reqId.isEmpty()) {
            params.put(CV.REFIDSEARCH, reqId);
        } else {
            params.put(CV.REFIDSEARCH, "");
        }
        if (!budget.isEmpty()) {
            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }
        } else {
            params.put(CV.BUDGETSEARCH, "");
        }


        params.put(CV.STARTDATESEARCH, startDate);
        params.put(CV.ENDDATESEARCH, endDate);


        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getFollowers(VolleyIntialization vollyInit, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.FOLLOWERS;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getPromocities(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GETHOTELCITIES;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);
    }

    public static void getCheckCity(VolleyIntialization vollyInit, String userId, String cityId, String duration, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.CHECKCITYSTATUSAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        params.put(CV.CITY_ID, cityId);
        params.put(CV.DURATION, duration);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    /*  public static void getCheckResposne(VolleyIntialization vollyInit, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
          String url = URLS.CHECKRESPONSESAPI;
          Map<String, String> params = new HashMap<>();
          params.put(CV.REQ_ID, userid);
          vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
      }
  */
    public static void getSplashCount(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.SPLASHCOUNTER;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getCounter(VolleyIntialization vollyInit, String userid, String rolID, String stateID, String pref, String cityId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.HOMECOUNTER;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.role_id, rolID);
        params.put(CV.state_id, stateID);
        params.put(CV.city_id, cityId);


        if (!pref.equals("null") && !pref.equals("") && pref != null) {
            params.put(CV.preference, pref);
        } else {
            params.put(CV.preference, "23");
        }
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }


    public static void getCityApi(VolleyIntialization vollyInit, String cityId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.CITYNAMEAPI;
        Map<String, String> params = new HashMap<>();
        params.put(city_id, cityId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getStateApi(VolleyIntialization vollyInit, String stateID, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.STATENAMEAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.state_id, stateID);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void getRespondToRequest(VolleyIntialization vollyInit, String userId, String roleId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.RESPONDTOREQUESTAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        params.put(CV.role_id, roleId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void getChatHistory(VolleyIntialization vollyInit, String reqId, String chatId, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.USERCHATAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.REQ_ID, reqId); //"64"
        params.put(CV.PrefChatUserid, chatId);  //"226"
        params.put(CV.USER_ID, userid);  //"28"
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getSendChat(VolleyIntialization vollyInit, String userId, String chatUserId, String reqId, String message, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADDCHATAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        params.put(CV.Chat_User_Id, chatUserId);
        params.put(CV.REQ_ID, reqId);
        params.put(CV.Message, message);

        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    /*public static void getEditProfile(VolleyIntialization vollyInit, String userid, String rolID, String stateID, String pref, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.SUBMITEDITPROFILE;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.role_id, rolID);
        params.put(CV.state_id, stateID);
        params.put(CV.preference, pref);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }*/

    // value2, value3, CM.getSp(ViewCheckResponse.this, CV.PrefID, "").toString()
    public static void getAcceptOffer(VolleyIntialization vollyInit, String resid, String reqid, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ACCEPTOFFERAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.REQ_ID, reqid);
        params.put("response_id", resid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getShareDetail(VolleyIntialization vollyInit, String userId, String reqId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.SHAREDETAILSAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        params.put(CV.Response_Id, reqId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getPackage(VolleyIntialization vollyInit, ArrayList<pojoPackage> pojoPackages, ArrayList<pojoStayReq> pojoStayReqs, ArrayList<pojoTransportReq> pojoTransportReqs, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.SENDREQAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.category_id, "1");
        params.put(CV.USER_ID, pojoPackages.get(0).getUser_id());
        params.put(CV.reference_id, pojoPackages.get(0).getReference_id());
        params.put(CV.total_budget, pojoPackages.get(0).getTotal_budget());
        params.put(CV.adult, pojoPackages.get(0).getAdult());
        params.put(CV.children, pojoPackages.get(0).getChildren());
        params.put(CV.room1, pojoPackages.get(0).getRoom1());
        params.put(CV.room2, pojoPackages.get(0).getRoom2());
        params.put(CV.room3, pojoPackages.get(0).getRoom3());
        params.put(CV.child_with_bed, pojoPackages.get(0).getChild_with_bed());
        params.put(CV.child_without_bed, pojoPackages.get(0).getChild_without_bed());
        params.put(CV.hotel_rating, pojoPackages.get(0).getHotel_rating());
        params.put(CV.hotel_category, pojoPackages.get(0).getHotel_category());
        params.put(CV.meal_plan, pojoPackages.get(0).getMeal_plan());
        params.put(CV.locality, pojoPackages.get(0).getLocality());
        params.put(CV.city_name, pojoPackages.get(0).getCity_name());
        params.put(CV.city_id, pojoPackages.get(0).getCity_id());
        params.put(CV.state_id, pojoPackages.get(0).getState_id());
        params.put(CV.state_name, pojoPackages.get(0).getState_name());
        params.put(CV.country_name, pojoPackages.get(0).getCountry_name());
        params.put(CV.country_id, pojoPackages.get(0).getCountry_id());

        params.put(CV.check_in, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getCheck_in()));
        params.put(CV.check_out, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getCheck_out()));
        params.put(CV.transport_requirement, pojoPackages.get(0).getTransport_requirement());
        params.put(CV.start_date, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getStart_date()));
        params.put(CV.end_date, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getEnd_date()));
        params.put(CV.pickup_locality, pojoPackages.get(0).getPickup_locality());
        params.put(CV.pickup_city_name, pojoPackages.get(0).getCity_name());
        params.put(CV.pickup_city_id, pojoPackages.get(0).getCity_id());
        params.put(CV.pickup_state_id, pojoPackages.get(0).getState_id());
        params.put(CV.pickup_state_name, pojoPackages.get(0).getPickup_state_name());
        params.put(CV.pickup_country_id, pojoPackages.get(0).getPickup_country_id());
        params.put(CV.finalLocality, pojoPackages.get(0).getFinalLocality());
        params.put(CV.p_final_city_name, pojoPackages.get(0).getP_final_city_name());
        params.put(CV.p_final_city_id, pojoPackages.get(0).getP_final_city_id());
        params.put(CV.p_final_state_id, pojoPackages.get(0).getP_final_state_id());
        params.put("id_package_stop_city", pojoPackages.get(0).getP_final_city_id());  // confuse
        params.put("state_id_package_stop_city", pojoPackages.get(0).getP_final_state_id()); // confuse

        params.put(CV.comment, pojoPackages.get(0).getComment());

        for (int i = 0; i < pojoStayReqs.size(); i++) {
            params.put(CV.hh_room1 + "[" + i + "]", pojoStayReqs.get(i).getSingleRoom());
            params.put(CV.hh_room2 + "[" + i + "]", pojoStayReqs.get(i).getDoubleRoom());
            params.put(CV.hh_room3 + "[" + i + "]", pojoStayReqs.get(i).getTripleRoom());
            params.put(CV.hh_child_with_bed + "[" + i + "]", pojoStayReqs.get(i).getChildwithbed());
            params.put(CV.hh_child_without_bed + "[" + i + "]", pojoStayReqs.get(i).getChildwithouthbed());
            params.put(CV.hh_hotel_rating + "[" + i + "]", pojoStayReqs.get(i).getHotelRating());
            params.put(CV.hh_hotel_category + "[" + i + "]", pojoStayReqs.get(i).getHotlCat());
            params.put(CV.hh_meal_plan + "[" + i + "]", pojoStayReqs.get(i).getMealPlane());
            params.put(CV.hh_city_id + "[" + i + "]", pojoStayReqs.get(i).getDestiCity());
            params.put(CV.hh_state_id + "[" + i + "]", pojoStayReqs.get(i).getDestiState());
            params.put(CV.hh_country_id + "[" + i + "]", pojoStayReqs.get(i).getDestiCountry());
            params.put(CV.hh_locality + "[" + i + "]", pojoStayReqs.get(i).getLocality());
            params.put(CV.hh_check_in + "[" + i + "]", CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoStayReqs.get(i).getCheckIn()));
            params.put(CV.hh_check_out + "[" + i + "]", CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoStayReqs.get(i).getCheckOut()));
        }

        for (int i = 0; i < pojoTransportReqs.size(); i++) {
            params.put("stops" + "[" + i + "]", pojoTransportReqs.get(i).getLocality());
            params.put("id_trasport_stop_city" + "[" + i + "]", pojoTransportReqs.get(i).getCity());
            params.put("state_id_trasport_stop_city" + "[" + i + "]", pojoTransportReqs.get(i).getState());
        }


        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getHotel(VolleyIntialization vollyInit, ArrayList<pojoPackage> pojoPackages, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.SENDREQAPI;
        Map<String, String> params = new HashMap<>();
        params.put("category_id", "3");
        params.put(CV.USER_ID, pojoPackages.get(0).getUser_id());
        params.put("reference_id", pojoPackages.get(0).getReference_id());
        params.put("total_budget", pojoPackages.get(0).getTotal_budget());
        params.put("hotelAdult", pojoPackages.get(0).getAdult());
        params.put("hotelChildren", pojoPackages.get(0).getChildren());
        params.put(CV.room1, pojoPackages.get(0).getRoom1());
        params.put(CV.room2, pojoPackages.get(0).getRoom2());
        params.put(CV.room3, pojoPackages.get(0).getRoom3());
        params.put(CV.child_with_bed, pojoPackages.get(0).getChild_with_bed());
        params.put(CV.child_without_bed, pojoPackages.get(0).getChild_without_bed());
        params.put(CV.hotel_rating, pojoPackages.get(0).getHotel_rating());
        params.put(CV.hotel_category, pojoPackages.get(0).getHotel_category());
        params.put(CV.meal_plan, pojoPackages.get(0).getMeal_plan());
        params.put(CV.locality, pojoPackages.get(0).getLocality());
        params.put("h_city_name", pojoPackages.get(0).getCity_name());
        params.put("h_city_id", pojoPackages.get(0).getCity_id());
        params.put("h_state_id", pojoPackages.get(0).getState_id());
        params.put("h_state_name", pojoPackages.get(0).getState_name());
        params.put("h_country_name", pojoPackages.get(0).getCountry_name());
        params.put("h_country_id", pojoPackages.get(0).getCountry_id());

        params.put(CV.check_in, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getCheck_in()));
        params.put(CV.check_out, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getCheck_out()));
        params.put(CV.comment, pojoPackages.get(0).getComment());


        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }


    public static void getTransport(VolleyIntialization vollyInit, ArrayList<pojoPackage> pojoPackages, ArrayList<transportPojo> transportPojos, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.SENDREQAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.category_id, "2");
        params.put(CV.USER_ID, pojoPackages.get(0).getUser_id());
        params.put(CV.reference_id, pojoPackages.get(0).getReference_id());
        params.put(CV.total_budget, pojoPackages.get(0).getTotal_budget());
        params.put("transportAdult", pojoPackages.get(0).getAdult());
        params.put("transportChildren", pojoPackages.get(0).getChildren());
        params.put("transport_requirement", pojoPackages.get(0).getTransport_requirement());
        params.put("t_pickup_country_name", "");
        params.put("t_pickup_country_id", pojoPackages.get(0).getPickup_country_id());
        params.put(CV.start_date, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getStart_date()));
        params.put(CV.end_date, CM.converDateFormate("dd-mm-yyyy", "mm/dd/yyyy", pojoPackages.get(0).getEnd_date()));
        params.put("pickup_locality", pojoPackages.get(0).getPickup_locality());
        params.put("t_pickup_city_name", pojoPackages.get(0).getPickup_city_name());
        params.put("t_pickup_city_id", pojoPackages.get(0).getPickup_city_id());
        params.put("t_pickup_state_id", pojoPackages.get(0).getPickup_state_id());
        params.put("t_pickup_state_name", pojoPackages.get(0).getPickup_state_name());
        params.put("finalLocality", pojoPackages.get(0).getFinalLocality());
        params.put("t_final_city_name", pojoPackages.get(0).getP_final_city_name());
        params.put("t_final_city_id", pojoPackages.get(0).getP_final_city_id());
        params.put("t_final_state_id", pojoPackages.get(0).getP_final_state_id());
        params.put("t_final_state_name", pojoPackages.get(0).getP_final_state_name());
        params.put("t_final_country_id", pojoPackages.get(0).getPickup_country_id());
        params.put("t_final_country_name", "");
        params.put(CV.comment, pojoPackages.get(0).getComment());


        for (int i = 0; i < transportPojos.size(); i++) {
            params.put("stops" + "[" + i + "]", transportPojos.get(i).getStops());
            params.put("id_trasport_stop_city" + "[" + i + "]", transportPojos.get(i).getId_trasport_stop_city());
            params.put("state_id_trasport_stop_city" + "[" + i + "]", transportPojos.get(i).getState_id_trasport_stop_city());
        }


        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getPromotionCity(VolleyIntialization vollyInit, String stateId, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.PROMOTIONSCITYAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.PrefState_id, stateId);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);

    }

    public static void getAddResponse(VolleyIntialization vollyInit, String quotPrice, String comment, String reqId, String userID, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADDRESPONSEAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.QUOTATION_PRICE, quotPrice);
        params.put(CV.REQ_ID, reqId);
        params.put(CV.COMMENT, comment);
        params.put(CV.USER_ID, userID);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getReview(VolleyIntialization vollyInit, String userId, String prouserid, String rating, String staus, String comment, String userName, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADDTESTIMONIALAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        params.put(CV.PROFILEUSER_ID, prouserid);
        params.put(CV.RATING, rating);
        params.put(CV.STATUS, staus);
        params.put(CV.COMMENT, comment);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getMyReq(VolleyIntialization vollyInit, String userid, String roleId, String reqType, String reqId, String startDate, String endDate, String budget, String pcity, String dcity, String member, String sort, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.MYREQUEST;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.PrefRole_Id, roleId);
        if (!sort.isEmpty()) {
            params.put(CV.SORT, sort);
        } else {
            // params.put(CV.REQ_TYPESEARCH, "");

        }

        if (!reqType.isEmpty()) {
            params.put(CV.REQ_TYPESEARCH, reqType);
        } else {
            params.put(CV.REQ_TYPESEARCH, "");

        }
        if (!reqId.isEmpty()) {
            params.put(CV.REFIDSEARCH, reqId);
        } else {
            params.put(CV.REFIDSEARCH, "");
        }
        if (!startDate.isEmpty()) {
            params.put(CV.STARTDATESEARCH, startDate);
        } else {
            params.put(CV.STARTDATESEARCH, "");
        }
        if (!endDate.isEmpty()) {
            params.put(CV.ENDDATESEARCH, endDate);
        } else {
            params.put(CV.ENDDATESEARCH, "");
        }
        if (!budget.isEmpty()) {

            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }

        } else {
            params.put(CV.BUDGETSEARCH, "");

        }

        if (!pcity.isEmpty()) {
            params.put(CV.PICKUP_CITY, pcity);
        } else {
            params.put(CV.PICKUP_CITY, "");
        }
        if (!dcity.isEmpty()) {
            params.put(CV.DESTINATION_CITY, dcity);
        } else {
            params.put(CV.DESTINATION_CITY, "");
        }
        if (!member.isEmpty()) {
            params.put(CV.MEMBERS, member);
        } else {
            params.put(CV.MEMBERS, "");
        }

        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getHotelPromotion(VolleyIntialization vollyInit, String userId, String hname, String hcategories, String ctariff, String etariff, String website, String cityid, String citycharge, String duration, String charges, String location, String hotelpic, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADDPROMOTIONAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userId);
        params.put(CV.HOTEL_NAME, hname);
        params.put(CV.HOTEL_CATEGORIES, hcategories);
        params.put(CV.CHEAP_TARIFF, ctariff);
        params.put(CV.EXPENSIVE_TARIFF, etariff);
        params.put(CV.WEBSITE, website);
        params.put(CV.CITYID, cityid);
        params.put(CV.CITYCHARGE, citycharge);
        params.put(CV.DURATION, duration);
        params.put(CV.CHARGES, charges);
        params.put(CV.HOTEL_PIC, hotelpic);
        params.put(CV.HOTEL_LOC, location);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getPromoReport(VolleyIntialization vollyInit, String userid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.PROMOTIONREPORTSAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getUnFollow(VolleyIntialization vollyInit, String userid, String followid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.REMOVEBUSINESSBUDDYAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.FOLLOW_ID, followid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getFollow(VolleyIntialization vollyInit, String userid, String followid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.ADDBUSINESSBUDDYAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.USER_ID, userid);
        params.put(CV.FOLLOW_ID, followid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    // reqId, refId, budget, price, name, order, quotPrice,
    public static void getCheckResposne(VolleyIntialization vollyInit, String reqid, String refid, String budget, String price, String agentName, String order, String quotPrice, String chatwith, String follow, String sharedetail, String sort, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.CHECKRESPONSESAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.REQ_ID, reqid);
        if (!sort.isEmpty()) {
            params.put(CV.SORT, sort);
        }
        if (!price.isEmpty()) {
            params.put(CV.REFIDSEARCH, refid);
        }

        if (!budget.isEmpty()) {
            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }

        } else {
            params.put(CV.BUDGETSEARCH, "");
        }
        if (!agentName.isEmpty()) {
            params.put(CV.AGENTNAMESEARCH, agentName);
        }


        if (!quotPrice.isEmpty()) {
            if (quotPrice.equals("100000+")) {
                params.put("quotesearch", "100000-1000000000000"); //CV.QUOTATION_PRICE
            } else {
                params.put("quotesearch", quotPrice);
            }

        } else {
            params.put("quotesearch", "");
        }

        if (!agentName.isEmpty()) {
            params.put("agentname", agentName);  //CV.AGENTNAMESEARCH
        } else {
            params.put("agentname", "");
        }


        // params.put(CV.SORT, "quotationprice");


        if (!order.isEmpty()) {
            params.put(CV.ORDER, order);
        }

        if (!chatwith.isEmpty()) {
            params.put(CV.CHATWITH, chatwith);
        } else {
            params.put(CV.CHATWITH, "");
        }
        if (!follow.isEmpty()) {
            params.put(CV.FOLLOWSEARCH, follow);
        } else {
            params.put(CV.FOLLOWSEARCH, "");
        }
        if (!sharedetail.isEmpty()) {
            params.put(CV.SHARED_DETAILS, sharedetail);
        } else {
            params.put(CV.SHARED_DETAILS, "");
        }

        if (!price.isEmpty()) {
            params.put(CV.REFIDSEARCH, refid);
        }

        if (!budget.isEmpty()) {
            if (budget.equals("100000+")) {
                params.put(CV.BUDGETSEARCH, "100000-1000000000000");
            } else {
                params.put(CV.BUDGETSEARCH, budget);
            }
        } else {
            params.put(CV.BUDGETSEARCH, "");
        }

        if (!quotPrice.isEmpty()) {
            if (quotPrice.equals("100000+")) {
                params.put(CV.QUOTESEARCH, "100000-1000000000000"); //CV.QUOTATION_PRICE
            } else {
                params.put(CV.QUOTESEARCH, quotPrice);
            }

        } else {
            params.put(CV.QUOTESEARCH, "");
        }

        if (!agentName.isEmpty()) {
            params.put(CV.AGENTNAMESEARCH, agentName);  //CV.AGENTNAMESEARCH
        } else {
            params.put(CV.AGENTNAMESEARCH, "");
        }
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }

    public static void getHotelCate(VolleyIntialization vollyInit, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.GETHOTELCATEGORIES;
        Map<String, String> params = new HashMap<>();
        vollyInit.vollyStringRequestCall(url, Request.Method.GET, params, vollyHanlder);

    }


    public static void getPromoCount(VolleyIntialization vollyInit, String pid, OnVolleyHandler vollyHanlder) throws JSONException {
        String url = URLS.PROMOTIONCOUNTSAPI;
        Map<String, String> params = new HashMap<>();
        params.put(CV.PROMOTION_ID, pid);
        vollyInit.vollyStringRequestCall(url, Request.Method.POST, params, vollyHanlder);
    }
}

