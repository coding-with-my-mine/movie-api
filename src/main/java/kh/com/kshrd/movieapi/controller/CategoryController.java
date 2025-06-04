package kh.com.kshrd.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.CategoryRequest;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.model.Category;
import kh.com.kshrd.movieapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create a new category")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<Category>> create(@RequestBody @Valid CategoryRequest request) {
        Category created = categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<Category>builder()
                        .message("Category created successfully")
                        .payload(created)
                        .status(HttpStatus.CREATED)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all categories with pagination")
    public ResponseEntity<APIResponse<List<Category>>> getAll(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        List<Category> categories = categoryService.getAll(page, size);
        return ResponseEntity.ok(
                APIResponse.<List<Category>>builder()
                        .message("Categories retrieved successfully")
                        .payload(categories)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping("/{category-id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<APIResponse<Category>> getById(@PathVariable("category-id") Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return ResponseEntity.ok(
                APIResponse.<Category>builder()
                        .message("Category retrieved successfully")
                        .payload(category)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PutMapping("/{category-id}")
    @Operation(summary = "Update a category by ID")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<Category>> update(
            @PathVariable("category-id") Long categoryId,
            @RequestBody @Valid CategoryRequest request) {
        Category updated = categoryService.update(categoryId, request);
        return ResponseEntity.ok(
                APIResponse.<Category>builder()
                        .message("Category updated successfully")
                        .payload(updated)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @DeleteMapping("/{category-id}")
    @Operation(summary = "Delete a category by ID")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable("category-id") Long categoryId) {
        categoryService.delete(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(
                APIResponse.<Void>builder()
                        .message("Category deleted successfully")
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }
}
