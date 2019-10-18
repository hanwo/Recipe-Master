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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.project.domain.Customer;
import com.recipe.project.service.CustomerService;

@CrossOrigin("127.0.0.1:9090")
@RestController
public class CustomerController {

	@Autowired
	CustomerService service;

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

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestParam String id, String pw) {
		ResponseEntity<Object> response = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		
		Customer userInfo = new Customer(id, pw);
		String result = "로그인 실패";
		boolean flag = service.login(userInfo);
		
		if (flag == true) {
			result = "로그인 성공";
			response = ResponseEntity
					.status(HttpStatus.MOVED_PERMANENTLY)
					.headers(responseHeaders)
					.body("<script>"
							+ "sessionStorage.setItem('" + userInfo.getId() + "', '" + userInfo.getId() + "');"
							+ "alert('로그인 성공');"
							+ "document.location.href = 'index.html';"
							+ "</script>");
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

//	@GetMapping("/members/{account}")
//	public void detail() {
//		//TODO 사용자 상세 정보
//	}

	@PutMapping("/members")
	public String update(@RequestBody Customer info) {
		// TODO 회원 정보 수정
//		customerService.updateCustomer(info);
		return "완료";
	}

//	@DeleteMapping("/members/{id}")
//	public String delete(@PathVariable Long id) {
//		// TODO 회원 탈퇴 (보완 필요)
//		System.out.println("111:    "+id);
//		customerService.deleteCustomer(id);
//		System.out.println("222");
//		return "탈퇴";
//	}
//
//	// Manager
//	@GetMapping("/members")
//	public List<Customer> list() {
//		//TODO 모든 사용자 리스트 (완료)
//		List<Customer> customers =customerService.getCustomers();
//		return customers;
//	}
}
