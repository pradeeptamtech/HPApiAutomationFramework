package baseTest;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import apiConfig.APIPath;

import io.restassured.RestAssured;
import utils.ExtentReportListener;
import utils.FileandEnv;

@Listeners(ExtentReportListener.class)
public class BaseTest extends ExtentReportListener {
	
	final static Logger logger = LogManager.getLogger(BaseTest.class);
		
	@BeforeClass
	public void baseTest() throws IOException {
		
		//logger.info("Before Class method.................");
		test.log(LogStatus.INFO,"*****Started Test case Execution **********");
		
		RestAssured.baseURI = FileandEnv.endAndFile().get("ServerUrl");
		//System.out.println("baseURI-> " + RestAssured.baseURI);
	}
	
	
	@AfterClass
	public void TearDown() {
		//logger.info("After Class method...........");
		test.log(LogStatus.INFO,"**************Finished all test cases execution***********");
	}
	
//    @Test
//	public void utilsTest()
//    {
//    	
//		int a=10;
//		int b=8;
//		int sum=a+b;
//		//logger.info("Util Test  suite method..........");
//				
////		test.log(LogStatus.INFO, "Test has been started.....");
////		test.log(LogStatus.PASS, "Test has  Passed.....");
//		System.out.println("----properties data----> \n"+FileandEnv.endAndFile());
//		System.out.println("\n ======server url====>"+FileandEnv.endAndFile().get("ServerUrl"));
//		System.out.println("\n server URL---:: " + APIPath.apiPath.GET_PRINT_HEAD_INFO);
//		System.out.println("\n server URL---:: " + APIPath.apiPath.GET_LIST_OF_USERS);
//		
////		test.log(LogStatus.PASS, "Sum value is" + sum);
////		test.log(LogStatus.FAIL, "Sum value is" + sum);
////		test.log(LogStatus.INFO, "Test has  completed.........");
//		
//	}

	
}
