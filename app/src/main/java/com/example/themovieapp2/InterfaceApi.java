package com.example.themovieapp2;

import android.graphics.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceApi {
    @GET ("/3/movie/{category}")
    Call<MovieResults> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey
//            @Query("language") String language,
//            @Query("page") int page
    );


    //    @POST("")
//    Call<String> checkLogin(@Header("Authorization") String a);


}
