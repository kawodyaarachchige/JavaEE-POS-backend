package org.example.demo.dao.custom.impl;

import org.example.demo.dao.SQLUtil;
import org.example.demo.dao.custom.OrderDAO;
import org.example.demo.entity.Order;
import org.example.demo.util.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public String nextId() throws SQLException {
    return IdGenerator.generateOrderId();
    }

    @Override
    public ArrayList<Order> search(String newValue) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM orders WHERE id=?", newValue + "");
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(rst.getString("id"), rst.getString("date"), rst.getDouble("discount_value"), rst.getDouble("sub_total"), rst.getString("customer_iD")));
        }
        return orders;
    }


@Override
public ArrayList<Order> getAll() throws SQLException {
    ResultSet rst = SQLUtil.execute("SELECT * FROM orders");

    ArrayList<Order> getAllOrders = new ArrayList<>();

    while (rst.next()) {
        Order order = new Order(rst.getString("id"), rst.getString("date"), rst.getDouble("discount_value"), rst.getDouble("sub_total"), rst.getString("customer_iD"));
        getAllOrders.add(order);
    }

    return getAllOrders;
}

@Override
public boolean save(Order dto) throws SQLException, ClassNotFoundException {
    return SQLUtil.execute("INSERT INTO orders VALUES (?,?,?,?,?)", dto.getId(), dto.getDate(), dto.getDiscount_value(), dto.getSub_total(), dto.getCustomer_id());
}

@Override
public boolean update(Order dto) {
    return false;
}

@Override
public boolean delete(String id) {
    return false;
}

@Override
public boolean exist(String orderId) throws SQLException {
    ResultSet rst = SQLUtil.execute("SELECT id FROM orders WHERE id=?", orderId);
    return rst.next();

}

@Override
public ArrayList<Order> searchByOrderId(String id) throws SQLException, ClassNotFoundException {
    ResultSet rst = SQLUtil.execute("SELECT * FROM orders WHERE id like ?", id + "%");
    ArrayList<Order> orders = new ArrayList<>();

    while (rst.next()) {
        orders.add(new Order(rst.getString("id"), rst.getString("date"), rst.getDouble("discount_value"), rst.getDouble("sub_total"), rst.getString("customer_iD")));
    }
    return orders;
}
}
