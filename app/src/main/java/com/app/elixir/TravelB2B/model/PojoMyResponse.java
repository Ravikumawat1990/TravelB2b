package com.app.elixir.TravelB2B.model;

/**
 * Created by NetSupport on 05-06-2017.
 */

public class PojoMyResponse {

    String requestType;
    String refId;
    String startDate;
    String endDate;
    String totBudget;
    String adult;

    public String getRequestType() {
        return requestType;
    }

    public String getRefId() {
        return refId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getTotBudget() {
        return totBudget;
    }

    public String getAdult() {
        return adult;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setTotBudget(String totBudget) {
        this.totBudget = totBudget;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }
}
