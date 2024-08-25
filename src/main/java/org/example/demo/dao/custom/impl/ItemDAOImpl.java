package org.example.demo.dao.custom.impl;

import org.example.demo.dao.SQLUtil;
import org.example.demo.dao.custom.ItemDAO;
import org.example.demo.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {

        return SQLUtil.execute("INSERT INTO item VALUES(?,?,?,?)",item.getItem_id(),item.getDescription(),item.getPrice(),item.getQuantity());
    }

    @Override
    public boolean update(Item item) throws SQLException {
        return SQLUtil.execute("UPDATE item SET description=?,price=?,quantity=? WHERE item_id=?",item.getDescription(),item.getPrice(),item.getQuantity(),item.getItem_id());
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM item WHERE item_id=?",id);
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM item");
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()){
            items.add(new Item(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getInt(4)));
        }
        return items;
    }
}
