package com.udemy;

import java.sql.*;

public class Exercise_TryCatchFinally {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet results = null;

        try{
            // get the user name and password from the command line args
            String username = args[0];
            String password = args[1];
            String database = args[2];
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 1. open a connection to the database
            // use the database URL to point to the correct database
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sakila",
                    username,
                    password);
            // create statement
            // the statement is tied to the open connection


            pStatement = connection.prepareStatement(
                    "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM northwind.products;");


            results = pStatement.executeQuery();

            String header = String.format("%-5s %-35s %-8s %-8s", "ID", "Name", "Price", "Stock");
            System.out.println(header);
            System.out.println("--".repeat(30));
            // process the results
            while (results.next()) {

                int productID = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                double unitsInStock = results.getDouble("UnitsInStock");

                String info = String.format("%-5d %-35s %-8.2f %-8.2f", productID, productName, unitPrice, unitsInStock);
                System.out.println(info);
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("There was an issue finding a class:");
            e.printStackTrace();
        }
        catch (SQLException e){
            System.out.println("There was a SQL issue:");
            e.printStackTrace();
        }
        finally {
            if (results != null) {results.close();}
            if (pStatement != null) {pStatement.close();}
            if (connection != null) {connection.close();}
        }
    }
}