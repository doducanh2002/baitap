package org.squad3.library.user.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.squad3.library.user.delivery.converters.AccountRestDTOConverter;
import org.squad3.library.user.delivery.converters.UserRestDTOConverter;
import org.squad3.library.user.persistance.converters.AccountRepositoryConverter;
import org.squad3.library.user.persistance.converters.RoleRepositoryConverter;
import org.squad3.library.user.persistance.converters.UserRepositoryConverter;
import org.squad3.library.user.persistance.impl.AccountRepositoryServiceImpl;
import org.squad3.library.user.persistance.impl.RoleRepositoryServiceImpl;
import org.squad3.library.user.persistance.impl.UserRepositoryServiceImpl;
import org.squad3.library.user.persistance.repositories.AccountRepository;
import org.squad3.library.user.persistance.repositories.RoleRepository;
import org.squad3.library.user.persistance.repositories.UserRepository;
import org.squad3.library.user.ports.AccountRepositoryService;
import org.squad3.library.user.ports.RoleRepositoryService;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.CreateUserUseCase;
import org.squad3.library.user.usecase.GetUserByUsernameOrGmailUseCase;
import org.squad3.library.user.usecase.impl.CreateUserUseCaseImpl;
import org.squad3.library.user.usecase.impl.GetUserByUsernameOrGmailUseCaseImpl;

@Configuration
@RequiredArgsConstructor
public class UserServiceConfiguration {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;

    @Bean
    public UserRestDTOConverter createUserRestDTOConverter() {
        return new UserRestDTOConverter(createAccountRestDTOConverter());
    }

    @Bean
    public AccountRestDTOConverter createAccountRestDTOConverter() {
        return new AccountRestDTOConverter();
    }

    @Bean
    public UserRepositoryConverter createUserRepositoryConverter() {
        return new UserRepositoryConverter(createAccountRepositoryConverter(), createRoleRepositoryConverter());
    }

    @Bean
    public RoleRepositoryConverter createRoleRepositoryConverter() {
        return new RoleRepositoryConverter();
    }

    @Bean
    public AccountRepositoryConverter createAccountRepositoryConverter() {
        return new AccountRepositoryConverter();
    }

    @Bean
    public UserRepositoryService createUserRepositoryService() {
        return new UserRepositoryServiceImpl(userRepository, createUserRepositoryConverter());
    }

    @Bean
    public RoleRepositoryService createRoleRepositoryService(){
        return new RoleRepositoryServiceImpl(roleRepository, createRoleRepositoryConverter());
    }

    @Bean
    public AccountRepositoryService createAccountRepositoryService(){
        return new AccountRepositoryServiceImpl(accountRepository);
    }

    @Bean
    public GetUserByUsernameOrGmailUseCase createGetUserByUsernameOrEmailUseCase(){
        return new GetUserByUsernameOrGmailUseCaseImpl(createUserRepositoryService());
    }

    @Bean
    public CreateUserUseCase createCreateUserUseCase() {
        return new CreateUserUseCaseImpl(
                createUserRepositoryService(),
                createRoleRepositoryService(),
                createAccountRepositoryService()
                );
    }


}
