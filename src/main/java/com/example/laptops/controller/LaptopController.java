package com.example.laptops.controller;

import java.util.ArrayList;
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

import com.example.laptops.model.laptop.Laptop;
import com.example.laptops.service.LaptopService;
@Controller
public class LaptopController {
	 @Autowired
	  private LaptopService laptopService;

	  @GetMapping("/admin/laptop_list")
	  public String getAllLaptops(Model model) {
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
	     public String showDashboard(Model model) {
			List<String>tenHang = new ArrayList<>();
	     tenHang.add("HP");
		 tenHang.add("Asus");
		 tenHang.add("Acer");
		 tenHang.add("Lenovo");
		 tenHang.add("Dell");
		 tenHang.add("MSI");
	    
	    model.addAttribute("tenHang", tenHang);

		List<Laptop> ds = laptopService.getAllLaptops();
		int hp,asus,acer,lenovo,dell,msi;
		hp=asus=acer=lenovo=dell=msi=0;
		for (Laptop laptop : ds) {
			if(laptop.getLaptop_name().toLowerCase().contains("lenovo") ){
				lenovo++;
			}else if(laptop.getLaptop_name().toLowerCase().contains("hp") ){
				hp++;
			}else if(laptop.getLaptop_name().toLowerCase().contains("asus") ){
				asus++;
			}else if(laptop.getLaptop_name().toLowerCase().contains("acer") ){
				acer++;
			}else if(laptop.getLaptop_name().toLowerCase().contains("dell") ){
				dell++;
			}else{
				msi++;
			}
		}
		
		List<String>soluong = new ArrayList<>();
		soluong.add(String.valueOf(hp));
		soluong.add(String.valueOf(asus));
		soluong.add(String.valueOf(acer));

		soluong.add(String.valueOf(lenovo));
		soluong.add(String.valueOf(dell));
		soluong.add(String.valueOf(msi));
		model.addAttribute("soluong", soluong);
	    
			
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
	   

	    
}
