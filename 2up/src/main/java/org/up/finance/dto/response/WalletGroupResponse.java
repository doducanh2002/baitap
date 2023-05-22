package org.up.finance.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.up.finance.entity.WalletGroup;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WalletGroupResponse {

    private String id;

    private String name;

    private String parentId;

    public static WalletGroupResponse from(WalletGroup walletGroup) {
        WalletGroupResponse response = new WalletGroupResponse();
        response.setId(walletGroup.getId());
        response.setName(walletGroup.getName());
        response.setParentId(walletGroup.getParentId());
        return response;
    }
}
