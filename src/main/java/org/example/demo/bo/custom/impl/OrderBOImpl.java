package org.example.demo.bo.custom.impl;

import org.example.demo.bo.SuperBO;
import org.example.demo.bo.custom.OrderBO;
import org.example.demo.dao.DAOFactory;
import org.example.demo.dao.custom.OrderDAO;
import org.example.demo.dto.OrderDTO;
import org.example.demo.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoFactoryTypes.ORDER);

    @Override
    public ArrayList<OrderDTO> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<Order> allOrders = orderDAO.getAll();
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : allOrders) {
            orderDTOS.add(new OrderDTO(order.getId(), order.getDate(), order.getDiscount_value(), order.getSub_total(), order.getCustomer_id()));
        }

        return orderDTOS;
    }

    @Override
    public boolean existOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.exist(id);
    }

    @Override
    public boolean saveOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Order(dto.getId(), dto.getDate(), dto.getDiscount_value(), dto.getSub_total(), dto.getCustomer_id()));
    }

    @Override
    public boolean updateOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new Order(dto.getId(), dto.getDate(), dto.getDiscount_value(), dto.getSub_total(), dto.getCustomer_id()));
    }

    @Override
    public boolean deleteOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(id);
    }

    @Override
    public String nextOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.nextId();
    }

    @Override
    public OrderDTO search(String orderId) throws SQLException, ClassNotFoundException {
        ArrayList<Order> search = orderDAO.search(orderId);
        if (search.isEmpty()) {
            return null;
        }
        Order order = search.get(0);
        return new OrderDTO(order.getId(), order.getDate(), order.getDiscount_value(), order.getSub_total(), order.getCustomer_id());
    }

    @Override
    public ArrayList<OrderDTO> searchByOrderId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<Order> search = orderDAO.searchByOrderId(id);
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : search) {
            orderDTOS.add(new OrderDTO(order.getId(), order.getDate(), order.getDiscount_value(), order.getSub_total(), order.getCustomer_id()));
        }
        return orderDTOS;
    }
}
