package com.recipe.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.project.domain.ChartDTO;
import com.recipe.project.service.ChartService;

@RestController
@Component
public class ChartController {
	
	@Autowired
	ChartService service;
	
	@RequestMapping("/move")
	public ChartDTO saveKeyWord(HttpServletRequest request, String ch) {
		System.out.println("11111 : " + ch);
		//ch.setId(ch.getId());
		//ch.setKeyword(request.getParameter("find"));
		ch = request.getParameter("find");
		return service.savsKeyWord(ch);
	}
}
