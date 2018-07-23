package com.hand.Dao;

import java.util.List;

import com.hand.Entity.Film;

public interface FilmDao {
    public List<Film> searchById(int customer_id);
}
