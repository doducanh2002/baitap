package org.squad3.library.user.persistance.converters;

import org.squad3.library.shared.RepositoryConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.persistance.entites.AccountEntity;

public class AccountRepositoryConverter implements RepositoryConverter<AccountEntity, Account> {
    @Override
    public AccountEntity mapToTable(Account persistenceObject) {
        return AccountEntity.builder()
                .username(persistenceObject.getUsername())
                .password(persistenceObject.getPassword())
                .build();
    }

    @Override
    public Account mapToEntity(AccountEntity tableObject) {
        return Account.builder()
                .id(tableObject.getId())
                .username(tableObject.getUsername())
                .password(tableObject.getPassword())
                .build();
    }
}
