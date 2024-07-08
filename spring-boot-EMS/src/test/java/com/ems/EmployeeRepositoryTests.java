package com.ems;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class EmployeeRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private EmployeeRepository repo;
	
	@Test
	public void testCreateEmployee() {
		Employee emp = new Employee();
		emp.setEmail("emp@gmail.com");
		emp.setFirstName("emp1");
		emp.setLastName("ploy");
		
		Employee savedEmp = repo.save(emp);
		
		Employee existEmp = entityManager.find(Employee.class, savedEmp.getId());
		
		assertThat(emp.getFirstName()).isNotNull();
		assertThat(emp.getFirstName()).isEqualTo(existEmp.getFirstName());
		
	}
	
}
