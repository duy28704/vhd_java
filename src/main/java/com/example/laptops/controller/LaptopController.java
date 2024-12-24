package com.example.laptops.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.laptops.model.laptop.Laptop;
import com.example.laptops.model.laptop.Laptop_Money;
import com.example.laptops.service.LaptopService;
@Controller
public class LaptopController {
	 @Autowired
	  private LaptopService laptopService;

	  @GetMapping("/admin/laptop_list")
	  public String getAllLaptops( Model model) {
	      List<Laptop> laptops = laptopService.getAllLaptops();
	      model.addAttribute("laptops", laptops);
	      
	      return "admin/laptop_list";
	  }
	  @GetMapping("/details/{laptop_id}")
	  public String getDetails_Product(@PathVariable(required = false) Integer laptop_id ,Model model) {
		  Laptop laptop = laptopService.getDetailsLaptopById(laptop_id);
	      model.addAttribute("laptop", laptop);
	      return "admin/detail_products";
	  }
	  @GetMapping({"/admin/addandupdate", "/update/{laptop_id}"})
	  public String addOrUpdateLaptopPage(@PathVariable(required = false) Integer laptop_id, Model model) {
		  System.out.println("Laptop ID: " + laptop_id); // Debugging
	      if (laptop_id == null) {
	    	  model.addAttribute("laptop", new Laptop());
	      } else {
	    	  Laptop laptop = laptopService.getLaptopById(laptop_id);
	          model.addAttribute("laptop", laptop);
	          
	      }
	      return "admin/addandupdate";
	  }
	  @GetMapping("/admin/dashboard")
	  public String showDashboard(Model model) throws SQLException {
		  // Danh sách các thương hiệu laptop
		List<Integer> doanhThu = laptopService.getAllDoanhThu();
		model.addAttribute("doanhThu", doanhThu);
		  // Lấy các thông tin về Laptop_Money
		  List<Laptop_Money> laptopMoney = laptopService.getAllLaptop_Moneys();
		  model.addAttribute("laptopMoney", laptopMoney);
		List<String> allThang = laptopService.getAllThang();
		model.addAttribute("allThang", allThang);
		List<Integer> allSoLuong = laptopService.getAllSoLuong();
		model.addAttribute("allSoLuong", allSoLuong);
		List<Integer> slT1 = laptopService.getAllSoLuongThang(1);
		model.addAttribute("slT1", slT1);
		List<Integer> slT2 = laptopService.getAllSoLuongThang(2);
		model.addAttribute("slT2", slT2);
		List<Integer> slT3 = laptopService.getAllSoLuongThang(3);
		model.addAttribute("slT3", slT3);
		List<Integer> slT4 = laptopService.getAllSoLuongThang(4);
		model.addAttribute("slT4", slT4);
		List<Integer> slT5 = laptopService.getAllSoLuongThang(5);
		model.addAttribute("slT5", slT5);
		List<Integer> slT6 = laptopService.getAllSoLuongThang(6);
		model.addAttribute("slT6", slT6);
		List<Integer> slT7 = laptopService.getAllSoLuongThang(7);
		model.addAttribute("slT7", slT7);
		List<Integer> slT8 = laptopService.getAllSoLuongThang(8);
		model.addAttribute("slT8", slT8);
		List<Integer> slT9 = laptopService.getAllSoLuongThang(9);
		model.addAttribute("slT9", slT9);
		List<Integer> slT10 = laptopService.getAllSoLuongThang(10);
		model.addAttribute("slT10", slT10);
		List<Integer> slT11 = laptopService.getAllSoLuongThang(11);
		model.addAttribute("slT11", slT11);
		List<Integer> slT12 = laptopService.getAllSoLuongThang(12);
		model.addAttribute("slT12", slT12);
		  // Trả về view cho dashboard
		  return "admin/dashboard";
	  }
	        
	  @PostMapping("/admin/addandupdate")
	  public String saveNewLaptop(@ModelAttribute Laptop laptop) {
	      if (laptop.getLaptop_id() == null) {
	          laptopService.addLaptop(laptop);
	      }
	      return "redirect:/admin/laptop-list";
	  }

	  @PutMapping("/admin/update/{laptop_id}")
	  public String updateLaptop(@PathVariable Integer laptop_id, @ModelAttribute Laptop laptop) {
	      laptopService.updateLaptop(laptop);
	      return "redirect:/admin/laptop-list";
	  }

	    @DeleteMapping("/delete/{laptop_id}")
	    public String deleteLaptop(@PathVariable Integer laptop_id) {
	        laptopService.deleteLaptop(laptop_id);
	        return "redirect:/admin/laptop_list";
	    }
	 @GetMapping("/shared/login")
	    public String showLoginPage() {
	        return"shared/login"; 
	    }
	 @GetMapping("/shared/register")
	    public String showResigterPage() {
	        return"shared/register"; 
	    }
	 @GetMapping("/shared/forgotpassword")
	    public String showForgotPage() {
	        return"shared/forgotpassword"; 
	    }
	 @GetMapping("/User/home")
	   	public String HomePage(Model model) {
		 	List<Laptop> laptops = laptopService.getAllLaptops();
		 	model.addAttribute("laptops", laptops);
	        return"User/home"; 
	    }	
	 @GetMapping("/compare")
	 public String compareLaptops(@RequestParam List<Integer> laptopIds, Model model) {
	     List<Laptop> laptops = laptopService.getLaptopByIds(laptopIds);
	     System.out.println("Laptop ID: " + laptopIds); // Debugging
	     model.addAttribute("laptops", laptops);
	     return "User/compare";
	 }
	 @GetMapping("/detailss/{laptop_id}")
	  public String getDetailss_Product(@PathVariable(required = false) Integer laptop_id ,Model model) {
		  Laptop laptop = laptopService.getDetailsLaptopById(laptop_id);
	      model.addAttribute("laptop", laptop);
	      return "User/detailproducts";
	  }
	    
}
