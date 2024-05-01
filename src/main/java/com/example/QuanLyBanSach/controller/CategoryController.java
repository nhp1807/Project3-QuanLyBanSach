package com.example.QuanLyBanSach.controller;

import com.example.QuanLyBanSach.model.Category;
import com.example.QuanLyBanSach.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Create category
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }

    // Update category
    @PostMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        Category existingCategory = categoryService.getCategoryById(category.getId());
        if (existingCategory == null) {
            return new ResponseEntity("Account not found", HttpStatus.NOT_FOUND);
        }
        existingCategory.setId(category.getId());
        existingCategory.setName(category.getName());
        Category updatedCategory = categoryService.updateCategory(category);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // Delete category
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam Long id) {
        if (categoryService.getCategoryById(id) == null) {
            return new ResponseEntity("Category not found", HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCategory(id);
        return new ResponseEntity(HttpResponse.BodyHandlers.ofString(), HttpStatus.OK);
    }

    // Get category by id
    @GetMapping("/get")
    public ResponseEntity<Category> getCategoryById(@RequestParam Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return new ResponseEntity("Category not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Get all category
    @GetMapping("/get/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
