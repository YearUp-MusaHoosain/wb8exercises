package com.pluralsight;

import java.sql.*;

public class Exercise3 {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/northwind";
    private static final String USER = System.getenv("MYSQL_ROOT_USER");
    private static final String PASSWORD = System.getenv("MYSQL_ROOT_PASSWORD");

    public static void main(String[] args) {
        homeScreen();
    }

    public static void printHomeScreen() {
        String homescreen = """
                What do you want to do?
                1) Display all products
                2) Display all customers
                3) Display all categories
                0) Exit
                """;

        System.out.println(homescreen);
    }

    public static void homeScreen() {
        int selection = 0;
        // HOME SCREEN OPTIONS LOOP
        do {
            try {
                printHomeScreen();
                switch (Console.PromptForInt("Enter an Option: ")) {
                    case 1 -> {
                        Console.displayDelayedString("\nDisplaying All Products...\n");
                        displayAllProducts();
                    }
                    case 2 -> {
                        Console.displayDelayedString("\nDisplay all customers...\n");
                        displayAllCustomers();
                    }
                    case 0 -> {
                        Console.displayDelayedString("Exiting...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            }
            catch (Exception e) {
                e.getMessage();
                e.printStackTrace();
            }
        }
        while (selection != 0);
    }

    public static void displayAllProducts() {
        String query = "SELECT * FROM products ORDER BY ProductID";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            assert connection != null;
            try {
                if (resultSet == null) {
                    System.out.println("Result set is null");
                    return;
                }

                printHeaderProducts();
                while (resultSet.next()) {
                    int productID = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    double unitPrice = resultSet.getDouble("UnitPrice");
                    double unitsInStock = resultSet.getDouble("UnitsInStock");

                    String info = String.format("%-11d %-35s %7.2f %10.2f", productID, productName, unitPrice, unitsInStock);
                    System.out.println(info);
                }
            }
            catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    }
                    catch (SQLException e) {
                        System.out.println("Error closing ResultSet: " + e.getMessage());
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    }
                    catch (SQLException e) {
                        System.out.println("Error closing PreparedStatement: " + e.getMessage());
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void displayAllCustomers() {
        String query = "SELECT * FROM customers ORDER BY country";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            assert connection != null;
            try {
                if (resultSet == null) {
                    System.out.println("Result set is null");
                    return;
                }

                printHeaderCustomers();
                while (resultSet.next()) {
                    String contactName = resultSet.getString("ContactName");
                    String companyName = resultSet.getString("CompanyName");
                    String city = resultSet.getString("City");
                    String country = resultSet.getString("Country");
                    String phoneNumber = resultSet.getString("Phone");

                    String info = String.format("%-35s %-35s %-20s %-20s %-20s", contactName, companyName, city, country, phoneNumber);
                    System.out.println(info);
                }
            }
            catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
            finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    }
                    catch (SQLException e) {
                        System.out.println("Error closing ResultSet: " + e.getMessage());
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    }
                    catch (SQLException e) {
                        System.out.println("Error closing PreparedStatement: " + e.getMessage());
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printHeaderProducts() {
        String header = String.format("%-11s %-35s %-11s %-8s", "ProductID", "ProductName", "UnitPrice", "UnitsInStock");
        System.out.println(header);
        System.out.println("--".repeat(37));
    }

    private static void printHeaderCustomers() {
        String header = String.format("%-35s %-35s %-20s %-20s %-20s", "ContactName", "CompanyName", "City", "Country", "Phone Number");
        System.out.println(header);
        System.out.println("--".repeat(65));
    }

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}