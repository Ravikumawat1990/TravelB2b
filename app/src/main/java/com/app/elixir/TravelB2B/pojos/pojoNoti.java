package com.app.elixir.TravelB2B.pojos;

/**
 * Created by NetSupport on 18-09-2017.
 */

public class pojoNoti {

    String sender_name;
    String id;
    String request_id;
    String user_id;
    String send_to_user_id;
    String is_read;
    String read_date_time;
    String notification;
    String message;
    String created;


    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSend_to_user_id() {
        return send_to_user_id;
    }

    public void setSend_to_user_id(String send_to_user_id) {
        this.send_to_user_id = send_to_user_id;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public String getRead_date_time() {
        return read_date_time;
    }

    public void setRead_date_time(String read_date_time) {
        this.read_date_time = read_date_time;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
