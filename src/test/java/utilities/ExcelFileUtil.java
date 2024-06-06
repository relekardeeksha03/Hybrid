package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
	XSSFWorkbook wb;
	//constructor for reading excel path
	public ExcelFileUtil(String Excelpath)throws Throwable 
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = new XSSFWorkbook(fi);
	}
	//method for counting no of rows in sheet 
	public int rowCount(String sheetName)
	{
		return wb.getSheet(sheetName).getLastRowNum();
	}
	//method for reading cell data 
	public String getCellData(String sheetname,int row,int column)
	{
		String data;
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldta = (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			
			data = String.valueOf(celldta);
		}
		else
		{
			data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	}

	//method for write results into  new workbook
	public void setCellData (String sheetname, int row , int column ,String status,String WriteExcel)throws Throwable
	{
		//get sheet from wb 
		XSSFSheet ws = wb.getSheet(sheetname);
		//get row from sheet
		XSSFRow rowNum = ws.getRow(row);
		//create cell in row 
		XSSFCell cell = rowNum.createCell(column);
		//write status into cell 
		cell .setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			//color text with green
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("Fail"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			//color text with Red
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase("blocked"))
		{
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			//color text with Blue
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);

	}
}

