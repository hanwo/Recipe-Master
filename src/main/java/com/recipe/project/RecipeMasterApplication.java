package com.recipe.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan({"com.recipe.project.domain","com.recipe.project.controller", "com.recipe.project.service", "com.recipe.project.configuration"})
//@EnableAutoConfiguration
//@EnableJpaRepositories(basePackages = "com.recipe.project.dao")
//@EntityScan("com.recipe.project.domain")
public class RecipeMasterApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RecipeMasterApplication.class, args);
	}
}
