package com.example.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import exception.FileExtensionNotAllowedException;

public class ExcelUtils {
	private static final List<String> EXT_NAME = List.of("xls", "xlsx");

	private ExcelUtils() {
	}
	
	public static Workbook getWorkbook(MultipartFile file) throws IOException {
		String fileExtension = FileNameUtils.getExtension(file.getOriginalFilename());
		Workbook workbook = null;
		try (InputStream inputStream=file.getInputStream()){
			if ("xls".equals(fileExtension)) {
				workbook = new HSSFWorkbook(inputStream);
			} else if ("xlsx".equals(fileExtension)) {
				workbook = new XSSFWorkbook(inputStream);
			}
		}
		return workbook;
	}

	public static String convertCellValueToStr(Cell cell) {
		if (cell == null) {
			return null;
		}
		String returnValue = null;
		switch (cell.getCellType()) {
		case NUMERIC:
			Double doubleValue = cell.getNumericCellValue();
			DecimalFormat df = new DecimalFormat("0");
			returnValue = df.format(doubleValue);
			break;
		case STRING:
			returnValue = cell.getStringCellValue();
			break;
		case BOOLEAN:
			Boolean booleanValue = cell.getBooleanCellValue();
			returnValue = booleanValue.toString();
			break;
		case BLANK:
			break;
		case FORMULA:
			returnValue = cell.getCellFormula();
			break;
		case ERROR:
			break;
		default:
			break;
		}
		return returnValue;
	}

	public static Integer convertCellValueToInt(Cell cell) {
		if (cell == null) {
			return null;
		}
		Integer returnValue = null;
		switch (cell.getCellType()) {
		case NUMERIC:
			Double doubleValue = cell.getNumericCellValue();
			returnValue = doubleValue.intValue();
			break;
		case STRING:
			String cellValue = cell.getStringCellValue();
			try {
				returnValue = Integer.parseInt(cellValue);
			} catch (NumberFormatException e) {
				return 0; // Return a default value or throw an exception
			}
			break;
		case BOOLEAN:
			Boolean booleanValue = cell.getBooleanCellValue();
			break;
		case FORMULA:
			break;
		case ERROR:
			break;
		default:
			break;
		}
		return returnValue;
	}

	public static boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
				return false;
			}
		}
		return true;
	}

	public static void checkFileExtension(String fileName) {

		if (StringUtils.isBlank(fileName) || !EXT_NAME.contains(FileNameUtils.getExtension(fileName))) {
			throw new FileExtensionNotAllowedException(fileName);
		}
		
	}
}
