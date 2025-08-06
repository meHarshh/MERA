package com.mobtions.mira.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobtions.mira.dto.RemarkRequestDTO;
import com.mobtions.mira.entity.Remark;
import com.mobtions.mira.service.RemarkService;
import com.mobtions.mira.util.ResponseStructure;

@RequestMapping("remarks")
@RestController
public class RemarkController {

	
	@Autowired
	private RemarkService remarkService;
	
	
	@PostMapping
	private ResponseEntity<ResponseStructure<Remark>> addRemark(@RequestBody RemarkRequestDTO remarkRequestDTO){
		return remarkService.addRemark(remarkRequestDTO);
	}
}
