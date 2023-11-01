package com.SpringSecutiryJWT.SpringSecutiryJWT;

import com.SpringSecutiryJWT.SpringSecutiryJWT.models.ERole;
import com.SpringSecutiryJWT.SpringSecutiryJWT.models.RoleEntity;
import com.SpringSecutiryJWT.SpringSecutiryJWT.models.UserEntity;
import com.SpringSecutiryJWT.SpringSecutiryJWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SpringSecutiryJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecutiryJwtApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;
	@Bean
	CommandLineRunner init() {
		return args -> {

			UserEntity userEntity = UserEntity.builder()
					.email("Naranjofjx@tuki.com")
					.username("jesusnjo")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			UserEntity userEntity2 = UserEntity.builder()
					.email("JNaranjox@tuki.com")
					.username("francisjo")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.USER.name()))
							.build()))
					.build();

			UserEntity userEntity3 = UserEntity.builder()
					.email("PManul@tuki.com")
					.username("pManulo")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.INVITED.name()))
							.build()))
					.build();


					userRepository.save(userEntity);
					userRepository.save(userEntity2);
					userRepository.save(userEntity3);

		};
	}
}
