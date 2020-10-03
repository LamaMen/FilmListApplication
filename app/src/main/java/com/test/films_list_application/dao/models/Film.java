package com.test.films_list_application.dao.models;

public class Film {
    private final int id;
    private final String name;
    private final String description;
    private final String photoPhotoUrl;
    private boolean isFavorite;

    public Film(FilmJson json) {
        this.id = json.id;
        this.name = json.name;
        this.description = json.description;
        this.photoPhotoUrl = json.img;
        this.isFavorite = false;
    }

    public int getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoPhotoUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
