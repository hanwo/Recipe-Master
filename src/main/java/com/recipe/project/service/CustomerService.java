package com.recipe.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.project.dao.CustomerRepository;
import com.recipe.project.domain.Customer;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepo;
	
	// 회원가입
	public boolean addUser(Customer customer) {
		boolean flag = false;
		List<Customer> list = customerRepo.findCustomerByIdEquals(customer.getId()); 	
        if (list.size() == 0) {
        	customerRepo.save(customer);
        	flag = true;
        } else {
        	return flag;
        }
        return flag;
	}
	
	// 로그인
	public boolean login(Customer cus) {
		
		boolean flag = false;
		
		List<Customer> admin = customerRepo.findCustomerByIdAndPasswordEquals("admin", "admin");
		List<Customer> list = customerRepo.findCustomerByIdAndPasswordEquals(cus.getId(), cus.getPassword());
		System.out.println("1 :"+flag);
		if(admin.size() == 1) {
			System.out.println("2 :"+flag);
			flag = true;
		} else if(list.size() == 1) {
			System.out.println("3 :"+flag);
			flag = true;
		}
		System.out.println("4 :"+flag);
		return flag;
	}
	
	public Iterable<Customer> AllCustomer() {
		//TODO 모든 회원 조회
		return customerRepo.findAll();
	}
	
	// id에 맞는 회원
	public Iterable<Customer> OneCustomer(String id) {
		
		return customerRepo.findCustomerByid(id);
	}
}
