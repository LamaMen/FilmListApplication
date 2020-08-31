package com.test.films_list_application;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.test.films_list_application.models.Film;

public class FilmDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        int filmId = getIntent().getIntExtra("film_id", -1);
        Film currentFilm = MainActivity.mapFilms.get(filmId);
        assert currentFilm != null;  // TODO: 8/31/20 What is mean??)

        TextView filmName = findViewById(R.id.film_name);
        filmName.setText(currentFilm.getName());

        ImageView filmCover = findViewById(R.id.film_cover);
        filmCover.setImageDrawable(Drawable.createFromPath(currentFilm.getPathToImage()));

        TextView filmDescription = findViewById(R.id.film_description);
        filmDescription.setText(currentFilm.getDescription());
    }
}