package com.test.films_list_application.activities.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.test.films_list_application.R;
import com.test.films_list_application.activities.fragments.ListFilmsFragment;
import com.test.films_list_application.dao.Films;
import com.test.films_list_application.dao.models.Film;

import java.util.List;

public class FilmItemsAdapter extends BaseAdapter<FilmItemViewHolder> {

    public FilmItemsAdapter(LayoutInflater inflater, List<Film> items, OnItemFilmClickListener listener) {
        super(inflater, items, listener);
    }

    @NonNull
    @Override
    public FilmItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmItemViewHolder(inflater.inflate(R.layout.item_film, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilmItemViewHolder holder, int position) {
        Film currentFilm = items.get(position);
        holder.bind(currentFilm);

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(currentFilm.getId());
            Log.d(ListFilmsFragment.TAG, "Button on item " + currentFilm.getId() + " pressed");
        });

        holder.likeButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Films.getInstance().addFavoriteFilm(currentFilm);
            } else {
                Films.getInstance().removeFilmFromFavorite(currentFilm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
