package com.grocery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grocery.dto.Product;
import com.grocery.exception.BadRequestException;
import com.grocery.exception.BaseException;
import com.grocery.repository.ProductRepository;
import com.grocery.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts(){
         return productRepository.findAll();
    }

	@Override
	public Product updateProduct(Product product) throws BaseException {
		// TODO Auto-generated method stub
		if(productRepository.getById(product.getId()) == null)
			throw new BaseException("Product not found");
		productRepository.save(product);
		return product;
	}

	@Override
	public Product createNewProduct(Product product) throws BadRequestException {
		// TODO Auto-generated method stub
		if(productRepository.findById(product.getId()).isPresent())
			throw new BadRequestException("Product already exists");
		return productRepository.save(product);
	}

	@Override
	public Product getProductDetailByProductId(Long productId) {
		// TODO Auto-generated method stub
		return productRepository.getById(productId);
	}

	@Override
	public String deleteProductByProductId(Long productId) {
		// TODO Auto-generated method stub
		productRepository.deleteById(productId);
		return "Success";
	}

	@Override
	public String updateProductByProductId(Long productId, Product product) {
		// TODO Auto-generated method stub
		product.setId(productId);
		productRepository.save(product);
		return "Success";
	}

	@Override
	public Product updateProductQuantity(Long productId, Integer noToAddOrSubtract) {
		// TODO Auto-generated method stub
		Product product = productRepository.getById(productId);
		product.setQuantity(product.getQuantity() + noToAddOrSubtract);
		productRepository.save(product);
		return product;
	}

}
