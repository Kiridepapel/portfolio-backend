package xyz.kiridepapel.portfoliobackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    private String token;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponseDTO {
        private String token;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequestDTO {
        private String username;
        private String password;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequestDTO {
        private String username;
        private String password;
        private String img;
    }
}
