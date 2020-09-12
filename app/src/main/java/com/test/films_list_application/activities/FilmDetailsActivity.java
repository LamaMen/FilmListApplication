package com.test.films_list_application.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.test.films_list_application.R;
import com.test.films_list_application.models.Film;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmDetailsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.film_name)
    TextView filmName;
    @BindView(R.id.film_cover)
    ImageView filmCover;
    @BindView(R.id.film_description)
    TextView filmDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int filmId = getIntent().getIntExtra("film_id", -1);
        Film currentFilm = MainActivity.mapFilms.get(filmId);
        if (currentFilm == null) {
            this.finish();
            return;
        }

        filmName.setText(currentFilm.getName());
        filmCover.setImageResource(currentFilm.getPhotoID());
        filmDescription.setText(currentFilm.getDescription());
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}