package com.recipe.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.recipe.project.domain.ChartDTO;
import com.recipe.project.service.ChartService;
import com.recipe.project.service.RecipeService;

@CrossOrigin(origins={"localhost:8009","127.0.0.1:8009"})
@Controller
@Component
public class ChartController {

	@Autowired
	ChartService charservice;
	
	@Autowired
	private RecipeService recipeService;

	@RequestMapping("/move")
	public String saveKeyWord(HttpServletRequest request, ChartDTO ch, Model model) throws IOException {
		ch.setKeyword(request.getParameter("find"));
		String updatekey = request.getParameter("find");
		model.addAttribute("searchKeyword", recipeService.search(request.getParameter("find")));
		if (charservice.findkeyword(request.getParameter("find")) == null) {
			charservice.savesKeyWord(ch);
		}
		else {
			charservice.updatecount(updatekey);
		}
		return "searchkeyword.html";
	}
	
	@ResponseBody
	@GetMapping("/findall")
	public JSONArray findAllId() {
		JSONArray jsonarray = charservice.findAll();
		System.out.println(jsonarray);
		return jsonarray;
	}
}
