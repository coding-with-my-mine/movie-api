package kh.com.kshrd.movieapi.service.impl;

import kh.com.kshrd.movieapi.dto.request.CategoryRequest;
import kh.com.kshrd.movieapi.exception.ConflictException;
import kh.com.kshrd.movieapi.exception.NotFoundException;
import kh.com.kshrd.movieapi.model.Category;
import kh.com.kshrd.movieapi.repository.CategoryRepository;
import kh.com.kshrd.movieapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(CategoryRequest request) {
        getByName(request.getName());
        return categoryRepository.create(request);
    }

    @Override
    public List<Category> getAll(Integer page, Integer size) {
        page = (page - 1) * size;
        return categoryRepository.getAll(page, size);
    }

    @Override
    public Category getById(Long categoryId) {
        Category category = categoryRepository.getById(categoryId);
        if (category == null) {
            throw new NotFoundException("Category not found");
        }
        return category;
    }

    @Override
    public Category update(Long categoryId, CategoryRequest request) {
        getById(categoryId);
        getByName(request.getName());
        return categoryRepository.update(categoryId, request);
    }

    @Override
    public void delete(Long categoryId) {
        getById(categoryId);
        categoryRepository.delete(categoryId);
    }

    @Override
    public void getByName(String categoryName) {
        Category category = categoryRepository.getByName(categoryName);
        if (category != null) {
            throw new ConflictException("Category already exists");
        }
    }
}
