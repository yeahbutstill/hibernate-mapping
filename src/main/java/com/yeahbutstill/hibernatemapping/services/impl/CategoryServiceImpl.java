package com.yeahbutstill.hibernatemapping.services.impl;

import com.yeahbutstill.hibernatemapping.domain.Category;
import com.yeahbutstill.hibernatemapping.repositories.CategoryRepository;
import com.yeahbutstill.hibernatemapping.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.getReferenceById(categoryId);
    }
}
