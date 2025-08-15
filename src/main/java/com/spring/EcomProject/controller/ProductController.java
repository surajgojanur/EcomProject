package com.spring.EcomProject.controller;

import com.spring.EcomProject.model.Product;
import com.spring.EcomProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    

    @GetMapping("/api/product/{prodId}")
    public Product getProduct(@PathVariable int prodId){
        return productService.getProductBIyd(prodId);
    }

    // Accepts product data and image file in a multipart/form-data request
    @PostMapping("/api/product")
    public Product addProduct(
            @RequestPart("product") Product prod,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) throws IOException {
        // You can now access both the product data and the image file
        // Pass the imageFile to your service if you want to save it
        return productService.addProduct(prod, imageFile);
    }

}
