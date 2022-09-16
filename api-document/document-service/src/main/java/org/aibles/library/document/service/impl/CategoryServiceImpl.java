package org.aibles.library.document.service.impl;

import lombok.RequiredArgsConstructor;
import org.aibles.library.document.converter.MappingHelper;
import org.aibles.library.document.dto.CategoryDTO;
import org.aibles.library.document.exception.CategoryNotFoundException;
import org.aibles.library.document.model.Category;
import org.aibles.library.document.repository.CategoryRepository;
import org.aibles.library.document.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    final private CategoryRepository categoryRepository;
    final private MappingHelper mappingHelper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return mappingHelper.map(categoryRepository.save(mappingHelper.map(categoryDTO, Category.class)), CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(int categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                            .map( c -> {
                                c.setName(categoryDTO.getName());
                                return c;
                            }).orElseThrow(CategoryNotFoundException::new);
        return mappingHelper.map(categoryRepository.save(category), CategoryDTO.class);

    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }

    @Override
    public Page<CategoryDTO> listCategory(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return mappingHelper.mapPage(categoryPage, CategoryDTO.class);
    }
}
