package org.ciara;

import java.sql.*;

public class DBConnection {
    public static Connection setConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost.3306/ciaraDb","danu","Danu2003@");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }
    public ResultSet executeQuery(String q){
        ResultSet resultSet = null;
        Connection connection = setConnection();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void executeUpdate(String q){
        Connection connection = setConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
