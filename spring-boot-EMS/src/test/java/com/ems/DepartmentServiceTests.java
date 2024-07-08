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
import com.ems.model.Department;
import com.ems.repository.DepartmentRepository;
import com.ems.service.DepartmentService;


@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTests {

	@Mock
	private DepartmentRepository repo;
	
	@InjectMocks
	private DepartmentService service;
	
	@Test
	  void DepartmentService_GetAllDepartment()
	    {
	      //given
			List<Department> mockList = new ArrayList<>();
	       Department department= new Department( 1L,"department1");
	       Department department1= new Department(2L,"department2");
	      mockList.add(department1);
	      mockList.add(department);
	       //When
	       when(repo.findAll()).thenReturn(mockList);
	      List<Department> deptList = service.getAllDepartments();
	       //Then
	         assertThat(deptList).isNotNull();
	         assertThat(deptList.size()).isEqualTo(2);
	  }
	
	@Test
	  void DepartmentService_GetDepartmentById()
	    {
	      //given
			long id = 1L;
		   Department department= new Department(1L,"mockDepartment");
		   //When
	       when(repo.findById(id)).thenReturn(Optional.of(department));
	       Optional<Department> dept = service.getById(id);
	       //Then
	         assertTrue(dept.isPresent());
	         assertSame(dept.get(),department);
	  }
	
	@Test
	void DepartmentService_GetDepartmentById_WhenDepartmentNotExist_ReturnsOptionalEmpty(){
	    // Arrange
	    long departmentId = 2L;
	    when(repo.findById(departmentId)).thenReturn(Optional.empty());

	    // Act
	    Optional<Department> result = service.getById(departmentId);

	    // Assert
	    assertFalse(result.isPresent()); // assert that the result is not present
	}
	
	@Test
	  void DepartmentService_CreateDepartment_ReturnsDepartment()
	    {
		Department dept = new Department();
		dept.setDeptName("mockDepartment");
		
		when(repo.save(Mockito.any(Department.class))).thenReturn(dept);

        Department savedDepartment = service.saveDepartment(dept);

        // Using AssertJ to check the correctness of the expected values.
        Assertions.assertThat(savedDepartment).isNotNull();
	  }
	
	@Test
	public void DepartmentService_UpdateDepartment_ReturnsDepartment(){
	    long departmentId=1L;
	    Department dept = new Department();
	    dept.setId(departmentId);
		dept.setDeptName("mockDept");

	    when(repo.save(dept)).thenReturn(dept);

	    Department updatedDepartment = service.saveDepartment(dept);

	    Assertions.assertThat(updatedDepartment).isNotNull();
	}
	
	@Test
	public void DepartmentService_DeleteDepartment_ReturnsVoid() {
	    long departmentId = 1L;
	    Department dept = new Department();
	    dept.setId(departmentId);
		dept.setDeptName("mockDept");

	    // Configure the delete method to perform no action when called with an Department object.
	    doNothing().when(repo).delete(dept);

	    // Invoke the delete method of the departmentService with the created departmentId.
	    service.deleteDepartment(dept);

	    // Use assertAll to ensure that no exceptions are thrown during the execution of the delete method.
	    assertAll(() -> service.deleteDepartment(dept));
	}
}
