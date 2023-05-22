package org.up.finance.repository;

import org.springframework.stereotype.Repository;
import org.up.finance.entity.Transaction;
import org.up.finance.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction> {

    List<Transaction> findByUserId(String userId);

    Optional<Transaction> findByIdAndUserId(String id, String userId);
}
