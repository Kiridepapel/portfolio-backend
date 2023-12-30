package xyz.kiridepapel.portfoliobackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import xyz.kiridepapel.portfoliobackend.dto.AuthResponseDTO;
import xyz.kiridepapel.portfoliobackend.dto.LoginRequestDTO;
import xyz.kiridepapel.portfoliobackend.dto.RegisterRequestDTO;
import xyz.kiridepapel.portfoliobackend.impl.AuthServiceImpl;

import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "**")
@RequiredArgsConstructor
public class AuthController {

  private final AuthServiceImpl authServiceImpl;

  @Value("${JWT_SECRET_KEY}")
  private String jwtSecretKey;

  @Value("${JWT_TIME_EXPIRATION}")
  private String jwtTimeExpiration;

  @GetMapping("/test")
  public ResponseEntity<?> test() {
    return new ResponseEntity<>("Hello World!: " + jwtSecretKey + " - " + jwtTimeExpiration, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
    return new ResponseEntity<AuthResponseDTO>(authServiceImpl.login(requestDTO), HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
    return new ResponseEntity<AuthResponseDTO>(authServiceImpl.register(requestDTO), HttpStatus.OK);
  }
}
