package com.github;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/*
    TODO
        Создайте bean для создания мока
*/
@Profile("test")
@Configuration
public class NameServiceTestConfiguration {
    @Bean
    FlowerController flowerControllerBean(){
        return Mockito.mock(FlowerController.class);
    }
    @Bean
    FlowerService flowerServiceBean(){
        return Mockito.mock(FlowerService.class);
    }

    @Bean
    MessageController messageControllerBean(){
        return Mockito.mock(MessageController.class);
    }
    @Bean
    MessageService messageServiceBean(){
        return Mockito.mock(MessageService.class);
    }

    @Bean
    UserService userServiceBean(){
        return Mockito.mock(UserService.class);
    }
}