package org.example.demo.bo.custom.impl;

import org.example.demo.bo.custom.CustomerBO;
import org.example.demo.dto.CustomerDTO;

import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    @Override
    public Boolean saveCustomer(CustomerDTO dto) {
        return null;
    }

    @Override
    public Boolean updateCustomer(String id, CustomerDTO dto) {
        return null;
    }

    @Override
    public Boolean deleteCustomer(String id) {
        return null;
    }

    @Override
    public CustomerDTO searchCustomer(String id) {
        return null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return List.of();
    }
}
