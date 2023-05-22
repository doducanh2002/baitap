package org.up.finance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.up.finance.dto.FinanceResponse;
import org.up.finance.dto.request.DailyNoteRequest;
import org.up.finance.dto.response.DailyNoteResponse;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.DailyNoteService;

import java.util.List;

import static org.up.finance.constant.FinanceApiConstant.BaseUrl.DAILY_NOTE_BASE_URL;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(DAILY_NOTE_BASE_URL)
public class DailyNoteController {
    private final DailyNoteService service;

    private final SecurityUtil securityUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FinanceResponse<DailyNoteResponse> create(@RequestBody @Valid DailyNoteRequest request) {
        log.info("(create)request: {}", request);
        return FinanceResponse.of(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(),
                service.create(securityUtil.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<Void> delete(@PathVariable("id") String id) {
        log.info("(delete)id:{}", id);
        service.deleteById(id);
        return FinanceResponse.of(HttpStatus.OK.value(), HttpStatus.OK.toString());
    }

    @PutMapping("/{dailyId}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<DailyNoteResponse> update(@PathVariable("dailyId") String dailyId, @Validated @RequestBody DailyNoteRequest request) {
        log.info("(update)request: {}, userId : {}, dailyNoteId : {}", request, securityUtil.getUserId(), dailyId);
        var response = service.update(dailyId, request, securityUtil.getUserId());
        return FinanceResponse.of(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                response
        );
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<List<DailyNoteResponse>> getAllByUserId() {
        log.info("getAllByUserId");
        var response = service.getAllByUserId(securityUtil.getUserId());
        return FinanceResponse.of(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                response
        );
    }

    @GetMapping(value = "/{dailyNoteId}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<DailyNoteResponse> getDaiLyNoteById(@PathVariable String dailyNoteId) {
        log.info("(getDaiLyNoteById)dailyNoteId : {}, userId : {}", dailyNoteId, securityUtil.getUserId());
        return FinanceResponse.of(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                service.getByDailyNoteId(dailyNoteId, securityUtil.getUserId()));
    }

}
