package org.squad3.library.user.persistance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.squad3.library.user.persistance.entites.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    boolean existsAccountEntityByUsername(String username);
}
