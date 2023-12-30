package xyz.kiridepapel.portfoliobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import xyz.kiridepapel.portfoliobackend.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class) // Para que Spring Boot pueda leer las propiedades de la clase AppProperties
public class PortfolioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioBackendApplication.class, args);
	}
}
