package com.example.QuanLyBanSach.controller;

import com.example.QuanLyBanSach.model.Product;
import com.example.QuanLyBanSach.service.CategoryService;
import com.example.QuanLyBanSach.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    // Create a new product
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // Check if any field is empty
        if (product.getName().isEmpty() || product.getDescription().isEmpty() || product.getAuthor().isEmpty() || product.getImage().isEmpty() || product.getCategoryId() == 0 || product.getStatus() == null){
            return new ResponseEntity("Please fill all fields", HttpStatus.BAD_REQUEST);
        }

        // Check if there is a product with the same name
        if (productService.getProductByName(product.getName()) != null) {
            return new ResponseEntity("Product already exists", HttpStatus.BAD_REQUEST);
        }

        // Check if category exists
        if (categoryService.getCategoryById(product.getCategoryId()) == null) {
            return new ResponseEntity("Category not found", HttpStatus.NOT_FOUND);
        }
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    // Update an existing product
    @PostMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Product existingProduct = productService.getProductById(product.getId());
        // Check if product exists
        if (existingProduct == null) {
            return new ResponseEntity("Product not found", HttpStatus.NOT_FOUND);
        }

        // Check if category exists
        if (categoryService.getCategoryById(product.getCategoryId()) == null) {
            return new ResponseEntity("Category not found", HttpStatus.NOT_FOUND);
        }

        existingProduct.setId(product.getId());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setAuthor(product.getAuthor());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImage(product.getImage());
        existingProduct.setStatus(product.getStatus());
        existingProduct.setCategoryId(product.getCategoryId());

        Product updatedProduct = productService.updateProduct(existingProduct);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Delete a product
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam Long id) {
        // Check if product exists
        if (productService.getProductById(id) == null) {
            return new ResponseEntity("Product not found", HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);
        return new ResponseEntity("Product deleted", HttpStatus.OK);
    }

    // Get all products
    @GetMapping("/get/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get a product by ID
    @GetMapping("/get")
    public ResponseEntity<Product> getProductById(@RequestParam Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return new ResponseEntity("Product not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Get products by category
    @GetMapping("/get/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam Long categoryId) {
        // Check if category exists
        if (categoryService.getCategoryById(categoryId) == null) {
            return new ResponseEntity("Category not found", HttpStatus.NOT_FOUND);
        }
        List<Product> products = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get products by author
    @GetMapping("/get/author")
    public ResponseEntity<List<Product>> getProductsByAuthor(@RequestParam String author) {
        List<Product> products = productService.getProductsByAuthor(author);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get a product by name
    @GetMapping("/get/name")
    public ResponseEntity<Product> getProductByName(@RequestParam String name) {
        Product product = productService.getProductByName(name);
        if (product == null) {
            return new ResponseEntity("Product not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
