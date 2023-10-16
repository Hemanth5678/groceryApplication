package com.grocery.service;

import java.util.List;

import com.grocery.dto.Product;
import com.grocery.exception.BadRequestException;
import com.grocery.exception.BaseException;

public interface ProductService {

    public List<Product> getAllProducts() throws BadRequestException;
    public Product updateProduct(Product product) throws BaseException;
    public Product createNewProduct(Product product) throws BadRequestException;
    public Product getProductDetailByProductId(Long productId);
    public String deleteProductByProductId(Long productId);
    public String updateProductByProductId(Long productId, Product product);
    public Product updateProductQuantity(Long productId, Integer count);
}
