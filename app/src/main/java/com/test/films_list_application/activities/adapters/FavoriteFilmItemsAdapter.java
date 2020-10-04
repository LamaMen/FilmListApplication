package com.test.films_list_application.activities.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.App;
import com.test.films_list_application.R;
import com.test.films_list_application.activities.fragments.ListFilmsFragment;
import com.test.films_list_application.dao.models.Film;

import java.util.List;

public class FavoriteFilmItemsAdapter extends BaseAdapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_NO_ITEMS = 0;
    private static final int VIEW_TYPE_REGULAR_ITEM = 1;


    public FavoriteFilmItemsAdapter(LayoutInflater inflater, List<Film> items, OnItemFilmClickListener listener) {
        super(inflater, items, listener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_NO_ITEMS) {
            return new NoItemsViewHolder(inflater.inflate(R.layout.item_no_films, parent, false));
        } else {
            return new FilmItemViewHolder(inflater.inflate(R.layout.item_film, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FilmItemViewHolder) {
            Film currentFilm = items.get(position);
            ((FilmItemViewHolder) holder).bind(currentFilm);

            holder.itemView.setOnClickListener(v -> {
                listener.onItemClick(currentFilm.getId());
                Log.d(ListFilmsFragment.TAG, "Button on item " + currentFilm.getId() + " pressed");
            });

            ((FilmItemViewHolder) holder).likeButton.setOnClickListener(view -> {
                App.getInstance().cash.removeFilmFromFavorite(currentFilm);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, items.size());
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && items.size() == 0) {
            return VIEW_TYPE_NO_ITEMS;
        } else {
            return VIEW_TYPE_REGULAR_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(items.size(), 1);
    }

    public static class NoItemsViewHolder extends RecyclerView.ViewHolder {

        public NoItemsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
