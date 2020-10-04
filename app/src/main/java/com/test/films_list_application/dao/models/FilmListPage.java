package com.test.films_list_application.dao.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmListPage {
    @SerializedName("page")
    public int currentPage;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("results")
    public List<FilmJson> results;
}
