package org.squad3.library.documentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.squad3.library.documentservice.converter.MappingHelper;
import org.squad3.library.documentservice.dto.CategoryDTO;
import org.squad3.library.documentservice.exception.CategoryNotFoundException;
import org.squad3.library.documentservice.model.Category;
import org.squad3.library.documentservice.repository.CategoryRepository;
import org.squad3.library.documentservice.service.CategoryService;

import java.util.List;

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
