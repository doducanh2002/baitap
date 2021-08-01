package com.example.storeservice.restfullapi.service.iml;

import com.example.storeservice.restfullapi.dto.ProductDto;
import com.example.storeservice.restfullapi.model.Product;

import java.util.Date;
import java.util.List;

public interface StoreService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(int productId, ProductDto productDto);

    Product deleteProduct(int productId);

    List<Product> listProducts();

    ProductDto getProductById(int productId);

    List<ProductDto> getProductByName(String productName);

    List<ProductDto> getProductByPrice(int  productPrice);

    List<ProductDto> getProductByExpired(Date expired);
}


