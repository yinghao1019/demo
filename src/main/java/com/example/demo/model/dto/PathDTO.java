package com.example.demo.model.dto;

import javax.management.loading.PrivateClassLoader;

import lombok.Data;

@Data
public class PathDTO {
	private String dirPath;
	private String fileName;
	private Integer sheetNum;
}
