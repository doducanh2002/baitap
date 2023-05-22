package org.up.finance.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.up.finance.entity.Wallet;
import org.up.finance.repository.base.BaseRepository;

import java.util.Optional;

@Repository
public interface WalletRepository extends BaseRepository<Wallet> {

  boolean existsByIdAndUserId(String id, String userId);

  Optional<Wallet> findByUserIdAndId(String userId, String walletId);

  List<Wallet> findByUserIdAndWalletGroupId(String userId, String walletGroupId);

  Optional<Wallet> findByIdAndWalletGroupIdAndUserId(String walletId, String walletGroupId, String userId);

}
