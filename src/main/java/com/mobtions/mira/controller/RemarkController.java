package com.mobtions.mira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobtions.mira.dto.RemarkRequestDTO;
import com.mobtions.mira.entity.Remark;
import com.mobtions.mira.service.RemarkService;
import com.mobtions.mira.util.ResponseStructure;

@CrossOrigin(allowCredentials = "true", origins = "http://mera.kingsmenrealty.com")
@RequestMapping("remarks")
@RestController
public class RemarkController {

	@Autowired
	private RemarkService remarkService;

	@PostMapping
	private ResponseEntity<ResponseStructure<Remark>> addRemark(@RequestBody RemarkRequestDTO remarkRequestDTO) {
		return remarkService.addRemark(remarkRequestDTO);
	}

	@GetMapping(value = "/{taskId}")
	private ResponseEntity<ResponseStructure<List<Remark>>> fetchRemarkBasedOnTaskId(@PathVariable int taskId) {
		return remarkService.fetchRemarkBasedOnTaskId(taskId);
	}
}
