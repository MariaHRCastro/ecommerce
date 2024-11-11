package com.maria.ecommerce.services;

import java.util.Optional;

import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.maria.ecommerce.dto.ProductDTO;
import com.maria.ecommerce.entities.Product;
import com.maria.ecommerce.repositories.ProductRepository;
import com.maria.ecommerce.services.exceptions.DatabaseException;
import com.maria.ecommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		ProductDTO dto = new ProductDTO(product);
		return dto;
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repo.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}

	@Transactional
	public ProductDTO insert(@RequestBody ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repo.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id , ProductDTO dto) {
		
		try {
			Product entity = repo.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repo.save(entity);
			return new ProductDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado.");
		}

	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repo.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado.");
		}
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial!");
		}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());

	}

}
