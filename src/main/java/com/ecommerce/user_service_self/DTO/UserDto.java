package com.ecommerce.user_service_self.DTO;

import com.ecommerce.user_service_self.models.Role;
import com.ecommerce.user_service_self.models.User;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class UserDto {
    private String name;
    private String email;
    @ManyToMany
    private List<Role> roles;
    private boolean isEmailVerified;

    public static UserDto from(User user) {
        if (user == null) return null;

        UserDto userDto = new UserDto();
        userDto.email = user.getEmail();
        userDto.name = user.getName();
        userDto.roles = user.getRoles();
        userDto.isEmailVerified = user.isEmail_Verified();

        return userDto;
    }
}
