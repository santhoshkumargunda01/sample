package com.dfe.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	FileInputStream fis;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row; 
	
	
	public ExcelUtil(String FilePath, String SheetName) throws IOException {
		 fis = new FileInputStream(FilePath);
		 workbook = new XSSFWorkbook(fis);
		 sheet = workbook.getSheet(SheetName);
		 row = sheet.getRow(0);
	}
	
	public String getSpecificColumnData( String ColumnName) {
		
		int col_num = -1;
		for(int i=0; i < row.getLastCellNum(); i++)
		{
			if(row.getCell(i).getStringCellValue().trim().equals(ColumnName))
				col_num = i;
		}
		row = sheet.getRow(1);
		XSSFCell cell = row.getCell(col_num);
		String value = cell.getStringCellValue();
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Value of the Excel Cell is - "+ value);    	 
		return value;
	}

	public void setSpecificColumnData(String FilePath, String SheetName, String ColumnName) throws IOException{
		FileInputStream fis;
		fis = new FileInputStream(FilePath);
		FileOutputStream fos = null;
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(SheetName);
		XSSFRow row = null;
		XSSFCell cell = null;
		XSSFFont font = workbook.createFont();
		XSSFCellStyle style = workbook.createCellStyle();
		int col_Num = -1;
		row = sheet.getRow(0);
		for(int i = 0; i < row.getLastCellNum(); i++)
		{
			if(row.getCell(i).getStringCellValue().trim().equals(ColumnName))
			{
				col_Num = i;
			}
		}
		row = sheet.getRow(1);
		if(row == null)
			row = sheet.createRow(1);
		cell = row.getCell(col_Num);
		if(cell == null)
			cell = row.createCell(col_Num);
		font.setFontName("Comic Sans MS");
		font.setFontHeight(14.0);
		font.setBold(true);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		style.setFillForegroundColor(HSSFColor.GREEN.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		cell.setCellValue("PASS");
		fos = new FileOutputStream(FilePath);
		workbook.write(fos);
		fos.close();
	}

}
