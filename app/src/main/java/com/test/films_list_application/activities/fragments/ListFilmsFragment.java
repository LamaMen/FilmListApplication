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

import com.test.films_list_application.R;
import com.test.films_list_application.activities.FilmItemsAdapter;
import com.test.films_list_application.activities.OnButtonClickListener;
import com.test.films_list_application.dao.Films;

public class ListFilmsFragment extends Fragment implements OnButtonClickListener {
    public final static String TAG = ListFilmsFragment.class.toString();

    private OnFilmClickListener listener = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_list_films, container, false);
        RecyclerView recyclerView = fragment.findViewById(R.id.list_film);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new FilmItemsAdapter(inflater, Films.getInstance().getFilms(), this));
        return fragment;
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
//        view.findViewById(R.id.f001).setOnClickListener(v -> {
//            onButtonPress(v, R.id.f001);
//        })
    }

    public void onButtonPress(int id) {
        if (listener != null) {
            listener.onFilmItemClick(id);
        }
    }


    public interface OnFilmClickListener {
        void onFilmItemClick(int id);
    }
}
