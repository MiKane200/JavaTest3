package com.hand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.hand.Dao.CityDao;
import com.hand.Dao.DaoImpl.CityDaoImpl;
import com.hand.Dao.DaoImpl.FilmDaoImpl;
import com.hand.Dao.FilmDao;
import com.hand.Entity.City;
import com.hand.Entity.Film;
import com.hand.Utils.JdbcConnector;

public class App
{
    public static void main( String[] args )
    {
        Map<String, String> map = System.getenv();
        String userName = map.get("MYSQL_ROOT_USER");
        String pass = map.get("MYSQL_ROOT_PASSWORD");
        String ip = map.get("IP");
        String port = map.get("PORT");
        String databaseName = map.get("DATABASENAME");

        String url = "jdbc:mysql://"+ip+":"+port+"/"+databaseName+"?useUnicode=true&characterEncoding=UTF-8&useSSL=false";

//        JdbcConnector.url = url;
//        JdbcConnector.user =userName;
//        JdbcConnector.pass = pass;

//        int countryId = Integer.parseInt(map.get("COUNTRYID"));
//        int customerId = Integer.parseInt(map.get("CUSTOMERID"));
        int countryId = 6;
        int customerId = 1;

//        for(Iterator<String> itr = map.keySet().iterator(); itr.hasNext();){
//            String key = itr.next();
//            System.out.println(key + "=" + map.get(key));
//        }


        CityDao cityDao = new CityDaoImpl();
        System.out.println("Country ID:"+countryId);
        String sql1 = "select country from country where country_id="+countryId;
        String countryName = searchName(sql1);
        System.out.println(countryName+"的城市：");
        List<City> cities = cityDao.findCity(countryId);
        System.out.println("城市ID | 城市名称");
        for (City city:cities) {
            System.out.println(city.getCity_id()+" | "+city.getCity());
        }

        System.out.println("\n");

        String sql2 = "select last_name from customer where customer_id="+customerId;
        String customerName = searchName(sql2);
        System.out.println(customerName+"的Film：");
        FilmDao filmDao = new FilmDaoImpl();
        List<Film> films = filmDao.searchById(customerId);

        System.out.println("FilmID | File名称 | 租用时间");
        for (Film film:films) {
            System.out.println(film.getFilm_id()+" | "+film.getTitle() +" | "+film.getRelease_year());
        }

    }

    public static String searchName(String sql){
        Connection conn = null;
        try {
            conn = JdbcConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            String name = null;
            while (rs.next()) {
                name = rs.getString(1);
            }
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
