package com.example.storeservice.restfullapi.repository;

import com.example.storeservice.restfullapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Product, Integer>{
    Optional<Product> findById(int id);

    List<Product> findByName(String name );

    List<Product> findByPrice(int price);

    List<Product> findByExpired(Date expired);
}
