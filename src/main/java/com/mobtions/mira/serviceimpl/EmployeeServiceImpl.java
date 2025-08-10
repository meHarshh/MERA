package com.mobtions.mira.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobtions.mira.dto.EmployeeRequestDTO;
import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.enums.Role;
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
	public ResponseEntity<ResponseStructure<Employee>> addEmployee(EmployeeRequestDTO dto) {
	    // Check for duplicate official email
	    if (employeeRepository.findByOfficialEmail(dto.getOfficialEmail()).isPresent()) {
	        throw new RuntimeException("Official email already in use: " + dto.getOfficialEmail());
	    }

	    // Check for duplicate personal email
	    if (employeeRepository.findByPersonalEmail(dto.getPersonalEmail()).isPresent()) {
	        throw new RuntimeException("Personal email already in use: " + dto.getPersonalEmail());
	    }

	    // Check for duplicate employee code
	    if (employeeRepository.findByEmployeeCode(dto.getEmployeeCode()).isPresent()) {
	        throw new RuntimeException("Employee code already exists: " + dto.getEmployeeCode());
	    }

	    // Encode password
	    String hashedPassword = passwordEncoder.encode(dto.getPassword());
	    dto.setPassword(hashedPassword);

	    // Map to entity
	    Employee employee = mapToEmployee(dto);

	    // Save to DB
	    Employee savedEmployee = employeeRepository.save(employee);

	    // Prepare response
	    ResponseStructure<Employee> structure = new ResponseStructure<>();
	    structure.setStatus(HttpStatus.CREATED.value());
	    structure.setMessage("Employee added successfully.");
	    structure.setData(savedEmployee);

	    return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}


	private Employee mapToEmployee(EmployeeRequestDTO dto) {
	    Employee employee = new Employee();
	    employee.setFullName(dto.getFullName());
	    employee.setOfficialEmail(dto.getOfficialEmail());
	    employee.setPersonalEmail(dto.getPersonalEmail());
	    employee.setPhoneNumber(dto.getPhoneNumber());
	    employee.setEmployeeCode(dto.getEmployeeCode());
	    employee.setRole(dto.getRole());
	    employee.setPassword(dto.getPassword());
	    employee.setDepartment(dto.getDepartment());
	    employee.setDesignation(dto.getDesignation());
	    employee.setDateOfJoining(dto.getDateOfJoining());
	    employee.setDateOfBirth(dto.getDateOfBirth());
	    employee.setDateOfLeaving(dto.getDateOfLeaving());
	    employee.setAadharNumber(dto.getAadharNumber());
	    employee.setPanNumber(dto.getPanNumber());

	    // Set manager with role validation
	    if (dto.getManagerId() != null) {
	        Employee manager = employeeRepository.findById(dto.getManagerId())
	            .orElseThrow(() -> new RuntimeException("Manager not found with ID: " + dto.getManagerId()));

	        if (manager.getRole() != Role.MANAGER && manager.getRole() != Role.ADMIN) {
	            throw new RuntimeException("Employee with ID: " + dto.getManagerId() + " must have MANAGER or ADMIN role to be assigned as a manager.");
	        }

	        employee.setManager(manager);
	    }

	    return employee;
	}




	public ResponseEntity<ResponseStructure<Employee>> addEmployee(Employee employee) {
		// Check for duplicate official email
		if (employeeRepository.findByOfficialEmail(employee.getOfficialEmail()).isPresent()) {
			throw new RuntimeException("Official email already in use: " + employee.getOfficialEmail());
		}

		// Check for duplicate personal email
		if (employeeRepository.findByPersonalEmail(employee.getPersonalEmail()).isPresent()) {
			throw new RuntimeException("Personal email already in use: " + employee.getPersonalEmail());
		}

		// Check for duplicate employee code
		if (employeeRepository.findByEmployeeCode(employee.getEmployeeCode()).isPresent()) {
			throw new RuntimeException("Employee code already exists: " + employee.getEmployeeCode());
		}

		// Encode password
		String hashedPassword = passwordEncoder.encode(employee.getPassword());
		employee.setPassword(hashedPassword);

		// Save employee
		Employee savedEmployee = employeeRepository.save(employee);

		// Prepare response
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
	public ResponseEntity<ResponseStructure<List<Employee>>> login(String email, String password) {
	    ResponseStructure<List<Employee>> structure = new ResponseStructure<>();

	    if (email == null || email.isBlank()) {
	        structure.setStatus(HttpStatus.BAD_REQUEST.value());
	        structure.setMessage("Email is required");
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
	    }

	    if (password == null || password.isBlank()) {
	        structure.setStatus(HttpStatus.BAD_REQUEST.value());
	        structure.setMessage("Password is required");
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
	    }

	    Optional<Employee> optional = employeeRepository.findByOfficialEmail(email);

	    if (optional.isEmpty()) {
	        structure.setStatus(HttpStatus.UNAUTHORIZED.value());
	        structure.setMessage("Invalid email");
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.UNAUTHORIZED);
	    }

	    Employee loggedInUser = optional.get();

	    if (!passwordEncoder.matches(password, loggedInUser.getPassword())) {
	        structure.setStatus(HttpStatus.UNAUTHORIZED.value());
	        structure.setMessage("Invalid password");
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.UNAUTHORIZED);
	    }

	    List<Employee> responseData;

	    Role role = loggedInUser.getRole();

	    if (role == Role.ADMIN) {
	        // Admin can view all employees
	        responseData = employeeRepository.findAll();
	    } else if (role == Role.MANAGER) {
	        // Manager can view their team + self
	        List<Employee> teamMembers = employeeRepository.findByManager(loggedInUser);
	        teamMembers.add(loggedInUser); // Include the manager themselves
	        responseData = teamMembers;
	    } else {
	        // Normal employee can view only themselves
	        responseData = List.of(loggedInUser);
	    }

	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("Login successful");
	    structure.setData(responseData);

	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}



