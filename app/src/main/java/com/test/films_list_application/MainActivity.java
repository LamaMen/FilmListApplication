package com.test.films_list_application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.test.films_list_application.dao.Films;
import com.test.films_list_application.models.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public final static Map<Integer, Film> mapFilms = new HashMap<>();

    private final static String TAG = MainActivity.class.toString();

    private ArrayList<Integer> changedColorTexts = new ArrayList<>();
    private static final String KEY_TEXT = "TextView";
    private static final int TEXT_CHANGE_COLOR = Color.rgb(82, 82, 82);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            changedColorTexts = savedInstanceState.getIntegerArrayList(KEY_TEXT);

            if (changedColorTexts != null && changedColorTexts.size() != 0) {
                for (Integer text : changedColorTexts) {
                    TextView textView = findViewById(text);
                    textView.setTextColor(TEXT_CHANGE_COLOR);
                }
            }
        }

        List<Film> films = Films.getInstance().getFilms();
        for (Film film : films) {
            mapFilms.put(film.getId(), film);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(KEY_TEXT, changedColorTexts);
    }

    public void showDetails(View view) {
        Intent openFilmDetails = new Intent(MainActivity.this, FilmDetails.class);
        openFilmDetails.putExtra("film_id", view.getId());
        startActivity(openFilmDetails);

        changeTextColor(view);
    }

    private void changeTextColor(View button) {
        View parent = (View) button.getParent();
        if (!(parent instanceof ViewGroup)) {
            return;
        }

        TextView text = (TextView) ((ViewGroup) parent).getChildAt(1);
        text.setTextColor(TEXT_CHANGE_COLOR);
        changedColorTexts.add(text.getId());
    }

    public void inviteFriend(View view) {
        Intent inviteIntent = new Intent();
        inviteIntent.setAction(Intent.ACTION_SEND);
        inviteIntent.putExtra(Intent.EXTRA_TEXT, "Присоединяйся ко мне в приложении SHIT!");
        inviteIntent.setType("text/plain");

        Intent chooser = Intent.createChooser(inviteIntent, "В каком приложении?");
        if (inviteIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}