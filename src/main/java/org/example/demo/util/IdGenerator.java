package org.example.demo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.demo.dao.SQLUtil;

public class IdGenerator {

    public static String generateCustomerId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT customer_id FROM customer ORDER BY customer_id DESC");
        String id = null;
        while (rst.next()) {
            String potentialId = rst.getString(1);
            if (potentialId.startsWith("C")) {
                try {
                    Integer.parseInt(potentialId.substring(1)); // Check if the ID is numeric after "C"
                    id = potentialId;
                    break;
                } catch (NumberFormatException ignored) {
                    // Continue searching for a valid numeric ID
                }
            }
        }

        if (id == null) {
            id = "C001";
        } else {
            int idNum = Integer.parseInt(id.substring(1)) + 1;
            id = "C" + String.format("%03d", idNum);
        }
        return id;
    }

    public static String generateItemId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT item_id FROM item ORDER BY item_id DESC");
        String id = null;
        while (rst.next()) {
            String potentialId = rst.getString(1);
            if (potentialId.startsWith("I")) {
                try {
                    Integer.parseInt(potentialId.substring(1));
                    id = potentialId;
                    break;
                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (id == null) {
            id = "I001";
        } else {
            int idNum = Integer.parseInt(id.substring(1)) + 1;
            id = "I" + String.format("%03d", idNum);
        }
        return id;
    }
    public static String generateOrderId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT id FROM orders ORDER BY id DESC");
        String id = null;
        while (rst.next()) {
            String potentialId = rst.getString(1);
            if (potentialId.startsWith("O")) {
                try {
                    Integer.parseInt(potentialId.substring(3));
                    id = potentialId;
                    break;
                } catch (NumberFormatException ignored) {
                }
            }
        }

        if (id == null) {
            id = "O001";
        } else {
            int idNum = Integer.parseInt(id.substring(3)) + 1;
            id = "O" + String.format("%03d", idNum);
        }
        return id;
        }
}
