package com.example.laptops.controller;


import java.util.List;

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
	    public ResponseEntity<List<Laptop>> getCompareList(@RequestBody List<Integer> laptopIds) {
	        List<Laptop> Comparelaptops = laptopService.getLaptopByIds(laptopIds);
	        return ResponseEntity.ok(Comparelaptops);
	    }
	}
