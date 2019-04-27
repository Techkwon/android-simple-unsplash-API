package com.example.simplejavaapi.utils;

import com.example.simplejavaapi.models.SearchImage;
import com.example.simplejavaapi.models.UnsplashImage;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {
    //you can get your own access key from here: https://unsplash.com/developers
    String header = "Authorization: Client-ID YOUR_ACCESS_KEY";


    @Headers(header)
    @GET("photos")
    Observable<List<UnsplashImage>> getPhotos();

    @Headers(header)
    @GET("photos")
    Observable<List<UnsplashImage>> getNextPhotos(@Query("page") int page);

    @Headers(header)
    @GET("search/photos")
    Observable<SearchImage> searchPhotos(@Query("query") String query);

}
