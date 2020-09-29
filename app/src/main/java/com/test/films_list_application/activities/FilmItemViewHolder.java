package com.test.films_list_application.activities;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.R;
import com.test.films_list_application.dao.models.Film;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmItemViewHolder extends RecyclerView.ViewHolder {
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
    }
}
