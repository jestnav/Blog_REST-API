package com.springboot.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootBootRestApiApplication {

	/////
	// Used to simplify the DTO mapping (entity to DTO and DTO to entity)
	// See the code without ModelMapper commented in PostServiceImpl class
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBootRestApiApplication.class, args);
	}
}
