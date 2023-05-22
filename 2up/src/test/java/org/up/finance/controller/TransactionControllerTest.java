package org.up.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.up.finance.dto.request.TransactionRequest;
import org.up.finance.dto.response.TransactionResponse;
import org.up.finance.entity.Transaction;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.security.JwtAuthenticationFilter;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.CategoryService;
import org.up.finance.service.TransactionService;
import org.up.finance.service.WalletService;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TransactionController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class))
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private SecurityUtil securityUtil;

    @MockBean
    private WalletService walletService;

    @MockBean
    private CategoryService categoryService;

    private TransactionRequest transactionRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(1L);
        request.setNote("duc anh");
        request.setType("INCOME");
        request.setDescription("test transaction");
        request.setWalletId("walletId");
        request.setCategoryId("categoryId");
        return request;
    }

    private Transaction transactionEntity() {
        Transaction transaction = Transaction.from("userId", transactionRequest());
        transaction.setCreatedAt(20230419L);
        transaction.setLastUpdatedAt(20230419L);
        return transaction;
    }

    @Test
    public void test_Create_WhenInputValid_Return201AndResponseBody() throws Exception {
        String userId = securityUtil.getUserId();
        TransactionRequest request = transactionRequest();
        Transaction transaction = transactionEntity();
        Mockito.doNothing().when(walletService).validateExistWallet(userId, "walletId");
        Mockito.doNothing().when(categoryService).validateExistCategory("categoryId");
        Mockito.when(transactionService.create(userId, request))
                .thenReturn(TransactionResponse.from(transaction));
        MvcResult mvcResult =
                mockMvc
                        .perform(
                                post("/api/v1/transactions")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(request)))
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();
        String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertEquals(201, status);
        Assertions.assertEquals(
                response,
                objectMapper.writeValueAsString(
                        transactionController.create(request)));
    }

    @Test
    public void test_Create_WhenCategoryIdNotFound_Return404() throws Exception {
        TransactionRequest request = transactionRequest();
        Mockito.when(transactionService.create(securityUtil.getUserId(), request))
                .thenThrow(new NotFoundException("categoryId", Transaction.class.getSimpleName()));
        mockMvc.perform(
                        post("/api/v1/transactions")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Create_WhenWalletIdNotFound_Return404() throws Exception {
        TransactionRequest request = transactionRequest();
        Mockito.when(transactionService.create(securityUtil.getUserId(), request))
                .thenThrow(new NotFoundException("walletId", Transaction.class.getSimpleName()));
        mockMvc.perform(
                        post("/api/v1/transactions")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Create_WhenInputInvalid_Return400() throws Exception {
        TransactionRequest request = transactionRequest();
        request.setAmount(null);
        request.setWalletId(null);
        request.setCategoryId(null);
        mockMvc
                .perform(
                        post("/api/v1/transactions")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_Delete_WhenTransactionIdNotFound_Return404() throws Exception {
        Mockito.doThrow(new NotFoundException("transactionId", Transaction.class.getSimpleName()))
                .when(transactionService).deleteById("transactionId");
        mockMvc.perform(
                        delete("/api/v1/transactions/{id}", "transactionId")
                                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Delete_WhenInputValid_Return200() throws Exception {
        Mockito.doNothing().when(transactionService).deleteById("transactionId");
        mockMvc
                .perform(delete("/api/v1/transactions/{id}", "transactionId"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_List_Return200AndRequestBody() throws Exception {
        Transaction transaction = transactionEntity();
        Mockito.when(transactionService.listTransactions(securityUtil.getUserId()))
                .thenReturn(Stream.of(transaction).map(TransactionResponse::from).collect(Collectors.toList()));
        mockMvc
                .perform(get("/api/v1/transactions")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_GetTransaction_WhenTransactionIdNotFound_Return404() throws Exception {
        Mockito.when(transactionService.getTransaction("transactionId", securityUtil.getUserId()))
                .thenThrow(new NotFoundException("transactionId", Transaction.class.getSimpleName()));
        mockMvc
                .perform(get("/api/v1/transactions/{transactionId}","transactionId"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_Get_WhenTransactionId_Return200() throws Exception {
        Transaction transaction = transactionEntity();
        Mockito.when(transactionService.getTransaction("transactionId", transaction.getUserId()))
                .thenReturn(TransactionResponse.from(transaction));
        mockMvc
                .perform(get("/api/v1/transactions/{transactionId}","transactionId"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_Update_WhenInputValid_Return200AndResponseBody() throws Exception {
        TransactionRequest request = transactionRequest();
        Transaction transaction = transactionEntity();
        Mockito.when(transactionService.updateTransaction("transactionId", "securityUtil.getUserId()", request)).
                thenReturn(TransactionResponse.from(transaction));
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/v1/transactions/{id}", "transactionId")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(200, status);

        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertEquals(responseBody, objectMapper.writeValueAsString(transactionController.update("transactionId", request)));
    }

    @Test
    public void test_Update_WhenTransactionNotFound_Return404() throws Exception {
        TransactionRequest request = transactionRequest();
        Mockito.when(transactionService.updateTransaction("transactionId", securityUtil.getUserId(), request))
                .thenThrow(new NotFoundException("transactionId", Transaction.class.getSimpleName()));
        mockMvc.perform(
                put("/api/v1/transactions/{id}", "transactionId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void test_Update_WhenWalletIdNotFound_Return404() throws Exception {
        TransactionRequest request = transactionRequest();
        String userId = securityUtil.getUserId();
        Mockito.doNothing().when(walletService).validateExistWallet(userId, "walletId");
        Mockito.when(transactionService.updateTransaction("transactionId", securityUtil.getUserId(), request))
                .thenThrow(new NotFoundException("walletId", Transaction.class.getSimpleName()));
        mockMvc.perform(
                put("/api/v1/transactions/{id}", "transactionId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotFound());
    }

    @Test
    public void test_Update_WhenCategoryIdNotFound_Return404() throws Exception {
        TransactionRequest request = transactionRequest();
        String userId = securityUtil.getUserId();
        Mockito.doNothing().when(categoryService).validateExistCategory("categoryId");
        Mockito.when(transactionService.updateTransaction("transactionId", securityUtil.getUserId(), request))
                .thenThrow(new NotFoundException("categoryId", Transaction.class.getSimpleName()));
        mockMvc.perform(
                put("/api/v1/transactions/{id}", "transactionId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotFound());
    }


}
