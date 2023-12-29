package xyz.kiridepapel.portfoliobackend.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String repo;
    private String demo;
    private Boolean show;
    private String mainImg;
    private Set<String> images;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") 
    private UserEntity user;
}
