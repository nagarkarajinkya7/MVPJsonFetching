package com.appmaker.mvpexample.model;

import com.appmaker.mvpexample.contractor.MainActivityContract;

public class MainViewDataModel implements MainActivityContract.Model {

    public final String JSON_URL;
    private String name, imageUrl, country, city;

    public MainViewDataModel(String name, String imageUrl, String country, String city) {

        this.name = name;
        this.imageUrl = imageUrl;
        this.country = country;
        this.city = city;
        JSON_URL = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getCity() {
        return city;
    }
}
