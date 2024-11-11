package com.maria.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.maria.ecommerce.dto.ProductDTO;
import com.maria.ecommerce.entities.Product;
import com.maria.ecommerce.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> result = repo.findById(id);
		Product product = result.get();
		ProductDTO dto = new ProductDTO(product);
		return dto;
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page <Product> result = repo.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO insert( @RequestBody ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repo.save(entity);
		return new ProductDTO(entity);
	}
	
	
	@Transactional
	public ProductDTO update(Long id , @RequestBody ProductDTO dto) {
		Product entity = repo.getReferenceById(id);
		copyDtoToEntity(dto, entity);
		entity = repo.save(entity);
		return new ProductDTO(entity);
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		
	}
	
	
	
	
}
