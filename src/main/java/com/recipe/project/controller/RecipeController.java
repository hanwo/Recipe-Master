package com.recipe.project.controller;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.project.configuration.Elasticsearch;
import com.recipe.project.service.RecipeService;

@RestController
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private Elasticsearch elasticsearch;

	@GetMapping("/setup") // 크롤링만..(최초 구축 단계)
	public String indexing() {
		recipeService.webCrawling();
		return "확인";
	}

	@GetMapping("/reset") // index삭제후 크롤링
	public String crawling() throws IOException {
		if (recipeService.deleteIndex() == "삭제 완료") {
			recipeService.webCrawling();
			return "정상 실행";
		}
		return "정상 도작하지 못함";
	}

	@GetMapping("/getAll") // 레시피 index의 모든 정보 가져오기(제한:1000개)
	public JSONArray contentsAll() throws IOException {
		JSONArray jsonArray = recipeService.matchAll();
		return jsonArray;
	}

	@GetMapping("/search/{keyword}") // 검색창 기능**잘됨(search.html 만들고 비동기 javascript로 실행하도록 설정) or Session
	public JSONArray search(@PathVariable("keyword") String keyword) throws IOException {
		JSONArray jsonArray = recipeService.search(keyword);
		return jsonArray;
	}

	@GetMapping("doc/{docId}")
	public Object detail(@PathVariable String docId) {
		return recipeService.getDoc(docId);
	}
	
	@RequestMapping("/recipeDetail")
	public String test() {
		//ModelAndView mv = new ModelAndView();
		System.out.println("working");
		//mv.setViewName("/test.html");
		
		return "test.html";
	}

}
