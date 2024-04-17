package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Product;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);
    
    public List<Product> searchProductsByName(String name);

    public List<Product> searchProductsByPrice(double minPrice, double maxPrice);





}