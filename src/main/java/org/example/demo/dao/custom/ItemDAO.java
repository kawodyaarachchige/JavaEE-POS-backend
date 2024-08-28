package org.example.demo.dao.custom;

import org.example.demo.dao.CrudDAO;
import org.example.demo.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {
    boolean updateQty(String id, String qty) throws SQLException, ClassNotFoundException;
}
