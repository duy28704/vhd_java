package com.example.laptops.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.laptops.model.laptop.Laptop;
import com.example.laptops.service.LaptopService;

@RestController
@RequestMapping("/api")
public class LaptopAPIController {

	 @Autowired
	    private LaptopService laptopService;
	 	// API lấy tất cả laptop
	    @GetMapping("/laptops")
	    public ResponseEntity<List<Laptop>> getAllLaptops() {
	        List<Laptop> laptops = laptopService.getAllLaptops();
	        return ResponseEntity.ok(laptops);  // Trả về JSON cho API
	    }
	    // API lấy laptop theo id
	    @GetMapping("/laptops/{laptop_id}")
	    public ResponseEntity<Laptop> getLaptopDetails(@PathVariable Integer laptop_id) {
	        Laptop laptop = laptopService.getDetailsLaptopById(laptop_id);
	        if (laptop != null) {
	            return ResponseEntity.ok(laptop);  // Trả về JSON cho API
	        } else {
	            return ResponseEntity.notFound().build();  // Trả về 404 nếu không tìm thấy laptop
	        }
	    }
	    //API lấy danh sách laptop theo bộ lọc
	    @GetMapping("/laptops/filters")
	    public ResponseEntity<List<Laptop>> filterLaptops(String brand) {
	        List<Laptop> filteredLaptops = laptopService.filterLaptops(brand);
	        return ResponseEntity.ok(filteredLaptops);
	    }
	    @PostMapping("/compare")
	    public ResponseEntity<Map<String, List<Object>>> getCompareList(@RequestBody List<Integer> laptopIds) {
	        List<Laptop> Comparelaptops = laptopService.getLaptopByIds(laptopIds);
	        Map<String, List<Object>> comparisonData = new LinkedHashMap<>();

	        // Thêm tiêu đề laptop
	        comparisonData.put("Laptop Name", Comparelaptops.stream().map(Laptop::getLaptop_name).collect(Collectors.toList()));
	        comparisonData.put("Price", Comparelaptops.stream().map(Laptop::getLaptop_price).collect(Collectors.toList()));
	        comparisonData.put("CPU", Comparelaptops.stream().map(laptop -> laptop.getCpu().getCpu_technology()).collect(Collectors.toList()));
	        comparisonData.put("CPU Cores", Comparelaptops.stream().map(laptop -> laptop.getCpu().getNum_cores()).collect(Collectors.toList()));
	        comparisonData.put("RAM", Comparelaptops.stream().map(laptop -> laptop.getRam().getRam_min()).collect(Collectors.toList()));
	        comparisonData.put("Storage", Comparelaptops.stream().map(laptop -> laptop.getRam().getRam_storage()).collect(Collectors.toList()));
	        comparisonData.put("Screen Size", Comparelaptops.stream().map(laptop -> laptop.getScreen().getScreen_size()).collect(Collectors.toList()));
	        comparisonData.put("Resolution", Comparelaptops.stream().map(laptop -> laptop.getScreen().getResolution()).collect(Collectors.toList()));
	        comparisonData.put("Graphics Card", Comparelaptops.stream().map(laptop -> laptop.getGraphics_audio().getGraphics_card()).collect(Collectors.toList()));
	        comparisonData.put("Weight", Comparelaptops.stream().map(laptop -> laptop.getDimension_weight().getWeight()).collect(Collectors.toList()));
	        comparisonData.put("Battery", Comparelaptops.stream().map(laptop -> laptop.getOther_info().getBatery_info()).collect(Collectors.toList()));

	        return ResponseEntity.ok(comparisonData);
	    }
	}
