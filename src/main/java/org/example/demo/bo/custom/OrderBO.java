package org.example.demo.bo.custom;


import org.example.demo.bo.SuperBO;
import org.example.demo.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException;
    boolean existOrder(String id) throws SQLException, ClassNotFoundException;
    boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteOrder(String id) throws SQLException, ClassNotFoundException;
    String nextOrderId() throws SQLException, ClassNotFoundException;
    OrderDTO search(String orderId) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDTO> searchByOrderId(String id) throws SQLException, ClassNotFoundException;
}
