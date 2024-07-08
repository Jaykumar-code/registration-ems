package com.ems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

	@Mock
	private EmployeeRepository repo;
	
	@InjectMocks
	private EmployeeService service;
	
	@Test
	  void EmployeeService_GetAllEmployee()
	    {
	      //given
			List<Employee> mockList = new ArrayList<>();
	       Employee employee= new Employee( 1L,"jay","pagare","jay@pagare");
	       Employee employee1= new Employee(2L,"vicky","pagare","vicky@pagare");
	      mockList.add(employee1);
	      mockList.add(employee);
	       //When
	       when(repo.findAll()).thenReturn(mockList);
	      List<Employee> empList = service.getAllEmployees();
	       //Then
	         assertThat(empList).isNotNull();
	         assertThat(empList.size()).isEqualTo(2);
	  }
	
	@Test
	  void EmployeeService_GetEmployeeById()
	    {
	      //given
			long id = 1L;
		   Employee employee= new Employee( 1L,"jay","pagare","jay@pagare");
		   //When
	       when(repo.findById(id)).thenReturn(Optional.of(employee));
	       Optional<Employee> emp = service.getById(id);
	       //Then
	         assertTrue(emp.isPresent());
	         assertSame(emp.get(),employee);
	  }
	
	@Test
	void EmployeeService_GetEmployeeById_WhenEmployeeNotExist_ReturnsOptionalEmpty(){
	    // Arrange
	    long employeeId = 2L;
	    when(repo.findById(employeeId)).thenReturn(Optional.empty());

	    // Act
	    Optional<Employee> result = service.getById(employeeId);

	    // Assert
	    assertFalse(result.isPresent()); // assert that the result is not present
	}
	
	@Test
	  void EmployeeService_CreateEmployee_ReturnsEmployee()
	    {
		Employee emp = new Employee();
		emp.setEmail("emp@gmail.com");
		emp.setFirstName("emp1");
		emp.setLastName("ploy");
		
		when(repo.save(Mockito.any(Employee.class))).thenReturn(emp);

        Employee savedEmployee = service.saveEmployee(emp);

        // Using AssertJ to check the correctness of the expected values.
        Assertions.assertThat(savedEmployee).isNotNull();
	  }
	
	@Test
	public void EmployeeService_UpdateEmployee_ReturnsEmployee(){
	    long employeeId=1L;
	    Employee emp = new Employee();
	    emp.setId(employeeId);
		emp.setEmail("emp@gmail.com");
		emp.setFirstName("emp1");
		emp.setLastName("ploy");

	    when(repo.save(emp)).thenReturn(emp);

	    Employee updatedEmployee = service.saveEmployee(emp);

	    Assertions.assertThat(updatedEmployee).isNotNull();
	}
	
	@Test
	public void EmployeeService_DeleteEmployee_ReturnsVoid() {
	    long employeeId = 1L;
	    Employee emp = new Employee();
	    emp.setId(employeeId);
		emp.setEmail("emp@gmail.com");
		emp.setFirstName("emp1");
		emp.setLastName("ploy");

	    // Configure the delete method to perform no action when called with an Employee object.
	    doNothing().when(repo).delete(emp);

	    // Invoke the delete method of the employeeService with the created employeeId.
	    service.deleteEmployee(emp);

	    // Use assertAll to ensure that no exceptions are thrown during the execution of the delete method.
	    assertAll(() -> service.deleteEmployee(emp));
	}
}
