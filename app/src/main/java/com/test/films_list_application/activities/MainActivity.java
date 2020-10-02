package com.test.films_list_application.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
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
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;
    private ActionBar actionBar;
    private boolean toolBarNavigationListenerRegistered = false;
    private String activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            resolveUpButtonWithFragmentStack();
        } else {
            openMainScreen();
        }

        List<Film> films = Films.getInstance().getFilms();
        for (Film film : films) {
            mapFilms.put(film.getId(), film);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                openMainScreen();
                break;
            case R.id.nav_favorite_films:
                openFavoriteFilmsScreen();
                break;
            case R.id.nav_about_app:
                openAboutAppScreen();
                break;
            case R.id.nav_exit:
                showExitDialog();
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openMainScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, ListFilmsFragment.newInstance(true), ListFilmsFragment.TAG_MAIN)
                .commit();
        activeFragment = ListFilmsFragment.TAG_MAIN;
    }

    private void openFavoriteFilmsScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, ListFilmsFragment.newInstance(false), ListFilmsFragment.TAG)
                .commit();
        activeFragment = ListFilmsFragment.TAG;
    }

    private void openAboutAppScreen() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, new AboutAppFragment(), AboutAppFragment.TAG)
                .commit();
        activeFragment = AboutAppFragment.TAG;
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

        bld.setMessage(getString(R.string.exit_dialog_message))
                .setTitle(getString(R.string.exit_dialog_title))
                .setNegativeButton(getString(R.string.no), lst)
                .setPositiveButton(getString(R.string.yes), lst)
                .create()
                .show();
    }

    private boolean returnMainFragment() {
        int backStack = getSupportFragmentManager().getBackStackEntryCount();
        if (backStack == 0) return false;

        getSupportFragmentManager().popBackStack();
        if (backStack >= 1) {
            showUpButton(false);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            if (!returnMainFragment()) {
                if (activeFragment.equals(ListFilmsFragment.TAG_MAIN)) {
                    showExitDialog();
                } else {
                    openMainScreen();
                }
            }
        }
    }

    private void resolveUpButtonWithFragmentStack() {
        showUpButton(getSupportFragmentManager().getBackStackEntryCount() > 0);
    }

    private void showUpButton(boolean show) {
        if (show) {
            drawerToggle.setDrawerIndicatorEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (!toolBarNavigationListenerRegistered) {
                drawerToggle.setToolbarNavigationClickListener(v -> onBackPressed());
                toolBarNavigationListenerRegistered = true;
            }

        } else {
            actionBar.setDisplayHomeAsUpEnabled(false);
            drawerToggle.setDrawerIndicatorEnabled(true);

            drawerToggle.setToolbarNavigationClickListener(null);
            toolBarNavigationListenerRegistered = false;
        }
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
        inviteIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_friend_message));
        inviteIntent.setType("text/plain");

        Intent chooser = Intent.createChooser(inviteIntent, getString(R.string.invite_friend_сhose_app));
        if (inviteIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    @Override
    public void onFilmItemClick(int id) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.main_content, FilmDetailsFragment.newInstance(id), FilmDetailsFragment.TAG)
                .commit();

        showUpButton(true);
    }
}