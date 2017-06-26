package com.app.elixir.TravelB2B.pojos;

/**
 * Created by NetSupport on 16-06-2017.
 */

public class pojoCity {

    String id;
    String name;
    String state_id;
    String price;
    String category;
    String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof pojoCity)
            return ((pojoCity) obj).getId().equals(this.getId());
        return false;
    }
}
