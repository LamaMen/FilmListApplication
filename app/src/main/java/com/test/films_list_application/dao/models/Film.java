package com.test.films_list_application.dao.models;

public class Film {
    private final int id;
    private final String name;
    private final String description;
    private final int photoID;
    private boolean isFavorite;

    public Film(int id, String name, String description, int pathToImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoID = pathToImage;
        this.isFavorite = false;
    }

    public int getId() {
        return id;
    }

    public int getPhotoID() {
        return photoID;
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
