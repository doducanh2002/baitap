package org.up.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.up.finance.dto.request.CategoryRequest;
import org.up.finance.entity.base.BaseEntityWithUpdater;

@Data
@Entity
@Table(name = "category")
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntityWithUpdater {
    @Column(nullable = false)
    private String name;

    private String categoryParentId;

    private String userId;

    public static Category from(CategoryRequest request, String userId) {
        Category category = new Category();
        category.setName(request.getName());
        category.setCategoryParentId(request.getCategoryParentId());
        category.setUserId(userId);
        return category;
    }
}

