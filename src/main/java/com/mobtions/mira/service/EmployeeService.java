package com.mobtions.mira.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mobtions.mira.dto.EmployeeRequestDTO;
import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.util.ResponseStructure;

public interface EmployeeService {



	ResponseEntity<ResponseStructure<List<Employee>>> fetchAllEmployees();

	ResponseEntity<ResponseStructure<Employee>> deleteEmployee(int employeeId);

	ResponseEntity<ResponseStructure<Employee>> findEmployeeById(int employeeId);


	ResponseEntity<ResponseStructure<List<Employee>>> login(String email, String password);

	ResponseEntity<ResponseStructure<Employee>> updateEmployeeDetail(Employee employee, int employeeId);

	ResponseEntity<ResponseStructure<Employee>> addEmployee(EmployeeRequestDTO employee);

}
