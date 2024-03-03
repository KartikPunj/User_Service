package com.ecommerce.user_service_self.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfigurations { // this class is used to configure the security of the application and
    // here we are disabling the cors and csrf to make the post requests from the frontend to the backend
    // also we have created a bean of SecurityFilterChain to configure the security of the application
    // we copied from spring security documentation
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> {
                            try {
                                requests
                                        .anyRequest().permitAll() //I have permitted all the requests even if user is not authenticated and logged in
                                        .and().cors().disable()//I have disabled the cors and csrf to make the post requests from the frontend to the backend
                                        // without any issues and errors related to cors and csrf
                                        .csrf().disable();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );


        return http.build();
    }

}
