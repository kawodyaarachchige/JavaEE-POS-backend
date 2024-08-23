package org.example.demo.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO implements Serializable {

    private String  id;
    private String name;
    private String address;
    private int phone;

}
