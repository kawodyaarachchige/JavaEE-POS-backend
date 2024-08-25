package org.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO implements Serializable {

    private String  item_id;
    private String description;
    private double price;
    private int quantity;
}
