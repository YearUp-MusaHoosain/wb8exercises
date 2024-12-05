package com.pluralsight;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
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

        // Create the SakilaDataManager
        SakilaDataManager sdbManager = new SakilaDataManager(dataSource);

        // getting actors by name
        String actorFirstName = Console.PromptForString("What is the first name of the actor?: ");
        String actorLastName = Console.PromptForString("What is the last name of the actor?: ");
        List<Actor> actors = sdbManager.actorsByLastName(actorFirstName, actorLastName);

        actors.forEach(System.out::println);


        // getting list of films by actor id
        int actorId = Console.PromptForInt("What is the actorID?: ");
        List<Film> films = sdbManager.actorsByIDForMovies(actorId);

        films.forEach(System.out::println);
    }

}