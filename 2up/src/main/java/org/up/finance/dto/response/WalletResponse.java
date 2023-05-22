package org.up.finance.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.up.finance.entity.Wallet;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WalletResponse {

    private String id;

    private String name;

    private Long amount;

    private String description;

    private String walletGroupId;

    public static WalletResponse from(Wallet wallet) {
        WalletResponse response = new WalletResponse();
        response.setId(wallet.getId());
        response.setName(wallet.getName());
        response.setAmount(wallet.getAmount());
        response.setDescription(wallet.getDescription());
        response.setWalletGroupId(wallet.getWalletGroupId());
        return response;
    }
}
