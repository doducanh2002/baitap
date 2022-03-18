package org.squad3.library.documentservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.squad3.library.documentservice.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    public CategoryDTO createCategory(CategoryDTO categoryDTO);
    public CategoryDTO updateCategory(int categoryId, CategoryDTO categoryDTO);
    public void deleteCategory(int categoryId);
    Page<CategoryDTO> listCategory(Pageable pageable);

}
