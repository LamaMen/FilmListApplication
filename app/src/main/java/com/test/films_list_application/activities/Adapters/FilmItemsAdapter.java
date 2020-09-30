package com.test.films_list_application.activities.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.R;
import com.test.films_list_application.activities.fragments.ListFilmsFragment;
import com.test.films_list_application.dao.Films;
import com.test.films_list_application.dao.models.Film;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmItemsAdapter extends RecyclerView.Adapter<FilmItemsAdapter.FilmItemViewHolder> {
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

    public static class FilmItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.film_name)
        TextView filmName;
        @BindView(R.id.film_description)
        TextView filmDescription;
        @BindView(R.id.film_cover)
        ImageView filmCover;
        @BindView(R.id.like_button)
        CheckBox likeButton;

        public FilmItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Film film) {
            filmName.setText(film.getName());
            filmCover.setImageResource(film.getPhotoID());
            filmDescription.setText(film.getDescription());
            likeButton.setChecked(film.isFavorite());
        }
    }
}
