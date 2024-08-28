package org.example.demo.bo.custom.impl;

import org.example.demo.bo.custom.CustomerBO;
import org.example.demo.dao.DAOFactory;
import org.example.demo.dao.SQLUtil;
import org.example.demo.dao.custom.CustomerDAO;
import org.example.demo.dto.CustomerDTO;
import org.example.demo.entity.Customer;
import org.example.demo.util.IdGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
 CustomerDAO customerDAO=(CustomerDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoFactoryTypes.CUSTOMER);
    @Override
    public Boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(IdGenerator.generateCustomerId(),dto.getName(),dto.getAddress(),dto.getPhone()));
    }

    @Override
    public Boolean updateCustomer(String id, CustomerDTO dto) throws SQLException {
        return customerDAO.update(new Customer(id,dto.getName(),dto.getAddress(),dto.getPhone()));
    }

    @Override
    public Boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException {
        Customer customer = customerDAO.search(id).get(0);
        return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getPhone());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<Customer> all = customerDAO.getAll();
        List<CustomerDTO> list = all.stream().map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getPhone())).toList();
        return list;

    }
    }
