package com.test.films_list_application.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.test.films_list_application.R;
import com.test.films_list_application.activities.fragments.AboutAppFragment;
import com.test.films_list_application.activities.fragments.FilmDetailsFragment;
import com.test.films_list_application.activities.fragments.ListFilmsFragment;
import com.test.films_list_application.dao.Films;
import com.test.films_list_application.dao.models.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListFilmsFragment.OnFilmClickListener {

    public final static Map<Integer, Film> mapFilms = new HashMap<>();
    private final static String TAG = MainActivity.class.toString();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, new ListFilmsFragment(), ListFilmsFragment.TAG)
                .commit();

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
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about_app:
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_content, new AboutAppFragment(), AboutAppFragment.TAG)
                        .commit();
                break;
            case R.id.nav_exit:
                showExitDialog();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().popBackStack();
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFilmItemClick(int id) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_content, FilmDetailsFragment.newInstance(id), FilmDetailsFragment.TAG)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void showExitDialog() {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener lst =
                (dialog, which) -> {
                    switch (which) {
                        case Dialog.BUTTON_POSITIVE:
                            finish();
                            break;
                        case Dialog.BUTTON_NEGATIVE:
                            break;
                    }
                    dialog.dismiss();
                };

        bld.setMessage("Вы уверены, что хотите выйти из приложения?")
                .setTitle("Выход из приложения")
                .setNegativeButton("Нет", lst)
                .setPositiveButton("Да", lst)
                .create()
                .show();
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

    private void inviteFriend() {
        Intent inviteIntent = new Intent();
        inviteIntent.setAction(Intent.ACTION_SEND);
        inviteIntent.putExtra(Intent.EXTRA_TEXT, "Присоединяйся ко мне в приложении Film List App!");
        inviteIntent.setType("text/plain");

        Intent chooser = Intent.createChooser(inviteIntent, "В каком приложении?");
        if (inviteIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}