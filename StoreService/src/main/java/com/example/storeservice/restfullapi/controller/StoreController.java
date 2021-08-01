package com.example.storeservice.restfullapi.controller;

import com.example.storeservice.restfullapi.dto.ProductDto;
import com.example.storeservice.restfullapi.model.Product;
import com.example.storeservice.restfullapi.service.iml.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {


    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createTeacher(@RequestBody ProductDto productDto) {
        final ProductDto createdProduct = storeService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("product_id") int productId,
                                                 @RequestBody ProductDto productDto) {
        final ProductDto updatedProduct = storeService.updateProduct(productId, productDto);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("product_id") int productId) {
       storeService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<Product>> listProducts() {
        final List<Product> products = storeService.listProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/{teacher_id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("product_id") int productId) {
        final ProductDto getProduct = storeService.getProductById(productId);
        return new ResponseEntity<>(getProduct, HttpStatus.OK);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<ProductDto>> getProductByName(@RequestParam("name") String name){
        final List<ProductDto> productDtos = storeService.getProductByName(name);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping(params = {"price"})
    public ResponseEntity<List<ProductDto>> getProductByPrice(@RequestParam("price") int price){
        final List<ProductDto> productDtos = storeService.getProductByPrice(price);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping(params = {"expired"})
    public ResponseEntity<List<ProductDto>> getProductByExpired(@RequestParam("expired")Date expired){
        final List<ProductDto> productDtos = storeService.getProductByExpired(expired);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}

