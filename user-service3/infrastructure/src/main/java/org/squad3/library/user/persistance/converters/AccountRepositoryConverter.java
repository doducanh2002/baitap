package org.squad3.library.user.persistance.converters;

import org.squad3.library.shared.RepositoryConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.persistance.entites.AccountEntity;

import java.util.Optional;

public class AccountRepositoryConverter implements RepositoryConverter<AccountEntity, Account> {
    @Override
    public AccountEntity mapToTable(Account accountPersistence) {
        return Optional.ofNullable(accountPersistence)
                .map(ap -> {
                    return AccountEntity.builder()
                            .username(ap.getUsername())
                            .password(ap.getPassword())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public Account mapToEntity(AccountEntity accountTable) {
        return Optional.ofNullable(accountTable)
                .map(at -> {
                    return Account.builder()
                            .id(at.getId())
                            .username(at.getUsername())
                            .password(at.getPassword())
                            .build();
                })
                .orElse(null);
    }
}
