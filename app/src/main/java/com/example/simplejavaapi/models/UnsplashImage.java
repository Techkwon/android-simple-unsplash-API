package com.example.simplejavaapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnsplashImage {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    @SerializedName("urls")
    @Expose
    private Urls urls = null;

    @SerializedName("user")
    private User user = null;

    public Urls getUrls(){
        return urls;
    }

    public User getUser(){
        return user;
    }

    public class Urls {
        @SerializedName("thumb")
        private String thumb;

        public String getThumb() {
            return thumb;
        }
    }

    public class User{
        @SerializedName("username")
        private String username;

        public String getUsername(){
            return username;
        }
    }

}
