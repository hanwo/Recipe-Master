package com.recipe.project.dao;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.recipe.project.domain.ChartDTO;

@Repository
public interface ChartRepository extends CrudRepository<ChartDTO, Long>{
	
	String findByKeyword(String keyword);
	
	JSONArray findAll(Sort orderByCountAsc);
	
	@Transactional
	@Modifying
	@Query(value ="UPDATE ChartDTO q set q.count = q.count + 1 where q.keyword = :keyword")
	int updateCount(@Param("keyword") String keyword);
	
}