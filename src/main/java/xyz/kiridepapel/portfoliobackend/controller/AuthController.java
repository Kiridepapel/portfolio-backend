package xyz.kiridepapel.portfoliobackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.kiridepapel.portfoliobackend.dto.AuthDTO.AuthResponseDTO;
import xyz.kiridepapel.portfoliobackend.dto.AuthDTO.LoginRequestDTO;
import xyz.kiridepapel.portfoliobackend.dto.AuthDTO.RegisterRequestDTO;

import xyz.kiridepapel.portfoliobackend.impl.AuthServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = { "https://kiridepapel.vercel.app", "http://localhost:4200" }, allowedHeaders = "**")
public class AuthController {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        return new ResponseEntity<AuthResponseDTO>(authServiceImpl.login(requestDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        return new ResponseEntity<AuthResponseDTO>(authServiceImpl.register(requestDTO), HttpStatus.OK);
    }
}
