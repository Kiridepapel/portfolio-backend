package xyz.kiridepapel.portfoliobackend.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_email")
public class LogEmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String email;
    private String body;
    private Boolean sendMeCopy;
    private Timestamp createdAt;
    private String responseId;

    private String ip;
}
