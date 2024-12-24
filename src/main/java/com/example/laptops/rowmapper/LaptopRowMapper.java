package com.example.laptops.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.example.laptops.model.laptop.Laptop;

public class LaptopRowMapper implements RowMapper<Laptop> {
    @Override
    public Laptop mapRow(ResultSet rs, int rowNum) throws SQLException {
        Laptop laptop = new Laptop();
        laptop.setLaptop_id(rs.getInt("laptop_id"));
        laptop.setLaptop_name(rs.getString("laptop_name"));
        laptop.setLaptop_price(rs.getString("laptop_price"));
        laptop.setImageURL(rs.getString("image_url"));
        return laptop;
    }
}
