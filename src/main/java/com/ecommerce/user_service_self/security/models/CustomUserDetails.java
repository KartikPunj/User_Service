package com.ecommerce.user_service_self.security.models;

import com.ecommerce.user_service_self.models.Role;
import com.ecommerce.user_service_self.models.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@JsonDeserialize
public class CustomUserDetails implements UserDetails {
    private User user;
    private List<CustomGrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Long userId;
    @Getter
    private String name;

    public CustomUserDetails(){
        return;
    }

    public CustomUserDetails(User user){
//        this.user = user;
        this.username = user.getEmail();
        this.password = user.getHashedPassword();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        List<CustomGrantedAuthority> customGrantedAuthorities = new ArrayList<>();

        for(Role role: user.getRoles()){
            customGrantedAuthorities.add(new CustomGrantedAuthority(role));
        }
        this.authorities = customGrantedAuthorities;

        this.userId = user.getId();
        this.name = user.getName();
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomGrantedAuthority> customGrantedAuthorities = new ArrayList<>();
//
//        for(Role role: user.getRoles()){
//            customGrantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
//        return customGrantedAuthorities;
        return authorities;
    }

    @Override
    public String getPassword() {
//        return user.getHashedPassword();
        return password;
    }

    @Override
    public String getUsername() {
//        return user.getEmail();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return true;
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return true;
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return true;
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
//        return true;
        return enabled;
    }
}
