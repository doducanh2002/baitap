package org.squad3.library.user.persistance.impl;

import lombok.RequiredArgsConstructor;
import org.squad3.library.user.persistance.repositories.AccountRepository;
import org.squad3.library.user.ports.AccountRepositoryService;

@RequiredArgsConstructor
public class AccountRepositoryServiceImpl implements AccountRepositoryService {

    private final AccountRepository accountRepository;

    @Override
    public boolean isUsernameExisted(String username) {
        return accountRepository.existsAccountEntityByUsername(username);
    }
}
