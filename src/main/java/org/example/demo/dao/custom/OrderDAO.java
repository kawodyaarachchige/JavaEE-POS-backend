package org.example.demo.dao.custom;

import org.example.demo.dao.CrudDAO;
import org.example.demo.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Order> {
    ArrayList<Order> searchByOrderId(String id) throws SQLException, ClassNotFoundException;
    boolean exist(String orderId ) throws SQLException;
    String nextId() throws SQLException;
}
