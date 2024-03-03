package com.ecommerce.user_service_self.security.models;

import com.ecommerce.user_service_self.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

import javax.swing.*;
@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    private Role role;
    private String authority;
    public CustomGrantedAuthority(){
        return;
    }
    public CustomGrantedAuthority(Role role) {
//        this.role = role;
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
