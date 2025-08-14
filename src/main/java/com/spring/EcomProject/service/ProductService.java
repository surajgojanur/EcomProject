package com.spring.EcomProject.service;
import com.spring.EcomProject.model.Product;
import com.spring.EcomProject.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public Product getProductBIyd;
    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductBIyd(int prodId) {
        return repo.findById(prodId).orElse(new Product());
    }
}
