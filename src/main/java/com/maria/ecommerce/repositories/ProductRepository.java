package com.maria.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maria.ecommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
