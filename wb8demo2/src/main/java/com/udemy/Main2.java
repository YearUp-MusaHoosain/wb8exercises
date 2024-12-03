package com.udemy;

import java.sql.*;

public class Main2 {
    public static void main(String[] args) {
        try{
            // get the user name and password from the command line args
            String username = args[0];
            String password = args[1];
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sakila",
                    username,
                    password);
            // create statement
            // the statement is tied to the open connection

            int country_id = 103;
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM sakila.city WHERE country_id = ?");
            pStatement.setInt(1, country_id);


            ResultSet results = pStatement.executeQuery();
            // process the results
            while (results.next()) {
                int cityId = results.getInt("city_id");
                String city = results.getString("city");
                System.out.println(cityId);
                System.out.println(city);
            }
            // 3. Close the connection
            connection.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("There was an issue finding a class:");
            e.printStackTrace();
        }
        catch (SQLException e){
            System.out.println("There was a SQL issue:");
            e.printStackTrace();
        }
    }
}