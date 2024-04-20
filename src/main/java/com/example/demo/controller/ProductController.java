package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter // lombok 
@Setter //lombok 


@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/images";

    @GetMapping ("/product")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/addproduct")
    public Product addProduct(@ModelAttribute Product product , @RequestParam("image") MultipartFile file) throws IOException {
    	String originalFilename = file.getOriginalFilename();
    	Path fileNameAndPath=Paths.get(uploadDirectory, originalFilename);
    	Files.write(fileNameAndPath, file.getBytes());
    	product.setProductimage(originalFilename);
    	return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
    
    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }
    
    
    @GetMapping("/searchByPrice")
    public List<Product> searchProductsByPrice(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return productService.searchProductsByPrice(minPrice, maxPrice);
    }
    
    
    
    
    
    
    
    
    
    
}