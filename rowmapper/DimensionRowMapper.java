package com.example.laptops.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.laptops.model.laptop.Dimension_weight;
import com.example.laptops.model.laptop.Laptop;

public class DimensionRowMapper implements RowMapper<Dimension_weight> {

	@Override
	public Dimension_weight mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Dimension_weight dimension = new Dimension_weight();
	     dimension.setDimension(rs.getString("dimensions"));
	     dimension.setWeight(rs.getString("weightt"));
	     dimension.setMaterial(rs.getString("material"));
	     Laptop laptop = new Laptop();
	        laptop.setLaptop_id(rs.getInt("laptop_id")); // Gán laptop_id cho laptop
	        dimension.setLaptop(laptop);
		return dimension;
	}

}
