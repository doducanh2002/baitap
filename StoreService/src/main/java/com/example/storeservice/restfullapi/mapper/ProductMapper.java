package com.example.storeservice.restfullapi.mapper;

import com.example.storeservice.restfullapi.dto.ProductDto;
import com.example.storeservice.restfullapi.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto convertToDto(Product product);

    Product convertToEntity(ProductDto productDto);
}
