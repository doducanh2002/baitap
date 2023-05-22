package org.up.finance.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.up.finance.dto.request.TransactionRequest;
import org.up.finance.dto.response.TransactionResponse;
import org.up.finance.entity.Transaction;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.TransactionRepository;
import org.up.finance.service.TransactionService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction>
    implements TransactionService {

  private final TransactionRepository repository;

  public TransactionServiceImpl(TransactionRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  @Transactional
  public TransactionResponse create(String userId, TransactionRequest request) {
    log.info("(create)request : {}, userId : {}", request, userId);
    Transaction transaction = Transaction.from(userId, request);
    transaction = create(transaction);
    return TransactionResponse.from(transaction);
  }

  @Override
  @Transactional
  public void deleteById(String transactionId){
//    log.info("(delete)transactionId : {}", transactionId);
    validateExistTransaction(transactionId);
    repository.deleteById(transactionId);
    }

  @Override
  public void validateExistTransaction(String transactionId) {
//    log.info("(validateExistTransactionId)TransactionId : {}", transactionId);
    if (!repository.existsById(transactionId)) {
//      log.error("(validateExistTransactionId)transactionId : {} -->NOT FOUND EXCEPTION", transactionId);
      throw new NotFoundException(transactionId, Transaction.class.getSimpleName());
    }
  }

  @Override
  public List<TransactionResponse> listTransactions(String userId) {
//    log.info("(listTransactions)userId : {}", userId);
    return repository.findByUserId(userId)
            .stream()
            .map(TransactionResponse::from)
            .collect(Collectors.toList());
  }

  @Override
  public TransactionResponse getTransaction(String transactionId, String userId) {
//    log.info("(getTransaction)transactionId : {}, userId : {}", transactionId, userId);
    var transaction = repository.findByIdAndUserId(transactionId, userId)
            .orElseThrow(() -> {
//              log.info("(getTransaction)transactionId : {} -> Not found exception",transactionId);
              throw new NotFoundException(transactionId, Transaction.class.getSimpleName());
            });
    return TransactionResponse.from(transaction);
  }

  @Override
  public TransactionResponse updateTransaction(String transactionId, String userId, TransactionRequest request) {
//    log.info("(updateTransaction)transactionId : {}, userId : {}, request : {}", transactionId, userId, request);
    Transaction transactionUpdate = Transaction.from(userId, request);
    Transaction result = repository.findByIdAndUserId(transactionId, userId)
            .map(transaction -> {
              transaction.setType(transactionUpdate.getType());
              transaction.setNote(transactionUpdate.getNote());
              transaction.setAmount(transactionUpdate.getAmount());
              transaction.setDescription(transactionUpdate.getDescription());
              transaction.setCategoryId(transactionUpdate.getCategoryId());
              transaction.setWalletId(transactionUpdate.getWalletId());
              return transaction;
            })
            .map(repository::save)
            .orElseThrow(() -> {
//              log.info("(updateTransaction)transactionId : {} --> Not found", transactionId);
              throw new NotFoundException(transactionId, Transaction.class.getSimpleName());
            });
    return TransactionResponse.from(result);
  }
}
