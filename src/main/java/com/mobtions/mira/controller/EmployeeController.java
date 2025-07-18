package com.mobtions.mira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.service.EmployeeService;
import com.mobtions.mira.util.ResponseStructure;


@CrossOrigin(allowCredentials = "true", origins = "http://localhost:8081/")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping(value = "employees")
	private ResponseEntity<ResponseStructure<Employee>> addEmployee(@RequestBody Employee employee){
		return employeeService.addEmployee(employee);
	}
	
	@GetMapping(value = "employees")
	private ResponseEntity<ResponseStructure<List<Employee>>> fetchAllEmployees(){
		return employeeService.fetchAllEmployees();
	}
	
	@DeleteMapping(value = "employees/{employeeId}")
	private ResponseEntity<ResponseStructure<Employee>> deleteEmployee(int employeeId){
		return employeeService.deleteEmployee(employeeId);
	}
	
	@GetMapping(value = "employees/{employeeId}")
	private ResponseEntity<ResponseStructure<Employee>> findEmployeeById(int employeeId){
		return employeeService.findEmployeeById(employeeId);
	}
	
	@GetMapping
	private ResponseEntity<ResponseStructure<List<Employee>>> login(String officialEmail, String password){
		return employeeService.login(officialEmail, password);
	}
	
}
