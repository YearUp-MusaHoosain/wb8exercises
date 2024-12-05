package com.pluralsight;

import com.pluralsight.models.Actor;
import com.pluralsight.models.Film;

import javax.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SakilaDataManager {

    private DataSource dataSource;

    public SakilaDataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> actorsByLastName(String firstName, String lastName){

        ArrayList<Actor> actors = new ArrayList<>();
        try(
                Connection connection = dataSource.getConnection();
        )
        {
            try(
                    PreparedStatement pStatement = connection.prepareStatement(
                            """
                                    SELECT actor_id, first_name, last_name
                                    FROM sakila.actor
                                    WHERE first_name like ?
                                    and last_name = ?""")
            )
            {
                pStatement.setString(1, firstName);
                pStatement.setString(2, lastName);

                try (
                        ResultSet results = pStatement.executeQuery()
                )
                {
                    if (results.next()){
                        System.out.println("Your matches are: \n");

                        do{
                            int col1 = results.getInt("actor_id");
                            String col2 = results.getString("first_name");
                            String col3 = results.getString("last_name");

                            actors.add(new Actor(col1, col2, col3));
                        }while (results.next());


                    }
                    else {
                        System.out.println("No matches!");
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return actors;
    }

    public List<Film> actorsByIDForMovies(int actorId){
        ArrayList<Film> films = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
        )
        {
            try (
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            """
                                    SELECT film.film_id, title, description, release_year, length FROM film
                                    JOIN film_actor ON film.film_id = film_actor.film_id
                                    WHERE actor_id = ?
                                    """)
            )
            {
                preparedStatement.setInt(1, actorId);

                try(
                        ResultSet results = preparedStatement.executeQuery()
                )
                {
                    if (results.next()){
                        System.out.println(actorId + " are in these films: ");

                        do{
                            int filmId = results.getInt("film_id");
                            String title = results.getString("title");
                            String desc = results.getString("description");
                            int releaseYear = results.getInt("release_year");
                            int length = results.getInt("length");

                            films.add(new Film(filmId, title, desc, releaseYear, length));

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
        return films;
    }


}
