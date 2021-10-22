package org.squad3.library.user.ports;

import org.squad3.library.user.Role;

import java.util.Optional;

public interface RoleRepositoryService {
    Optional<Role> getRoleByRoleName(String role);
}
