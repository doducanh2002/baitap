package org.squad3.library.document.persistance.converters;

import org.squad3.library.document.Category;
import org.squad3.library.document.persistance.entites.CategoryEntity;
import org.squad3.library.shared.RepositoryConverter;

import java.util.Optional;

public class CategoryRepositoryConverter implements RepositoryConverter<CategoryEntity, Category> {

    @Override
    public CategoryEntity mapToTable(Category categoryPersistence) {
        return Optional.ofNullable(categoryPersistence)
                .map(ap -> {
                    return CategoryEntity.builder()
                            .name(ap.getName())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public Category mapToEntity(CategoryEntity categoryTable) {
        return Optional.ofNullable(categoryTable)
                .map(at -> {
                    return Category.builder()
                            .id(at.getId())
                            .name(at.getName())
                            .build();
                })
                .orElse(null);
    }
}
