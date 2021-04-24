package com.example.themovieapp2.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieGenreResults {

    @SerializedName("genres")
    public List<MovieGenre> genreList;

}
