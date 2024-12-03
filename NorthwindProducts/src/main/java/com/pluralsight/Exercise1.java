package com.pluralsight;

import java.sql.*;

public class Exercise1 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];
        String database = args[2];
        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 1. open a connection to the database
        // use the database URL to point to the correct database
        Connection connection = DriverManager.getConnection(
                database,
                username,
                password);
        // create statement
        // the statement is tied to the open connection
        PreparedStatement pStatement = connection.prepareStatement(
                "SELECT * FROM northwind.products;");

        ResultSet results = pStatement.executeQuery();
        // process the results
        while (results.next()) {
            String productName = results.getString("ProductName");
            System.out.println(productName);
        }
    }
}