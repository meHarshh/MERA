package com.mobtions.mira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobtions.mira.entity.Leave;
import com.mobtions.mira.enums.LeaveStatus;
import com.mobtions.mira.service.LeaveService;
import com.mobtions.mira.util.ResponseStructure;


@RequestMapping(value = "leaves")
@RestController
public class LeaveController {
	
	@Autowired
	private LeaveService leaveService;
	
	
	@PostMapping(value = "/{employeeId}")
	private ResponseEntity<ResponseStructure<Leave>> applyLeave(@RequestBody Leave leave, @PathVariable int employeeId){
		return leaveService.applyLeave(leave, employeeId);
	}

	@PutMapping("/{leaveId}/status/{leaveStatus}/by/{approverId}")
	public ResponseEntity<ResponseStructure<Leave>> updateLeaveStatus(@PathVariable int leaveId, @PathVariable LeaveStatus leaveStatus,@PathVariable int approverId) {
	    return leaveService.updateLeaveStatus(leaveId, approverId, leaveStatus);
	}

	
	@GetMapping("/{employeeId}")
	public ResponseEntity<ResponseStructure<List<Leave>>> fetchAllLeavesBasedOnEmployee(@PathVariable int employeeId){
		return leaveService.fetchAllLeavesBasedOnEmployee(employeeId);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Leave>>> fetchAllLeaves(){
		return leaveService.fetchAllLeaves();
	}
}

