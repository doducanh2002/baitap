package org.up.finance.service;

import org.up.finance.dto.request.TransactionRequest;
import org.up.finance.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

  /**
   * create transaction by request
   * @param request - from client
   * @return created transaction
   */
  TransactionResponse create(String userId, TransactionRequest request);

  /**
   * delete transaction by transactionId
   * @param transactionId - id transaction
   */
  void deleteById(String transactionId);

  /**
   * validateExistTransaction
   * @param transactionId - id transaction
   */
  void validateExistTransaction(String transactionId);

  /**
   * get list transaction by userId
   * @param userId - id user
   * @return list transaction
   */
  List<TransactionResponse> listTransactions(String userId);

  /**
   * get transaction by id
   * @param userId - id user
   * @param transactionId -id transaction
   * @return transaction
   */
  TransactionResponse getTransaction(String transactionId, String userId);

  /**
   * update transaction by id
   * @param transactionId - transaction id
   * @param userId - id user
   * @param request - from client
   * @return transaction
   */
  TransactionResponse updateTransaction(String transactionId, String userId, TransactionRequest request);
}
