package com.learning.secureelect.service;

import com.learning.secureelect.dto.LoginRequest;
import com.learning.secureelect.dto.RegisterRequest;
import com.learning.secureelect.entity.User;
import com.learning.secureelect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already registered!";
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(hashedPassword);
        user.setRole("USER");
        user.setVerified(false);
        user.setVoted(false);

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {
        Optional<User> userEntity = userRepository.findByEmail(request.getEmail());

        if( !userEntity.isPresent()) {
            return "Invalid email or password";
        }

        User user = userEntity.get();

        if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            return "Invalid email or password";
        }

        return "Login successful";
    }

}
