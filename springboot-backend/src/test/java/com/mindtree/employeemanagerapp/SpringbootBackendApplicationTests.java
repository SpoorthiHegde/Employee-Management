package com.mindtree.employeemanagerapp;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.mindtree.employeemanagerapp.model.Employee;
import com.mindtree.employeemanagerapp.repository.EmployeeRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringbootBackendApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

//---------------------Get List of Employees Test ------------------------

	@Test
	@Order(3)
	public void getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		Assertions.assertThat(employees.size()).isGreaterThan(0);
	}

// ---------------Create new Employee Test-------------------------------

	@Test
	@Order(1)
	@Rollback(value = false)
	public void createEmployeeTest() {
		Employee employee = new Employee("Spoorthi", "Hegde", "spoo@gmail.com");
		employeeRepository.save(employee);
		
		Assertions.assertThat(employee.getId()).isGreaterThan(0);
	}

//---------------------Get Employee by Id Test-----------------------
	
	@Test
	@Order(2)
	public void getEmployeeTest() {
		Employee employee = employeeRepository.findById(3L).get();
		Assertions.assertThat(employee.getId()).isEqualTo(3);
	}

//---------------------Update (Edit) Employee Test--------------------------

	@Test
	@Order(4)
	@Rollback(value = false)
	public void updateEmployeeTest() {
		Employee employee = employeeRepository.findById(3L).get();
		employee.setEmailId("bob@gmail.com");
		Employee updatedEmailOfEmployee = employeeRepository.save(employee);
		Assertions.assertThat(updatedEmailOfEmployee.getEmailId()).isEqualTo("bob@gmail.com");
	}

//----------------------- Delete Employee Test------------------------
	@Test
	@Order(5)
	@Rollback(value = false)
	public void deleteEmployeeTest() {

		Employee employee = employeeRepository.findById(17L).get();
		employeeRepository.delete(employee);

		Employee employee1 = null;

		Optional<Employee> optionalEmployee = employeeRepository.findByEmailId("abc@gmail.com");

		if (optionalEmployee.isPresent()) {
			employee1 = optionalEmployee.get();
		}

		Assertions.assertThat(employee1).isNull();
	}
}
