package org.up.finance.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.up.finance.configuration.FinanceServiceTestConfiguration;
import org.up.finance.configuration.RedisConfiguration;
import org.up.finance.dto.request.CategoryRequest;
import org.up.finance.dto.response.CategoryResponse;
import org.up.finance.entity.Category;
import org.up.finance.exception.CategoryBadRequestException;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.CategoryRepository;
import org.up.finance.security.SecurityUtil;

import java.util.List;
import java.util.Optional;

@WebMvcTest(CategoryService.class)
@ContextConfiguration(classes = {FinanceServiceTestConfiguration.class, RedisConfiguration.class})
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private SecurityUtil securityUtil;

    private CategoryRequest mockCategoryRequest() {
        CategoryRequest request = new CategoryRequest();
        request.setName("DucAnh");
        request.setCategoryParentId(null);
        return request;
    }

    private Category mockCategoryEntity() {
        return Category.from(mockCategoryRequest(), securityUtil.getUserId());
    }

    @Test
    public void test_Create_WhenInputValid_Successful() {
        CategoryRequest mockRequest = mockCategoryRequest();
        Category mockEntity = mockCategoryEntity();
        Mockito.when(categoryRepository.save(mockEntity)).thenReturn(mockEntity);
        CategoryResponse response = categoryService.create(mockRequest, securityUtil.getUserId());
        Assertions.assertEquals(mockRequest.getCategoryParentId(), response.getCategoryParentId());
        Assertions.assertEquals(mockRequest.getName(), response.getName());

    }

    @Test
    public void test_Create_WhenParentIdNotFound_ThrowNotFoundException() {
        CategoryRequest categoryRequest = mockCategoryRequest();
        categoryRequest.setCategoryParentId("ducanh");
        Assertions.assertThrows(NotFoundException.class, () -> categoryService.create(categoryRequest, securityUtil.getUserId()));
    }

    @Test
    public void test_Update_WhenInputValid_Successful() {
        CategoryRequest request = mockCategoryRequest();
        Category entity = mockCategoryEntity();
        Mockito.when(categoryRepository.findById("categoryId")).thenReturn(Optional.of(entity));
        Mockito.when(categoryRepository.save(entity)).thenReturn(entity);
        CategoryResponse response = categoryService.update("categoryId", request, securityUtil.getUserId());
        Assertions.assertEquals(request.getCategoryParentId(), response.getCategoryParentId());
        Assertions.assertEquals(request.getName(), response.getName());
    }

    @Test
    public void test_Update_WhenParentNotFound_ThrowNotFoundException() {
        CategoryRequest categoryRequest = mockCategoryRequest();
        categoryRequest.setCategoryParentId("ducanh");
        Assertions.assertThrows(NotFoundException.class, () -> categoryService.update("categoryId", categoryRequest, securityUtil.getUserId()));
    }

    @Test
    public void test_Update_WhenNotFoundCategory_ThrowNotFoundException() {
        CategoryRequest request = mockCategoryRequest();
        Mockito.when(categoryRepository.findById("categoryId"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () ->
                categoryService.update("categoryId", request, securityUtil.getUserId()));
    }

    @Test
    public void test_List_Successful() {
        Category category = mockCategoryEntity();
        Mockito.when(categoryRepository.findByUserId(securityUtil.getUserId())).thenReturn(List.of(category));
        Assertions.assertEquals(CategoryResponse.from(category), categoryService.list(securityUtil.getUserId()).get(0));
    }

    @Test
    public void test_Get_WhenInputValid_Successful() {
        Category category = mockCategoryEntity();
        Mockito.when(categoryRepository.findByIdAndUserId("categoryId", securityUtil.getUserId()))
                .thenReturn(Optional.of(category));
        CategoryResponse response = categoryService.getCategoryById("categoryId", securityUtil.getUserId());
        Assertions.assertEquals(category.getCategoryParentId(), response.getCategoryParentId());
        Assertions.assertEquals(category.getName(), response.getName());
    }

    @Test
    public void test_Get_WhenCategoryNotFound_ThrowNotFoundException() {
        Mockito.when(categoryRepository.findByIdAndUserId("categoryId", securityUtil.getUserId()))
                .thenThrow(new NotFoundException("categoryId", Category.class.getSimpleName()));
        Assertions.assertThrows(NotFoundException.class,
                () -> categoryService.getCategoryById("categoryId", securityUtil.getUserId()));
    }


    @Test
    public void test_DeleteById_WhenCategoryNotFound_ThrowNotFoundException() {
        Mockito.when(categoryRepository.existsById("categoryId")).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                () -> categoryService.delete("categoryId"));
    }

    @Test
    public void test_DeleteById_WhenThrowParentNotFoundException_BadRequestException() {
        Mockito.when(categoryRepository.existsById("categoryId")).thenReturn(true);
        Mockito.when(categoryRepository.existSubCategoryById("categoryId")).thenReturn(true);
        Assertions.assertThrows(CategoryBadRequestException.class,
                () -> categoryService.delete("categoryId"));
    }

}
