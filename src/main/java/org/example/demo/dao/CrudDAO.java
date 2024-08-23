package org.example.demo.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    ArrayList<T> search(String id) throws SQLException;

    boolean save(T t) throws SQLException, ClassNotFoundException;

    boolean update(T t) throws SQLException;

    boolean delete(String id) throws SQLException;

    ArrayList<T> getAll() throws SQLException;
}
