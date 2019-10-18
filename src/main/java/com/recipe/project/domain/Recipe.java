package com.recipe.project.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Recipe {
	private String title;
	private List<String> content;
	private String path;
	
	public void setContent(List<String> content) {
		this.content = new ArrayList<String>(content);
	}

	public void setContent(String string) {
		this.content.add(string);
	}


}
