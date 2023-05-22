package org.up.finance.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.up.finance.dto.request.CategoryRequest;
import org.up.finance.dto.response.CategoryResponse;
import org.up.finance.entity.Category;
import org.up.finance.exception.CategoryBadRequestException;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.CategoryRepository;
import org.up.finance.service.CategoryService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest categoryReq, String userId) {
        log.info("(create)category request : {}, userId : {}", categoryReq, userId);
        validateCategoryParentId(categoryReq.getCategoryParentId());
        Category category = Category.from(categoryReq, userId);
        category = create(category);
        return CategoryResponse.from(category);
    }

    @Override
    @Transactional
    public CategoryResponse update(String categoryId, CategoryRequest categoryReq, String userId) {
        log.info("(update)id : {},request : {}, userId : {}", categoryId, categoryReq, userId);
        validateCategoryParentId(categoryReq.getCategoryParentId());
        Category categoryUpdate = Category.from(categoryReq, userId);
        Category result = repository.findById(categoryId)
                .map(category -> {
                    category.setName(categoryUpdate.getName());
                    category.setCategoryParentId(categoryUpdate.getCategoryParentId());
                    return category;
                })
                .map(repository::save)
                .orElseThrow(() -> {
                    log.error("(update)categoryId : {} --> NOT FOUND EXCEPTION", categoryId);
                    throw new NotFoundException(categoryId, Category.class.getSimpleName());
                });
        return CategoryResponse.from(result);
    }

    @Override
    public List<CategoryResponse> list(String userId) {
        log.info("(list)userId: {}" , userId);
        return repository.findByUserId(userId)
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        log.info("(deleteById)id: {}", id);
        validateExistCategory(id);
        validateBadRequest(id);
        repository.deleteById(id);
    }

    private void validateBadRequest(String categoryId) {
        log.error("(validateCategoryIdBadRequest)categoryId : {}  -->BAD REQUEST EXCEPTION", categoryId);
        if (repository.existSubCategoryById(categoryId)) {
            throw new CategoryBadRequestException(categoryId);
        }
    }

    @Override
    public CategoryResponse getCategoryById(String categoryId, String userId) {
        log.info("(getCategoryById)id: {}, userId: {}", categoryId, userId);
        var category = repository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> {
                    log.error("(getCategory)categoryId : {} --> NOT FOUND EXCEPTION", categoryId);
                    throw new NotFoundException(categoryId, Category.class.getSimpleName());
                });
        return CategoryResponse.from(category);
    }

    private void validateCategoryParentId(String categoryParentId) {
        log.info("(validateCategoryParentId)categoryId : {}", categoryParentId);
        if (Objects.nonNull(categoryParentId)) {
            validateExistCategory(categoryParentId);
        }
    }

    @Override
    public void validateExistCategory(String categoryId) {
        log.info("(validateExistCategoryId)CategoryId : {}", categoryId);
        if (!repository.existsById(categoryId)) {
            log.error("(validateExistCategoryId)categoryId : {} -->NOT FOUND EXCEPTION", categoryId);
            throw new NotFoundException(categoryId, Category.class.getSimpleName());
        }
    }

}
