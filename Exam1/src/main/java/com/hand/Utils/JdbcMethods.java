package com.hand.Utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcMethods {

    public static <T> List<T> selectMethod(Class<T> clazz, String sql,
                                           Object... args) throws SQLException  {
        T entity = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<T>();
        try{
            connection = JdbcConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            // 赋值
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);
                }
            }
            // 执行查询
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                entity = clazz.newInstance();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(entity, columnValue);
                }

                list.add(entity);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            JdbcConnector.closeResore(resultSet, preparedStatement, connection);
        }

        return list;
    }

    /**
     * 更新的方法 返回更新的记录数量
     */
    public static int updateMethod(String sql, Object... args) throws SQLException  {
        int flag = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = JdbcConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i + 1, args[i]);// 赋值操作---给preparestatement
                }
            }

            flag = preparedStatement.executeUpdate();// 执行sql语句
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            // 释放资源
            preparedStatement.close();
            connection.close();
        }

        return flag;
    }

    /**
     * @parm sql执行的多条sql,List<Object[]>装有参数的参数值的数组的list
     * 返回更新的记录数量
     * 事务管理
     */
    public static int updateMethod(String[] sql, List<Object[]> list)
            throws Exception {
        int flag = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = JdbcConnector.getConnection();
        // 关闭自动提交，管理事务
        connection.setAutoCommit(false);
        try {
            for (int i = 0; i < sql.length; i++) {
                preparedStatement = connection.prepareStatement(sql[i]);
                if (list.get(i) != null) {
                    for (int k = 0; k < list.get(i).length; k++) {
                        preparedStatement.setObject(k + 1, list.get(i)[k]);// 赋值操作---给preparestatement
                    }
                }
                flag += preparedStatement.executeUpdate();// 执行sql语句
            }

        }
        catch (Exception e) {
            System.err.println("插入时出现异常~~");
            flag = 0;
            System.out.println(e);
            connection.rollback();
        } finally {
            // 释放资源和提交
            //每次执行结束都将事务又设置成自动提交，就不需要设置commit了
            connection.setAutoCommit(true);
            preparedStatement.close();
            connection.close();
        }
        return flag;
    }
}
