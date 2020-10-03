package com.test.films_list_application.dao;

import com.test.films_list_application.dao.models.FilmJson;
import com.test.films_list_application.dao.models.FilmListPage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmsService {
    @GET("movie/popular?api_key=e3b192c2546dd17a96029e06fec24c0a")
    Call<FilmListPage> getPopularFilms(@Query("page") int page, @Query("language") String language);

    @GET("movie/{id}}?api_key=e3b192c2546dd17a96029e06fec24c0a")
    Call<FilmJson> getFilmFromId(@Path("id") int id, @Query("language") String language);
}
