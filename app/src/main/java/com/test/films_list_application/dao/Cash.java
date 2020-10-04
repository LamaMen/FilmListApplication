package com.test.films_list_application.dao;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.App;
import com.test.films_list_application.dao.models.Film;
import com.test.films_list_application.dao.models.FilmJson;
import com.test.films_list_application.dao.models.FilmListPage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cash {
    private final List<Film> cashedFilms;
    private final List<Film> favoriteFilms = new ArrayList<>();

    public Cash() {
        this.cashedFilms = new ArrayList<>();
    }

    public int countCashedFilms() {
        return cashedFilms.size();
    }

    public List<Film> getCashedFilms() {
        return cashedFilms;
    }

    public void getFilmsFromApi(RecyclerView recyclerView, int page) {
        App.getInstance().filmsService.getPopularFilms(page, "ru").enqueue(new Callback<FilmListPage>() {
            @Override
            public void onResponse(@NotNull Call<FilmListPage> call, @NotNull Response<FilmListPage> response) {
                if (response.isSuccessful()) {
                    FilmListPage page = response.body();
                    Log.d("App", "Current page: " + page.currentPage);
                    Log.d("App", "Total films: " + page.results.size());

                    for (FilmJson json : page.results) {
                        cashedFilms.add(new Film(json));
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<FilmListPage> call, @NotNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void addFavoriteFilm(Film film) {
        film.setFavorite(true);
        favoriteFilms.add(film);
    }

    public void removeFilmFromFavorite(Film film) {
        film.setFavorite(false);
        favoriteFilms.remove(film);
    }

    public List<Film> getFavoriteFilms() {
        return favoriteFilms;
    }
}
