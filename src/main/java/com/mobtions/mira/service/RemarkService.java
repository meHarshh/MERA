package com.mobtions.mira.service;

import org.springframework.http.ResponseEntity;

import com.mobtions.mira.dto.RemarkRequestDTO;
import com.mobtions.mira.entity.Remark;
import com.mobtions.mira.util.ResponseStructure;

public interface RemarkService {

	ResponseEntity<ResponseStructure<Remark>> addRemark(RemarkRequestDTO remarkRequestDTO);

}
