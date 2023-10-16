package com.grocery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocery.dto.Product;
import com.grocery.exception.BadRequestException;
import com.grocery.repository.OrderRepository;
import com.grocery.repository.ProductRepository;
import com.grocery.service.OrderService;
import com.grocery.service.ProductService;

@RestController
@RequestMapping("/api/v1")
public class GroceryController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductRepository productRepository;

	@PostMapping("/admin/product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) throws BadRequestException {

		Product result = productService.createNewProduct(product);
		return ResponseEntity.status(201).body(result); // 201:new record created
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProductByid(@PathVariable("id") Long id) {

		Product product = productService.getProductDetailByProductId(id);
		Map<String, String> map = new HashMap<>();
		map.put("message : ", "product record not found");
		if (product.equals(null))
			return ResponseEntity.status(404).body(map);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/admin/product/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){

		String result = productService.updateProductByProductId(id, product);
		if (result.equalsIgnoreCase("success"))
			return ResponseEntity.status(200).body(result);
		else {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "Sorry,product with " + id + " not found");
			return ResponseEntity.status(403).body(map);
		}
	}

	@GetMapping("/product")
	public ResponseEntity<?> getAllproductDetails() throws BadRequestException {

		Optional<List<Product>> optional = Optional.ofNullable(productService.getAllProducts());

		if (optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "product record not found");
			return ResponseEntity.status(200).body(map);
		}
		return ResponseEntity.ok(optional.get());
	}

	@DeleteMapping("/admin/product/{id}")
	public ResponseEntity<?> deleteproductByID(@PathVariable("id") Long id){

		String result = productService.deleteProductByProductId(id);
		Map<String, String> map = new HashMap<>();
		if (result.equalsIgnoreCase("Success")) {
			map.put("message : ", "product deleted successfully");
			return ResponseEntity.status(200).body(map);
		} else {

			map.put("message : ", "Sorry,product with " + id + " not found");
			return ResponseEntity.status(403).body(map);
		}
	}
	
	@PutMapping("/admin/product/{id}/inventory")
	public ResponseEntity<?> productInventory(@PathVariable("id") Long id,@RequestParam Integer count) {
		System.out.println(count);
		return ResponseEntity.status(200).body(productService.updateProductQuantity(id, count));
	}

}
