package com.hand.Dao.DaoImpl;

import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.hand.Dao.FilmDao;
import com.hand.Entity.Film;
import com.hand.Utils.JdbcConnector;

public class FilmDaoImpl implements FilmDao {

    @Override
    public List<Film> searchById(int customer_id) {
        Connection conn = null;
        try {
            conn = JdbcConnector.getConnection();
            String sql1 = "select inventory_id from rental where customer_id = "+customer_id;
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            ResultSet rs = pstmt1.executeQuery();
            List<Integer> list_inventory = new ArrayList<>();
            while (rs.next()) {
                int s = rs.getInt(1);
                list_inventory.add(s);
            }

            String sql2 = "select film_id from inventory  where inventory_id = ?";

            String sql3 = "select film_id,title,release_year from film where film_id = ?";

            List<Integer> list_film = new ArrayList<>();

            List<Film> films = new ArrayList<>();

            for (int s:list_inventory ) {
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setObject(1,s);
                ResultSet rs2 = pstmt2.executeQuery();
                while (rs2.next()) {
                    int s2 = rs2.getInt(1);
                    list_film.add(s2);
                }
                for (int s3:list_film ) {
                    PreparedStatement pstmt3 = conn.prepareStatement(sql3);
                    pstmt3.setObject(1,s3);
                    ResultSet rs3 = pstmt3.executeQuery();
                    while (rs3.next()) {
                        Film film = new Film();
                        film.setFilm_id(rs3.getInt(1));
                        film.setTitle(rs3.getString(2));
                        film.setRelease_year(rs3.getDate(3));
                        films.add(film);
                    }
                }

            }
            return films;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
