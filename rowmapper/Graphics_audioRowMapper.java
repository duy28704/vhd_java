package com.example.laptops.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.laptops.model.laptop.Graphics_audio;
import com.example.laptops.model.laptop.Laptop;

public class Graphics_audioRowMapper implements RowMapper<Graphics_audio> {

	@Override
	public  Graphics_audio  mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Graphics_audio graphics_audio = new Graphics_audio();
	     graphics_audio.setGraphics_card(rs.getString("graphics_card"));
	     graphics_audio.setAudio_technology(rs.getString("audio_technology"));
	     Laptop laptop = new Laptop();
	     laptop.setLaptop_id(rs.getInt("laptop_id")); // Gán laptop_id cho laptop
	     graphics_audio.setLaptop(laptop);
		 return graphics_audio;
	}

}
