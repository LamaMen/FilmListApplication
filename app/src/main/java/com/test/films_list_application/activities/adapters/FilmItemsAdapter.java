package com.test.films_list_application.activities.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

import com.test.films_list_application.App;
import com.test.films_list_application.R;
import com.test.films_list_application.dao.models.Film;

import java.util.List;

public class FilmItemsAdapter extends BaseAdapter<FilmItemViewHolder> {

    public FilmItemsAdapter(LayoutInflater inflater, List<Film> items, OnItemFilmClickListener listener) {
        super(inflater, items, listener);
    }

    @NonNull
    @Override
    public FilmItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmItemViewHolder(inflater.inflate(R.layout.item_film, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmItemViewHolder holder, int position) {
        Film currentFilm = items.get(position);
        holder.bind(currentFilm);

        holder.likeButton.setOnClickListener(view -> {
            boolean isChecked = ((CheckBox) view).isChecked();
            if (isChecked) {
                App.getInstance().cash.addFavoriteFilm(currentFilm);
            } else {
                App.getInstance().cash.removeFilmFromFavorite(currentFilm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
