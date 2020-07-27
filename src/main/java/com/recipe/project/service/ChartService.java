package com.recipe.project.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.recipe.project.dao.ChartRepository;
import com.recipe.project.domain.ChartDTO;

@Service
@Component
public class ChartService {
	
	@Autowired
	ChartRepository chartRepo;
	
	public ChartDTO savesKeyWord(ChartDTO ch) {
		return chartRepo.save(ch);
	}
	
	
	public String findkeyword(String keyword) {
		return chartRepo.findByKeyword(keyword);
	}
	
	public JSONArray findAll(){
		JSONArray jsonarray = chartRepo.findAll(orderByConuntAsc());
		return jsonarray;
	}
	
	public Sort orderByConuntAsc() {
		return new Sort(Sort.Direction.DESC, "count");
	}
	
	public int updatecount(String keyword) {
		return chartRepo.updateCount(keyword);
	}
}
