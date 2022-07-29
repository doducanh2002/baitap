package com.aibles.democar.configuration;

import com.aibles.democar.repository.CarRepository;
import com.aibles.democar.service.CarService;
import com.aibles.democar.service.Impl.CarServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.aibles.democar.repository"})
@ComponentScan(basePackages = {"com.aibles.democar.repository"})
public class CarConfiguration {

    @Bean
    public CarService carService(CarRepository carRepository){
        return new CarServiceImpl(carRepository);
    }
}
