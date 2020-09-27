package com.test.films_list_application.activities;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.R;
import com.test.films_list_application.dao.models.Film;

import java.util.List;

public class FilmItemsAdapter extends RecyclerView.Adapter<FilmItemViewHolder> {
    private final LayoutInflater inflater;
    private final List<Film> items;
    private final OnButtonClickListener buttonClickListener;

    public FilmItemsAdapter(LayoutInflater inflater, List<Film> items, OnButtonClickListener buttonClickListener) {
        this.inflater = inflater;
        this.items = items;
        this.buttonClickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public FilmItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilmItemViewHolder(inflater.inflate(R.layout.item_film, parent, false), buttonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
