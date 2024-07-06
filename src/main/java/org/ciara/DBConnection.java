package org.ciara;

import java.sql.*;

public class DBConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/ciara";
    private static final String USER = "danu";
    private static final String PASSWORD = "Danu2003@";

    public static Connection setConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection failed!");
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
