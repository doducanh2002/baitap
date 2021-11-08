package org.squad3.library.document.delivery.converters;

import org.squad3.library.document.Category;
import org.squad3.library.document.delivery.rest.dto.CategoryDTO;
import org.squad3.library.shared.RestDTOConverter;

import java.util.Optional;

public class CategoryRestDTOConverter implements RestDTOConverter<CategoryDTO, Category> {

        @Override
        public Category mapToEntity(CategoryDTO categoryDTO) {
                return Optional.ofNullable(categoryDTO)
                        .map(adto -> {
                                return Category.builder()
                                        .name(adto.getName())
                                        .build();
                        })
                        .orElse(null);
        }

        @Override
        public CategoryDTO mapToDTO(Category category) {
                return Optional.ofNullable(category)
                        .map(a -> {
                                 return CategoryDTO.builder()
                                        .name(a.getName())
                                        .build();
                        })
                        .orElse(null);
        }
}
