package org.up.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.up.finance.dto.request.CategoryRequest;
import org.up.finance.dto.response.CategoryResponse;
import org.up.finance.entity.Category;
import org.up.finance.exception.CategoryBadRequestException;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.CategoryService;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private SecurityUtil securityUtil;

    private CategoryRequest categoryRequest() {
        CategoryRequest request = new CategoryRequest();
        request.setName("ducanh");
        request.setCategoryParentId("1");
        return request;
    }

    private Category categoryEntity() {
        return Category.from(categoryRequest(), securityUtil.getUserId());
    }

    @Test
    public void test_Create_WhenInputValid_Return201AndResponseBody() throws Exception {
        CategoryRequest request = categoryRequest();
        Category category = categoryEntity();
        Mockito.when(categoryService.create(request, securityUtil.getUserId())).
                thenReturn(CategoryResponse.from(category));
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(201, status);

        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertEquals(responseBody, objectMapper.writeValueAsString(categoryController.create(request)));
    }

    @Test
    public void test_Create_WhenParentIdNotFound_Return404() throws Exception {
        CategoryRequest request = categoryRequest();
        Mockito.when(categoryService.create(request, securityUtil.getUserId()))
                .thenThrow(new NotFoundException("categoryId", Category.class.getSimpleName()));
        mockMvc.perform(
                        post("/api/v1/categories")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Update_WhenInputValid_Return200AndResponseBody() throws Exception {
        CategoryRequest request = categoryRequest();
        Category category = categoryEntity();
        Mockito.when(categoryService.update("1", request, securityUtil.getUserId())).
                thenReturn(CategoryResponse.from(category));
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/v1/categories/{id}", "categoryId")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertEquals(responseBody, objectMapper.writeValueAsString(categoryController.update("categoryId", request)));
    }

    @Test
    public void test_Update_WhenParentNotFound_Return404() throws Exception {
        CategoryRequest request = categoryRequest();
        Mockito.when(categoryService.update("categoryId", request, securityUtil.getUserId()))
                .thenThrow(new NotFoundException("parentId", Category.class.getSimpleName()));
        mockMvc.perform(
                put("/api/v1/categories/{id}", "categoryId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_WhenCategoryIdNotFound_Return404() throws Exception {
        Mockito.doThrow(new NotFoundException("categoryId", Category.class.getSimpleName()))
                .when(categoryService).delete("categoryId");
        mockMvc.perform(
                        delete("/api/v1/categories/{id}", "categoryId")
                                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_WhenInputValid_Return200() throws Exception {
        Mockito.doNothing().when(categoryService).delete("categoryId");
        mockMvc.perform(
                        delete("/api/v1/categories/{id}", "categoryId")
                                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    public void test_Delete_WhenExistParentCategory_Return400() throws Exception {
        Mockito.doThrow(new CategoryBadRequestException("categoryId"))
                .when(categoryService).delete("categoryId");
        mockMvc.perform(
                        delete("/api/v1/categories/{id}", "categoryId")
                                .contentType("application/json"))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void test_List_Return200AndResponseBody() throws Exception {
        Category category = categoryEntity();
        Mockito.when(categoryService.list(securityUtil.getUserId()))
                .thenReturn(Stream.of(category).map(CategoryResponse::from).collect(Collectors.toList()));
        mockMvc.perform(
                        get("/api/v1/categories")
                                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_WhenCategoryById_Return200() throws Exception {
        Category category = categoryEntity();
        Mockito.when(categoryService.getCategoryById("categoryId", securityUtil.getUserId()))
                .thenReturn(CategoryResponse.from(category));
        mockMvc.perform(
                        get("/api/v1/categories/{categoryId}", "categoryId"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_WhenCategoryIdNotFound_Return404() throws Exception {
        CategoryRequest request = categoryRequest();
        Mockito.when(categoryService.getCategoryById("categoryId", securityUtil.getUserId()))
                .thenThrow(new NotFoundException("categoryId", Category.class.getSimpleName()));
        mockMvc.perform(
                get("/api/v1/categories/{id}", "categoryId")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }
}
