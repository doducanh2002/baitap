package org.squad3.library.user.persistance.impl;

import lombok.RequiredArgsConstructor;
import org.squad3.library.shared.RepositoryConverter;
import org.squad3.library.user.Role;
import org.squad3.library.user.persistance.entites.RoleEntity;
import org.squad3.library.user.persistance.repositories.RoleRepository;
import org.squad3.library.user.ports.RoleRepositoryService;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleRepositoryServiceImpl implements RoleRepositoryService {

    private final RoleRepository roleRepository;
    private final RepositoryConverter<RoleEntity, Role> roleRepositoryConverter;

    @Override
    public Optional<Role> getRoleByRoleName(String role) {
        final RoleEntity roleEntity = roleRepository.findByRole(role).orElse(null);
        return Optional.ofNullable(roleRepositoryConverter.mapToEntity(roleEntity));
    }
}
