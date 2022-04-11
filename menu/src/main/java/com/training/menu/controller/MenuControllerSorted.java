package com.training.menu.controller;



import com.training.menu.models.Category;
import com.training.menu.models.CreateCategoryRequest;
import com.training.menu.models.MenuException;
import com.training.menu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/api")
public class MenuControllerSorted {

    private CategoryService categoryService;

    public MenuControllerSorted(@Autowired CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<?> createNewCategory(@RequestHeader HttpHeaders headers
            , @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category category = categoryService.createNewCategory(createCategoryRequest);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category")
    public ResponseEntity<?> listAllCategories(@RequestHeader HttpHeaders headers,
                                               @RequestParam int pageSize,
                                               @RequestParam int pageNumber) {
        try {
            return ResponseEntity.ok(categoryService.listAllCategories(pageSize, pageNumber));
        }
        catch (MenuException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryId ){
        try{
            categoryService.delete(categoryId);
            return ResponseEntity.ok("Category Id deleted successfully!");
        }catch (MenuException m) {
            return ResponseEntity.ok(m.getMessage());

        }


    }

    @GetMapping("/ASC")
    public ResponseEntity<?> listAllCategoriesByASC(@RequestHeader HttpHeaders headers,
                                               @RequestParam int pageSize,
                                               @RequestParam int pageNumber) {
        try {
            return ResponseEntity.ok(categoryService.sortByAscending(pageSize, pageNumber));
        }
        catch (MenuException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("/DESC")
    public ResponseEntity<?> listAllCategoriesByDESC(@RequestHeader HttpHeaders headers,
                                                    @RequestParam int pageSize,
                                                    @RequestParam int pageNumber) {
        try {
            return ResponseEntity.ok(categoryService.sortByDescending(pageSize, pageNumber));
        }
        catch (MenuException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }
}
