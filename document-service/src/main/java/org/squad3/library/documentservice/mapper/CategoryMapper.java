package org.squad3.library.documentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.squad3.library.documentservice.dto.CategoryDTO;
import org.squad3.library.documentservice.dto.DocumentDTO;
import org.squad3.library.documentservice.model.Category;
import org.squad3.library.documentservice.model.Document;

//@Mapper(componentModel = "spring")
public class CategoryMapper {

    private static CategoryMapper INSTANCE;

    public static CategoryMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CategoryMapper();
        }

        return INSTANCE;
    }

    CategoryDTO ToDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());

        return categoryDTO;
    }


    Category ToEntity(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());

        return category;
    }

}
