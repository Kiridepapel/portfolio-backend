package xyz.kiridepapel.portfoliobackend.exception;

public class JwtExceptions {

    public static class UsernameNotFoundException extends RuntimeException {
        public UsernameNotFoundException() {
            super("Username not found");
        }
    }

    public static class NoTokenException extends RuntimeException {
        public NoTokenException() {
            super("No token provided");
        }
    }

    public static class BlacklistedTokenException extends RuntimeException {
        public BlacklistedTokenException() {
            super("Token is blacklisted");
        }
    }

    public static class ExpiredTokenException extends RuntimeException {
        public ExpiredTokenException() {
            super("Token has expired");
        }
    }

    public static class MalformedTokenException extends RuntimeException {
        public MalformedTokenException() {
            super("Token is malformed");
        }
    }

    public static class SignatureTokenException extends RuntimeException {
        public SignatureTokenException() {
            super("Token signature is invalid");
        }
    }
}
