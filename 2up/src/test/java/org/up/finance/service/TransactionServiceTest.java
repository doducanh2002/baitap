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
import org.up.finance.dto.request.TransactionRequest;
import org.up.finance.dto.response.TransactionResponse;
import org.up.finance.entity.Transaction;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.TransactionRepository;
import org.up.finance.security.SecurityUtil;

import java.util.List;
import java.util.Optional;

@WebMvcTest(TransactionService.class)
@ContextConfiguration(classes = {FinanceServiceTestConfiguration.class, RedisConfiguration.class})
public class TransactionServiceTest {


    @Autowired
    private TransactionService transactionService;
    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private SecurityUtil securityUtil;

    private TransactionRequest mockTransactionRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(1L);
        request.setNote("duc anh");
        request.setType("INCOME");
        request.setDescription("test transaction");
        request.setWalletId("walletId");
        request.setCategoryId("categoryId");
        return request;
    }

    private Transaction mockTransactionEntity() {
        Transaction transaction = Transaction.from("userId", mockTransactionRequest());
        transaction.setCreatedAt(20230419L);
        transaction.setLastUpdatedAt(20230419L);
        return transaction;
    }

    @Test
    public void test_Create_WhenInputValid_Successful() {
        TransactionRequest mockRequest = mockTransactionRequest();
        Transaction mockEntity = mockTransactionEntity();
        Mockito.when(transactionRepository.save(mockEntity)).thenReturn(mockEntity);
        TransactionResponse response = transactionService.create("userId", mockRequest);
        Assertions.assertEquals(mockRequest.getType(), response.getType().toString());
        Assertions.assertEquals(mockRequest.getAmount(), response.getAmount());
        Assertions.assertEquals(mockRequest.getDescription(), response.getDescription());
        Assertions.assertEquals(mockRequest.getNote(), response.getNote());
        Assertions.assertEquals(mockRequest.getWalletId(), response.getWalletId());
        Assertions.assertEquals(mockRequest.getCategoryId(), response.getCategoryId());

    }

    @Test
    public void test_DeleteById_WhenTransactionNotFound_ThrowNotFoundException() {
        Mockito.when(transactionRepository.existsById("transactionId")).thenReturn(false);
        Assertions.assertThrows(NotFoundException.class,
                () -> transactionService.deleteById("transactionId"));
    }

    @Test
    public void test_List_Successful() {
        Transaction transaction = mockTransactionEntity();
        Mockito.when(transactionRepository.findByUserId("1")).thenReturn(List.of(transaction));
        Assertions.assertEquals(TransactionResponse.from(transaction), transactionService.listTransactions("1").get(0));
    }

    @Test
    public void test_Get_WhenInputValid_Successful() {
        Transaction transaction = mockTransactionEntity();
        Mockito.when(transactionRepository.findByIdAndUserId("transactionId", securityUtil.getUserId()))
                .thenReturn(Optional.of(transaction));
        TransactionResponse response = transactionService.getTransaction("transactionId", securityUtil.getUserId());
        Assertions.assertEquals(transaction.getAmount(), response.getAmount());
        Assertions.assertEquals(transaction.getDescription(), response.getDescription());
        Assertions.assertEquals(transaction.getNote(), response.getNote());
        Assertions.assertEquals(transaction.getWalletId(), response.getWalletId());
        Assertions.assertEquals(transaction.getCategoryId(), response.getCategoryId());
    }

    @Test
    public void test_Get_WhenTransactionIdNotFound_Return404() {

        Mockito.when(transactionRepository.findByIdAndUserId("transactionId", securityUtil.getUserId()))
                .thenThrow(new NotFoundException("transactionId", Transaction.class.getSimpleName()));
        Assertions.assertThrows(NotFoundException.class,
                () -> transactionService.getTransaction("transactionId", securityUtil.getUserId()));
    }

    @Test
    public void test_Update_WhenInputValid_Successful() {
        TransactionRequest request = mockTransactionRequest();
        Transaction entity = mockTransactionEntity();
        Mockito.when(transactionRepository.findByIdAndUserId("transactionId","userId")).thenReturn(Optional.of(entity));
        Mockito.when(transactionRepository.save(entity)).thenReturn(entity);
        TransactionResponse response = transactionService.updateTransaction("transactionId","userId", request);
        Assertions.assertEquals(request.getAmount(), response.getAmount());
        Assertions.assertEquals(request.getDescription(), response.getDescription());
        Assertions.assertEquals(request.getNote(), response.getNote());
        Assertions.assertEquals(request.getWalletId(), response.getWalletId());
        Assertions.assertEquals(request.getCategoryId(), response.getCategoryId());
    }

    @Test
    public void test_Update_WhenNotFoundTransaction_ThrowNotFoundException() {
        TransactionRequest request = mockTransactionRequest();
        Mockito.when(transactionRepository.findByIdAndUserId("transactionId","userId"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () ->
                transactionService.updateTransaction("transactionId","userId", request));
    }

}
