package xyz.kiridepapel.portfoliobackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import xyz.kiridepapel.portfoliobackend.entity.UserEntity;
import xyz.kiridepapel.portfoliobackend.entity.enums.RoleEnum;
import xyz.kiridepapel.portfoliobackend.repository.UserRepository;

@SpringBootApplication
public class PortfolioBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(
		UserRepository userRepository
	) {
		return args -> {
			if(userRepository.count() == 0) {
				userRepository.save(
					UserEntity.builder()
						.username("kiridepapel")
						.password("!#!40se02j7123A!#!")
						.role(RoleEnum.ROLE_ADMIN)
						.build()
				);
			}
		};
	}
	
}
