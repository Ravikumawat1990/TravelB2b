package com.app.elixir.TravelB2B.pojos;

/**
 * Created by User on 26-Jul-2017.
 */

public class pojoPromoReport {

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getViewer_count() {
        return viewer_count;
    }

    public void setViewer_count(String viewer_count) {
        this.viewer_count = viewer_count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDateOfPromo() {
        return dateOfPromo;
    }

    public void setDateOfPromo(String dateOfPromo) {
        this.dateOfPromo = dateOfPromo;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    String hotelname;
    String viewer_count;
    String status;
    String dateOfPromo;
    String duration;
    String cities;

}
