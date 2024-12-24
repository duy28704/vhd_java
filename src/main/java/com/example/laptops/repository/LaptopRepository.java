package com.example.laptops.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.laptops.model.laptop.CPU;
import com.example.laptops.model.laptop.Dimension_weight;
import com.example.laptops.model.laptop.Graphics_audio;
import com.example.laptops.model.laptop.Laptop;
import com.example.laptops.model.laptop.Laptop_Money;
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
import com.example.laptops.rowmapper.Laptop_MoneyRowMapper;
@Repository
public class LaptopRepository {
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
    
    public List<Laptop_Money> getAllLaptop_Moneys(){
        String sql ="Select * from Laptop_Money";
        return namedParameterJdbcTemplate.query(sql, new Laptop_MoneyRowMapper());
    }
    public List<String> getAllThang(){
        String sql = "SELECT MONTH(ngayBan) AS Month FROM Laptop_Money GROUP BY MONTH(ngayBan)";
        return namedParameterJdbcTemplate.query(sql,
                (rs, rowNum) -> "Tháng " + rs.getInt("Month")) ;
    }
    public List<Integer> getAllSoLuong(){
        String sql="SELECT MONTH(ngayBan) AS Month, SUM(soLuong) AS TotalSales FROM Laptop_Money GROUP BY MONTH(ngayBan) ORDER BY Month;";
        return namedParameterJdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt("TotalSales"));
    }
    public List<Integer> getAllDoanhThu(){
        String sql = "SELECT MONTH(ngayBan) AS Month, SUM(doanhThu) AS TotalSales FROM Laptop_Money GROUP BY MONTH(ngayBan) ORDER BY Month;";
        return namedParameterJdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getInt("TotalSales"));
    }
    public List<Integer> getAllSoLuongThang(int month) {
        String sql = "SELECT d.day, COALESCE(SUM(lm.soLuong), 0) AS sl " +
                     "FROM (" +
                     "SELECT 1 AS day UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL " +
                     "SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL " +
                     "SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL " +
                     "SELECT 10 UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL " +
                     "SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL " +
                     "SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL " +
                     "SELECT 19 UNION ALL SELECT 20 UNION ALL SELECT 21 UNION ALL " +
                     "SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL " +
                     "SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 27 UNION ALL " +
                     "SELECT 28 UNION ALL SELECT 29 UNION ALL SELECT 30 UNION ALL " +
                     "SELECT 31) AS d " +
                     "LEFT JOIN Laptop_Money lm ON DAY(lm.ngayBan) = d.day AND MONTH(lm.ngayBan) = :month " +
                     "GROUP BY d.day " +
                     "ORDER BY d.day";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("month", month);
        return namedParameterJdbcTemplate.query(sql,params, (rs, rowNum) -> rs.getInt("sl"));
    }    

    public List<Laptop> getLaptopByIds (List<Integer> laptopids){
    	String sqlLaptop = "SELECT * FROM Laptops WHERE laptop_id IN (:laptop_ids)";
        String sqlCPU = "SELECT * FROM CPU_Details WHERE laptop_id IN (:laptop_ids)";
        String sqlRam = "SELECT * FROM RAM_Storage WHERE laptop_id IN (:laptop_ids)";
        String sqlScreen = "SELECT * FROM Screen_Details WHERE laptop_id IN (:laptop_ids)";
        String sqlGraphics_audio = "SELECT * FROM Graphics_Audio WHERE laptop_id IN (:laptop_ids)";
        String sqlPorts_features = "SELECT * FROM Ports_Features WHERE laptop_id IN (:laptop_ids)";
        String sqlDimension_weight = "SELECT * FROM Dimensions_Weight WHERE laptop_id IN (:laptop_ids)";
        String sqlOther_info = "SELECT * FROM Other_Info WHERE laptop_id IN (:laptop_ids)";

        // Sử dụng MapSqlParameterSource để truyền mảng laptopIds
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("laptop_ids", laptopids);

        // Truy vấn và ánh xạ kết quả cho từng bảng
        List<Laptop> laptops = namedParameterJdbcTemplate.query(sqlLaptop, parameters, new LaptopRowMapper());
        List<CPU> cpus = namedParameterJdbcTemplate.query(sqlCPU, parameters, new CPURowMapper());
        List<Ram_storage> rams = namedParameterJdbcTemplate.query(sqlRam, parameters, new RamStorageRowMapper());
        List<Screen> screens = namedParameterJdbcTemplate.query(sqlScreen, parameters, new ScreenRowMapper());
        List<Graphics_audio> graphicsAudio = namedParameterJdbcTemplate.query(sqlGraphics_audio, parameters, new Graphics_audioRowMapper());
        List<Ports_features> portsFeatures = namedParameterJdbcTemplate.query(sqlPorts_features, parameters, new Port_featureRowMapper());
        List<Dimension_weight> dimensionWeights = namedParameterJdbcTemplate.query(sqlDimension_weight, parameters, new DimensionRowMapper());
        List<Other_info> otherInfos = namedParameterJdbcTemplate.query(sqlOther_info, parameters, new Other_infoRowMapper());
        if (laptops.isEmpty()) {
            throw new RuntimeException("Không tìm thấy laptop với ID: " + laptopids);
        }
        // Gán thông tin chi tiết vào từng laptop
        for (Laptop laptop : laptops) {
            // Gán CPU, RAM, v.v. vào laptop
        	 CPU cpu = cpus.stream().filter(c -> c.getLaptop().getLaptop_id().equals(laptop.getLaptop_id())).findFirst().orElse(null);
             Ram_storage ram = rams.stream().filter(r -> r.getLaptop().getLaptop_id().equals(laptop.getLaptop_id())).findFirst().orElse(null);
             Screen screen = screens.stream().filter(s -> s.getLaptop().getLaptop_id().equals(laptop.getLaptop_id())).findFirst().orElse(null);
             Graphics_audio graphics = graphicsAudio.stream().filter(g -> g.getLaptop().getLaptop_id().equals(laptop.getLaptop_id())).findFirst().orElse(null);
             Ports_features portFeature = portsFeatures.stream().filter(p -> p.getLaptop().getLaptop_id().equals(laptop.getLaptop_id())).findFirst().orElse(null);
             Dimension_weight dimension = dimensionWeights.stream().filter(d -> d.getLaptop().getLaptop_id().equals(laptop.getLaptop_id())).findFirst().orElse(null);
             Other_info otherInfo = otherInfos.stream().filter(o -> o.getLaptop().getLaptop_id().equals(laptop.getLaptop_id())).findFirst().orElse(null);
             laptop.setCpu(cpu);
             laptop.setRam(ram);
             laptop.setScreen(screen);
             laptop.setGraphics_audio(graphics);
             laptop.setPorts_features(portFeature);
             laptop.setDimension_weight(dimension);
             laptop.setOther_info(otherInfo);
        
        
        }

        return laptops;
    }
    
    public List<Laptop> getAllLaptops() {
        String sql = "SELECT * FROM Laptops";       
        return namedParameterJdbcTemplate.query(sql ,new LaptopRowMapper());	
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
