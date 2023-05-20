package com.example.demo.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.MessageDTO;
import com.example.demo.model.dto.PathDTO;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.ExcelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {
	private final EmployeeService employeeService;
	
	@PostMapping("/import")
	public ResponseEntity<MessageDTO> importExcel(@RequestBody PathDTO pathDTO) throws IOException {
		employeeService.importLocalExcelData(pathDTO);
		
		MessageDTO messageDTO=new MessageDTO();
		messageDTO.setMessage("specif file success");
		return ResponseEntity.ok(messageDTO);
	}
}