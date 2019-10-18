package com.recipe.project.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.project.configuration.Elasticsearch;
import com.recipe.project.domain.Recipe;
import com.recipe.project.service.RecipeService;

@CrossOrigin(origins="http://localhost:9000")
@RestController
public class RecipeController {

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private Elasticsearch elasticsearch;
	
	@GetMapping("/setup") // 크롤링만..(최초 구축 단계)
	public String indexing(){
		recipeService.webCrawling();
		return "확인";
	}
	
	@GetMapping("/reset") // index삭제후 크롤링
	public String crawling() throws IOException{
		if(recipeService.deleteIndex() == "삭제 완료") {
			recipeService.webCrawling();
			return "정상 실행";
		}
		return "정상 도작하지 못함";
	}
	
	@GetMapping("/recipe") // 레시피 index의 모든 정보 가져오기(제한:1000개)
	public JSONArray contentsAll() throws IOException{
		JSONArray jsonArray = recipeService.matchAll();
		return jsonArray;
	}
	
	@GetMapping("/search") // 검색창 기능
	public JSONArray searchWindow() throws IOException {
		JSONArray jsonArray = recipeService.search();
		return jsonArray;
	}
}	
		

