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
    private int currentPage;
    private int totalPages = -1;

    public Cash() {
        this.cashedFilms = new ArrayList<>();
    }

    public int countCashedFilms() {
        return cashedFilms.size();
    }

    public List<Film> getCashedFilms() {
        return cashedFilms;
    }

    private void getFilmsFromApi(RecyclerView recyclerView, int page) {
        if ((totalPages == -1 && page == 1) || page <= totalPages) {
            App.getInstance().filmsService.getPopularFilms(page, "ru").enqueue(new Callback<FilmListPage>() {
                @Override
                public void onResponse(@NotNull Call<FilmListPage> call, @NotNull Response<FilmListPage> response) {
                    if (response.isSuccessful()) {
                        FilmListPage returnedPage = response.body();
                        currentPage = returnedPage.currentPage;
                        if (page == 1) {
                            totalPages = returnedPage.totalPages;
                        }
                        Log.d("App", "Current page: " + returnedPage.currentPage);
                        Log.d("App", "Total films: " + returnedPage.results.size());

                        int oldSize = cashedFilms.size();
                        for (FilmJson json : returnedPage.results) {
                            cashedFilms.add(new Film(json));
                        }
                        recyclerView.getAdapter().notifyItemRangeInserted(oldSize, cashedFilms.size());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<FilmListPage> call, @NotNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    public void getFirstFilmPage(RecyclerView recyclerView) {
        getFilmsFromApi(recyclerView, 1);
    }

    public void getNextFilmPage(RecyclerView recyclerView) {
        getFilmsFromApi(recyclerView, ++currentPage);
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
