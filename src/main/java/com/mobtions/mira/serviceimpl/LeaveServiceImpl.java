package com.mobtions.mira.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.entity.Leave;
import com.mobtions.mira.enums.LeaveStatus;
import com.mobtions.mira.repo.EmployeeRepository;
import com.mobtions.mira.repo.LeaveRepository;
import com.mobtions.mira.service.LeaveService;
import com.mobtions.mira.util.ResponseStructure;

@Service
public class LeaveServiceImpl implements LeaveService{


	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private LeaveRepository leaveRepository;

	@Override
	public ResponseEntity<ResponseStructure<Leave>> applyLeave(Leave leave, int employeeId) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

		if (optionalEmployee.isEmpty()) {
			throw new RuntimeException("Employee not found with ID: " + employeeId);
		}

		Employee employee = optionalEmployee.get();

		leave.setEmployee(employee);
		leave.setLeaveStatus(LeaveStatus.PENDING);
		leave.setAppliedDate(LocalDate.now());
		leave.setApprovedBy(null);

		Leave savedLeave = leaveRepository.save(leave);

		ResponseStructure<Leave> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setMessage("Leave applied successfully");
		structure.setData(savedLeave);

		return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}


	@Override
	public ResponseEntity<ResponseStructure<Leave>> approveLeave(int leaveId, int approverId) {
		Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
		Optional<Employee> optionalApprover = employeeRepository.findById(approverId);

		if (optionalLeave.isEmpty()) {
			throw new RuntimeException("Leave not found with ID: " + leaveId);
		}

		if (optionalApprover.isEmpty()) {
			throw new RuntimeException("Approver not found with ID: " + approverId);
		}

		Leave leave = optionalLeave.get();
		Employee approver = optionalApprover.get();

		leave.setLeaveStatus(LeaveStatus.APPROVED);
		leave.setApprovedBy(approver);

		Leave updatedLeave = leaveRepository.save(leave);

		ResponseStructure<Leave> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("Leave approved successfully");
		structure.setData(updatedLeave);

		return new ResponseEntity<>(structure, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<ResponseStructure<List<Leave>>> fetchAllLeavesBasedOnEmployee(int employeeId) {
	    Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

	    if (optionalEmployee.isEmpty()) {
	        throw new RuntimeException("Employee not found with ID: " + employeeId);
	    }

	    Employee employee = optionalEmployee.get();
	    List<Leave> leaves = leaveRepository.findByEmployee(employee);

	    ResponseStructure<List<Leave>> structure = new ResponseStructure<>();
	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("Leaves fetched successfully for employee ID: " + employeeId);
	    structure.setData(leaves);

	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<ResponseStructure<List<Leave>>> fetchAllLeaves() {
	    List<Leave> leaves = leaveRepository.findAll();

	    ResponseStructure<List<Leave>> structure = new ResponseStructure<>();

	    if (leaves.isEmpty()) {
	        structure.setStatus(HttpStatus.NO_CONTENT.value());
	        structure.setMessage("No leave records found");
	        structure.setData(leaves);
	        return new ResponseEntity<>(structure, HttpStatus.NO_CONTENT);
	    }

	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("All leave records fetched successfully");
	    structure.setData(leaves);

	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}


}
