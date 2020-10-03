package com.test.films_list_application.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.films_list_application.R;
import com.test.films_list_application.activities.MainActivity;
import com.test.films_list_application.dao.models.Film;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmDetailsFragment extends Fragment {
    public final static String TAG = FilmDetailsFragment.class.toString();
    private static final String KEY_ID = "FILM_ID";

    @BindView(R.id.film_name)
    TextView filmName;
    @BindView(R.id.film_cover)
    ImageView filmCover;
    @BindView(R.id.film_description)
    TextView filmDescription;

    public static FilmDetailsFragment newInstance(int id) {
        FilmDetailsFragment fragment = new FilmDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_film_details, container, false);
        ButterKnife.bind(this, content);
        return content;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            int filmId = getArguments().getInt(KEY_ID);
            Film currentFilm = MainActivity.mapFilms.get(filmId);
            filmName.setText(currentFilm.getName());
            filmCover.setImageResource(R.drawable.default_film_cover); // TODO: 03.10.2020 Добавить скачивание картинки из интернета
            filmDescription.setText(currentFilm.getDescription());
        } else {
            getActivity().onBackPressed();
        }
    }
}
