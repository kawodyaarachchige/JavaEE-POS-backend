package org.example.demo.bo.custom;

import org.example.demo.bo.SuperBO;
import org.example.demo.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {
    boolean save(OrderDetailDTO dto) throws SQLException, ClassNotFoundException;
    ArrayList<OrderDetailDTO> searchByOrderId(String id) throws SQLException, ClassNotFoundException;
}
