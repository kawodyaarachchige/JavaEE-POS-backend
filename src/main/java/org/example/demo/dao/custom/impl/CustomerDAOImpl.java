package org.example.demo.dao.custom.impl;

import org.example.demo.dao.custom.CustomerDAO;
import org.example.demo.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        return null;
    }
}
