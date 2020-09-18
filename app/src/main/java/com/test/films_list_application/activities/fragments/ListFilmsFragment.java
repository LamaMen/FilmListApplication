package com.test.films_list_application.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.films_list_application.R;

public class ListFilmsFragment extends Fragment {
    public final static String TAG = ListFilmsFragment.class.toString();

    private OnFilmClickListener listener = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_films, container, false);
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
        view.findViewById(R.id.f001).setOnClickListener(v -> {
            onButtonPress(v, R.id.f001);
        });

        view.findViewById(R.id.f002).setOnClickListener(v -> {
            onButtonPress(v, R.id.f002);
        });

        view.findViewById(R.id.f003).setOnClickListener(v -> {
            onButtonPress(v, R.id.f003);
        });
    }

    private void onButtonPress(View item, int id) {
        if (listener != null) {
            listener.onFilmItemClick(id);
        }

//        View container = (View) item.getParent();
//        if (!(container instanceof ViewGroup)) {
//            return;
//        }
//
//        TextView text = (TextView) ((ViewGroup) container).getChildAt(1);
//        text.setTextColor(getResources().getColor(R.color.selectedItem));
    }


    public interface OnFilmClickListener {
        void onFilmItemClick(int id);
    }
}
