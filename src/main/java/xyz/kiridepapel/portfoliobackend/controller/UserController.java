package xyz.kiridepapel.portfoliobackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import xyz.kiridepapel.portfoliobackend.exception.CustomExceptions.NotFoundException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = { "https://kiridepapel.vercel.app", "http://127.0.0.1" })
public class UserController {

  @GetMapping("/test")
  public ResponseEntity<String> test() {
    // int as = 1;
    // if (as == 1) {
    // throw new NotFoundException("User not found");
    // }
    return new ResponseEntity<String>("Hello World!", HttpStatus.OK);
  }
}
