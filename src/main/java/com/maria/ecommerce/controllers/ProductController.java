package com.maria.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maria.ecommerce.dto.ProductDTO;
import com.maria.ecommerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping(value = "/{id}")
	public ProductDTO findById(@PathVariable Long id) {
		return service.findById(id);
		
	}
	
	@GetMapping
	public Page<ProductDTO> findAll(Pageable pageable) {
		return service.findAll(pageable);
		
	}
	
	@PostMapping
	public ProductDTO insert(@RequestBody ProductDTO productDto) {
		return service.insert(productDto);
	}
	
	
}
