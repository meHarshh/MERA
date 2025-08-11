package com.mobtions.mira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobtions.mira.dto.TaskRequestDTO;
import com.mobtions.mira.entity.Task;
import com.mobtions.mira.service.TaskService;
import com.mobtions.mira.util.ResponseStructure;

@CrossOrigin(allowCredentials = "true", origins = "http://mera.kingsmenrealty.com/")
@RequestMapping("tasks")
@RestController
public class TaskController {

	
	@Autowired
	private TaskService taskService;
	
	
	@PostMapping
	private ResponseEntity<ResponseStructure<Task>> createTask(@RequestBody TaskRequestDTO taskRequestDTO){
		return taskService.createTask(taskRequestDTO);
	}
	
	@GetMapping
	private ResponseEntity<ResponseStructure<List<Task>>> fetchAllTask(){
		return taskService.fetchAllTask();
	}
	
}
