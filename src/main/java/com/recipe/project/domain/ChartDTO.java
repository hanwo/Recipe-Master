package com.recipe.project.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String keyword;
	
	public ChartDTO(String keyword) {
		this.keyword = keyword;
	}
}
