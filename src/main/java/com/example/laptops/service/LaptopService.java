package com.example.laptops.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.laptops.model.laptop.CPU;
import com.example.laptops.model.laptop.Dimension_weight;
import com.example.laptops.model.laptop.Graphics_audio;
import com.example.laptops.model.laptop.Laptop;
import com.example.laptops.model.laptop.Other_info;
import com.example.laptops.model.laptop.Ports_features;
import com.example.laptops.model.laptop.Ram_storage;
import com.example.laptops.model.laptop.Screen;
import com.example.laptops.rowmapper.CPURowMapper;
import com.example.laptops.rowmapper.DimensionRowMapper;
import com.example.laptops.rowmapper.Graphics_audioRowMapper;
import com.example.laptops.rowmapper.LaptopRowMapper;
import com.example.laptops.rowmapper.Other_infoRowMapper;
import com.example.laptops.rowmapper.Port_featureRowMapper;
import com.example.laptops.rowmapper.RamStorageRowMapper;
import com.example.laptops.rowmapper.ScreenRowMapper;
@Service
public class LaptopService {

	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	public int addLaptop(Laptop laptop) {
        String sql = "INSERT INTO Laptops (laptop_id,product_name, price) VALUES (:laptop_id, :laptop_name, :laptop_price)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", laptop.getLaptop_id());
        params.addValue("name", laptop.getLaptop_name());
        params.addValue("price", laptop.getLaptop_price());

        return namedParameterJdbcTemplate.update(sql, params);
    }

    public int updateLaptop(Laptop laptop) {
        String sql = "UPDATE Laptops SET  laptop_name = :laptop_name, laptop_price = :laptop_price WHERE laptop_id = :laptop_id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("laptop_id", laptop.getLaptop_id());
        params.addValue("laptop_name", laptop.getLaptop_name());
        params.addValue("laptop_price", laptop.getLaptop_price());

        return namedParameterJdbcTemplate.update(sql, params);
    }
   
    public int deleteLaptop(long id) {
        String sql = "DELETE FROM Laptops WHERE laptop_id = :laptop_id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("laptop_id", id);

        return namedParameterJdbcTemplate.update(sql, params);
    }
    public Laptop getLaptopById(Integer laptop_id) {
        String sql = "SELECT * FROM Laptops WHERE laptop_id = :laptop_id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("laptop_id", laptop_id);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, new LaptopRowMapper());
        } catch (EmptyResultDataAccessException e) {
          
            System.out.println("Laptop not found with id: " + laptop_id);
            return null;  
        }
    }
    public List<Laptop> getAllLaptops() {
        String sql = "SELECT * FROM Laptops";  
        return namedParameterJdbcTemplate.query(sql, new LaptopRowMapper());
    }
    public Laptop getDetailsLaptopById(Integer laptop_id) {
    	 	String sqlLaptop = "SELECT * FROM Laptops WHERE laptop_id = :laptop_id";
    	    String sqlCPU = "SELECT * FROM CPU_Details WHERE laptop_id = :laptop_id";
    	    String sqlRam = "SELECT * FROM RAM_Storage WHERE laptop_id = :laptop_id";
    	    String sqlScreen = "SELECT * FROM Screen_Details WHERE laptop_id = :laptop_id";
    	    String sqlGraphics_audio = "SELECT * FROM Graphics_Audio WHERE laptop_id = :laptop_id";
    	    String sqlPorts_features = "SELECT * FROM Ports_Features WHERE laptop_id = :laptop_id";
    	    String sqlDimension_weight = "SELECT * FROM Dimensions_Weight WHERE laptop_id = :laptop_id";
    	    String sqlOther_info = "SELECT * FROM Other_Info WHERE laptop_id = :laptop_id";
    	    Laptop laptop = namedParameterJdbcTemplate.queryForObject(sqlLaptop, new MapSqlParameterSource("laptop_id", laptop_id), new LaptopRowMapper());
    	    CPU cpu = namedParameterJdbcTemplate.queryForObject(sqlCPU, new MapSqlParameterSource("laptop_id", laptop_id), new CPURowMapper());
    	    Ram_storage ram = namedParameterJdbcTemplate.queryForObject(sqlRam, new MapSqlParameterSource("laptop_id", laptop_id), new RamStorageRowMapper());
    	    Screen screen = namedParameterJdbcTemplate.queryForObject(sqlScreen, new MapSqlParameterSource("laptop_id", laptop_id), new ScreenRowMapper());
    	    Graphics_audio graphics_audio = namedParameterJdbcTemplate.queryForObject(sqlGraphics_audio, new MapSqlParameterSource("laptop_id", laptop_id), new Graphics_audioRowMapper());
    	    Ports_features port_features = namedParameterJdbcTemplate.queryForObject(sqlPorts_features, new MapSqlParameterSource("laptop_id", laptop_id), new Port_featureRowMapper());
    	    Dimension_weight dimension_weight = namedParameterJdbcTemplate.queryForObject(sqlDimension_weight, new MapSqlParameterSource("laptop_id", laptop_id), new DimensionRowMapper());
    	    Other_info other_info = namedParameterJdbcTemplate.queryForObject(sqlOther_info, new MapSqlParameterSource("laptop_id", laptop_id), new Other_infoRowMapper());
    	    laptop.setCpu(cpu);
    	    laptop.setRam(ram);
    	    laptop.setScreen(screen);
    	    laptop.setGraphics_audio(graphics_audio);
    	    laptop.setPorts_features(port_features);
    	    laptop.setDimension_weight(dimension_weight);
    	    laptop.setOther_info(other_info);
    	return laptop;
    }
    
}

