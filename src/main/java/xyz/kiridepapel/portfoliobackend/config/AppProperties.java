package xyz.kiridepapel.portfoliobackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private String jwtSecretKey;
  
  private String jwtTimeExpiration;
  
}