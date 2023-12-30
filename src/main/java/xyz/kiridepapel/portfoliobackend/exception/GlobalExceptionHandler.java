package xyz.kiridepapel.portfoliobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import xyz.kiridepapel.portfoliobackend.dto.ResponseDTO;
import xyz.kiridepapel.portfoliobackend.exception.JwtExceptions.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Access Exceptions

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseDTO response = new ResponseDTO(
            ex.getMessage(), 401);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // JWT Exceptions

    @ExceptionHandler(NoTokenException.class)
    public ResponseEntity<ResponseDTO> handleNoTokenException(NoTokenException ex) {
        ResponseDTO response = new ResponseDTO(
            ex.getMessage(), 401);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @ExceptionHandler(BlacklistedTokenException.class)
    public ResponseEntity<ResponseDTO> handleBlacklistedTokenException(BlacklistedTokenException ex) {
        ResponseDTO response = new ResponseDTO(
            ex.getMessage(), 401);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @ExceptionHandler(SignatureTokenException.class)
    public ResponseEntity<ResponseDTO> handleSignatureTokenException(SignatureTokenException ex) {
        ResponseDTO response = new ResponseDTO(
            ex.getMessage(), 401);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @ExceptionHandler(MalformedTokenException.class)
    public ResponseEntity<ResponseDTO> handleMalformedTokenException(MalformedTokenException ex) {
        ResponseDTO response = new ResponseDTO(
            ex.getMessage(), 401);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ResponseDTO> handleExpiredTokenException(ExpiredTokenException ex) {
        ResponseDTO response = new ResponseDTO(
            ex.getMessage(), 401);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ResponseDTO response = new ResponseDTO(
            ex.getMessage(), 401);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
