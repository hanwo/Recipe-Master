package com.recipe.project;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.recipe.project.service.RecipeService;

@SpringBootApplication
public class RecipeMasterApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(RecipeMasterApplication.class, args);
	}
}
