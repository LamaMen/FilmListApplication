package com.test.films_list_application.models;

public class Film {
    private final String id;
    private final String name;
    private final String description;
    private final String pathToImage;

    public Film(String id, String name, String description, String pathToImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pathToImage = pathToImage;
    }

    public String getId() {
        return id;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
