package com.test.films_list_application.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.test.films_list_application.R;
import com.test.films_list_application.dao.Films;
import com.test.films_list_application.models.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public final static Map<Integer, Film> mapFilms = new HashMap<>();

    private final static String TAG = MainActivity.class.toString();

    private ArrayList<Integer> changedColorTexts = new ArrayList<>();
    private static final String KEY_TEXT = "TextView";
    private static final int TEXT_CHANGE_COLOR = Color.rgb(82, 82, 82);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about_app:
                Intent intent = new Intent(this, AboutAppActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_home:
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.invite_friend) {
            inviteFriend(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(KEY_TEXT, changedColorTexts);
    }

    public void showDetails(View view) {
        Intent openFilmDetails = new Intent(MainActivity.this, FilmDetailsActivity.class);
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

    private void inviteFriend(MenuItem item) {
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