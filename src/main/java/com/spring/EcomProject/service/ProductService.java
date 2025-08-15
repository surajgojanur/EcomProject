package com.spring.EcomProject.service;
import com.spring.EcomProject.model.Product;
import com.spring.EcomProject.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductBIyd(int prodId) {
        return repo.findById(prodId).orElse(null);
    }

    public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            prod.setImageName(imageFile.getOriginalFilename());
            prod.setImageType(imageFile.getContentType());
            prod.setImageDate(imageFile.getBytes());
        }
        return repo.save(prod);
    }

    public Product updateProduct(int id, Product prod, MultipartFile imageFile) throws IOException {
        Product existing = getProductBIyd(id);
        if (existing == null) return null;
        // Update fields
        existing.setName(prod.getName());
        existing.setDesc(prod.getDesc());
        existing.setBrand(prod.getBrand());
        existing.setPrice(prod.getPrice());
        existing.setCategory(prod.getCategory());
        existing.setReleaseDate(prod.getReleaseDate());
        existing.setAvailable(prod.isAvailable());
        existing.setQuantity(prod.getQuantity());
        if (imageFile != null && !imageFile.isEmpty()) {
            existing.setImageName(imageFile.getOriginalFilename());
            existing.setImageType(imageFile.getContentType());
            existing.setImageDate(imageFile.getBytes());
        }
        return repo.save(existing);
    }



    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }

}
