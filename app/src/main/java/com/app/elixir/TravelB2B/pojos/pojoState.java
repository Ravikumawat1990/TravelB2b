package com.app.elixir.TravelB2B.pojos;

/**
 * Created by NetSupport on 16-06-2017.
 */

public class pojoState {

    String id;
    String state_name;
    String country_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof pojoState)
            return ((pojoState) obj).getId().equals(this.getId());
        return false;
    }
}

