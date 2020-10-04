package com.test.films_list_application;

import android.app.Application;

import com.test.films_list_application.dao.Cash;
import com.test.films_list_application.dao.FilmsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    public final static String API_KEY = "e3b192c2546dd17a96029e06fec24c0a";
    public final static String API_BASE_URL = "https://api.themoviedb.org/3/";
    public final static String API_BASE_IMG_S_URL = "https://image.tmdb.org/t/p/w154";
    public final static String API_BASE_IMG_L_URL = "https://image.tmdb.org/t/p/w300";

    public FilmsService filmsService;
    public Cash cash;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        cash = new Cash();
        initRetrofit();
    }

    public static App getInstance() {
        return instance;
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        filmsService = retrofit.create(FilmsService.class);
    }
}
