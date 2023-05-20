package com.example.demo.utils;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtils {
	
	private ExcelUtils() {}
	
	
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
                returnValue=Integer.parseInt(cellValue);
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
}
