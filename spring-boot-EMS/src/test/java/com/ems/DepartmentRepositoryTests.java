package com.ems;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.ems.model.Department;
import com.ems.repository.DepartmentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DepartmentRepositoryTests {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private DepartmentRepository repo;
	
	@Test
	public void testCreateDepartment() {
		Department dept = new Department();
		dept.setDeptName("dept1");
		
		Department savedDept = repo.save(dept);
		
		Department existDept = entityManager.find(Department.class, savedDept.getId());
		
		assertThat(dept.getDeptName()).isNotNull();
		assertThat(dept.getDeptName()).isEqualTo(existDept.getDeptName());
		
	}
	
}
