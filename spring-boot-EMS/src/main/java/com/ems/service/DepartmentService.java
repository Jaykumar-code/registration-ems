package com.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ems.model.Department;
import com.ems.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository deptRepo;
	
	
	public List<Department> getAllDepartments() {
		return deptRepo.findAll();
	}

	public Optional<Department> getById(Long deptId) {
		return deptRepo.findById(deptId);
	}

	public Department saveDepartment(Department dept) {
		return deptRepo.save(dept);
	}
	
	public void deleteDepartment(Department dept) {
		deptRepo.delete(dept);
	}

}
