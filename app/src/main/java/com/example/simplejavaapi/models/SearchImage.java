package com.example.simplejavaapi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchImage {

    @SerializedName("results")
    private List<UnsplashImage> imageList = null;

    public List<UnsplashImage> getImageList(){
        return imageList;
    }
}
