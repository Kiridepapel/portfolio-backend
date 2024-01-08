package xyz.kiridepapel.portfoliobackend.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.kiridepapel.portfoliobackend.entity.LogEmailEntity;

@Repository
public interface LogEmailRepository extends JpaRepository<LogEmailEntity, Long> {
    public List<LogEmailEntity> findByIpAndCreatedAtBetween(String ip, Timestamp start, Timestamp end);
}