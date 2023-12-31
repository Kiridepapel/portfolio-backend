package xyz.kiridepapel.portfoliobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResendDTO {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResendRequestDTO {
        private String title;
        private String email;
        private String message;
        private Boolean sendMeCopy;
    }
}
