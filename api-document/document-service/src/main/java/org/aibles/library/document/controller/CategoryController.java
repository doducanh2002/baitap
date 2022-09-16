package org.aibles.library.document.controller;

import lombok.RequiredArgsConstructor;

import org.aibles.library.document.dto.CategoryDTO;
import org.aibles.library.document.service.CategoryService;
import org.aibles.library.document.utils.paging.PageReq;
import org.aibles.library.document.utils.paging.PageRes;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    final private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO addedCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") int categoryId,
                                                      @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PageRes<CategoryDTO>> getAllCategory(@RequestParam("page") int pageNumber,
                                                               @RequestParam("page_size") int pageSize) {
        Page<CategoryDTO> categoryDTOPage = categoryService.listCategory(new PageReq(pageSize).makePageable(pageNumber));
        return new ResponseEntity<>(PageRes.of(categoryDTOPage), HttpStatus.OK);
    }

}
