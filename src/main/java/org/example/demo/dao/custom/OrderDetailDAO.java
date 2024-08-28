package org.example.demo.dao.custom;

import org.example.demo.dao.SuperDAO;
import org.example.demo.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends SuperDAO {
    boolean save(OrderDetail dto) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDetail> searchByOrderId(String id) throws SQLException, ClassNotFoundException;
}
