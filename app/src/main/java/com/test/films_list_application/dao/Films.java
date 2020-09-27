package com.test.films_list_application.dao;

import com.test.films_list_application.R;
import com.test.films_list_application.dao.models.Film;

import java.util.ArrayList;
import java.util.List;

public class Films {
    private static Films instance;
    private List<Film> films;

    private Films() {
        films = new ArrayList<>();
        films.add(new Film(1, "Бешенные псы", "Это должно было стать идеальным преступлением. Задумав ограбить ювелирный магазин, криминальный босс Джо Кэбот собрал вместе шестерых опытных и совершенно незнакомых друг с другом преступников. Но с самого начала все пошло не так, и обычный грабеж превратился в кровавую бойню.", R.drawable.dogs));
        films.add(new Film(2, "Купи меня", "Три девушки — три истории о том, как изменить свою жизнь и чем предстоит пожертвовать, чтобы стать «счастливыми». Порше, олигархи, секс, любовь и мечты о красивой жизни. Смогут ли героини добиться успеха, или они сломаются, так и не дойдя до цели?", R.drawable.buy_me));
        films.add(new Film(3, "Зведные войны: Последние Джедаи", "Новая история о противостоянии света и тьмы, добра и зла начинается после гибели Хана Соло. В Галактике, где Первый Орден и Сопротивление яростно сражаются друг с другом в войне, героиня Рей пробудила в себе Силу. Но что произойдет, когда она встретится с единственным оставшимся в живых рыцарем-джедаем — Люком Скайуокером?", R.drawable.last_jedi));
    }

    public static Films getInstance() {
        if (instance == null) {
            instance = new Films();
        }
        return instance;
    }

    public List<Film> getFilms() {
        return films;
    }
}
