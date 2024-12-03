package com.udemy;

import java.sql.*;

public class Main {

//    private static final String PASSWORD = System.getenv("MYSQL_ROOT_USER”);
    public static void main(String[] args){

        try{
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sakila",
                    "root",
                    "yearup");
            // create statement
            // the statement is tied to the open connection
            Statement statement = connection.createStatement();
            // define your query
            String query = "SELECT * FROM sakila.city WHERE country_id = 103";
            // 2. Execute your query
            ResultSet results = statement.executeQuery(query);
            // process the results
            while (results.next()) {
                String city = results.getString("city");
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