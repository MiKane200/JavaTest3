package com.hand.Entity;

import java.util.Date;

public class Film {
    private int film_id;
    private String title;
    private Date release_year;


    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Date release_year) {
        this.release_year = release_year;
    }
}
