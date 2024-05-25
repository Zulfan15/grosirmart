package com.oopprojek.grosirmart.controllers;

import java.io.InputStream;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.oopprojek.grosirmart.barang.Produk;
import com.oopprojek.grosirmart.barang.ProdukDto;
import com.oopprojek.grosirmart.services.ProdukRepository;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/produk")
public class ProdukControllers {
	
	@Autowired
	private ProdukRepository repo;
	
	@GetMapping({"", "/"})
    public String showProductList(Model model) {
        List<Produk> products = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("produk", products);
        return "produk/index";
    }

	@GetMapping("/create")
	public String showCreatePage(Model model) {
	    ProdukDto produkDto = new ProdukDto();
	    model.addAttribute("produkDto", produkDto);
	    return "produk/CreateProduk";
	}
	
	@PostMapping("/create")
	public String createProduk (
			@Valid @ModelAttribute ProdukDto produkDto,
			BindingResult result
			) {
		
		if (produkDto.getGambar().isEmpty()) {
			result.addError(new FieldError("produkDto", "Gambar", "The Gambar is required"));
		}
		
		if (result.hasErrors()) {
			return "produk/CreateProduk";
		}
		
		// save image file
		MultipartFile image = produkDto.getGambar();
		Date createdAt = new Date(0);
		String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

		try {
		    String uploadDir = "public/images/";
		    Path uploadPath = Paths.get(uploadDir);
		    
		    if (!Files.exists(uploadPath)) {
		        Files.createDirectories(uploadPath);
		    }
		    
		    try (InputStream inputStream = image.getInputStream()) {
		        Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
		    }
		} catch (Exception ex) {
		    System.out.println("Exception: " + ex.getMessage());
		}
		
		Produk produk = new Produk();
		produk.setNama(produkDto.getNama());
		produk.setMerk(produkDto.getMerk());
		produk.setKategori(produkDto.getKategori());
		produk.setPrice(produkDto.getPrice());
		produk.setDeskripsi(produkDto.getDeskripsi());
		produk.setDitambahkanSaat(produkDto.getDitambahkanSaat());
		produk.setNamaFileFoto(produkDto.getNamaFileFoto());
		
		repo.save(produk);
		
		return "redirect:/produk";
	
	}
	
	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam int id) {
	    try {
	        Produk produk = repo.findById(id).get();
	        model.addAttribute("produk", produk);
	        ProdukDto produkDto = new ProdukDto();
	        produkDto.setNama(produk.getNama());
	        produkDto.setMerk(produk.getMerk());
	        produkDto.setKategori(produk.getKategori());
	        produkDto.setPrice(produk.getPrice());
	        produkDto.setDeskripsi(produk.getDeskripsi());
	        model.addAttribute("produkDto", produkDto);
	    } catch (Exception ex) {
	        System.out.println("Exception: " + ex.getMessage());
	        return "redirect:/produk";
	    }
	    return "products/EditProduk";
	}
	
	@PostMapping("/edit")
	public String updateProduk(Model model, @RequestParam int id, @ModelAttribute ProdukDto produkDto, BindingResult result) {
	    try {
	        Produk produk = repo.findById(id).get();
	        model.addAttribute("product", produk);
	        
	        if (result.hasErrors()) {
	            return "produk/EditProduk";
	        }

	        if (!produkDto.getGambar().isEmpty()) {
	            // delete old image
	            String uploadDir = "public/images/";
	            Path oldImagePath = Paths.get(uploadDir + produk.getNamaFileFoto());
	            
	            try {
	                Files.delete(oldImagePath);
	            } catch (Exception ex) {
	                System.out.println("Exception: " + ex.getMessage());
	            }
	            
	            // save new image file
	            MultipartFile image = produkDto.getGambar();
	            Date createdAt = new Date(0);
	            String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
	            
	            try (InputStream inputStream = image.getInputStream()) {
	                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
	            }
	            
	            produk.setNamaFileFoto(storageFileName);
	        }

	        produk.setNama(produkDto.getNama());
			produk.setMerk(produkDto.getMerk());
			produk.setKategori(produkDto.getKategori());
			produk.setPrice(produkDto.getPrice());
			produk.setDeskripsi(produkDto.getDeskripsi());
	    }
	    catch (Exception ex) {
	        System.out.println("Exception: " + ex.getMessage());
	    }
	    
	    return "redirect:/produk";
	}
	
	@GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        try {
            Produk produk = repo.findById(id).get();
            Path imagePath = Paths.get("public/images/" + produk.getNamaFileFoto());
            
            try {
                Files.delete(imagePath);
            } 
            catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
            
            repo.delete(produk);
        } 
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        
        return "redirect:/produk";
    }

}
