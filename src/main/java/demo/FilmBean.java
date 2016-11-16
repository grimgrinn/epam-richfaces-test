package demo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.FilmDao;
import entity.Film;


public class FilmBean implements Serializable {
    private Film currentItem = new Film();

    private List<Film> allFilms = null;
    private int currentRow;

    public void fetchCurrentRow(ActionEvent event) {
        String id=(FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("id"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance(). 
                getExternalContext().getRequestParameterMap().get("row"));
        for (Film item : allFilms) {
            if (item.getId() == Integer.parseInt(id)){
                currentItem=item;
                break;
            }
        }
    }

    public Film getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Film currentItem) {
        this.currentItem = currentItem;
    }


    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    private Set<Integer> keys = new HashSet<Integer>();

    public void store() {
        FilmDao dao = new FilmDao();
        dao.update(currentItem);

        allFilms.set(currentRow, currentItem);
        keys.clear();
        keys.add(currentRow);
    }

    public void delete() {
        allFilms.remove(currentRow);
    }

    private Film editedFilm;

    public List<Film> getFilmList() {
        FilmDao dao = new FilmDao();

        allFilms = dao.get();
        return allFilms;
    }

    public Set<Integer> getKeys() {
        return keys;
    }

    public void setKeys(Set<Integer> keys) {
        this.keys = keys;
    }


}
