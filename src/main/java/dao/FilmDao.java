package dao;

import connectionPool.ConnectionPool;
import entity.Film;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("JpaQueryApiInspection")
public class FilmDao implements InterfaceDao<Film> {
    public static final Logger MEGALOG = LogManager.getLogger(FilmDao.class);

    @Override
    public ArrayList<Film> get() {
        String select = "SELECT film_id, title, description, release_year, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features, last_update FROM film";
        ConnectionPool pool = ConnectionPool.getInstance();
        ArrayList<Film> result =  null;
        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){


            ArrayList<Film> films = getFilms(ps);
            if (films.size() > 0) {
                result = films;
            }
        } catch (SQLException e) {
            MEGALOG.error("connection error", e);
        }

        return result;
    }

    /**
     * Обновление фильма
     * @param item инстанс фильма
     */
    @Override
    public void update(final Film item) {

        String update = "UPDATE `film` SET `title`=?,`description`=? WHERE `film_id` = ?";

        ConnectionPool pool = ConnectionPool.getInstance();
        try(Connection connection = pool.takeConnection();
            PreparedStatement ps = connection.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, item.getTitle());
            ps.setString(2, item.getDescription());
            ps.setInt   (3, item.getId());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                   int  newId = rs.getInt(1);
                   MEGALOG.info("film was updated");
                    System.out.println("film was updated, id #"+ newId);

                }
            }

        } catch (SQLException e) {
            MEGALOG.error("connection error", e);
        }


    }

    /**
     * Фильм по id
     * @param id фильма
     * @return фильм
     */

    @Override
    public Film getById(final int id){
        String select = "SELECT film_id, title, description, release_year, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features, last_update FROM film WHERE film_id = ? LIMIT 1";
        ConnectionPool pool = ConnectionPool.getInstance();
        Film result = null;

        try (Connection connection = pool.takeConnection();
             PreparedStatement ps = connection.prepareStatement(
                     select,
                     ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY
             )
        ){
            ps.setInt(1, id);

            ArrayList<Film> films = getFilms(ps);
            if(films.size() > 0){
                result = films.get(0);
            } else {
   //             MEGALOG.error("trying to get user with wrong id");
                return null;
            }
        } catch (SQLException e) {
    //        MEGALOG.error("connection error",e);
        }
        return result;
    }

//
//
//    @Override
//    public Film create(final Film item) {
//        Film newUser = null;
//        String insert = "INSERT INTO users (email, last_name, first_name, password) VALUES (?, ?, ?, ?)";
//        ConnectionPool pool = ConnectionPool.getInstance();
//        try(Connection connection = pool.takeConnection();
//            PreparedStatement ps = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
//        ) {
//            ps.setString(1, item.getEmail());
//            ps.setString(2, item.getLastName());
//            ps.setString(3, item.getFirstName());
//            ps.setString(4, item.getPassword());
//            ps.executeUpdate();
//            try (ResultSet rs = ps.getGeneratedKeys()) {
//                if (rs.next()) {
//                    int newId = rs.getInt(1);
//                    newUser = new Film(newId, item.getFirstName(), item.getLastName(), item.getPassword(), item.getEmail());
//                }
//            }
//
//        } catch (SQLException e) {
//            MEGALOG.error("connection error", e);
//        }
//        return newUser;
//    }


    @Override
    public Film create(final Film item) {
        throw new NotImplementedException();
    }
    /**
     * Удаляет фильм
     * @param id фильма
     */
    @Override
    public void delete(final int id) {
        throw new NotImplementedException();
    }

    /**
     * Заполняет коллекцию объектов из PreparedStatement
     * @param ps PreparedStatement
     * @return массив фильмов
     * @throws SQLException
     */
    private ArrayList<Film> getFilms(PreparedStatement ps) throws SQLException {
        ArrayList<Film> result = new ArrayList<>();


        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("film_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int rYear = rs.getInt("release_year");
                int lanId = rs.getInt("language_id");
                int orLanId = rs.getInt("original_language_id");
                int rentalDuration = rs.getInt("rental_duration");
                float rentalRate = rs.getFloat("rental_rate");
                int length = rs.getInt("length");
                float replCost = rs.getFloat("replacement_cost");
                String rating  = rs.getString("rating");
                String specFe = rs.getString("special_features");
                int lastUp = rs.getInt("last_update");
                Film film = new Film(id, title, description, rYear, lanId, orLanId, rentalDuration,rentalRate, length, replCost, rating, specFe, lastUp );
                result.add(film);

            }
        }

        return result;
    }




}
