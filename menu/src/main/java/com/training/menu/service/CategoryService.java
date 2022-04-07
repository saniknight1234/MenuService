package com.training.menu.service;

import com.training.menu.models.Category;
import com.training.menu.models.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    Category createNewCategory(CreateCategoryRequest createCategoryRequest);

    List<Category> listAllCategories();
}
