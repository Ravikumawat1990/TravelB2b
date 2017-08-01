package com.app.elixir.TravelB2B.pojos;

/**
 * Created by NetSupport on 03-07-2017.
 */

public class pojoCheckResposne {

    String comment;
    String quotation_price;
    String total_budget;
    String first_name;
    String last_name;
    String reference_id;
    String user_id;
    String id;
    String request_id;
    String response_id;
    String Follow_id;

    public String getFollow_id() {
        return Follow_id;
    }

    public void setFollow_id(String follow_id) {
        Follow_id = follow_id;
    }

    public String getResponse_id() {
        return response_id;
    }

    public void setResponse_id(String response_id) {
        this.response_id = response_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQuotation_price() {
        return quotation_price;
    }

    public void setQuotation_price(String quotation_price) {
        this.quotation_price = quotation_price;
    }

    public String getTotal_budget() {
        return total_budget;
    }

    public void setTotal_budget(String total_budget) {
        this.total_budget = total_budget;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }
}
