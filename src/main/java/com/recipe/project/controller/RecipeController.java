package com.recipe.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.project.domain.ChartDTO;
import com.recipe.project.domain.Recipe;
import com.recipe.project.service.ChartService;
import com.recipe.project.service.RecipeService;

//@CrossOrigin(origins={"localhost:8009","127.0.0.1:8009"})
@Controller
public class RecipeController {
	
	@Autowired
	RecipeService recipeService;

	@RequestMapping(value = "/recipeDetail/{id}", method=RequestMethod.GET) 
	public String test(@PathVariable String id, Model model) { 
		Recipe recipe = new Recipe();
		recipe = (Recipe) recipeService.getDoc(id);
		model.addAttribute("myData", recipe); 
		return "test.html"; 
	}
	
	// 지인철 + userRecipe.html 수정됨
	@RequestMapping(value="/recipeCreate/{id}", method=RequestMethod.POST)	// 레시피 생성
	public ModelAndView recipeCreate(@PathVariable String id, Recipe recipe, Model model){
		recipe.setUser(id);
		recipeService.createDoc(recipe.getTitle(), recipe.getContent(), recipe.getIngre(), recipe.getUser());
		return new ModelAndView("redirect:/recipe.html");
	}
	
	
	
}
