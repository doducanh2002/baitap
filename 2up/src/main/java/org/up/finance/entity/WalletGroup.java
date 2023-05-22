package org.up.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.up.finance.dto.request.WalletGroupRequest;
import org.up.finance.entity.base.BaseEntityWithUpdater;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "wallet_group")
public class WalletGroup extends BaseEntityWithUpdater {

    @Column(nullable = false)
    private String name;

    private String parentId;


    @Column(nullable = false)
    private String userId;

    public static WalletGroup from(String userId, WalletGroupRequest request) {
        WalletGroup walletGroup = new WalletGroup();
        walletGroup.setName(request.getName());
        walletGroup.setParentId(request.getParentId());
        walletGroup.setUserId(userId);
        return walletGroup;
    }
}
