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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.films_list_application.App;
import com.test.films_list_application.R;
import com.test.films_list_application.dao.models.Film;
import com.test.films_list_application.dao.models.FilmJson;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmDetailsFragment extends Fragment {
    public final static String TAG = FilmDetailsFragment.class.toString();
    private static final String KEY_ID = "FILM_ID";

    @BindView(R.id.film_name)
    TextView filmName;
    @BindView(R.id.film_cover)
    ImageView filmCover;
    @BindView(R.id.film_description)
    TextView filmDescription;
    @BindView(R.id.loader)
    View loader;

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
            loader.setVisibility(View.VISIBLE);
            App.getInstance().filmsService.getFilmFromId(filmId, "ru").enqueue(new Callback<FilmJson>() {
                @Override
                public void onResponse(@NotNull Call<FilmJson> call, @NotNull Response<FilmJson> response) {
                    if (response.isSuccessful()) {
                        Film currentFilm = new Film(response.body());
                        filmName.setText(currentFilm.getName());
                        filmDescription.setText(currentFilm.getDescription());

                        String img = App.API_BASE_IMG_L_URL + currentFilm.getPhotoUrl();
                        Glide.with(getContext())
                                .load(img)
                                .centerCrop()
                                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(15, 0)))
                                .error(R.drawable.default_film_cover)
                                .into(filmCover);
                        loader.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<FilmJson> call, @NotNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            getActivity().onBackPressed();
        }
    }
}
