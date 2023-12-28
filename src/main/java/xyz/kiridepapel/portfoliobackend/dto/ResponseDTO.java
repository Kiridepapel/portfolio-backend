package xyz.kiridepapel.portfoliobackend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private Integer statusCode;
}
