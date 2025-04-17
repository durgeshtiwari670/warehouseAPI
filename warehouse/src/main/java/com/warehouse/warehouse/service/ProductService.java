package com.warehouse.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.warehouse.warehouse.exception.FailedToUpdateProductException;
import com.warehouse.warehouse.exception.ProductAlreadyExistsException;
import com.warehouse.warehouse.exception.ProductNotFoundException;
import com.warehouse.warehouse.model.Product;
import com.warehouse.warehouse.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new ProductAlreadyExistsException("Product with SKU " + product.getSku() + " already exists");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product product) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct.equals(null)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        try {
            existingProduct.setSku(product.getSku());
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCurrency(product.getCurrency());
            return productRepository.save(existingProduct);
        } catch (Exception e) {
            // TODO: handle exception
            throw new FailedToUpdateProductException("Failed to update product");
        }

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public boolean deleteProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> getproductsByVendorName(String vendor) {
        return productRepository.findByVendor(vendor);
    }
}
