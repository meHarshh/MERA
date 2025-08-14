package com.mobtions.mira.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mobtions.mira.dto.TaskRequestDTO;
import com.mobtions.mira.entity.Task;
import com.mobtions.mira.util.ResponseStructure;

public interface TaskService {

	ResponseEntity<ResponseStructure<Task>> createTask(TaskRequestDTO taskRequestDTO);

	ResponseEntity<ResponseStructure<List<Task>>> fetchAllTask();

	ResponseEntity<ResponseStructure<Task>> updateTask(Task task);

}
