package com.example.themovieapp2;

public class MovieModel {
    String id;
    String name;
    String img;
    String rating;

    public MovieModel(String id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public MovieModel() {
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
