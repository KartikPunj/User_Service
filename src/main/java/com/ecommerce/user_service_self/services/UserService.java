package com.ecommerce.user_service_self.services;

import com.ecommerce.user_service_self.DTO.SendEmailEventDto;
import com.ecommerce.user_service_self.models.Token;
import com.ecommerce.user_service_self.models.User;
import com.ecommerce.user_service_self.repository.TokenRepository;
import com.ecommerce.user_service_self.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
@Getter
@Setter
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;
    private KafkaTemplate kafkaTemplate;
    private ObjectMapper objectMapper;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                       TokenRepository tokenRepository,KafkaTemplate kafkaTemplate,ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder= bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate=kafkaTemplate;
        this.objectMapper=objectMapper;

    }

    public User SignUp(String name,String email, String password) throws JsonProcessingException {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User user = userRepository.save(u);
        //code to send email using kafka
        //created a new topic sendEmail
        SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();
        sendEmailEventDto.setTo(email);
        sendEmailEventDto.setFrom("");
        sendEmailEventDto.setSubject("Welcome to Ecommerce");
        sendEmailEventDto.setBody("Dear " + name + ",\n" +
                "Welcome to Ecommerce \n" +
                "Thanks for signing up with us. We are glad to have you as a part of our community. " +
                "We are committed to provide you with the best shopping experience. " +
                "We are looking forward to serve you. \n \n \n" +
                "Thanks and Regards, \n " +
                "Ecommerce Team");

        kafkaTemplate.send("sendEmail",
                objectMapper.writeValueAsString(sendEmailEventDto)//object mapper creates a json string from the object
        );

        return user;


    }

    public String logIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return "User Not Found";
        }
        User user = userOptional.get();
        if (bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            return getJwsToken(user);
        }

        else
            return "User Not Found";
    }

    public String getJwsToken(User user) {
        //Created a key using Jwts.sig.HS256.key().build() for jwt token
        SecretKey key = Jwts.SIG.HS256.key().build();
        //created a map to store the claims of the jwt token which will be used to create the token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("IssuedAt", new Date());
        claims.put("Expiration", new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)); //10 hours
        //creation of jwt token using jwts.builder
        String jws = Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
        Token token = new Token();
        token.setValue(jws);
        token.setUser(user);
        token.setIssuedAt(new Date());
        token.setExpiryAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
        tokenRepository.save(token);
        return jws;
    }
    public String logOut(String value) {
        //Decryption of the token is not required as we have already stored the encrypted token string in value
        // field of token table
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(value, false);

        if (tokenOptional.isEmpty()) {
            return "Invalid Token";
        } else {
            Token token1 = tokenOptional.get();
            token1.setDeleted(true);
            tokenRepository.save(token1);
            return "User Logged Out";
        }
    }

        public User validateToken(String token) {
            Optional<Token> tkn = tokenRepository.
                    findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token, false, new Date());

            if (tkn.isEmpty()) {
                return null;
            }

//            TODO 2: Instead of validating via the DB, as the token is now a JWT
//            token, validate using JWT

            return tkn.get().getUser();
        }
    }

