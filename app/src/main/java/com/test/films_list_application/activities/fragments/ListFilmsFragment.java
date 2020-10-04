package com.test.films_list_application.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.films_list_application.App;
import com.test.films_list_application.R;
import com.test.films_list_application.activities.adapters.BaseAdapter;
import com.test.films_list_application.activities.adapters.FavoriteFilmItemsAdapter;
import com.test.films_list_application.activities.adapters.FilmItemsAdapter;
import com.test.films_list_application.dao.Cash;
import com.test.films_list_application.dao.models.Film;

import java.util.ArrayList;
import java.util.List;

public class ListFilmsFragment extends Fragment implements BaseAdapter.OnItemFilmClickListener {
    public final static String TAG_MAIN = ListFilmsFragment.class.toString() + "MainScreen";
    public final static String TAG = ListFilmsFragment.class.toString();
    private static final String KEY_IS_MAIN_SCREEN = "IS_MAIN_SCREEN";

    private OnFilmClickListener listener = null;

    public static ListFilmsFragment newInstance(boolean isMainScreen) {
        ListFilmsFragment fragment = new ListFilmsFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_IS_MAIN_SCREEN, isMainScreen);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            View fragment = inflater.inflate(R.layout.fragment_list_films, container, false);
            RecyclerView recyclerView = fragment.findViewById(R.id.list_film);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            Cash cash = App.getInstance().cash;
            List<Film> films = cash.getCashedFilms();
            if (cash.countCashedFilms() == 0) {
                cash.getFilmsFromApi(recyclerView, 1);
            }

            if (getArguments().getBoolean(KEY_IS_MAIN_SCREEN)) {
                recyclerView.setAdapter(new FilmItemsAdapter(inflater, films, this));
            } else {
                recyclerView.setAdapter(new FavoriteFilmItemsAdapter(inflater, new ArrayList<>(), this)); // TODO: 03.10.2020 Придумать как сохранять избранные фильмы
            }

            return fragment;
        } else {
            getActivity().onBackPressed();
            return null;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof OnFilmClickListener) {
            listener = (OnFilmClickListener) getActivity();
        } else {
            throw new IllegalArgumentException("Wrong Activity! (Activity must implement OnFilmClickListener)");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onItemClick(int id) {
        if (listener != null) {
            listener.onFilmItemClick(id);
        }
    }


    public interface OnFilmClickListener {
        void onFilmItemClick(int id);
    }
}
