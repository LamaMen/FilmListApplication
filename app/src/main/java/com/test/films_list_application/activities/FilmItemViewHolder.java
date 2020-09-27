package com.test.films_list_application.activities;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.test.films_list_application.R;
import com.test.films_list_application.activities.fragments.ListFilmsFragment;
import com.test.films_list_application.dao.models.Film;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.film_name)
    public TextView filmName;
    @BindView(R.id.film_cover)
    public ImageView filmCover;
    @BindView(R.id.film_show_details)
    public MaterialButton filmShowDetailsButton;

    private final OnButtonClickListener listener;

    public FilmItemViewHolder(@NonNull View itemView, OnButtonClickListener listener) {
        super(itemView);
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Film film) {
        filmName.setText(film.getName());
        filmCover.setImageResource(film.getPhotoID());
        filmShowDetailsButton.setOnClickListener(v -> {
            listener.onButtonPress(film.getId());
            filmName.setTextColor(Color.rgb(82, 82, 82));
            Log.d(ListFilmsFragment.TAG, "Button on item " + film.getId() + " pressed");
        });
    }
}
