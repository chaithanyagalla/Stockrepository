package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFilesUtili {
  Workbook wb;
	public ExcelFilesUtili(String Excelpath) throws Throwable {
		
		FileInputStream ji=new FileInputStream(Excelpath);
		wb=WorkbookFactory.create(ji);
		
	}
 public int rowCount(String sheetname) {
	 
	 return wb.getSheet(sheetname).getLastRowNum();
	 
 }
public String getCellData(String sheetname,int row,int column) {
	
	String data="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	{
		//read integert type cell and store
		int celldata = (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		//convert integer type to string
		data =String.valueOf(celldata);
	}
	else
	{
		data =wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}
 public void setCellData(String sheetname,int row,int column,String status,String writeExcel) throws Throwable {
	 
	 Sheet sh=wb.getSheet(sheetname);
     Row rows=sh.getRow(row);
    Cell cel=rows.createCell(column);
    cel.setCellValue(status);
     if(status.equalsIgnoreCase("pass")) {
    	 
    	CellStyle style=wb.createCellStyle();
    	Font font=wb.createFont();
    	font.setColor(IndexedColors.GREEN.getIndex());
        font.setBold(true);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        rows.getCell(column).setCellStyle(style);
     }else  if(status.equalsIgnoreCase("Fail")) {
    	 
    	CellStyle style=wb.createCellStyle();
    	Font font=wb.createFont();
    	font.setColor(IndexedColors.BLUE.getIndex());
        font.setBold(true);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        rows.getCell(column).setCellStyle(style);
     }else  if(status.equalsIgnoreCase("Blocked")) {
    	 
    	CellStyle style=wb.createCellStyle();
    	Font font=wb.createFont();
    	font.setColor(IndexedColors.RED.getIndex());
        font.setBold(true);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        rows.getCell(column).setCellStyle(style);
 }
  FileOutputStream op=new FileOutputStream(writeExcel);
  wb.write(op);
}
public static void main(String[]args) throws Throwable {
	
	ExcelFilesUtili xl=new ExcelFilesUtili("D:/Sample.xlsx"); 
  int rows= xl.rowCount("EmpData");
	System.out.println("No of Rows Count"+rows);
	for(int i=1;i<=rows;i++) {
  String fname= xl.getCellData("EmpData", i, 0);
	System.out.println(fname);
	}
}

}