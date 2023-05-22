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
import org.up.finance.dto.request.WalletRequest;
import org.up.finance.dto.response.WalletResponse;
import org.up.finance.entity.Wallet;
import org.up.finance.exception.base.NotFoundException;
import org.up.finance.repository.WalletRepository;

import java.util.List;
import java.util.Optional;

@WebMvcTest(WalletGroupService.class)
@ContextConfiguration(classes = {FinanceServiceTestConfiguration.class, RedisConfiguration.class})
public class WalletServiceTest {

    @Autowired
    private WalletService walletService;

    @MockBean
    private WalletRepository walletRepository;

    private WalletRequest mockWalletRequest() {
        WalletRequest request = new WalletRequest();
        request.setName("Etherium");
        request.setDescription("Quỹ đầu tư ETH");
        request.setAmount(200000L);
        return request;
    }

    private Wallet mockWalletEntity() {
        return Wallet.of("userId", "walletGroupId", mockWalletRequest());
    }

    @Test
    public void test_Create_WhenInputValid_Successful() {
        WalletRequest request = mockWalletRequest();
        Wallet wallet = mockWalletEntity();
        Mockito.when(walletRepository.save(wallet)).thenReturn(wallet);

        WalletResponse response = walletService.create("userId", "walletGroupId", request);

        Assertions.assertEquals(request.getName(), response.getName());
        Assertions.assertEquals(request.getAmount(), response.getAmount());
        Assertions.assertEquals(request.getDescription(), response.getDescription());
    }

    @Test
    public void test_Delete_WhenWalletNotFound_ThrowNotFoundException() {
        Mockito.when(walletRepository.existsByIdAndUserId("walletId", "userId"))
                .thenThrow(new NotFoundException("walletId", Wallet.class.getSimpleName()));
        Assertions.assertThrows(NotFoundException.class,
                () -> walletService.delete("userId", "walletId"));
    }

    @Test
    public void test_Get_WhenInputValid_Successfully() {
        Wallet wallet = mockWalletEntity();
        Mockito.when(walletService.get("userId", "walletId"))
                .thenReturn(WalletResponse.from(wallet));
        WalletResponse response = walletService.get("userId", "walletId");
        Assertions.assertEquals(wallet.getWalletGroupId(), response.getWalletGroupId());
        Assertions.assertEquals(wallet.getName(), response.getName());
        Assertions.assertEquals(wallet.getAmount(), response.getAmount());
        Assertions.assertEquals(wallet.getDescription(), response.getDescription());
    }

    @Test
    public void test_Get_WhenWalletNotFound_Return404() {
        Mockito.doThrow(new NotFoundException("walletId", Wallet.class.getSimpleName())).when(walletService)
                .get("userId", "walletId");
        Assertions.assertThrows(NotFoundException.class,
                () -> walletService.get("userId", "walletId"));
    }

    @Test
    public void test_List_Successful() {
        Wallet wallet = mockWalletEntity();

        Mockito.when(walletRepository.findByUserIdAndWalletGroupId("userId", "walletGroupId")).thenReturn(List.of(wallet));
        Assertions.assertEquals(1, walletService.list("userId", "walletGroupId").size());
        Assertions.assertEquals(WalletResponse.from(wallet), walletService.list("userId", "walletGroupId").get(0));
    }

    @Test
    public void test_Update_WhenInputValid_Successful() {
        WalletRequest request = mockWalletRequest();
        Wallet entity = mockWalletEntity();
        Mockito.when(walletRepository.findByIdAndWalletGroupIdAndUserId("walletId", "walletGroupId", "userId"))
                .thenReturn(Optional.of(entity));
        Mockito.when(walletRepository.save(entity)).thenReturn(entity);
        WalletResponse response = walletService.update("walletId", "walletGroupId", "userId", request);
        Assertions.assertEquals(request.getAmount(), response.getAmount());
        Assertions.assertEquals(request.getDescription(), response.getDescription());
        Assertions.assertEquals(request.getName(), response.getName());
    }

    @Test
    public void test_Update_WhenNotFoundWallet_ThrowNotFoundException() {
        WalletRequest request = mockWalletRequest();
        Mockito.when(walletRepository.findByIdAndWalletGroupIdAndUserId("walletId", "walletGroupId", "userId"))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () ->
                walletService.update("userId", "walletId", "walletId", request));
    }
}
