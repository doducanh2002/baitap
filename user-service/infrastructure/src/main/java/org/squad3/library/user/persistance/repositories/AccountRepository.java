package org.squad3.library.user.persistance.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.squad3.library.user.persistance.entites.AccountEntity;
import org.squad3.library.user.persistance.entites.UserEntity;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

}
