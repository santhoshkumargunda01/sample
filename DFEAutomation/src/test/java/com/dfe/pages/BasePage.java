package com.dfe.pages;

import java.io.IOException;

import com.dfe.utilities.ExcelUtil;

public class BasePage  {
	
	ExcelUtil xlsrdr; 
	
	public BasePage()  {
		try {
			xlsrdr = new ExcelUtil("./testdata/Data.xlsx","Sheet1");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

}
