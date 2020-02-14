/**
 * 
 */
package com.fremap.prueba.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author CMONTE5
 *
 */
public class WriteExcelFile {

//EScribir lista de datos
	
	@SuppressWarnings("resource")
	public void writeExcel (String filepath, String sheetName, String [] dataToWrite) throws IOException{
		
		File file = new File(filepath);// Creamos nuestro archivo
		
		FileInputStream inputStream = new FileInputStream(file);
		
		XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);//Creamos donde guardamos el libro excel
		
		XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
		
		int rowCount = newSheet.getLastRowNum() - newSheet.getFirstRowNum();
		
		XSSFRow row = newSheet.getRow(0);
		
		XSSFRow newRow = newSheet.createRow(rowCount+1);
		
		for (int i = 0; i < row.getLastCellNum(); i++) {
			XSSFCell newCell = newRow.createCell(i);
			newCell.setCellValue(dataToWrite[i]);
		}
		
		inputStream.close();
		
		FileOutputStream output = new FileOutputStream(file);
		
		newWorkbook.write(output);
		
		output.close();	
	}
	
	//Para crear un valor en una celda especifica
	
	 @SuppressWarnings("resource")
	public void writeCellValue(String filepath, String sheetName, int rowNumber, int cellNumber, String resultText) throws IOException{
			File file = new File(filepath);// Creamos nuestro archivo
			
			FileInputStream inputStream = new FileInputStream(file);
			
			XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);//Creamos donde guardamos el libro excel
			
			XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
			
			XSSFRow row = newSheet.getRow(rowNumber);
			
			XSSFCell firstCell = row.getCell(cellNumber-1);
			
			System.out.println("first cell value is: " + firstCell.getStringCellValue());
			
			XSSFCell nextCell = row.createCell(cellNumber);
			
			DataFormatter formatter = new DataFormatter();
			String formattedValue = formatter.formatCellValue(nextCell);
			
			nextCell.setCellValue(resultText);

			System.out.println("nextcell value: " + formattedValue);
			
			inputStream.close();
			
			FileOutputStream output = new FileOutputStream(file);
			
			newWorkbook.write(output);
			
			output.close();
	 }
}
