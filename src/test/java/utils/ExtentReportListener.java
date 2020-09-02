package utils;

import java.io.*;
import java.io.File;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportListener implements ITestListener {
	
	protected static ExtentReports reports;
	protected static ExtentTest test;

	private static String resultPath = getResultPath();
	
	String reportLocation = "test-output/Report/" + resultPath + "/";

	public static void  deleteDirectory(File directory)
	{
		if(directory.exists()) {
			File[] files = directory.listFiles();
			if(null != files) {
				for(int i=0; i<files.length; i++)
				{
					System.out.println("Files Name===> " + files[i].getName());
					if(files[i].isDirectory())
						deleteDirectory(files[i]);
					else
						files[i].delete();
				}

			}
		}

	}

	// Date format
	public static String getResultPath() 
	{
		resultPath = "test"; // new SimpleDateFormat("yyyy-mm-dd hh-mm-ss").format(new Date());
		if(!new File(resultPath).isDirectory()) {
			new File(resultPath);
		}
		return resultPath;
	}



	public void onTestStart(ITestResult result) 
	{

		test = reports.startTest(result.getMethod().getMethodName());
		test.log(LogStatus.INFO, result.getMethod().getMethodName());
		//System.out.println(result.getTestClass().getTestName());
		//System.out.println("\n<===== Test Method Name =====> " + result.getMethod().getMethodName() + "\n");	  
	}

	public void onTestSuccess(ITestResult result)
	{
		test.log(LogStatus.PASS, "Test is Pass");
	}
	public void onTestFailure(ITestResult result)
	{
		test.log(LogStatus.FAIL, "Test is Fail");
	}
	public void onTestSkipped(ITestResult result)
	{
		test.log(LogStatus.SKIP, "Test is Skipped");
	}
	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
		//TODO Auto-generated method stub
	}


	public void onStart(ITestContext context)
	{
		//logger.info(" ReportLocation:>  " + reportLocation );
		reports = new ExtentReports(reportLocation + "ExtentReport.html");
		test = reports.startTest("");
	}

	public void onFinish(ITestContext context)
	{
		reports.endTest(test);
		reports.flush();
	}





}
