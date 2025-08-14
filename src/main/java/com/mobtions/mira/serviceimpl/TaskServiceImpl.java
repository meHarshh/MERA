package com.mobtions.mira.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mobtions.mira.dto.TaskRequestDTO;
import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.entity.Remark;
import com.mobtions.mira.entity.Task;
import com.mobtions.mira.enums.TaskStatus;
import com.mobtions.mira.repo.EmployeeRepository;
import com.mobtions.mira.repo.TaskRepository;
import com.mobtions.mira.service.TaskService;
import com.mobtions.mira.util.ResponseStructure;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public ResponseEntity<ResponseStructure<Task>> createTask(TaskRequestDTO dto) {
	    // Validate assignedBy
	    Employee assignedBy = employeeRepository.findById(dto.getAssignedById())
	            .orElseThrow(() -> new RuntimeException("AssignedBy (Employee ID: " + dto.getAssignedById() + ") not found"));

	    // Validate assignedTo
	    Employee assignedTo = employeeRepository.findById(dto.getAssignedToId())
	            .orElseThrow(() -> new RuntimeException("AssignedTo (Employee ID: " + dto.getAssignedToId() + ") not found"));

	    // Validate reassignedTo (if present)
	    Employee reassignedTo = null;
	    if (dto.getReassignedToId() != null) {
	        reassignedTo = employeeRepository.findById(dto.getReassignedToId())
	                .orElseThrow(() -> new RuntimeException("ReassignedTo (Employee ID: " + dto.getReassignedToId() + ") not found"));
	    }

	    // Map to Task
	    Task task = mapToTask(dto, assignedBy, assignedTo, reassignedTo);

	    // Save
	    Task savedTask = taskRepository.save(task);

	    // Response
	    ResponseStructure<Task> structure = new ResponseStructure<>();
	    structure.setStatus(HttpStatus.CREATED.value());
	    structure.setMessage("Task created successfully");
	    structure.setData(savedTask);

	    return new ResponseEntity<>(structure, HttpStatus.CREATED);
	}

	
	private Task mapToTask(TaskRequestDTO dto, Employee assignedBy, Employee assignedTo, Employee reassignedTo) {
	    Task task = new Task();
	    task.setTitle(dto.getTitle());
	    task.setDescription(dto.getDescription());
	    task.setAssignedDate(dto.getAssignedDate());
	    task.setDeadline(dto.getDeadline());
	    task.setStatus(dto.getStatus() != null ? dto.getStatus() : TaskStatus.PENDING);
	    task.setAssignedBy(assignedBy);
	    task.setAssignedTo(assignedTo);
	    task.setReassignedTo(reassignedTo);
	    return task;
	}


	@Override
	public ResponseEntity<ResponseStructure<List<Task>>> fetchAllTask() {
	    List<Task> allTasks = taskRepository.findAll();

	    ResponseStructure<List<Task>> structure = new ResponseStructure<>();
	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("All tasks fetched successfully");
	    structure.setData(allTasks);

	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<ResponseStructure<Task>> updateTask(Task task) {
	    ResponseStructure<Task> structure = new ResponseStructure<>();

	    // Basic validation
	    if (task == null || task.getTaskId() <= 0) {
	        structure.setStatus(HttpStatus.BAD_REQUEST.value());
	        structure.setMessage("Task or valid taskId is required");
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
	    }

	    // Load existing task
	    Optional<Task> optional = taskRepository.findById(task.getTaskId());
	    if (optional.isEmpty()) {
	        structure.setStatus(HttpStatus.NOT_FOUND.value());
	        structure.setMessage("Task not found with ID: " + task.getTaskId());
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	    }

	    Task existing = optional.get();

	    // ---- Partial merge: only overwrite when a new value is provided ----
	    if (task.getTitle() != null && !task.getTitle().isBlank()) {
	        existing.setTitle(task.getTitle());
	    }
	    if (task.getDescription() != null) {
	        existing.setDescription(task.getDescription());
	    }
	    if (task.getAssignedDate() != null) {
	        existing.setAssignedDate(task.getAssignedDate());
	    }
	    if (task.getDeadline() != null) {
	        existing.setDeadline(task.getDeadline());
	    }
	    if (task.getStatus() != null) {
	        existing.setStatus(task.getStatus());
	    }
	    if (task.getAssignedBy() != null) {
	        existing.setAssignedBy(task.getAssignedBy());
	    }
	    if (task.getAssignedTo() != null) {
	        existing.setAssignedTo(task.getAssignedTo());
	    }
	    if (task.getReassignedTo() != null) {
	        existing.setReassignedTo(task.getReassignedTo());
	    }

	    // Remarks handling:
	    // Append any provided remarks to the existing list and fix the back-reference.
	    // (Safer default than replacing, avoids orphan issues unless orphanRemoval is enabled.)
	    if (task.getRemarks() != null && !task.getRemarks().isEmpty()) {
	        if (existing.getRemarks() == null) {
	            existing.setRemarks(new java.util.ArrayList<>());
	        }
	        for (Remark r : task.getRemarks()) {
	            if (r != null) {
	                r.setTask(existing);              // maintain owning side
	                existing.getRemarks().add(r);     // append
	            }
	        }
	    }

	    // Persist
	    Task saved = taskRepository.save(existing);

	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("Task updated successfully");
	    structure.setData(saved);
	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}






}
