package org.squad3.library.user.persistance.converters;

import org.squad3.library.shared.RepositoryConverter;
import org.squad3.library.user.Role;
import org.squad3.library.user.persistance.entites.RoleEntity;

import java.util.Optional;

public class RoleRepositoryConverter implements RepositoryConverter<RoleEntity, Role> {

    @Override
    public RoleEntity mapToTable(Role rolePersistence) {
        return Optional.ofNullable(rolePersistence)
                .map(rp -> {
                    return RoleEntity.builder()
                            .id(rp.getId())
                            .role(rp.getRoleName())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public Role mapToEntity(RoleEntity roleTable) {
        return Optional.ofNullable(roleTable)
                .map(rt -> {
                    return Role.builder()
                            .id(rt.getId())
                            .roleName(rt.getRole())
                            .build();
                })
                .orElse(null);
    }
}
