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
    private String img;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ImagesEntity> images;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ProjectEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "project_tech", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "tech_id"))
    private Set<TechEntity> techs;
}
