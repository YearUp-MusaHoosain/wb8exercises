package com.pluralsight;

import java.sql.*;

public class Exercise4 {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/northwind";
    private static final String USER = System.getenv("MYSQL_ROOT_USER");
    private static final String PASSWORD = System.getenv("MYSQL_ROOT_PASSWORD");

    public static void main(String[] args) {
        homeScreen();
    }

    public static void printHomeScreen() {
        String homescreen = """
                
                🤔 What do you want to do? 🤔
                
                1) Display all products
                2) Display all customers
                3) Display all categories
                0) Exit
                ==============================""";

        System.out.println(homescreen);
    }

    public static void homeScreen() {
        int selection = 0;
        // HOME SCREEN OPTIONS LOOP
        do {
            try {
                printHomeScreen();
                selection = Console.PromptForInt("Enter an Option >>> ");
                switch (selection) {
                    case 1 -> {
                        Console.displayDelayedString("\nDisplaying All Products...\n");
                        displayAllProducts();
                    }
                    case 2 -> {
                        Console.displayDelayedString("\nDisplay all customers...\n");
                        displayAllCustomers();
                    }
                    case 3 -> {
                        Console.displayDelayedString("\nDisplay all categories...\n");
                        displayAllCategories();
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

                    String info = String.format("%-11d %-35s %10.2f %16.2f", productID, productName, unitPrice, unitsInStock);
                    System.out.println(info);
                }
            }
            catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
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
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void displayAllCategories() {
        String query = "SELECT * FROM categories ORDER BY CategoryID";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet == null) {
                System.out.println("Result set is null");
                return;
            }
            try {
                printHeaderCategories();
                while (resultSet.next()) {
                    int categoryID = resultSet.getInt("CategoryID");
                    String categoryName = resultSet.getString("CategoryName");
                    String description = resultSet.getString("Description");

                    String info = String.format("%-11d %-35s %-40s", categoryID, categoryName, description);
                    System.out.println(info);

                }
                int caId = Console.PromptForInt("categoryID: ");
                query = "SELECT * FROM products WHERE CategoryID = ?";
                try (PreparedStatement preparedStatement1 = connection.prepareStatement(query)) {
                    preparedStatement1.setInt(1, caId);
                    try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
                        if (resultSet1 == null) {
                            System.out.println("Result set is null");
                            return;
                        }
                        printHeaderProducts();
                        while (resultSet1.next()) {
                            int productID = resultSet1.getInt("ProductID");
                            String productName = resultSet1.getString("ProductName");
                            double unitPrice = resultSet1.getDouble("UnitPrice");
                            double unitsInStock = resultSet1.getDouble("UnitsInStock");

                            String info1 = String.format("%-5d %-35s %-8.2f %-8.2f", productID, productName, unitPrice, unitsInStock);
                            System.out.println(info1);
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("Error closing ResultSet: " + e.getMessage());
                }
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printHeaderProducts() {
        String header = String.format("%-11s %-35s %-12s %-8s", "Product ID", "Product Name", "Unit Price", "Units In Stock");
        System.out.println(header);
        System.out.println("--".repeat(40));
    }

    private static void printHeaderCustomers() {
        String header = String.format("%-35s %-35s %-20s %-20s %-20s", "Contact Name", "Company Name", "City", "Country", "Phone Number");
        System.out.println(header);
        System.out.println("--".repeat(65));
    }

    public static void printHeaderCategories() {
        String header = String.format("%-5s %-35s %-40s", "Category ID", "Category Name", "Description");
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