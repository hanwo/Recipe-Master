package com.recipe.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recipe.project.domain.ChartDTO;

@Repository
public interface ChartRepository extends CrudRepository<ChartDTO, String>{
	
	String countBykeyword(String keyword);

	ChartDTO save(String ch);

}
