package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDAO;
import com.example.demo.model.dto.PathDTO;


import ch.qos.logback.core.joran.conditional.IfAction;
import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.PathSelectors;

@Service
public class ExcelService {

	
	public Workbook readFile(String dirPath, String fileName) throws IOException{
		String extName = fileName.substring(fileName.lastIndexOf("."));

		Workbook workbook = null;
		try (FileInputStream inputStream = new FileInputStream(new File(dirPath+fileName));) {
			if ("xls".equals(extName)) {
				workbook = new HSSFWorkbook(inputStream);

			} else {
				workbook = new XSSFWorkbook(inputStream);
			}
		}
		return workbook;
	}
	
	

}
