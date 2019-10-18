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
	
	// dpffktmxrl
	
	// RDB 데이터 적제
	
	// 로그인
	public boolean login(Customer cus) {
		
		boolean flag = false;
		
		List<Customer> list = customerRepo.findCustomerByIdAndPasswordEquals(cus.getId(), cus.getPassword());
		
		if(list.size() == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	
//	public void updateCustomer(Customer info) {
		// TODO 회원 정보 수정
//		info.setUpdatedAt(LocalDateTime.now());
//		info.setCreateBy(info.getAccount());
//		customerRepository.save(info);
//	}
//	
//	public void getCustomer() {
//		// TODO 회원 상세 정보 조회
//	}
//
//	public List<Customer> getCustomers() {
//		//TODO 모든 회원 조회
//		List<Customer> customers = customerRepo.findAll();
//		return customers;
//		
//	}
//	
//	public void deleteCustomer(Long id) {
//		// TODO 회원 탈퇴
//		customerRepo.deleteById((long) id);
//	}

}
