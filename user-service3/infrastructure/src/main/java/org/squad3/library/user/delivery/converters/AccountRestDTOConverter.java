package org.squad3.library.user.delivery.converters;

import org.squad3.library.shared.RestDTOConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.delivery.rest.dto.AccountDTO;

import java.util.Objects;
import java.util.Optional;

public class AccountRestDTOConverter implements RestDTOConverter<AccountDTO, Account> {

    @Override
    public Account mapToEntity(AccountDTO accountDTO) {
        return Optional.ofNullable(accountDTO)
                .map(adto -> {
                    return Account.builder()
                            .username(adto.getUsername())
                            .password(adto.getPassword())
                            .build();
                })
                .orElse(null);
    }

    @Override
    public AccountDTO mapToDTO(Account account) {
        return Optional.ofNullable(account)
                .map(a -> {
                    return AccountDTO.builder()
                            .username(a.getUsername())
                            .password(a.getPassword())
                            .build();
                })
                .orElse(null);
    }
}
