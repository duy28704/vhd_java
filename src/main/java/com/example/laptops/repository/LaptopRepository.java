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
        String sql = "INSERT INTO Laptops (laptop_name, laptop_price, image_url) VALUES (:laptop_name, :laptop_price, :image_url)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("laptop_name", laptop.getLaptop_name());
        params.addValue("laptop_price", laptop.getLaptop_price());
        params.addValue("image_url", laptop.getImageURL());
        int rowsAffected = namedParameterJdbcTemplate.update(sql, params);
        
        if (rowsAffected > 0) {
            // Lấy laptop_id vừa được thêm
            String sqlGetLaptopId = "SELECT MAX(laptop_id) FROM Laptops";
            Integer laptopId = namedParameterJdbcTemplate.queryForObject(sqlGetLaptopId, new MapSqlParameterSource(), Integer.class);
            
            // Thêm thông tin vào các bảng chi tiết liên quan
            addCpuDetails(laptopId, laptop.getCpu());
            addRamStorageDetails(laptopId, laptop.getRam());
            addScreenDetails(laptopId, laptop.getScreen());
            addGraphicsAudioDetails(laptopId, laptop.getGraphics_audio());
            addPortFeaturesDetails(laptopId, laptop.getPorts_features());
            addDimensionDetails(laptopId, laptop.getDimension_weight());
            addOtherInfoDetails(laptopId, laptop.getOther_info());
        }
        return rowsAffected;
    }

	private int addRamStorageDetails(int laptopId, Ram_storage ramStorage) {
	    String sql = "INSERT INTO RAM_Storage (laptop_id, ram, ram_type, ram_speed, max_ram, storage) " +
	                 "VALUES (:laptop_id, :ram, :ram_type, :ram_speed, :max_ram, :storage)";
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("laptop_id", laptopId);
	    params.addValue("ram", ramStorage.getRam_min());  // Gán ram_min vào cột ram
	    params.addValue("ram_type", ramStorage.getRam_type());
	    params.addValue("ram_speed", ramStorage.getRam_speed());
	    params.addValue("max_ram", ramStorage.getMax_ram());  // Gán max_ram vào cột max_ram
	    params.addValue("storage", ramStorage.getRam_storage());  // Gán ram_storage vào cột storage
	    
	    return namedParameterJdbcTemplate.update(sql, params);
	}

	private int addOtherInfoDetails(int laptopId, Other_info otherInfo) {
	    String sql = "INSERT INTO Other_info (laptop_id, battery, charger_power, operating_system, release_year) " +
	                 "VALUES (:laptop_id, :battery, :charger_power, :operating_system, :release_year)";
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("laptop_id", laptopId);
	    params.addValue("battery", otherInfo.getBatery_info());  // Gán battery_info vào cột battery
	    params.addValue("charger_power", otherInfo.getCharger_power());
	    params.addValue("operating_system", otherInfo.getOperating_system());
	    params.addValue("release_year", otherInfo.getRelease_date());  // Gán release_date vào cột release_year
	    
	    return namedParameterJdbcTemplate.update(sql, params);
	}

	private int addDimensionDetails(int laptopId, Dimension_weight dimension) {
	    String sql = "INSERT INTO Dimension_weight (laptop_id, dimensions, weightt, material) " +
	                 "VALUES (:laptop_id, :dimensions, :weightt, :material)";
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("laptop_id", laptopId);
	    params.addValue("dimensions", dimension.getDimension());
	    params.addValue("weightt", dimension.getWeight());
	    params.addValue("material", dimension.getMaterial());
	    
	    return namedParameterJdbcTemplate.update(sql, params);
	}

	private int addPortFeaturesDetails(int laptopId, Ports_features portFeatures) {
	    String sql = "INSERT INTO Ports_features (laptop_id, portss, wireless, webcam, extra_features, keyboard_backlight) " +
	                 "VALUES (:laptop_id, :portss, :wireless, :webcam, :extra_features, :keyboard_backlight)";
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("laptop_id", laptopId);
	    params.addValue("portss", portFeatures.getPort_type());
	    params.addValue("wireless", portFeatures.getWireless_connection());
	    params.addValue("webcam", portFeatures.getWebcam());
	    params.addValue("extra_features", portFeatures.getExtra_features());
	    params.addValue("keyboard_backlight", portFeatures.getKeyboard_blacklight());
	    
	    return namedParameterJdbcTemplate.update(sql, params);
	}

	private int addGraphicsAudioDetails(int laptopId, Graphics_audio graphicsAudio) {
	    String sql = "INSERT INTO Graphics_Audio (laptop_id, graphics_card, audio_technology) " +
	                 "VALUES (:laptop_id, :graphics_card, :audio_technology)";
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("laptop_id", laptopId);
	    params.addValue("graphics_card", graphicsAudio.getGraphics_card());
	    params.addValue("audio_technology", graphicsAudio.getAudio_technology());
	    
	    return namedParameterJdbcTemplate.update(sql, params);
	}

	private int addScreenDetails(int laptopId, Screen screen) {
	    String sql = "INSERT INTO Screen (laptop_id, screen_size, resolution, refresh_rate, color_coverage, screen_technology) " +
	                 "VALUES (:laptop_id, :screen_size, :resolution, :refresh_rate, :color_coverage, :screen_technology)";
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("laptop_id", laptopId);
	    params.addValue("screen_size", screen.getScreen_size());
	    params.addValue("resolution", screen.getResolution());
	    params.addValue("refresh_rate", screen.getRefresh_rate());
	    params.addValue("color_coverage", screen.getColor_coverage());
	    params.addValue("screen_technology", screen.getScreen_technology());
	    
	    return namedParameterJdbcTemplate.update(sql, params);
	}

	private int addCpuDetails(int laptopId, CPU cpu) {
	    String sql = "INSERT INTO CPU_Details (laptop_id, cpu_technology, cpu_cores, cpu_threads, cpu_base_clock, cpu_max_clock, cpu_cache) " +
	                 "VALUES (:laptop_id, :cpu_technology, :cpu_cores, :cpu_threads, :cpu_base_clock, :cpu_max_clock, :cpu_cache)";
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("laptop_id", laptopId);
	    params.addValue("cpu_technology", cpu.getCpu_technology());
	    params.addValue("cpu_cores", cpu.getNum_cores());
	    params.addValue("cpu_threads", cpu.getNum_threads());
	    params.addValue("cpu_base_clock", cpu.getCpu_speed());
	    params.addValue("cpu_max_clock", cpu.getCpu_maxspeed());
	    params.addValue("cpu_cache", cpu.getCache_size());
	    
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
