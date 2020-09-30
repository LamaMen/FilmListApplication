package com.test.films_list_application.activities;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.R;
import com.test.films_list_application.activities.fragments.ListFilmsFragment;
import com.test.films_list_application.dao.Films;
import com.test.films_list_application.dao.models.Film;

import java.util.List;

public class FilmItemsAdapter extends RecyclerView.Adapter<FilmItemViewHolder> {
    private final LayoutInflater inflater;
    private final List<Film> items;
    private final OnItemFilmClickListener listener;

    public FilmItemsAdapter(LayoutInflater inflater, List<Film> items, OnItemFilmClickListener listener) {
        this.inflater = inflater;
        this.items = items;
        this.listener = listener;
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
                Films.getInstance().addFavoriteFilm(position);
            } else {
                Films.getInstance().removeFilmFromFavorite(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemFilmClickListener {
        void onItemClick(int id);
    }
}
