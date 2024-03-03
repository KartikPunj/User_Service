package com.ecommerce.user_service_self.configs;

import com.ecommerce.user_service_self.repository.TokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
public class configurations {
        //this class is used to create beans of the classes which are used in the application
        //here we have created a bean of BCryptPasswordEncoder to encode the password of the user
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(16);
    }
//    @Bean
//    public TokenRepository tokenRepository(){
//        return new TokenRepository();
//    }
}
