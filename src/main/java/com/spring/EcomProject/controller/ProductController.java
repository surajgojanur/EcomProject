package com.spring.EcomProject.controller;

import com.spring.EcomProject.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {

    @RequestMapping("/api")
    public String greet(){
        return "Welcome";
    }
    



}
