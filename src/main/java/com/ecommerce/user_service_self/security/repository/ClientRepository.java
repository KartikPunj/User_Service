package com.ecommerce.user_service_self.security.repository;

import java.util.Optional;


import com.ecommerce.user_service_self.security.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);
}
