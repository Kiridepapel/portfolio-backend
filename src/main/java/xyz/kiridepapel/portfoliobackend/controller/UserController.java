package xyz.kiridepapel.portfoliobackend.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.kiridepapel.portfoliobackend.dto.ResendDTO.ResendRequestDTO;
import xyz.kiridepapel.portfoliobackend.dto.ResponseDTO;
import xyz.kiridepapel.portfoliobackend.impl.ResendServiceImpl;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = { "https://kiridepapel.vercel.app", "http://localhost:4200" })
public class UserController {
  @Autowired
  private ResendServiceImpl resendServiceImpl;

  @GetMapping("/test")
  public ResponseEntity<ResponseDTO> test() {
      return new ResponseEntity<ResponseDTO>(new ResponseDTO("Production: " + resendServiceImpl.APP_PRODUCTION, 200), HttpStatus.OK);
  }

  @GetMapping("/send-email")
  // @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<ResponseDTO> sendEmail(@RequestBody ResendRequestDTO rq) {
      ResponseDTO response = resendServiceImpl.sendEmail(rq);
      return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
  }
  
}
