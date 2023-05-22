package org.up.finance.service;

import org.up.finance.dto.request.CategoryRequest;
import org.up.finance.dto.response.CategoryResponse;
import org.up.finance.entity.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category> {

    /**
     * Create category
     * @param categoryReq - from client
     * @return information category
     */
    CategoryResponse create(CategoryRequest categoryReq, String userId);

    /**
     * Update category
     * @param categoryId - id of a category
     * @param request - from client
     * @return ok
     */
    CategoryResponse update(String categoryId, CategoryRequest request, String userId);

    /**
     * Get list by userId
     * @param userid - id of a user
     * @return list category
     */
    List<CategoryResponse> list(String userid);

    /**
     * delete a category
     * @param id - from client
     */
    void delete(String id);

    /**
     * validate a category exist or not
     * @param categoryId - id of a category
     */
    void validateExistCategory(String categoryId);

    /**
     * Get category by id category
     * @param categoryId - id of a category
     * @param userId-    id of a user
     * @return category
     */
    CategoryResponse getCategoryById(String categoryId, String userId);

}
