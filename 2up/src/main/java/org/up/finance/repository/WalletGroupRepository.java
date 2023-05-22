package org.up.finance.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.up.finance.entity.WalletGroup;
import org.up.finance.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletGroupRepository extends BaseRepository<WalletGroup> {

    @Query(value = "select case when exists(" +
            "select 1 from wallet_group wg where wg.parent_id = :id)" +
            "then 'true' else 'false' end", nativeQuery = true)
    boolean existSubWalletGroupById(String id);


    List<WalletGroup> findByUserId(String userId);

    @Query("select wg from WalletGroup wg where wg.id = :id and wg.userId = :userId")
    Optional<WalletGroup> find(String userId, String id);

}
