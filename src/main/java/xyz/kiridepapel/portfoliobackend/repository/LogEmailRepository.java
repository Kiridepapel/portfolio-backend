package xyz.kiridepapel.portfoliobackend.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import xyz.kiridepapel.portfoliobackend.entity.LogEmailEntity;

@Repository
public interface LogEmailRepository extends JpaRepository<LogEmailEntity, Long> {

    @Query(
        "SELECT COUNT(le) FROM LogEmailEntity le WHERE le.ip = :ip AND le.createdAt >= :start AND le.createdAt <= :end"
    )
    Long countEmailsByIpBetweenDates(
        @Param("ip") String ip,
        @Param("start") Timestamp start,
        @Param("end") Timestamp end
    );
}