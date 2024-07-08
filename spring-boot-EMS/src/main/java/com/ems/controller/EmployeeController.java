package com.ems.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ems.exception.ItemNotFoundException;
import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.service.DepartmentService;
import com.ems.service.EmployeeService;
import com.ems.util.EmployeeDTO;


@Controller
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private DepartmentService deptService;
	
	@GetMapping("/users")
	public String listE(Model model) {
		List<Employee> listEmployees = empService.getAllEmployees();
		model.addAttribute("listEmps", listEmployees);
		return "users";
	}
	
	@GetMapping("/employee")
	public String showEmployeeForm(Model model) {
		model.addAttribute("employee", new EmployeeDTO());	
		List<Department> departmentsList = deptService.getAllDepartments();
		logger.info("Employee List");
		model.addAttribute("depts",departmentsList);
		return "employee";
	}
	
	
	@PostMapping("/process_employee")
	public String processEmployee(EmployeeDTO dto) {
		Employee emp = new Employee(dto);
		if(dto.getId() != null) {
			emp.setId(dto.getId());
		}else {
			logger.error("Employee Id not found");
		}
		if(dto.getDeptId() != null) {
			Optional<Department> dept = deptService.getById(dto.getDeptId());
			emp.setDepartment(dept.get());
		}else {
			logger.error("Department Id not found");
		}
		logger.info("Register employee : {}", dto.getFirstName());
		
		empService.saveEmployee(emp);
		
		return "redirect:/users";
	}
	
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Employee emp = empService.getById(id)
	      .orElseThrow(() -> new ItemNotFoundException(id));
	    logger.info("Update employee : {}", emp.getFirstName());
	    model.addAttribute("employee", emp);
	    List<Department> departmentsList = deptService.getAllDepartments();
		model.addAttribute("depts",departmentsList);
	    return "update-emp";
	}
	
	@PostMapping("/update/{id}")
	public String updateEmployee(@PathVariable("id") long id, Employee emp, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        emp.setId(id);
	        logger.error("something went wrong");    
	        return "update-emp";
	    }
	    logger.info("Update employee : {}", emp.getFirstName());        
	    empService.saveEmployee(emp);
	    return "redirect:/users";
	}
	    
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") long id, Model model) {
	    Employee emp = empService.getById(id)
	      .orElseThrow(() -> new ItemNotFoundException(id));
	    logger.warn("Delete employee : {}", emp.getFirstName()); 
	    empService.deleteEmployee(emp);
	    return "redirect:/users";
	}
}
