package com.test.films_list_application;

import android.app.Application;

import com.test.films_list_application.dao.FilmsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private final static String API_KEY = "e3b192c2546dd17a96029e06fec24c0a";
    public FilmsService filmsService;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRetrofit();
    }

    public static App getInstance() {
        return instance;
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        filmsService = retrofit.create(FilmsService.class);
    }
}
