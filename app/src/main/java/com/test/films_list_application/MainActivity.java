package com.test.films_list_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.test.films_list_application.models.Film;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public final static Map<Integer, Film> mapFilms = new HashMap<>();

    private final static String TAG = MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFilms.put(2131165271, new Film("f001", "Бешенные псы", "Это должно было стать идеальным преступлением. Задумав ограбить ювелирный магазин, криминальный босс Джо Кэбот собрал вместе шестерых опытных и совершенно незнакомых друг с другом преступников. Но с самого начала все пошло не так, и обычный грабеж превратился в кровавую бойню.", "/home/ilia/Projects/Android/Films/app/src/main/res/drawable/dogs.jpeg"));
        mapFilms.put(2131165272, new Film("f002", "Купи меня", "Три девушки — три истории о том, как изменить свою жизнь и чем предстоит пожертвовать, чтобы стать «счастливыми». Порше, олигархи, секс, любовь и мечты о красивой жизни. Смогут ли героини добиться успеха, или они сломаются, так и не дойдя до цели?", "/home/ilia/Projects/Android/Films/app/src/main/res/drawable/buy_me.jpg"));
        mapFilms.put(2131165273, new Film("f003", "Зведные войны: Последние Джедаи", "Новая история о противостоянии света и тьмы, добра и зла начинается после гибели Хана Соло. В Галактике, где Первый Орден и Сопротивление яростно сражаются друг с другом в войне, героиня Рей пробудила в себе Силу. Но что произойдет, когда она встретится с единственным оставшимся в живых рыцарем-джедаем — Люком Скайуокером?", "/home/ilia/Projects/Android/Films/app/src/main/res/drawable/last_jedi.jpeg"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO: 8/30/20 Add save instance
    }

    public void showDetails(View view) {
        // TODO: 8/30/20 Add change color chosen film
        Intent openFilmDetails = new Intent(MainActivity.this, FilmDetails.class);
        openFilmDetails.putExtra("film_id", view.getId());
        startActivity(openFilmDetails);
    }
}