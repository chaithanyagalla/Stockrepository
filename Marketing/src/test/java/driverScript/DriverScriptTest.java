package driverScript;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.WebConsole.Logger;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibra;
import utility.ExcelFilesUtili;

public class DriverScriptTest {
String inputpath="D:\\qedg\\Marketing\\InputPath\\outresult.xlsx";
String outputPath="D:\\qedg\\Marketing\\OutputPath\\resy.xlsx";
ExtentReports reports;
ExtentTest test;
public static WebDriver driver;
public void testScript() throws Throwable {
	String ModuleStatus="";
	ExcelFilesUtili xl=new ExcelFilesUtili(inputpath);
  for(int i=1;i<=xl.rowCount("MasterTestCases");i++) {
	  
	  if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y")) {
		 
		  String testmodule=xl.getCellData("MasterTestCases", i, 1);
		   
		  for(int j=1;j<=xl.rowCount(testmodule);j++) {
			 
			  String Description=xl.getCellData(testmodule, j, 0);
			  String Object_Type=xl.getCellData(testmodule, j, 1);
			  String Locator_Type=xl.getCellData(testmodule, j, 2);
			  String Locator_Value=xl.getCellData(testmodule, j, 3);
			  String TestData =xl.getCellData(testmodule, j, 4);
			  reports=new ExtentReports("./Extentreports/"+testmodule+FunctionLibra.getDate()+".html");   
			  test=reports.startTest(testmodule);
			  test.assignAuthor("chaitanya");
			  try {
			  if(Object_Type.equalsIgnoreCase("startBrowser")) {
				  
				  driver=FunctionLibra.startBrowser();
			     test.log(LogStatus.INFO, Description);
			  
			  }else if(Object_Type.equalsIgnoreCase("openUrl")) {
				  FunctionLibra.openUrl(driver);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("waitFotElement")) {
				  FunctionLibra.waitFotElement(Object_Type, Locator_Type, Locator_Value, TestData);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("typeAction")) {
				  FunctionLibra.typeAction(driver, Locator_Type, Locator_Value, TestData);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("clickAction")) {
				  FunctionLibra.clickAction(driver, Locator_Type, Locator_Value);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("validateHomeTitle")) {
				  FunctionLibra.validateHomeTitle(driver, TestData);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("closeBrowser")) {
				  FunctionLibra.closeBrowser();
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("mouseClick")) {
				  
				  FunctionLibra.mouseClick(driver);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("categoryTable")) {
				  FunctionLibra.categoryTable(driver, TestData);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("captureData")) {
				  FunctionLibra.captureData(driver, Locator_Type, Locator_Value);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("supplierTable")) {
				  FunctionLibra.supplierTable(driver);
				  test.log(LogStatus.INFO, Description);
			  }else if(Object_Type.equalsIgnoreCase("validateTable")) {
				 FunctionLibra.validateTable(driver,Locator_Type,Locator_Value);
				 test.log(LogStatus.INFO, Description);
			 
			  }else if(Object_Type.equalsIgnoreCase("selectList")) {
				  FunctionLibra.selectList(driver, Locator_Value);
				  test.log(LogStatus.INFO, Description);
			  }
			  
			  
			  xl.setCellData(testmodule, j, 5, "Pass",outputPath);
			 test.log(LogStatus.PASS, Description);
			  ModuleStatus="True";
			  
			  
		  } catch(Exception t) {
				  System.out.println(t.getMessage());
			 xl.setCellData(testmodule, j, 5, "Fail", outputPath);
			test.log(LogStatus.FAIL, Description);	 
			 ModuleStatus="False";
			 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				
				FileUtils.copyFile(scrFile, new File("./Screenshots/"+Description+"_"+FunctionLibra.getDate()+".png"));
				
				String image = test.addScreenCapture("./Screenshots/"+Description+"_"+FunctionLibra.getDate()+".png");
				
				test.log(LogStatus.FAIL, image);
				break;		
			}
			catch(AssertionError e)
			{
				xl.setCellData(testmodule, j, 5, "Fail", outputPath);
				ModuleStatus = "false";
				test.log(LogStatus.FAIL, Description + "Fail");
				
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
             
				FileUtils.copyFile(scrFile, new File("./Screenshots/"+Description+"_"+FunctionLibra.getDate()+".jpg"));
				
				String image = test.addScreenCapture("./Screenshots/"+Description+"_"+FunctionLibra.getDate()+".jpg");
				
				test.log(LogStatus.FAIL, image);
				
				break;
		  }
		           if(ModuleStatus.equals("True")) {
		        	   xl.setCellData("MasterTestCases", i, 3, "Pass", outputPath);
		           }else {
		        	   
		        	   xl.setCellData("MasterTestCases", i, 3, "Fail", outputPath);
		           }
		       
		  }
		  
	  }else {
		  
		  xl.setCellData("MasterTestCases", i, 3, "Blocked", outputPath);
	  }
  }


}
}


