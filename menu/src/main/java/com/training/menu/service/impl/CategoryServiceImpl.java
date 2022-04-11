package com.training.menu.service.impl;

import com.training.menu.dao.CategoryDAO;
import com.training.menu.dto.CategoryEntity;
import com.training.menu.models.Category;
import com.training.menu.models.CreateCategoryRequest;
import com.training.menu.models.MenuException;
import com.training.menu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(@Autowired CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public Category createNewCategory(CreateCategoryRequest createCategoryRequest) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .categoryName(createCategoryRequest.getCategoryName())
                .categoryImageURL(createCategoryRequest.getCategoryImageURL())
                .build();

        categoryDAO.save(categoryEntity);

        return Category.builder()
                .categoryName(categoryEntity.getCategoryName())
                .categoryImageURL(categoryEntity.getCategoryImageURL())
                .categoryId(categoryEntity.getCategoryId())
                .build();
    }

    @Override
    public List<Category> listAllCategories(int pageSize, int pageNumber) throws MenuException {
        if(pageSize <= 0){
            throw new MenuException("Page size can not be less than or equal to zero", HttpStatus.BAD_REQUEST);
        }
        if(pageNumber <= 0){
            throw new MenuException("Page Number can not be less than or equal to zero", HttpStatus.BAD_REQUEST);
        }

            Page<CategoryEntity> categoryDTOS = categoryDAO.findAll(PageRequest.of(pageNumber,pageSize ));
            List<Category> categories = new ArrayList<>();
            for (CategoryEntity categoryDTO : categoryDTOS.getContent()) {
                categories.add(Category.builder()
                        .categoryName(categoryDTO.getCategoryName())
                        .categoryImageURL(categoryDTO.getCategoryImageURL())
                        .categoryId(categoryDTO.getCategoryId())
                        .build());
            }

        return categories;
    }

    @Override
    public void delete(String categoryId) throws MenuException {

        CategoryEntity categoryEntity= categoryDAO.findByCategoryId(categoryId);

        if(categoryEntity != null) {
            categoryDAO.delete(categoryDAO.findByCategoryId(categoryId));
           // categoryDAO.delete(categoryDAO.findByCategoryId(categoryId)); Abdul edited..
            return;
        } throw new MenuException ("category Id is not found", HttpStatus.BAD_REQUEST);

//		categoryDAO.deleteByCategoryId(categoryId);

    }

    @Override
    public List<?> sortByAscending(int pageSize, int pageNumber) {
        if(pageSize <= 0){
            throw new MenuException("Page size can not be less than or equal to zero", HttpStatus.BAD_REQUEST);
        }
        if(pageNumber <= 0){
            throw new MenuException("Page Number can not be less than or equal to zero", HttpStatus.BAD_REQUEST);
        }

        PageRequest sortByCategoryName = PageRequest.of(pageNumber,pageSize , Sort.by("categoryName").ascending());

        Page<CategoryEntity> categoryDTOS = categoryDAO.findAll(sortByCategoryName);
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryDTO : categoryDTOS.getContent()) {
            categories.add(Category.builder()
                    .categoryName(categoryDTO.getCategoryName())
                    .categoryImageURL(categoryDTO.getCategoryImageURL())
                    .categoryId(categoryDTO.getCategoryId())
                    .build());
        }

        return categories;
    }

    @Override
    public List<?> sortByDescending(int pageSize, int pageNumber) {
        if(pageSize <= 0){
            throw new MenuException("Page size can not be less than or equal to zero", HttpStatus.BAD_REQUEST);
        }
        if(pageNumber <= 0){
            throw new MenuException("Page Number can not be less than or equal to zero", HttpStatus.BAD_REQUEST);
        }

        PageRequest sortByDescending = PageRequest.of(pageNumber,pageSize , Sort.by("categoryName").descending());

        Page<CategoryEntity> categoryDTOS = categoryDAO.findAll(sortByDescending);
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryDTO : categoryDTOS.getContent()) {
            categories.add(Category.builder()
                    .categoryName(categoryDTO.getCategoryName())
                    .categoryImageURL(categoryDTO.getCategoryImageURL())
                    .categoryId(categoryDTO.getCategoryId())
                    .build());
        }

        return categories;
    }
    }

