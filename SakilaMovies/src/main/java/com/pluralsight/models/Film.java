package com.pluralsight.models;

public class Film {

    private int filmId;
    private String title;
    private String description;
    private int releaseDate;
    private int length;

    public Film(int filmId, String title, String description, int releaseDate, int length) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.length = length;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Film{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", length=" + length +
                '}';
    }
}
