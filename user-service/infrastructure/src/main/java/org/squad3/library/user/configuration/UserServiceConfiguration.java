package org.squad3.library.user.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.squad3.library.shared.RestDTOConverter;
import org.squad3.library.user.delivery.converters.UserRestDTOConverter;
import org.squad3.library.user.persistance.converters.UserRepositoryConverter;
import org.squad3.library.user.persistance.impl.UserRepositoryServiceImpl;
import org.squad3.library.user.persistance.repositories.UserRepository;
import org.squad3.library.user.ports.UserRepositoryService;
import org.squad3.library.user.usecase.GetUserNameUseCase;
import org.squad3.library.user.usecase.impl.GetUserNameUseCaseImpl;

@Configuration
@RequiredArgsConstructor
public class UserServiceConfiguration {

    private final UserRepository userRepository;

    @Bean
    public UserRepositoryConverter getAccountRepositoryConverter() {
        return new UserRepositoryConverter();//tìm hàm constructor
    }

    @Bean
    public RestDTOConverter getAccountRestDTOConverter() {
        return new UserRestDTOConverter();
    }


    @Bean
    public UserRepositoryService getUserNameRepositoryService(){
        return new UserRepositoryServiceImpl(
                userRepository,
                getAccountRepositoryConverter()
        );
    }

    @Bean
    public GetUserNameUseCase getUserNameUseCase() {
        return new GetUserNameUseCaseImpl(getUserNameRepositoryService());
    }
}
