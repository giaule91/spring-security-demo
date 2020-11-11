package com.tina.demo.controller;

import com.tina.demo.vo.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @GetMapping("/get-product")
    public Product getProduct() {
        return new Product("milk", "milk milk");
    }

    @PutMapping("/addProduct")
    public Product addNewProd(@RequestBody Product newProd){
        return new Product(newProd.getName(), newProd.getDescription());
    }

    @GetMapping("/product-demo")
    public Product demoReqParam(@RequestParam(required = false) String name) {
        return new Product(name, "milk milk");
    }

    @GetMapping("/product-path/{name}")
    public Product demoPathVar(@PathVariable String name) {
        return new Product(name, "milk milk");
    }
}
