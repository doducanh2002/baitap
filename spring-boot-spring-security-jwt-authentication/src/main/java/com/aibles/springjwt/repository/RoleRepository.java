package com.aibles.springjwt.repository;

import java.util.Optional;

import com.aibles.springjwt.data.models.ERole;
import com.aibles.springjwt.data.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(ERole name);
}
