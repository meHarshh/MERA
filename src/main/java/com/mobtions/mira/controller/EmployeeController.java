package com.mobtions.mira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mobtions.mira.dto.EmployeeRequestDTO;
import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.service.EmployeeService;
import com.mobtions.mira.util.ResponseStructure;


@CrossOrigin(allowCredentials = "true", origins = "http://mera.kingsmenrealty.com/")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping(value = "employees")

	private ResponseEntity<ResponseStructure<Employee>> addEmployee(@RequestBody EmployeeRequestDTO employee){
		return employeeService.addEmployee(employee);
	}
	
	
//	this is the controller layer of update employee method
	@PutMapping(value = "employees/{employeeId}")
	private ResponseEntity<ResponseStructure<Employee>> updateEmployeeDetail(@RequestBody Employee employee, @PathVariable int employeeId){
		return employeeService.updateEmployeeDetail(employee, employeeId);
	}
	
	@GetMapping(value = "employees")
	private ResponseEntity<ResponseStructure<List<Employee>>> fetchAllEmployees(){
		return employeeService.fetchAllEmployees();
	}
	
	@DeleteMapping(value = "employees/{employeeId}")
	private ResponseEntity<ResponseStructure<Employee>> deleteEmployee(@PathVariable int employeeId){
		return employeeService.deleteEmployee(employeeId);
	}
	
	@GetMapping(value = "employees/{employeeId}")
	private ResponseEntity<ResponseStructure<Employee>> findEmployeeById(@PathVariable int employeeId){
		return employeeService.findEmployeeById(employeeId);
	}
	
	@GetMapping(value = "employee/login/{email}/{password}")
	public ResponseEntity<ResponseStructure<List<Employee>>> login(@PathVariable String email, @PathVariable String password) {
	    return employeeService.login(email, password);
	}
	
}
