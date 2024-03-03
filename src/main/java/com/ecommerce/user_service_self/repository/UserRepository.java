package com.ecommerce.user_service_self.repository;

import com.ecommerce.user_service_self.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    User save(User user);

    Optional<User> findByEmail(String email);
}
