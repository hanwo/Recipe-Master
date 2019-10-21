package com.recipe.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.recipe.project.dao.ChartRepository;
import com.recipe.project.domain.ChartDTO;

@Service
@Component
public class ChartService {
	
	@Autowired
	ChartRepository chartRepo;
	
	public ChartDTO savsKeyWord(String ch) {
		System.out.println("22222222222 : " + ch);
		return chartRepo.save(ch);
	}

}
