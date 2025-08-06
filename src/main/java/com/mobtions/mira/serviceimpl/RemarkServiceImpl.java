package com.mobtions.mira.serviceimpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mobtions.mira.dto.RemarkRequestDTO;
import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.entity.Remark;
import com.mobtions.mira.entity.Task;
import com.mobtions.mira.repo.EmployeeRepository;
import com.mobtions.mira.repo.RemarkRepository;
import com.mobtions.mira.repo.TaskRepository;
import com.mobtions.mira.service.RemarkService;
import com.mobtions.mira.util.ResponseStructure;

@Service
public class RemarkServiceImpl implements RemarkService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private RemarkRepository remarkRepository;
	
	@Override
	public ResponseEntity<ResponseStructure<Remark>> addRemark(RemarkRequestDTO remarkRequestDTO) {
	    // Step 1: Fetch the employee who commented
	    Employee commenter = employeeRepository.findById(remarkRequestDTO.getCommentedById())
	        .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + remarkRequestDTO.getCommentedById()));

	    // Step 2: Fetch the task the remark is for
	    Task task = taskRepository.findById(remarkRequestDTO.getTaskId())
	        .orElseThrow(() -> new RuntimeException("Task not found with ID: " + remarkRequestDTO.getTaskId()));

	    // Step 3: Create and populate the remark
	    Remark remark = new Remark();
	    remark.setContent(remarkRequestDTO.getContent());
	    remark.setDate(LocalDate.now());
	    remark.setCommentedBy(commenter);
	    remark.setTask(task);

	    // Step 4: Save the remark
	    Remark savedRemark = remarkRepository.save(remark);

	    // Step 5: Prepare response
	    ResponseStructure<Remark> structure = new ResponseStructure<>();
	    structure.setStatus(HttpStatus.CREATED.value());
	    structure.setMessage("Remark added to task successfully");
	    structure.setData(savedRemark);

	    return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}


}
