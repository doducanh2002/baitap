package org.up.finance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import org.up.finance.dto.request.WalletGroupRequest;
import org.up.finance.dto.request.WalletRequest;
import org.up.finance.dto.response.WalletGroupResponse;
import org.up.finance.dto.response.WalletResponse;
import org.up.finance.entity.Wallet;
import org.up.finance.entity.WalletGroup;
import org.up.finance.exception.WalletGroupParentConstraintException;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.security.JwtAuthenticationFilter;
import org.up.finance.security.SecurityUtil;
import org.up.finance.service.WalletGroupService;
import org.up.finance.service.WalletService;

@WebMvcTest(WalletGroupController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WalletGroupControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private WalletGroupController walletGroupController;

  @MockBean private SecurityUtil securityUtil;

  @MockBean private WalletGroupService walletGroupService;

  @MockBean private WalletService walletService;

  private WalletGroupRequest mockWalletGroupRequest() {
    WalletGroupRequest request = new WalletGroupRequest();
    request.setName("Đầu tư chứng khoán");
    request.setParentId(null);
    return request;
  }

  private WalletGroup mockWalletGroupEntity() {
    return WalletGroup.from(securityUtil.getUserId(), mockWalletGroupRequest());
  }

  private WalletRequest mockWalletRequest() {
    WalletRequest request = new WalletRequest();
    request.setName("Etherium");
    request.setDescription("Quỹ đầu tư ETH");
    request.setAmount(200000L);
    return request;
  }

  private Wallet mockWalletEntity() {
    return Wallet.of(securityUtil.getUserId(), "walletGroupId", mockWalletRequest());
  }

  @Test
  public void test_Create_WhenInputValid_Return201AndResponseBody() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    WalletGroup walletGroup = mockWalletGroupEntity();
    Mockito.when(walletGroupService.create(securityUtil.getUserId(), request))
        .thenReturn(WalletGroupResponse.from(walletGroup));
    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/api/v1/wallet-groups")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request)))
            .andReturn();
    int status = mvcResult.getResponse().getStatus();
    Assertions.assertEquals(201, status);
    String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(
        responseBody, objectMapper.writeValueAsString(walletGroupController.create(request)));
  }

  @Test
  public void test_Create_WhenInputInvalid_Return400() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    request.setName(null);
    mockMvc
        .perform(
            post("/api/v1/wallet-groups")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void test_Create_WhenParentIdNotFound_Return404() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    Mockito.when(walletGroupService.create(securityUtil.getUserId(), request))
        .thenThrow(new NotFoundException("walletGroupId", WalletGroup.class.getSimpleName()));
    mockMvc
        .perform(
            post("/api/v1/wallet-groups")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_Delete_WhenInputValid_Return200() throws Exception {
    Mockito.doNothing().when(walletGroupService).deleteById("walletGroupId");
    mockMvc
        .perform(delete("/api/v1/wallet-groups/{id}", "walletGroupId"))
        .andExpect(status().isOk());
  }

  @Test
  public void test_Delete_WhenInputValid_Return400() throws Exception {
    Mockito.doThrow(new NotFoundException("walletGroupId", WalletGroup.class.getSimpleName()))
        .when(walletGroupService)
        .deleteById("walletGroupId");
    mockMvc
        .perform(delete("/api/v1/wallet-groups/{id}", "walletGroupId"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_Delete_WhenExistParentConstraint_Return400() throws Exception {
    Mockito.doThrow(new WalletGroupParentConstraintException("walletGroupId"))
        .when(walletGroupService)
        .deleteById("walletGroupId");
    mockMvc
        .perform(delete("/api/v1/wallet-groups/{id}", "walletGroupId"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void test_List_Return200AndResponseBody() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    WalletGroup walletGroup = mockWalletGroupEntity();
    Mockito.when(walletGroupService.list(securityUtil.getUserId()))
        .thenReturn(
            Stream.of(walletGroup).map(WalletGroupResponse::from).collect(Collectors.toList()));
    MvcResult mvcResult = mockMvc.perform(get("/api/v1/wallet-groups")).andReturn();

    int status = mvcResult.getResponse().getStatus();
    String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(status, 200);
    Assertions.assertEquals(
        response, objectMapper.writeValueAsString(walletGroupController.list()));
  }

  @Test
  public void test_Get_WhenInputValid_Return200AndResponseBody() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    WalletGroup walletGroup = mockWalletGroupEntity();
    Mockito.when(walletGroupService.get(securityUtil.getUserId(), "walletGroupId"))
        .thenReturn(WalletGroupResponse.from(walletGroup));
    MvcResult mvcResult =
        mockMvc
            .perform(
                get("/api/v1/wallet-groups/{id}", "walletGroupId")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(200, status);
    Assertions.assertEquals(
        response, objectMapper.writeValueAsString(walletGroupController.get("walletGroupId")));
  }

  @Test
  public void test_Get_WhenWalletGroupNotFound_Return404() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    Mockito.when(walletGroupService.get(securityUtil.getUserId(), "walletGroupId"))
        .thenThrow(new NotFoundException("walletGroupId", WalletGroup.class.getSimpleName()));
    mockMvc
        .perform(
            get("/api/v1/wallet-groups/{id}", "walletGroupId")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_CreateWallet_WhenInputValid_Return201AndResponseBody() throws Exception {
    WalletRequest request = mockWalletRequest();
    Wallet wallet = mockWalletEntity();
    Mockito.doNothing().when(walletGroupService).validateExistWalletGroup("walletGroupId");
    Mockito.when(walletService.create(securityUtil.getUserId(), "walletGroupId", request))
        .thenReturn(WalletResponse.from(wallet));
    MvcResult mvcResult =
        mockMvc
            .perform(
                post("/api/v1/wallet-groups/{wallet-group-id}/wallets", "walletGroupId")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(201, status);
    Assertions.assertEquals(
        response,
        objectMapper.writeValueAsString(
            walletGroupController.createWallet("walletGroupId", request)));
  }

  @Test
  public void test_CreateWallet_WhenInputInvalid_Return400() throws Exception {
    WalletRequest request = mockWalletRequest();
    request.setName(null);
    mockMvc
        .perform(
            post("/api/v1/wallet-groups/{wallet-group-id}/wallets", "walletGroupId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void test_CreateWallet_WhenWalletGroupNotFound_Return404() throws Exception {
    WalletRequest request = mockWalletRequest();
    Mockito.doThrow(new NotFoundException("walletGroupId", WalletGroup.class.getSimpleName()))
        .when(walletGroupService)
        .validateExistWalletGroup("walletGroupId");
    mockMvc
        .perform(
            post("/api/v1/wallet-groups/{wallet-group-id}/wallets", "walletGroupId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_Update_WhenInputValid_Return201AndResponseBody() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    WalletGroup walletGroup = mockWalletGroupEntity();
    Mockito.when(walletGroupService.create(securityUtil.getUserId(), request))
        .thenReturn(WalletGroupResponse.from(walletGroup));
    MvcResult mvcResult =
        mockMvc
            .perform(
                put("/api/v1/wallet-groups/{id}", "walletGroupId")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andReturn();

    int status = mvcResult.getResponse().getStatus();
    String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(200, status);
    Assertions.assertEquals(
        response,
        objectMapper.writeValueAsString(walletGroupController.update("walletGroupId", request)));
  }

  @Test
  public void test_Update_WhenInputInvalid_Return400() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    request.setName(null);
    mockMvc
        .perform(
            put("/api/v1/wallet-groups/{id}", "walletGroupId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void test_Update_WhenParentNotFound_Return404() throws Exception {
    WalletGroupRequest request = mockWalletGroupRequest();
    Mockito.when(walletGroupService.update(securityUtil.getUserId(), "walletGroupId", request))
        .thenThrow(new NotFoundException("parentId", WalletGroup.class.getSimpleName()));
    mockMvc
        .perform(
            put("/api/v1/wallet-groups/{id}", "walletGroupId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_DeleteWallet_WhenInputValid_Return200() throws Exception {
    String userId = securityUtil.getUserId();
    Mockito.doNothing().when(walletGroupService).validateExistWalletGroup("walletGroupId");
    Mockito.doNothing().when(walletService).delete(userId, "walletId");
    mockMvc
        .perform(
            delete(
                "/api/v1/wallet-groups/{wallet-group-id}/wallets/{wallet-id}",
                "walletGroupId",
                "walletId"))
        .andExpect(status().isOk());
  }

  @Test
  public void test_DeleteWallet_WhenWalletGroupNotFound_Return404() throws Exception {
    Mockito.doThrow(new NotFoundException("walletGroupId", WalletGroup.class.getSimpleName()))
        .when(walletGroupService)
        .validateExistWalletGroup("walletGroupId");
    mockMvc
        .perform(
            delete(
                "/api/v1/wallet-groups/{wallet-group-id}/wallets/{wallet-id}",
                "walletGroupId",
                "walletId"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_DeleteWallet_WhenWalletNotFound_Return404() throws Exception {
    String userId = securityUtil.getUserId();
    Mockito.doNothing().when(walletGroupService).validateExistWalletGroup("walletGroupId");
    Mockito.doThrow(new NotFoundException("wallet", Wallet.class.getSimpleName()))
        .when(walletService)
        .delete(userId, "walletId");
    mockMvc
        .perform(
            delete(
                "/api/v1/wallet-groups/{wallet-group-id}/wallets/{wallet-id}",
                "walletGroupId",
                "walletId"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_GetWallet_WhenInputValid_Return200AndResponseBody() throws Exception {
    String userId = securityUtil.getUserId();
    WalletRequest request = mockWalletRequest();
    Wallet wallet = mockWalletEntity();
    Mockito.doNothing().when(walletGroupService).validateExistWalletGroup("walletGroupId");
    Mockito.when(walletService.get(userId, "walletId")).thenReturn(WalletResponse.from(wallet));
    MvcResult mvcResult =
        mockMvc
            .perform(
                get(
                        "/api/v1/wallet-groups/{wallet-group-id}/wallets/{wallet-id}",
                        "walletGroupId",
                        "walletId")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
            .andReturn();
    int status = mvcResult.getResponse().getStatus();
    String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(200, status);
    Assertions.assertEquals(
        objectMapper.writeValueAsString(
            walletGroupController.getWallet("walletGroupId", "walletId")),
        response);
  }

  @Test
  public void test_GetWallet_WhenWalletGroupNotFound_Return404() throws Exception {
    Mockito.doThrow(new NotFoundException("walletGroupId", WalletGroup.class.getSimpleName()))
        .when(walletGroupService)
        .validateExistWalletGroup("walletGroupId");
    mockMvc
        .perform(
            get(
                "/api/v1/wallet-groups/{wallet-group-id}/wallets/{wallet-id}",
                "walletGroupId",
                "walletId"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_GetWallet_WhenWalletNotFound_Return404() throws Exception {
    String userId = securityUtil.getUserId();;
    Mockito.doNothing()
        .when(walletGroupService)
        .validateExistWalletGroup("walletGroupId");
    Mockito.doThrow(new NotFoundException("walletId", Wallet.class.getSimpleName()))
            .when(walletService)
                .get(userId, "walletId");
    mockMvc
        .perform(
            get(
                "/api/v1/wallet-groups/{wallet-group-id}/wallets/{wallet-id}",
                "walletGroupId",
                "walletId"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_ListWallet_Return200AndResponseBody() throws Exception {
    Wallet wallet = mockWalletEntity();
    Mockito.when(walletService.list("userId", "walletGroupId"))
            .thenReturn(
                    Stream.of(wallet).map(WalletResponse::from).collect(Collectors.toList()));
    MvcResult mvcResult = mockMvc.perform(
            get(
                    "/api/v1/wallet-groups/{walletGroupId}/wallets",
                    "walletGroupId")).andReturn();

    int status = mvcResult.getResponse().getStatus();
    String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(status, 200);
    Assertions.assertEquals(
            response, objectMapper.writeValueAsString(walletGroupController.list()));
  }

  @Test
  public void test_UpdateWallet_WhenInputValid_Return200AndResponseBody() throws Exception {
    WalletRequest request = mockWalletRequest();
    Wallet wallet = mockWalletEntity();
    Mockito.when(walletService.update("walletId", "walletGroupId", securityUtil.getUserId(), request))
            .thenReturn(WalletResponse.from(wallet));
    MvcResult mvcResult =
            mockMvc
                    .perform(
                            put("/api/v1/wallet-groups/{walletGroupId}/wallets/{walletId}",
                                    "walletGroupId",
                                    "walletId")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(request)))
                    .andReturn();

    int status = mvcResult.getResponse().getStatus();
    String response = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

    Assertions.assertEquals(200, status);
    Assertions.assertEquals(
            response,
            objectMapper.writeValueAsString(walletGroupController.updateWallet("walletGroupId", "walletId", request)));
  }

  @Test
  public void test_UpdateWallet_WhenInputInvalid_Return400() throws Exception {
    WalletRequest request = mockWalletRequest();
    request.setName(null);
    mockMvc
            .perform(
                    put("/api/v1/wallet-groups/{walletGroupId}/wallets/{walletId}",
                            "walletGroupId",
                            "walletId")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
  }
}
