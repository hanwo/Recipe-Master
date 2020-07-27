package com.recipe.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.project.configuration.Elasticsearch;
import com.recipe.project.domain.Recipe;
import com.recipe.project.service.RecipeService;

@RestController
@CrossOrigin(origins={"localhost:8009","127.0.0.1:8009"})
public class RecipeRestController {

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
	
	// 지인철
	@GetMapping("/myRecipe/{user}")
	public JSONArray myRecipe(@PathVariable("user") String user) throws IOException {
		System.out.println("user: "+user);
		JSONArray jsonArray = recipeService.personal(user);
		System.out.println("Controller jsonArray:  "+jsonArray);
		return jsonArray;
	}
	
	//지인철
	@GetMapping("/recipeDelete/{id}")
	public ModelAndView delete(@PathVariable String id, Model model) throws IOException{
		recipeService.deleteDoc(id);
		return new ModelAndView("redirect:/recipe.html");
	}
	
	// Front-End 구현 필요 + 일부 수정
	@PostMapping("/recipeUpdate")
	public Object recipeUpdate(Recipe recipe, Model model) {
		UpdateRequest updateRequest = null;
		UpdateResponse response = null;
		try(RestHighLevelClient client = elasticsearch.createConnection();) {
			XContentBuilder builder = XContentFactory.jsonBuilder()
	                .startObject()
	                	.field("title","title2")
	                    .field("content","title2")
	                    .field("ingre","title2")
	                .endObject();
			updateRequest = new UpdateRequest("recipe", "_doc", "8iyK3G0BL34Lcvo0UCcO").doc(builder);
			response = client.update(updateRequest, RequestOptions.DEFAULT);
		}catch (ElasticsearchException e) {
	        e.printStackTrace();
	        if(e.status().equals(RestStatus.NOT_FOUND)) {
	            return "수정할 문서가 존재하지 않습니다.";
	        }
	    }catch (Exception e) {
	        System.out.println("이런!!ㄴ");
	    }
	    return response.getResult();
	}

}
