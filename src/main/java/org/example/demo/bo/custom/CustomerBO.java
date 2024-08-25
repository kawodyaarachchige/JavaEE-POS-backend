package org.example.demo.bo.custom;

import org.example.demo.bo.SuperBO;
import org.example.demo.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO  extends SuperBO {

    Boolean saveCustomer(CustomerDTO dto) throws SQLException,ClassNotFoundException;
    Boolean updateCustomer(String id, CustomerDTO dto) throws SQLException;
    Boolean deleteCustomer(String id) throws SQLException;
    CustomerDTO searchCustomer(String id);
    List<CustomerDTO> getAllCustomers()throws SQLException;
}
