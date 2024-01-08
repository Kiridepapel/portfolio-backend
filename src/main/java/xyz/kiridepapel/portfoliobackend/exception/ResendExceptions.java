package xyz.kiridepapel.portfoliobackend.exception;

public class ResendExceptions {
    
    public static class FailSendEmail extends RuntimeException {
        public FailSendEmail(String message) {
            super(message);
        }
    }
}
