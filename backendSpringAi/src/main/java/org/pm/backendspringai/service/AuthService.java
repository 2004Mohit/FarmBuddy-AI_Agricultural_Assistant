package org.pm.backendspringai.service;

import org.pm.backendspringai.dto.AuthResponse;
import org.pm.backendspringai.dto.LoginRequest;
import org.pm.backendspringai.dto.LoginResponse;
import org.pm.backendspringai.dto.RegisterRequest;
import org.pm.backendspringai.entity.User;
import org.pm.backendspringai.exception.EmailAlreadyExistsException;
import org.pm.backendspringai.exception.UsernameAlreadyExistsException;
import org.pm.backendspringai.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                token,
                "Login Successful"
        );
    }

    public AuthResponse register(RegisterRequest request){

        if(userRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException("Email already registered");
        }

        if(userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException("Username already taken");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.username(),
                request.email(),
                hashedPassword
        );

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }
}
