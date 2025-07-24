package com.mobtions.mira.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mobtions.mira.entity.Leave;
import com.mobtions.mira.util.ResponseStructure;

public interface LeaveService {

	ResponseEntity<ResponseStructure<Leave>> applyLeave(Leave leave, int employeeId);

	ResponseEntity<ResponseStructure<Leave>> approveLeave(int leaveId, int approverId);

	ResponseEntity<ResponseStructure<List<Leave>>> fetchAllLeavesBasedOnEmployee(int employeeId);

	ResponseEntity<ResponseStructure<List<Leave>>> fetchAllLeaves();

}
