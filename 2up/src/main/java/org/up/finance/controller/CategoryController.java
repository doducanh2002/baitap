package org.up.finance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.up.finance.dto.FinanceResponse;
import org.up.finance.dto.request.CategoryRequest;
import org.up.finance.dto.response.CategoryResponse;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.CategoryService;

import java.util.List;

import static org.up.finance.constant.FinanceApiConstant.BaseUrl.CATEGORY_BASE_URL;

@RestController
@RequestMapping(CATEGORY_BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService service;

    private final SecurityUtil securityUtil;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FinanceResponse<CategoryResponse> create(@Validated @RequestBody CategoryRequest request) {
        log.info("(create)request: {}", request);
        var response = service.create(request, securityUtil.getUserId());
        return FinanceResponse.of(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.toString(),
                response
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "{id}")
    public FinanceResponse<CategoryResponse> update(@PathVariable("id") String id, @Validated @RequestBody CategoryRequest request) {
        log.info("(update)id : {}, request : {}", id, request);
        return FinanceResponse.of(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                service.update(id, request, securityUtil.getUserId())
        );
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<Void> deleteById(@PathVariable("id") String id) {
        log.info("(deleteById)id : {}", id);
        service.delete(id);
        return FinanceResponse.of(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString());
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<List<CategoryResponse>> getAllByUserId() {
        log.info("getAllByUserId");
        var response = service.list(securityUtil.getUserId());
        return FinanceResponse.of(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                response
        );
    }

    @GetMapping(path = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<CategoryResponse> getCategory(@PathVariable("categoryId") String categoryId) {
        log.info("(getCategory)categoryId : {}, userId : {}", categoryId, securityUtil.getUserId());
        var response = service.getCategoryById(categoryId, securityUtil.getUserId());
        return FinanceResponse.of(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                response
        );
    }
}
