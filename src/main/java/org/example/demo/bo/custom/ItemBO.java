package org.example.demo.bo.custom;

import org.example.demo.bo.SuperBO;
import org.example.demo.dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {

    Boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    Boolean updateItem(String id, ItemDTO dto) throws SQLException;
    Boolean deleteItem(String id) throws SQLException;
    List<ItemDTO> getAllItems() throws SQLException;
    ItemDTO searchItem(String id) throws SQLException;
}
