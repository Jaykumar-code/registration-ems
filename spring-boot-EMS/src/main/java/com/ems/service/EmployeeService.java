package com.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository empRepo;
	
	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}
	
	public Employee saveEmployee(Employee emp) {
		return empRepo.save(emp);
	}

	public Optional<Employee> getById(long id) {
		return empRepo.findById(id);
	}

	public void deleteEmployee(Employee emp) {
		empRepo.delete(emp);
	}

}
