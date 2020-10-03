package com.test.films_list_application.dao;

import com.test.films_list_application.dao.models.FilmJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FilmsService {
    @GET
    Call<List<FilmJson>> getPopularFilms();
}
