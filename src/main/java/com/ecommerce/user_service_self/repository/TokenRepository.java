package com.ecommerce.user_service_self.repository;

import com.ecommerce.user_service_self.models.Token;
import com.ecommerce.user_service_self.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    @Override
    Token save(Token token);

    Optional<Token> findByValueAndDeletedEquals(String value, boolean falseValue);

    Optional<Token> findByValueAndDeletedEqualsAndExpiryAtGreaterThan(String value, boolean deleted, Date expiryGreaterThan);
}
