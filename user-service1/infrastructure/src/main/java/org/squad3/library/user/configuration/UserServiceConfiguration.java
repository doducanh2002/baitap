package org.squad3.library.user.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.squad3.library.shared.RestDTOConverter;
import org.squad3.library.user.Account;
import org.squad3.library.user.Role;
import org.squad3.library.user.delivery.converters.AccountRestDTOConverter;
import org.squad3.library.user.delivery.converters.UserRestDTOConverter;
import org.squad3.library.user.persistance.converters.AccountRepositoryConverter;
import org.squad3.library.user.persistance.converters.RoleRepositoryConverter;
import org.squad3.library.user.persistance.converters.UserRepositoryConverter;
import org.squad3.library.user.persistance.entites.AccountEntity;
import org.squad3.library.user.persistance.entites.RoleEntity;
import org.squad3.library.user.persistance.impl.UserRepositoryServiceImpl;
import org.squad3.library.user.persistance.repositories.AccountRepository;
import org.squad3.library.user.persistance.repositories.RoleRepository;
import org.squad3.library.user.persistance.repositories.UserRepository;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.GetUserNameUseCase;
import org.squad3.library.user.usecase.impl.GetUserNameUseCaseImpl;

@Configuration
@RequiredArgsConstructor
public class UserServiceConfiguration {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;


    @Bean
    public UserRepositoryConverter getAccountRepositoryConverter() {

        return new UserRepositoryConverter(createAccountRepositoryConverter(), createRoleRepositoryConverter());
    }

    @Bean
    public RestDTOConverter getAccountRestDTOConverter() {
        return new UserRestDTOConverter();
    }


    @Bean
    public UserRepositoryService getUserNameRepositoryService() {
        return new UserRepositoryServiceImpl(
                userRepository,
                getAccountRepositoryConverter()
        );
    }

    @Bean
    public GetUserNameUseCase getUserNameUseCase() {
        return new GetUserNameUseCaseImpl(getUserNameRepositoryService());
    }

    @Bean
    public AccountRepositoryConverter createAccountRepositoryConverter() {
        return new AccountRepositoryConverter();
    }

    @Bean
    public RoleRepositoryConverter createRoleRepositoryConverter() {
        return new RoleRepositoryConverter();
    }
}
