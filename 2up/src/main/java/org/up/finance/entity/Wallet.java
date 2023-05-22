package org.up.finance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.up.finance.dto.request.WalletRequest;
import org.up.finance.entity.base.BaseEntityWithUpdater;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "wallet")
@Entity
public class Wallet extends BaseEntityWithUpdater {

    @Column(nullable = false)
    public String name;

    public Long amount;

    public String description;

    @Column(nullable = false)
    public String walletGroupId;

    @Column(nullable = false)
    public String userId;

    public static Wallet of(String userId, String walletGroupId, WalletRequest request) {
        Wallet wallet = new Wallet();
        wallet.setName(request.getName());
        wallet.setAmount(Objects.isNull(request.getAmount()) ? 0 : request.getAmount());
        wallet.setDescription(request.getDescription());
        wallet.setWalletGroupId(walletGroupId);
        wallet.setUserId(userId);
        return wallet;
    }
}
