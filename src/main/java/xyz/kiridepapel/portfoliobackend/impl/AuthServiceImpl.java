package xyz.kiridepapel.portfoliobackend.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import xyz.kiridepapel.portfoliobackend.dto.AuthResponseDTO;
import xyz.kiridepapel.portfoliobackend.dto.LoginRequestDTO;
import xyz.kiridepapel.portfoliobackend.dto.RegisterRequestDTO;
import xyz.kiridepapel.portfoliobackend.entity.UserEntity;
import xyz.kiridepapel.portfoliobackend.entity.enums.RoleEnum;
import xyz.kiridepapel.portfoliobackend.repository.UserRepository;

@Service
public class AuthServiceImpl {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtServiceImpl jwtServiceImpl;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthResponseDTO login(LoginRequestDTO request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtServiceImpl.genToken(user);
        return AuthResponseDTO.builder().token(token).build();
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        UserEntity user = UserEntity.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .img(request.getImg())
            .role(RoleEnum.ROLE_ADMIN)
            .build();
        userRepository.save(user);
        return AuthResponseDTO.builder()
            .token(jwtServiceImpl.genToken(user))
            .build();
    }
}
