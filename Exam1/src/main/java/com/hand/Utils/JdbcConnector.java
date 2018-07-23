package com.hand.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcConnector {

//    public static String url;
//    public static String user;
//    public static String pass;

    public static Connection getConnection()
            throws IOException, SQLException, ClassNotFoundException
    {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mysql.properties");
        Properties properties = new Properties();

        properties.load(inStream);

        String url = null;
        String driver = null;
        String user = null;
        String pass = null;
        // 为需要的变量赋值
        url = properties.getProperty("url");
        driver = properties.getProperty("driver");
        user = properties.getProperty("user");
        pass = properties.getProperty("pass");
//        url = "jdbc:mysql://192.168.99.100:3306/library?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
//        driver = "com.mysql.cj.jdbc.Driver";
//        user = "root";
//        pass = "root";
        Connection con = null;
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, pass);
        return con;
    }


    public static void closeResore(ResultSet rs, Statement st, Connection con)
            throws SQLException
    {
        if (rs != null)
        {
            rs.close();
        }
        if (st != null)
        {
            st.close();
        }
        if (con != null)
        {
            con.close();
        }
    }
}
