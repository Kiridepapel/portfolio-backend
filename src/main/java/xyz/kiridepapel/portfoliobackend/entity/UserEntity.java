package xyz.kiridepapel.portfoliobackend.entity;

import java.util.Set;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

import xyz.kiridepapel.portfoliobackend.entity.enums.RoleEnum;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 32, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "role", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private String img;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonalInfoEntity personalInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ExperienceEntity> experiences;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = TechEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_tech", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tech_id"))
    private Set<TechEntity> techs;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ProjectEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_project", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<ProjectEntity> projects;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
