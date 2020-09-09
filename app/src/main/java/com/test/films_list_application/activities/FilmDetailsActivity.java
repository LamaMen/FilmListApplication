package com.test.films_list_application.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.test.films_list_application.R;
import com.test.films_list_application.models.Film;

public class FilmDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int filmId = getIntent().getIntExtra("film_id", -1);
        Film currentFilm = MainActivity.mapFilms.get(filmId);
        if (currentFilm == null) {
            this.finish();
            return;
        }

        TextView filmName = findViewById(R.id.film_name);
        filmName.setText(currentFilm.getName());

        ImageView filmCover = findViewById(R.id.film_cover);
        filmCover.setImageResource(currentFilm.getPhotoID());

        TextView filmDescription = findViewById(R.id.film_description);
        filmDescription.setText(currentFilm.getDescription());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}