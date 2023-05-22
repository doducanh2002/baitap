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
import org.up.finance.dto.request.WalletGroupRequest;
import org.up.finance.dto.response.WalletGroupResponse;
import org.up.finance.entity.WalletGroup;
import org.up.finance.exception.WalletGroupParentConstraintException;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.WalletGroupRepository;

import java.util.List;
import java.util.Optional;

@WebMvcTest(WalletGroupService.class)
@ContextConfiguration(classes = {FinanceServiceTestConfiguration.class, RedisConfiguration.class})
public class WalletGroupServiceTest {

  @Autowired
  private WalletGroupService walletGroupService;
  @MockBean
  private WalletGroupRepository walletGroupRepository;

  private WalletGroupRequest mockWalletGroupRequest() {
    WalletGroupRequest request = new WalletGroupRequest();
    request.setName("Đầu tư chứng khoán");
    request.setParentId(null);
    return request;
  }

  private WalletGroup mockWalletGroupEntity() {
    return WalletGroup.from("userId", mockWalletGroupRequest());
  }

  @Test
  public void test_Create_WhenInputValid_Successful() {
    WalletGroupRequest mockRequest = mockWalletGroupRequest();
    WalletGroup mockEntity = mockWalletGroupEntity();
    Mockito.when(walletGroupRepository.save(mockEntity)).thenReturn(mockEntity);
    WalletGroupResponse response = walletGroupService.create("userId", mockRequest);
    Assertions.assertEquals(mockRequest.getParentId(), response.getParentId());
    Assertions.assertEquals(mockRequest.getName(), response.getName());
  }

  @Test
  public void test_Create_WhenParentIdNotFound_ThrowNotFoundException() {
    WalletGroupRequest mockRequest = mockWalletGroupRequest();
    mockRequest.setParentId("abcd");
    Assertions.assertThrows(NotFoundException.class, () -> walletGroupService.create("1", mockRequest));
  }

  @Test
  public void test_DeleteById_WhenWalletGroupNotFound_ThrowNotFoundException() {
    Mockito.when(walletGroupRepository.existsById("walletGroupId")).thenReturn(false);
    Assertions.assertThrows(NotFoundException.class,
          () -> walletGroupService.deleteById("walletGroupId"));
  }

  @Test
  public void test_DeleteById_WhenExistSubWallet_ThrowParentConstraintException() {
    Mockito.when(walletGroupRepository.existsById("walletGroupId")).thenReturn(true);
    Mockito.when(walletGroupRepository.existSubWalletGroupById("walletGroupId")).thenReturn(true);
    Assertions.assertThrows(WalletGroupParentConstraintException.class,
          () -> walletGroupService.deleteById("walletGroupId"));
  }

  @Test
  public void test_List_Successful() {
    WalletGroup walletGroup = mockWalletGroupEntity();

    Mockito.when(walletGroupRepository.findByUserId("userId")).thenReturn(List.of(walletGroup));
    Assertions.assertEquals(1, walletGroupService.list("userId").size());
    Assertions.assertEquals(WalletGroupResponse.from(walletGroup), walletGroupService.list("userId").get(0));
  }

  @Test
  public void test_Get_WhenInputValid_Successful() {
    WalletGroup walletGroup = mockWalletGroupEntity();
    Mockito.when(walletGroupRepository.find("userId", "walletGroupId"))
          .thenReturn(Optional.of(walletGroup));
    WalletGroupResponse response = walletGroupService.get("userId", "walletGroupId");
    Assertions.assertEquals(walletGroup.getParentId(), response.getParentId());
    Assertions.assertEquals(walletGroup.getName(), response.getName());
  }

  @Test
  public void test_Get_WhenWalletGroupNotFound_ThrowNotFoundException() {
    Mockito.when(walletGroupRepository.find("userId", "walletGroupId"))
          .thenThrow(new NotFoundException("walletGroupId", WalletGroup.class.getSimpleName()));
    Assertions.assertThrows(NotFoundException.class,
          () -> walletGroupService.get("userId", "walletGroupId"));
  }

  @Test
  public void test_Update_WhenInputValid_Successful() {
    WalletGroupRequest request = mockWalletGroupRequest();
    WalletGroup entity = mockWalletGroupEntity();
    Mockito.when(walletGroupRepository.find("userId", "walletGroupId"))
          .thenReturn(Optional.of(entity));
    Mockito.when(walletGroupRepository.save(entity)).thenReturn(entity);
    WalletGroupResponse response = walletGroupService.update("userId", "walletGroupId", request);
    Assertions.assertEquals(request.getParentId(), response.getParentId());
    Assertions.assertEquals(request.getName(), response.getName());
  }

  @Test
  public void test_Update_WhenParentNotFound_ThrowNotFoundException() {
    WalletGroupRequest mockRequest = mockWalletGroupRequest();
    mockRequest.setParentId("abcd");
    Assertions.assertThrows(NotFoundException.class, () ->
          walletGroupService.update("userId", "walletGroupId", mockRequest));
  }

  @Test
  public void test_Update_WhenNotFoundWalletGroup_ThrowNotFoundException() {
    WalletGroupRequest request = mockWalletGroupRequest();
    Mockito.when(walletGroupRepository.find("userId", "walletGroupId"))
          .thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () ->
          walletGroupService.update("userId", "walletGroupId", request));
  }
}
