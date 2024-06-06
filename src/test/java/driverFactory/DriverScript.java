package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionalLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	//declare global variables
	WebDriver driver;
	String inputpath = "./FileInput/DataEngine.xlsx";
	String outputpath = "./FileOutPut/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String TCSheet = "MasterTestCases";
	@Test
	public void startTest() throws Throwable
	{
		String Module_status ="";
		String Module_new = "";
		// create object for excel file util class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//iterate all test cases in  TC sheet
		for (int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				//store each sheet into one variable 
				String TCModule = xl.getCellData(TCSheet, i, 1);
				//define path of html 
				report = new ExtentReports("./target/ExtentReports/"+TCModule+FunctionalLibrary.generateDate()+".html");
				logger = report.startTest(TCModule);
				logger.assignAuthor("REVATHI");
				//iterate all rows in every sheet
				for(int j=1;j<= xl.rowCount(TCModule);j++)
				{
					//read each cell from TCModule
					String Description = xl.getCellData(TCModule, j, 0);
					String ObjectType = xl.getCellData(TCModule, j, 1);
					String Ltype = xl.getCellData(TCModule, j, 2);
					String Lvalue = xl.getCellData(TCModule, j, 3);
					String TestData = xl.getCellData(TCModule, j, 4);
					try {
						if(ObjectType.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionalLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjectType.equalsIgnoreCase("openUrl"))
						{
							FunctionalLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);

						}
						if (ObjectType.equalsIgnoreCase("waitForElement"))
						{
							FunctionalLibrary.waitForElement(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);

						}
						if (ObjectType.equalsIgnoreCase("typeAction"))
						{
							FunctionalLibrary.typeAction(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);

						}
						if (ObjectType.equalsIgnoreCase("clickAction"))
						{
							FunctionalLibrary.clickAction(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);

						}
						if (ObjectType.equalsIgnoreCase("validateTitle"))
						{
							FunctionalLibrary.validateTitle(TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if (ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							FunctionalLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}

						if(ObjectType.equalsIgnoreCase("dropDownAction"))
						{
							FunctionalLibrary.dropDownAction(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("captureStock"))
						{
							FunctionalLibrary.captureStock(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							FunctionalLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("captureSup"))
						{
							FunctionalLibrary.captureSup(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("supplierTable"))
						{
							FunctionalLibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("captureCus"))
						{
							FunctionalLibrary.captureCus(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(ObjectType.equalsIgnoreCase("customerTable"))
						{
							FunctionalLibrary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}


						//write as pass  into status call in TcModule Sheet
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_status ="True";
					}catch (Throwable t)
					{
						System.out.println(t.getMessage());
						//write as fail into status cell in TCModule sheet
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Module_new = "False";
						//take screen shot 
						File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./target/Screenshot/"+Description+FunctionalLibrary.generateDate()+".png"));
					}
					if (Module_status.equalsIgnoreCase("True"))
					{
						xl.setCellData(TCSheet, i, 3, "pass", outputpath);
					}
					if (Module_status.equalsIgnoreCase("Fail"))
					{
						xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
					}
					report.endTest(logger);
					report.flush();
				}
			}
			else
			{
				//write as blocked for testcases flag to N
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);

			}
		}
	}
}
