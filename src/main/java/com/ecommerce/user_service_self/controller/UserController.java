package com.ecommerce.user_service_self.controller;

import com.ecommerce.user_service_self.DTO.LoginRequestDto;
import com.ecommerce.user_service_self.DTO.SignUpRequestDto;
import com.ecommerce.user_service_self.DTO.UserDto;
import com.ecommerce.user_service_self.models.User;
import com.ecommerce.user_service_self.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;


    public UserController(UserService userService){
        this.userService=userService;

    }
    @PostMapping("/login")
    public String  login(@RequestBody LoginRequestDto requestDto){
        String email=requestDto.getEmail();
        String password=requestDto.getPassword();
        return userService.logIn(email,password);
    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws JsonProcessingException {
        return userService.SignUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(),signUpRequestDto.getPassword());
    }
    @PostMapping("/logout/{value}")
    public ResponseEntity<String> logout(@PathVariable String value){
        if(userService.logOut(value).equals("User Logged Out"))
            return new ResponseEntity<String>("Logged Out", HttpStatus.OK);
        else
            return new ResponseEntity<String>("Invalid Token", HttpStatus.BAD_REQUEST);
    }
    //Created a new endpoint to validate the token and return the user details if the token is valid and not expired
    //else return null
    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String  token){
        return UserDto.from(userService.validateToken(token));
    }
}
