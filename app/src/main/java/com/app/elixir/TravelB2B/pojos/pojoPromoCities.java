package com.app.elixir.TravelB2B.pojos;

import java.io.Serializable;

/**
 * Created by User on 15-Sep-2017.
 */

public class pojoPromoCities implements Serializable {

    String lable;
    String userCount;
    String value;
    String price;
    String stateId;
    String stateName;
    String cId;
    String cName;

   /* public pojoPromoCities(String lable, String userCount, String value, String price, String stateId, String stateName, String cId, String cName) {
        this.lable = lable;
        this.userCount = userCount;
        this.value = value;
        this.price = price;
        this.stateId = stateId;
        this.stateName = stateName;
        this.cId = cId;
        this.cName = cName;
    }*/

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }


}
