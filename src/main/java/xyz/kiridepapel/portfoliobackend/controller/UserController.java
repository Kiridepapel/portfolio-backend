package xyz.kiridepapel.portfoliobackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "**")
public class UserController {

  @GetMapping("/test")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> test() {
      return new ResponseEntity<>("Hello World!", HttpStatus.OK);
  }
}
