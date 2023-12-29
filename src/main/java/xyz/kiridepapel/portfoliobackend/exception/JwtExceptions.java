package xyz.kiridepapel.portfoliobackend.exception;

public class JwtExceptions {

    public static class NoTokenException extends RuntimeException {
        public NoTokenException(String message) {
            super(message);
        }
    }

    public static class BlacklistedTokenException extends RuntimeException {
        public BlacklistedTokenException(String message) {
            super(message);
        }
    }

    public static class SignatureTokenException extends RuntimeException {
        public SignatureTokenException(String message) {
            super(message);
        }
    }

    public static class MalformedTokenException extends RuntimeException {
        public MalformedTokenException(String message) {
            super(message);
        }
    }

    public static class ExpiredTokenException extends RuntimeException {
        public ExpiredTokenException(String message) {
            super(message);
        }
    }

    public static class UsernameNotFoundException extends RuntimeException {
        public UsernameNotFoundException(String message) {
            super(message);
        }
    }
}
