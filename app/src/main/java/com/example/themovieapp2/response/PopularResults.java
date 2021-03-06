package com.example.themovieapp2.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularResults {

    @SerializedName("results")
    public List<Popular> popularMovieList;
    @SerializedName("total_pages")
    public int totalPages;

    public int getTotal_pages() {
        return totalPages;
    }

    public void setTotal_pages(int totalPages) {
        this.totalPages = totalPages;
    }
}
