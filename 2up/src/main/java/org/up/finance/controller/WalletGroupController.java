package org.up.finance.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.up.finance.dto.FinanceResponse;
import org.up.finance.dto.request.WalletGroupRequest;
import org.up.finance.dto.request.WalletRequest;
import org.up.finance.dto.response.WalletGroupResponse;
import org.up.finance.dto.response.WalletResponse;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.WalletGroupService;
import org.up.finance.service.WalletService;

import java.util.List;

import static org.up.finance.constant.FinanceApiConstant.BaseUrl.WALLET_GROUP_URL;
import static org.up.finance.constant.FinanceApiConstant.ResourceConstant.WALLET_RESOURCE;

@RestController
@RequiredArgsConstructor
@RequestMapping(WALLET_GROUP_URL)
@Slf4j
public class WalletGroupController {

    private final WalletGroupService service;

    private final WalletService walletService;

    private final SecurityUtil securityUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FinanceResponse<WalletGroupResponse> create(@RequestBody @Validated WalletGroupRequest request) {
        log.info("(create)request : {}", request);
        return FinanceResponse.of(HttpStatus.CREATED.value(),
                HttpStatus.CREATED.toString(),
                service.create(securityUtil.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<Void> delete(@PathVariable("id") String id) {
        log.info("(delete)id : {}", id);
        service.deleteById(id);
        return FinanceResponse.of(HttpStatus.OK.value(),
                HttpStatus.OK.toString());
    }

    @GetMapping
    public FinanceResponse<List<WalletGroupResponse>> list() {
        log.info("(list)");
        return FinanceResponse.of(HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                service.list(securityUtil.getUserId()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<WalletGroupResponse> get(@PathVariable("id") String id) {
        log.info("(get)id : {}", id);
        var response = service.get(securityUtil.getUserId(), id);
        return FinanceResponse.of(HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<WalletGroupResponse> update(@PathVariable("id") String id,
                                                       @RequestBody @Validated WalletGroupRequest request) {
        log.info("(update)id : {}, request : {}", id, request);
        var response = service.update(securityUtil.getUserId(), id, request);
        return FinanceResponse.of(HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                response);
    }

    @PostMapping("/{wallet-group-id}" + WALLET_RESOURCE)
    @ResponseStatus(HttpStatus.CREATED)
    public FinanceResponse<WalletResponse> createWallet(
            @PathVariable("wallet-group-id") String walletGroupId,
            @RequestBody @Validated WalletRequest request) {
        log.info("(createWallet)walletGroupId : {}, request : {}", walletGroupId, request);
        service.validateExistWalletGroup(walletGroupId);
        var response = walletService.create(securityUtil.getUserId(), walletGroupId, request);
        return FinanceResponse.of(HttpStatus.CREATED.value(),
                HttpStatus.CREATED.toString(),
                response);
    }

    @DeleteMapping("/{wallet-group-id}" + WALLET_RESOURCE + "/{wallet-id}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<Void> deleteWallet(@PathVariable("wallet-group-id") String walletGroupId,
                                              @PathVariable("wallet-id") String walletId) {
        log.info("(deleteWallet)walletGroupId : {}. walletId : {}", walletGroupId, walletId);
        service.validateExistWalletGroup(walletGroupId);
        walletService.delete(securityUtil.getUserId(), walletId);
        return FinanceResponse.of(HttpStatus.OK.value(),
                HttpStatus.OK.toString());
    }

    @GetMapping("/{wallet-group-id}" + WALLET_RESOURCE + "/{wallet-id}")
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<WalletResponse> getWallet(
            @PathVariable("wallet-group-id") String walletGroupId,
            @PathVariable("wallet-id") String walletId
    ) {
        service.validateExistWalletGroup(walletGroupId);
        var response = walletService.get(securityUtil.getUserId(), walletId);
        return FinanceResponse.of(HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                response);
    }

    @PutMapping("/{wallet-group-id}" + WALLET_RESOURCE + "/{wallet-id}")
    public FinanceResponse<WalletResponse> updateWallet(
            @PathVariable("wallet-group-id") String walletGroupId,
            @PathVariable("wallet-id") String walletId,
            @RequestBody @Validated WalletRequest request) {
        log.info("(updateWallet)walletGroupId : {}, wallet : {}", walletGroupId, walletId);
        service.validateExistWalletGroup(walletGroupId);
        var response = walletService.update(walletId, walletGroupId, "1", request);
        return FinanceResponse.of(HttpStatus.OK.value(), HttpStatus.OK.toString(), response);
    }

    @GetMapping("/{wallet-group-id}" + WALLET_RESOURCE)
    @ResponseStatus(HttpStatus.OK)
    public FinanceResponse<List<WalletResponse>> listWallet(
        @PathVariable("wallet-group-id") String walletGroupId) {
        log.info("(listWallet)");
        service.validateExistWalletGroup(walletGroupId);
        var response = walletService.list(securityUtil.getUserId(), walletGroupId);
        return FinanceResponse.of(HttpStatus.OK.value(),
            HttpStatus.OK.toString(),
            response);
    }

}
