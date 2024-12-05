package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;


public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println(
                    "Application needs three arguments to run: " +
                            "java com.pluralsight.NorthwindTraders <username> <password> <sqlAddress>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];
        String sqlAddress = args[2];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(sqlAddress);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        doActorName(dataSource);

        doMoviesOfName(dataSource);
    }

    public static void doActorName(DataSource dataSource) {
        try(
                Connection connection = dataSource.getConnection();
        )
        {
            System.out.print("What is the last name of the user?: ");
            String lastName = scanner.nextLine();

            try(
                    PreparedStatement pStatement = connection.prepareStatement(
                            "SELECT first_name, last_name \n" +
                                    "FROM sakila.actor\n" +
                                    "WHERE last_name = ?")
            )
            {
                pStatement.setString(1, lastName);

                try (
                        ResultSet results = pStatement.executeQuery()
                )
                {
                    if (results.next()){
                        System.out.println("Your matches are: \n");

                        do{
                            String col1 = results.getString("first_name");
                            String col2 = results.getString("last_name");

                            System.out.println(col1 + " " + col2);
                        }while (results.next());
                    }
                    else {
                        System.out.println("No matches!");
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println("ERROR!");
            e.printStackTrace();
        }
    }

    public static void doMoviesOfName(DataSource dataSource) {

        try (
                Connection connection = dataSource.getConnection();
        )
        {
            System.out.print("What is the first name of the user?: ");
            String firstName = scanner.nextLine();

            System.out.print("What is the last name of the user?: ");
            String lastName = scanner.nextLine();

            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "select film.title \n" +
                                    "FROM sakila.film\n" +
                                    "join film_actor on film_actor.film_id = film.film_id\n" +
                                    "join actor on actor.actor_id = film_actor.actor_id\n" +
                                    "where actor.first_name = ? AND actor.last_name = ?;")
            )
            {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);

                try(
                        ResultSet results = preparedStatement.executeQuery()
                )
                {
                    if (results.next()){
                        System.out.println(firstName + " " + lastName + " are in these films: ");

                        do{
                            String col1 = results.getString("title");
                            System.out.println(col1);

                        }while (results.next());
                    }
                    else {
                        System.out.println("No matches!");
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println("ERROR!");
            e.printStackTrace();
        }
    }
}