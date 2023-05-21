package com.example.demo.model.excel;

import java.time.OffsetDateTime;

import com.example.demo.model.entity.EmployeeEntity;

import lombok.Data;

@Data
public class EmployeeExcelDTO {
	private Long oid;
	private String firstName;
	private String lastName;
	private Integer age;
}
