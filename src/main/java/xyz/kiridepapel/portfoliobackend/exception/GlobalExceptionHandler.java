package xyz.kiridepapel.portfoliobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.kiridepapel.portfoliobackend.dto.ResponseDTO;
import xyz.kiridepapel.portfoliobackend.exception.JwtExceptions.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // JWT
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ResponseDTO response = new ResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoTokenException.class)
    public ResponseEntity<ResponseDTO> handleNoTokenException(NoTokenException ex) {
        ResponseDTO response = new ResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlacklistedTokenException.class)
    public ResponseEntity<ResponseDTO> handleBlacklistedTokenException(BlacklistedTokenException ex) {
        ResponseDTO response = new ResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ResponseDTO> handleExpiredTokenException(ExpiredTokenException ex) {
        ResponseDTO response = new ResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MalformedTokenException.class)
    public ResponseEntity<ResponseDTO> handleMalformedTokenException(MalformedTokenException ex) {
        ResponseDTO response = new ResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SignatureTokenException.class)
    public ResponseEntity<ResponseDTO> handleSignatureTokenException(SignatureTokenException ex) {
        ResponseDTO response = new ResponseDTO(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
