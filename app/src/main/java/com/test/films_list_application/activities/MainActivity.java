package com.test.films_list_application.activities;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.test.films_list_application.R;
import com.test.films_list_application.animations.BasicAnimatorListener;
import com.test.films_list_application.dao.Films;
import com.test.films_list_application.models.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    public final static Map<Integer, Film> mapFilms = new HashMap<>();

    private final static String TAG = MainActivity.class.toString();
    private static final int TEXT_CHANGE_COLOR = Color.rgb(82, 82, 82);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        List<Film> films = Films.getInstance().getFilms();
        for (Film film : films) {
            mapFilms.put(film.getId(), film);
        }
    }

    @Override
    public void onBackPressed() {
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
            inviteFriend();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDetails(View view) {
        Intent openFilmDetails = new Intent(MainActivity.this, FilmDetailsActivity.class);
        openFilmDetails.putExtra("film_id", view.getId());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        View container = (View) view.getParent();
        View card = (View) container.getParent();

        float startPosition = card.getTranslationX();
        ObjectAnimator trY = ObjectAnimator.ofFloat(card, "translationX", displayMetrics.widthPixels);
        AnimatorSet set = new AnimatorSet();
        set.play(trY);
        set.setDuration(350);
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
        set.addListener(new BasicAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                startActivity(openFilmDetails);
                card.setTranslationX(startPosition);
            }
        });

        changeTextColor(container);
    }

    private void changeTextColor(View container) {
        if (!(container instanceof ViewGroup)) {
            return;
        }

        TextView text = (TextView) ((ViewGroup) container).getChildAt(1);
        text.setTextColor(TEXT_CHANGE_COLOR);
    }

    private void inviteFriend() {
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