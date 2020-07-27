package com.recipe.project.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.project.domain.Customer;
import com.recipe.project.service.CustomerService;

@CrossOrigin(origins = {"http://localhost:8009", "http://127.0.0.1:8009"})
@RestController
public class CustomerController {

	@Autowired
	CustomerService service;

	// 회원가입
	@PostMapping("/customer")
	public ResponseEntity<Object> addCustomer(@RequestParam String id, @RequestParam String pw, @RequestParam int age,
			@RequestParam String email) {
		ResponseEntity<Object> response = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		
		Customer userInfo = new Customer(id, pw, age, email);
		String result = "가입 실패";
		boolean flag = service.addUser(userInfo);
		if (flag == true) {
			result = "가입 성공";
			response = ResponseEntity
					.status(HttpStatus.MOVED_PERMANENTLY)
					.headers(responseHeaders)
					.body("<script>"
							+ "alert('회원가입 성공');"
							+ "document.location.href = 'login.html';"
							+ "</script>");
		} else {
			response = ResponseEntity
					.status(HttpStatus.MOVED_PERMANENTLY)
					.headers(responseHeaders)
					.body("<script>"
							+ "alert('회원가입 실패');"
							+ "document.location.href = 'join.html';"
							+ "</script>");
		}
		System.out.println(result);
		return response;
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestParam String id, String pw) {
		System.out.println("22@@@@@@@@@@@@");
		ResponseEntity<Object> response = null;  
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		System.out.println("1111111 : "+"Test");
		Customer userInfo = new Customer(id, pw);
		String result = "로그인 실패";
		boolean flag = service.login(userInfo);
		
		if (flag == true) {
			if(userInfo.getId().equals("admin")) {
				result = "어드민 로그인 성공";
				response = ResponseEntity
						.status(HttpStatus.MOVED_PERMANENTLY)
						.headers(responseHeaders)
						.body("<script>"
								+ "sessionStorage.setItem('" + userInfo.getId() + "', '" + userInfo.getId() + "');"
								+ "alert('관리자님 반갑습니다.');"
								+ "document.location.href = 'adminLogin.html';"
								+ "</script>");

			} else {
				result = "로그인 성공";
				response = ResponseEntity
						.status(HttpStatus.MOVED_PERMANENTLY)
						.headers(responseHeaders)
						.body("<script>"
								+ "sessionStorage.setItem('" + userInfo.getId() + "', '" + userInfo.getId() + "');"
								+ "alert('로그인 성공');"
								+ "document.location.href = 'afterLogin.html';"
								+ "</script>");
			}
		} else {
			response = ResponseEntity
					.status(HttpStatus.MOVED_PERMANENTLY)
					.headers(responseHeaders)
					.body("<script>"
							+ "alert('로그인 실패');"
							+ "document.location.href = 'login.html';"
							+ "</script>");
		}
		System.out.println(result);
		return response;
	}

	// 전체 회원 목록
	@RequestMapping("/allCus")
	public Iterable<Customer> allCustomer() {
		return service.AllCustomer();
	}
	
	// id에 맞는 회원 - 세션 + myPage.html 
	@RequestMapping("/oneCus/{id}")
	public Iterable<Customer> oneCustomer(@PathVariable String id) {
		return service.OneCustomer(id);
	}
}
