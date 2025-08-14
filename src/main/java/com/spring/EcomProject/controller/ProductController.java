package com.spring.EcomProject.controller;

import com.spring.EcomProject.model.Product;
import com.spring.EcomProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/api")
    public String greet(){
        return "Welcome";
    }

    @GetMapping("/api/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    



}
