package org.up.finance.controller;

import static org.up.finance.constant.FinanceApiConstant.BaseUrl.TRANSACTION_BASE_URL;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.up.finance.dto.FinanceResponse;
import org.up.finance.dto.request.TransactionRequest;
import org.up.finance.dto.response.TransactionResponse;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.CategoryService;
import org.up.finance.service.TransactionService;
import org.up.finance.service.WalletService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(TRANSACTION_BASE_URL)
public class TransactionController {

  private final WalletService walletService;

  private final CategoryService categoryService;

  private final TransactionService service;

  private final SecurityUtil securityUtil;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public FinanceResponse<TransactionResponse> create(@RequestBody @Valid TransactionRequest request) {
    log.info("(create)request : {}", request);
    walletService.validateExistWallet(securityUtil.getUserId(), request.getWalletId());
    categoryService.validateExistCategory(request.getCategoryId());
    var response = service.create(securityUtil.getUserId(), request);
    return FinanceResponse.of(HttpStatus.CREATED.value(),
        HttpStatus.CREATED.toString(),
        response);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(path = "{transactionId}")
  public FinanceResponse<Void> delete(@PathVariable String transactionId) {
    service.deleteById(transactionId);
    return FinanceResponse.of(HttpStatus.OK.value(),
            HttpStatus.OK.toString());
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping()
  public FinanceResponse<List<TransactionResponse>> list() {
//    log.info("(list)");
    var response = service.listTransactions(securityUtil.getUserId());
    return FinanceResponse.of(HttpStatus.OK.value(),
            HttpStatus.OK.toString(),
            response);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping(path = "{transactionId}")
  public FinanceResponse<TransactionResponse> getTransaction(@PathVariable String transactionId) {
//    log.info("(getTransaction)transactionId : {}", transactionId);
    var response = service.getTransaction(transactionId, securityUtil.getUserId());
    return FinanceResponse.of(HttpStatus.OK.value(),
            HttpStatus.OK.toString(),
            response);
  }

  @ResponseStatus(HttpStatus.OK)
  @PutMapping(path = "{transactionId}")
  public FinanceResponse<TransactionResponse> update(@PathVariable String transactionId, @RequestBody TransactionRequest transactionRequest) {
//    log.info("(update)transactionId : {}, userId : {}", transactionId, securityUtil.getUserId());
    walletService.validateExistWallet(securityUtil.getUserId(), transactionRequest.getWalletId());
    categoryService.validateExistCategory(transactionRequest.getCategoryId());
    var response = service.updateTransaction(transactionId, securityUtil.getUserId(), transactionRequest);
    return FinanceResponse.of(HttpStatus.OK.value(),
            HttpStatus.OK.toString(),
            response);
  }
}
