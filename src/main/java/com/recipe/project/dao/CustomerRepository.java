package com.recipe.project.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.recipe.project.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {

	List<Customer> findAll();
	
	List<Customer> findCustomerByid(String id);
	
	List<Customer> findCustomerByIdAndPasswordEquals(String id, String pw);
	
	List<Customer> findCustomerByIdEquals(String id);
	
//	void updateByAccount(Customer info);

	
//	void deleteById(Long id);

//	void deleteByAccount(String account);

//	void deleteById(int i);

}
