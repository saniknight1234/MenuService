package com.training.menu.service;

import com.training.menu.models.Category;
import com.training.menu.models.CreateCategoryRequest;
import com.training.menu.models.MenuException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Category createNewCategory(CreateCategoryRequest createCategoryRequest);

    List<Category> listAllCategories(int pageSize, int pageNumber);
    void delete(String categoryId) throws MenuException;

    List<?> sortByAscending(int pageSize, int pageNumber);

    List<?> sortByDescending(int pageSize, int pageNumber);
}
