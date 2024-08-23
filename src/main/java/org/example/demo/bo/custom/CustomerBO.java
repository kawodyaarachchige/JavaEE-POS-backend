package org.example.demo.bo.custom;

import org.example.demo.bo.SuperBO;
import org.example.demo.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO  extends SuperBO {

    Boolean saveCustomer(CustomerDTO dto);
    Boolean updateCustomer(String id, CustomerDTO dto);
    Boolean deleteCustomer(String id);
    CustomerDTO searchCustomer(String id);
    List<CustomerDTO> getAllCustomers();
}