//	ServiceImpl layer for updating the employee
	@Override
	public ResponseEntity<ResponseStructure<Employee>> updateEmployeeDetail(Employee employee, int employeeId) {
	    Optional<Employee> optional = employeeRepository.findById(employeeId);
	    ResponseStructure<Employee> structure = new ResponseStructure<>();

	    if (optional.isEmpty()) {
	        structure.setStatus(HttpStatus.NOT_FOUND.value());
	        structure.setMessage("Employee not found with ID: " + employeeId);
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	    }

	    Employee existingEmployee = optional.get();

	    // Update the fields (ignoring nulls or selectively updating)
	    existingEmployee.setFullName(employee.getFullName());
	    existingEmployee.setOfficialEmail(employee.getOfficialEmail());
	    existingEmployee.setPersonalEmail(employee.getPersonalEmail());
	    existingEmployee.setPhoneNumber(employee.getPhoneNumber());
	    existingEmployee.setEmployeeCode(employee.getEmployeeCode());
	    existingEmployee.setRole(employee.getRole());
	    existingEmployee.setDepartment(employee.getDepartment());
	    existingEmployee.setDesignation(employee.getDesignation());
	    existingEmployee.setDateOfJoining(employee.getDateOfJoining());
	    existingEmployee.setDateOfBirth(employee.getDateOfBirth());
	    existingEmployee.setDateOfLeaving(employee.getDateOfLeaving());
	    existingEmployee.setAadharNumber(employee.getAadharNumber());
	    existingEmployee.setPanNumber(employee.getPanNumber());

	    // Optional: handle password update with hashing
	    if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
	        existingEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
	    }

	    Employee updatedEmployee = employeeRepository.save(existingEmployee);

	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("Employee details updated successfully.");
	    structure.setData(updatedEmployee);

	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}


}
