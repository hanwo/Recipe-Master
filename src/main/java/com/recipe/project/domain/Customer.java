package com.recipe.project.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	@Id
	private String id;
	@NotEmpty
	private String password;
	private int age;
//	@NotEmpty
	private String email;
	
	public Customer(String id, String password) {
		this.id = id;
		this.password = password;
	}
	
}
