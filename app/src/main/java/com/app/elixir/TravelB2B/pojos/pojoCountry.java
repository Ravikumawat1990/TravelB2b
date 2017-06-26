package com.app.elixir.TravelB2B.pojos;

/**
 * Created by NetSupport on 16-06-2017.
 */

public class pojoCountry {

    private String id;
    private String country_cod;
    private String country_name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_cod() {
        return country_cod;
    }

    public void setCountry_cod(String country_cod) {
        this.country_cod = country_cod;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof pojoCountry)
            return ((pojoCountry) obj).getId().equals(this.getId());
        return false;
    }
}
