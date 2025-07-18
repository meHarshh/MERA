package com.mobtions.mira.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.repo.EmployeeRepository;
import com.mobtions.mira.service.EmployeeService;
import com.mobtions.mira.util.ResponseStructure;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ResponseStructure<Employee>> addEmployee(Employee employee) {
    	
    	String hashedPassword = passwordEncoder.encode(employee.getPassword());
    	employee.setPassword(hashedPassword);
        Employee savedEmployee = employeeRepository.save(employee);
        ResponseStructure<Employee> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setMessage("Employee added successfully.");
        structure.setData(savedEmployee);
        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<Employee>>> fetchAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();
        ResponseStructure<List<Employee>> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMessage("All employees fetched successfully.");
        structure.setData(employeeList);
        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<Employee>> deleteEmployee(int employeeId) {
        Optional<Employee> optional = employeeRepository.findById(employeeId);
        ResponseStructure<Employee> structure = new ResponseStructure<>();
        
        if (optional.isPresent()) {
            employeeRepository.deleteById(employeeId);
            structure.setStatus(HttpStatus.OK.value());
            structure.setMessage("Employee deleted successfully.");
            structure.setData(optional.get());
            return new ResponseEntity<>(structure, HttpStatus.OK);
        } else {
            structure.setStatus(HttpStatus.NOT_FOUND.value());
            structure.setMessage("Employee not found with ID: " + employeeId);
            structure.setData(null);
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<Employee>> findEmployeeById(int employeeId) {
        Optional<Employee> optional = employeeRepository.findById(employeeId);
        ResponseStructure<Employee> structure = new ResponseStructure<>();

        if (optional.isPresent()) {
            structure.setStatus(HttpStatus.OK.value());
            structure.setMessage("Employee found.");
            structure.setData(optional.get());
            return new ResponseEntity<>(structure, HttpStatus.OK);
        } else {
            structure.setStatus(HttpStatus.NOT_FOUND.value());
            structure.setMessage("Employee not found with ID: " + employeeId);
            structure.setData(null);
            return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
        }
    }

	@Override
	public ResponseEntity<ResponseStructure<List<Employee>>> login(String officialEmail, String password) {
		// TODO Auto-generated method stub
		return null;
	}
}
