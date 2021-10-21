package org.squad3.library.user.persistance.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.squad3.library.user.persistance.entites.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsUserEntityByEmail(String email);

    @EntityGraph(attributePaths = {"accountEntity", "userRoleEntity"})
    Optional<UserEntity> findByAccountEntityUsername(String username);

    Optional<UserEntity> findByEmail(String email);

}
