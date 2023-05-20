package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.EmployeeDAO;
import com.example.demo.model.dto.PathDTO;
import com.example.demo.model.entity.EmployeeEntity;
import com.example.demo.utils.ExcelUtils;

import exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.var;

@Service
@RequiredArgsConstructor
public class EmployeeService {
	private final ExcelService excelService;

	private final EmployeeDAO employeeDAO;

	public void importLocalExcelData(PathDTO pathDTO) throws IOException {
		Path filePath = Paths.get(pathDTO.getDirPath(), pathDTO.getFileName());

		if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
			throw new BadRequestException("resource-not-found");
		}

		Workbook workbook = excelService.readFile(pathDTO.getDirPath(), pathDTO.getFileName());
		List<EmployeeEntity> employeeEntities = parseEmployeeExcel(pathDTO.getSheetNum(), workbook);
		employeeDAO.saveAll(employeeEntities);
	}

	public void uploadExcel(MultipartFile file,Integer sheetNum) {
		String fileExtension=file.getOriginalFilename();
		Workbook workbook=null;
		if("xls".equals(fileExtension)) {
			
		}else if("xlsx".equals(fileExtension)) {
			
		}
		
		multipartFile.getInputStream();
		
		return 
	}

	private List<EmployeeEntity> parseEmployeeExcel(Integer sheetIndex, Workbook workbook) {
		List<EmployeeEntity> employeeList = new ArrayList<EmployeeEntity>();

		Sheet sheet = workbook.getSheetAt(sheetIndex);
		if (sheet == null) {
			return employeeList;
		}

		int firstRowNum = sheet.getFirstRowNum();
		Row firstRow = sheet.getRow(firstRowNum);

		if (firstRow == null) {
			throw new BadRequestException("invalid file content");
		}

		int rowEnd = sheet.getPhysicalNumberOfRows();
		for (int rowNum = 1; rowNum < rowEnd; rowNum++) {
			Row row = sheet.getRow(rowNum);
			// check row is empty
			if (null == row) {
				continue;
			}
			try {
				employeeList.add(convertRowToEmployee(row));
			} catch (Exception e) {
				throw new BadRequestException(String.format("invalid row num:%d", rowNum));
			}
		}

		return employeeList;
	}

	private EmployeeEntity convertRowToEmployee(Row row) {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		int cellNum = 0;
		// first name
		Cell firstName = row.getCell(cellNum);
		employeeEntity.setFirstName(ExcelUtils.convertCellValueToStr(firstName));

		// last Name
		Cell lastName = row.getCell(cellNum++);
		employeeEntity.setFirstName(ExcelUtils.convertCellValueToStr(lastName));
		// age
		Cell age = row.getCell(cellNum++);
		employeeEntity.setAge(ExcelUtils.convertCellValueToInt(age));

		return employeeEntity;
	}
}