package com.test.films_list_application.activities.adapters;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.dao.models.Film;

import java.util.List;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final LayoutInflater inflater;
    protected final List<Film> items;
    protected final OnItemFilmClickListener listener;

    protected BaseAdapter(LayoutInflater inflater, List<Film> items, OnItemFilmClickListener listener) {
        this.inflater = inflater;
        this.items = items;
        this.listener = listener;
    }

    public interface OnItemFilmClickListener {
        void onItemClick(int id);
    }
}
