package com.example.laptops.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.example.laptops.model.laptop.Laptop;
import com.example.laptops.model.laptop.Laptop_Money;
import com.example.laptops.repository.LaptopRepository;

@Service
public class LaptopService {

	@Autowired
    private LaptopRepository laptopRepository;
	public int addLaptop(Laptop laptop) {
        
        return laptopRepository.addLaptop(laptop);
    }

    public int updateLaptop(Laptop laptop) {
        
        return laptopRepository.updateLaptop(laptop);
    }
   
    public int deleteLaptop(long id) {
        return laptopRepository.deleteLaptop(id);
    }
    public Laptop getLaptopById(Integer laptop_id) {
       return laptopRepository.getLaptopById(laptop_id);
    }
    public List<Laptop> getAllLaptops() {
        return laptopRepository.getAllLaptops();
    }
    public List<Laptop> filterLaptops(String brand) {
        if (brand.equals("Tất cả")|| brand.isEmpty()) {
            return laptopRepository.getAllLaptops(); // Trả về tất cả nếu không lọc
        }
        return laptopRepository.getAllLaptops().stream().filter(laptop -> laptop.getLaptop_name().toLowerCase().contains(brand.toLowerCase())) 
                .collect(Collectors.toList()); // Lọc theo hãng
    }
    public Laptop getDetailsLaptopById(Integer laptop_id) {
    	 
    	return laptopRepository.getDetailsLaptopById(laptop_id);
    }

	public List<Laptop> getLaptopByIds(List<Integer> laptopIds) {
		
		return laptopRepository.getLaptopByIds(laptopIds);
	}

	public List<Integer> getAllDoanhThu() {
		
		return laptopRepository.getAllDoanhThu();
	}

	public List<Laptop_Money> getAllLaptop_Moneys() {
		// TODO Auto-generated method stub
		return laptopRepository.getAllLaptop_Moneys();
	}

	public List<String> getAllThang() {
		
		return laptopRepository.getAllThang();
	}

	public List<Integer> getAllSoLuong() {
		// TODO Auto-generated method stub
		return laptopRepository.getAllSoLuong();
	}

	public List<Integer> getAllSoLuongThang(int month) {
		// TODO Auto-generated method stub
		return laptopRepository.getAllSoLuongThang(month);
	}
    
}

