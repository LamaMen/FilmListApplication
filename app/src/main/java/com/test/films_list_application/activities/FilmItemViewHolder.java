package com.test.films_list_application.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.R;
import com.test.films_list_application.dao.models.Film;

public class FilmItemViewHolder extends RecyclerView.ViewHolder {
    private TextView filmName;
    private ImageView filmCover;

    public FilmItemViewHolder(@NonNull View itemView) {
        super(itemView);
        filmName = itemView.findViewById(R.id.film_name);
        filmCover = itemView.findViewById(R.id.film_cover);
    }

    public void bind(Film film) {
        filmName.setText(film.getName());
        filmCover.setImageResource(film.getPhotoID());
    }
}
