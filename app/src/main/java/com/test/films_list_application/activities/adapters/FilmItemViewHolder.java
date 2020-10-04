package com.test.films_list_application.activities.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.films_list_application.App;
import com.test.films_list_application.R;
import com.test.films_list_application.dao.models.Film;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FilmItemViewHolder extends RecyclerView.ViewHolder {
    private final BaseAdapter.OnItemFilmClickListener listener;
    @BindView(R.id.film_name)
    TextView filmName;
    @BindView(R.id.film_description)
    TextView filmDescription;
    @BindView(R.id.film_cover)
    ImageView filmCover;
    @BindView(R.id.like_button)
    CheckBox likeButton;

    public FilmItemViewHolder(@NonNull View itemView, BaseAdapter.OnItemFilmClickListener listener) {
        super(itemView);
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Film film) {
        String img = App.API_BASE_IMG_S_URL + film.getPhotoUrl();
        Glide.with(itemView.getContext())
                .load(img)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(15, 0)))
                .error(R.drawable.default_film_cover)
                .into(filmCover);

        filmName.setText(film.getName());
        filmDescription.setText(film.getDescription());
        likeButton.setChecked(film.isFavorite());
        itemView.setOnClickListener(v -> listener.onItemClick(film.getId()));
    }
}
