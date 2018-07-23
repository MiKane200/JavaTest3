package com.hand.Dao.DaoImpl;

import java.sql.SQLException;
import java.util.List;

import com.hand.Dao.CityDao;
import com.hand.Entity.City;
import com.hand.Utils.JdbcMethods;

public class CityDaoImpl implements CityDao {
    @Override
    public List<City> findCity(int country_id) {
        String sql = "select * from city where country_id = ?";
        Object object = country_id;
        try {
            List<City> cities= JdbcMethods.selectMethod(City.class,sql,object);
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
