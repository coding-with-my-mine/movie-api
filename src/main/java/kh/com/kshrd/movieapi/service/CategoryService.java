package kh.com.kshrd.movieapi.service;

import kh.com.kshrd.movieapi.dto.request.CategoryRequest;
import kh.com.kshrd.movieapi.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(CategoryRequest request);

    List<Category> getAll(Integer page, Integer size);

    Category getById(Long categoryId);

    Category update(Long categoryId, CategoryRequest request);

    void delete(Long categoryId);

    void getByName(String categoryName);
}
