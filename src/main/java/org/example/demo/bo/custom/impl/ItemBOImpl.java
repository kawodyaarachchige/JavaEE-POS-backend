package org.example.demo.bo.custom.impl;

import org.example.demo.bo.custom.ItemBO;
import org.example.demo.dao.DAOFactory;
import org.example.demo.dao.custom.ItemDAO;
import org.example.demo.dto.ItemDTO;
import org.example.demo.entity.Item;
import org.example.demo.util.IdGenerator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DaoFactoryTypes.ITEM);
    @Override
    public Boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
       return itemDAO.save(new Item(IdGenerator.generateItemId(),dto.getDescription(),dto.getPrice(),dto.getQuantity()));
    }

    @Override
    public Boolean updateItem(String id, ItemDTO dto) throws SQLException {
        return itemDAO.update(new Item(id,dto.getDescription(),dto.getPrice(),dto.getQuantity()));
    }


    @Override
    public Boolean deleteItem(String id) throws SQLException {
        return itemDAO.delete(id);
    }

    @Override
    public List<ItemDTO> getAllItems() throws SQLException {
        ArrayList<Item> all = itemDAO.getAll();
        List<ItemDTO> list = all.stream().map(item -> new ItemDTO(item.getItem_id(), item.getDescription(), item.getPrice(), item.getQuantity())).toList();
        return list;
    }


    @Override
    public ItemDTO searchItem(String id) throws SQLException {
        return null;
    }
}
