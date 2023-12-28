package xyz.kiridepapel.portfoliobackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "personal_info")
public class PersonalInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String names;
    private String surnames;
    private String studies;
    private String information;
    private String email;
    private String phone;
    private String city;
    private String country;
    private String address;
    private String cv;
    // Social media
    private String github;
    private String linkedin;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
