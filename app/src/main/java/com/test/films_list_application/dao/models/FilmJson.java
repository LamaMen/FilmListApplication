package com.test.films_list_application.dao.models;

import com.google.gson.annotations.SerializedName;

public class FilmJson {
    public int id;
    @SerializedName("title")
    public String name;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    public String img;
}
