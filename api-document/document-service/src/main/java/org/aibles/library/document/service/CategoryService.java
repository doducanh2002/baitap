package org.aibles.library.document.service;


import org.aibles.library.document.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    public CategoryDTO createCategory(CategoryDTO categoryDTO);
    public CategoryDTO updateCategory(int categoryId, CategoryDTO categoryDTO);
    public void deleteCategory(int categoryId);
    Page<CategoryDTO> listCategory(Pageable pageable);

}
