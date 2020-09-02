package hp.Seals.APITest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;
import apiConfig.HeaderConfigs;
import apiConfig.APIPath;
import apiVerification.APIVerification;
import baseTest.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ExcelFileSetFromProp;
import utils.Excel_Utility;
import utils.ExtentReportListener;
import utils.FileandEnv;
import utils.UtilityApiMethods;


public class SealsApiTest extends BaseTest {

	final static Logger logger = LogManager.getLogger(SealsApiTest.class);

	HeaderConfigs header = new HeaderConfigs();
	ExcelFileSetFromProp  xlProp = new ExcelFileSetFromProp();

	List<List> row = new ArrayList<List>();

	@BeforeTest
	public void readExcel()
	{
		String excelPath = "./Data/TestData.xlsx";

		String sheetName = "Sheet1";

		Excel_Utility excel = null;
		try {
			excel = new Excel_Utility(excelPath,sheetName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read dynamic row count of excel sheet from Properties file
		Map<String,String>  map1 = ExcelFileSetFromProp.propFileRead();
		int rowCount = 0;	
		if(map1.get("excelSheetRowCount") != null) {
			rowCount = Integer.parseInt(map1.get("excelSheetRowCount"));
		}

		for(int i = 1 ; i <= rowCount  ; i++)  // for(int i = 0 ; i<excel.getRowCount(); i++)
		{   
			List list = new ArrayList();
			for( int j = 0; j < 8 ; j++ ) {
				list.add(excel.getCellData(i, j));		
			}
			row.add(list);
		}
	}

	/*   
	 * ************************** All API Test Method starts from here **************************************** 
	 */

	/* 
	 * ****************************** Get the Print head Details Information ****************************** 
	 */
	@Test(priority=1)
	public void getPrintHeadDetails_Ink_Used()
	{	
		test.log(LogStatus.INFO	, "******* Get 'ink_Used' from Print Head Details API Information *******");
		for(int i=0;i<row.size();i++)
		{
			List rowvalue=row.get(i);	
			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setPrintHeadDetailsUrl(APIPath.apiPath.GET_PRINT_HEAD_DETAILS,rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.verifyKey_Response(response, "serial_Number");  // works fine
			UtilityApiMethods.verifyKeyValueFromResponse(response,"printhead" ,"ink_Used"); 

		}

		logger.info("<-------------Print Head Details Test is Ended-----------------------------> " );
		test.log(LogStatus.INFO	, "******* Print Head Details Test is Ended *******");
	}

	/*
	 * ******* verifying ink_Used_Units in liters("L") from printHeadDetails API   ********************** 
	 */
	@Test(priority=2)
	public void getPrintHeadDetails_Ink_Used_Units()
	{	
		test.log(LogStatus.INFO	, "******* Get the 'ink_Used_Units' from Print Head Details Information *******");
		//Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.GET_PRINT_HEAD_DETAILS);
		for(int i=0;i<row.size();i++)
		{
			List rowvalue=row.get(i);
			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setPrintHeadDetailsUrl(APIPath.apiPath.GET_PRINT_HEAD_DETAILS.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.verifyKey_Response(response, "serial_Number");  // works fine
			APIVerification.verify_Key_Value_Response(response, "ink_Used_Units");
		}
		test.log(LogStatus.INFO	, "*************** Print Head Details Test is Ended ***************");
		logger.info("*************** Print Head Details Test is Ended ***************");
	}

	/*
	 * ********  verifying EventType:replace from printHeadDetails API  ********************** 
	 */
	@Test(priority=3)
	public void getPrintHeadDetails_event_TYPE()
	{	
		test.log(LogStatus.INFO	, "******* Get the EventType:replace from Print Head Details Information *******");
		//Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.GET_PRINT_HEAD_DETAILS);
		for(int i=0;i<row.size();i++)
		{
			List rowvalue=row.get(i);
			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setPrintHeadDetailsUrl(APIPath.apiPath.GET_PRINT_HEAD_DETAILS.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);
			//APIVerification.verifyKey_Response(response, "event_TYPE");  // works fine

			APIVerification.verify_KeyValue(response, "event_TYPE");
		}

		test.log(LogStatus.INFO	, "***************EventType:replace from Print Head Details Test is Ended ***************");
		logger.info("***************EventType:replace from Print Head Details Test is Ended ***************");
	}

	/* 
	 * *******verifying warranty_status from printHeadDetails API   ********************** **********
	 */  

	@Test(priority=4)
	public void getPrintHeadDetails_warranty_status()
	{	
		test.log(LogStatus.INFO	, "******* Get the warranty_status from Print Head Details Information *******");		
		for(int i=0;i<row.size();i++)
		{
			List rowvalue=row.get(i);
			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setPrintHeadDetailsUrl(APIPath.apiPath.GET_PRINT_HEAD_DETAILS.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.verify_KeyValue(response, "warranty_status");
			APIVerification.verify_KeyValue(response, "status");
		}
		logger.info("***** warranty_status from Print Head Details Test is Ended ****** ");
		test.log(LogStatus.INFO	, "*************** warranty_status from Print Head Details Test is Ended ***************");
	}


	
	/* 
	 * ****************************** Get Error Events Api Test ***************************************
	 * 
	 */
	@Test(priority=5)
	public void getErrorEventsAPI_InvalidErrorCode()
	{
		test.log(LogStatus.INFO	, "******* Get Error Events Test for Wrong error_Code is  started *******");
		for(int i = 0; i < row.size() ; i++)
		{			
			List rowvalue = row.get(i);
			test.log(LogStatus.PASS	, "<<<<<<<<<< Test Executed based on Serial_no = " + rowvalue.get(0));
			//logger.info( "<<.............. Test Executed based on Serial_no = " + rowvalue.get(0));

			Response  response = RestAssured.given().headers(header.HeadersWithToken()).when().
					get(APIPath.apiPath.setPrintUrl(APIPath.apiPath.GET_ERROR_EVENTS.toString(),rowvalue.get(0).toString(),
							rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.verifyInvalidErrorCode( response,"alert", "error_Code");
		}

		logger.info(" ******* Get Error Events Test is Ended ******* ");
		test.log(LogStatus.INFO	, " ******* Get Error Events Test is Ended ******* ");
	}	

	//@Test(priority=1)
	public void getErrorEventsAPI_errorCode_0000()
	{
		test.log(LogStatus.INFO	, "******* Get Error Events Test for Wrong error_Code is  started *******");

		for(int i = 0; i < row.size() ; i++)
		{			
			List rowvalue = row.get(i);
			test.log(LogStatus.PASS	, "******* Test Executed based on Serial_no = " + rowvalue.get(0));
			//logger.info( "******* Test Executed based on Serial_no = " + rowvalue.get(0));

			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().
					get(APIPath.apiPath.setPrintUrl(APIPath.apiPath.GET_ERROR_EVENTS.toString(),rowvalue.get(0).toString(),
							rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.validate_Keyvalue(response,"alert", "error_Code","0000-0000-0000");
			//APIVerification.validate_KeyValueCode(response,"alert", "error_Code",1,"0000-0000-0000");

			test.log(LogStatus.INFO	, "########## Get Error Events Test Execution is Passed  ############# ");	
		}
		test.log(LogStatus.INFO	, "******* Get Error Events Test is Ended *******");
	}	


	//@Test(priority=2)
	public void getErrorEventsAPI_errorCode_Unknown()
	{
		test.log(LogStatus.INFO	, "******* Get Error Events API Test for Wrong error_Code='UNKNOWN'  is  started *******");
		for(int i = 0; i < row.size() ; i++)
		{			
			List rowvalue = row.get(i);
			test.log(LogStatus.PASS	, "******* Test Executed based on Serial_no = " + rowvalue.get(0));

			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setPrintUrl(APIPath.apiPath.GET_ERROR_EVENTS.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.validate_Keyvalue(response,"alert", "error_Code","UnKnown");
			//APIVerification.validate_KeyValueCode(response,"alert", "error_Code",2,"UnKnown");
			test.log(LogStatus.PASS	, "########## Get Error Events Test Execution is Passed ############# ");	
		}
		test.log(LogStatus.INFO	, "******* Get Error Events Test is Ended *******");
		logger.info(" ******* Get Error Events Test is Ended *******");
	}	


	//@Test(priority=3)
	public void getErrorEventsAPI_errorCode_null()
	{
		test.log(LogStatus.INFO	, "******* Get Error Events API Test for Wrong error_Code  is  started *******");
		for(int i = 0; i < row.size() ; i++)
		{			
			List rowvalue = row.get(i);
			test.log(LogStatus.PASS	, "******* Test Executed based on Serial_no = " + rowvalue.get(0));
			//System.out.println("******* Test Executed based on Serial_no = " + rowvalue.get(0));			

			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().
					get(APIPath.apiPath.setPrintUrl(APIPath.apiPath.GET_ERROR_EVENTS.toString(),rowvalue.get(0).toString(),
							rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.validate_Keyvalue(response,"alert", "error_Code", null);
			//APIVerification.validate_KeyValueCode(response,"alert", "error_Code",3,null);				
		}	
		logger.info("########## Get Error Events Test Execution is Passed ############# ");
		test.log(LogStatus.INFO	, "******* Get Error Events Test is Ended *******");
	}	


	/* 
	 * ****************************** Get the Printer obligation Information ******************************
	 * 
	 */

	//@Test(priority=6)
	public void getObligation_APITest()
	{	
		test.log(LogStatus.INFO	, "******* Get the Printer obligation Test*******");

		for(int i=0;i<row.size();i++)
		{
			List rowvalue=row.get(i);

			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setObligationUrl(APIPath.apiPath.GET_OBLIGATION.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.ResponseTimeValidation(response);
			UtilityApiMethods.verifyKeyValueFromResponse(response,"warranty" ,"offerCode");

		}
		logger.info("******* Printer obligation Test is Ended *******");
		test.log(LogStatus.INFO	, "******* Printer obligation Test is Ended *******");
	}



	/* 
	 * ****************************** Get the Printer head Entitled  Information ******************************
	 * 
	 */

	//@Test(priority=7)
	public void getPrintHeadEntitled_APITest()
	{	
		test.log(LogStatus.INFO	, "*******Get Print Head Entitled *******");
		for(int i=0;i<row.size();i++)
		{
			List rowvalue=row.get(i);
			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setPrinterEntitledUrl(APIPath.apiPath.GET_PrinterEntitled.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString()));
			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.ResponseTimeValidation(response);
		}
		logger.info("******* Print Head Entitled API Test is Ended *******");
		test.log(LogStatus.INFO	, "******* Print Head Entitled API Test is Ended *******");
	}

	/*  
	 * ****************************** Get Solution Api Test ******************************
	 * 
	 */

	//@Test(priority=8)
	public void getSolution_APITest()
	{	

		for(int i = 0 ; i < row.size() ; i++)
		{
			test.log(LogStatus.INFO	, " ******* Get Solution API Started ******* ");
			List rowValue = row.get(i);		

			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().
					get(APIPath.apiPath.setGetSolutionUrl(APIPath.apiPath.GET_SOLUTION.toString(),rowValue.get(0).toString(),rowValue.get(1).toString(),rowValue.get(4).toString(),rowValue.get(5).toString()));		
			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			APIVerification.verifyKey_Response(response,"event_Code");
			APIVerification.verifyKey_Response(response,"severity"); 
			APIVerification.validateKeyFromResponseWithDB(response,"event_Code");

			test.log(LogStatus.INFO	, "******* Get Solution Test is Ended *******");
			logger.info( "******* Get Solution Test is Ended *******");
		}		
	}


	/* 
	 * ********************Get the Printer Status History Information ******************************
	 * 
	 */

	//@Test(priority=9)
	public void getPrinterStatusHistory_APITest()
	{	
		test.log(LogStatus.INFO	, "******* Get the Printer status History Test*******");
		//Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.GET_PrinterStatusHistory);
		for(int i=0;i<row.size();i++)
		{
			List rowvalue=row.get(i);

			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setPrintUrl(APIPath.apiPath.GET_PrinterStatusHistory.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			//APIVerification.getListVauesfromAPi(response,"status", "sub_Status", "status");

			APIVerification.verifyMoreKeysFromResponseAndDb( response,"status", "status", "sub_Status");

			//APIVerification.verifyObjectsCountFromResponse(response,"status" ,"sub_Status");
		}
		logger.info("******* Printer Status History Test is Ended *******");
		test.log(LogStatus.INFO	, "******* Printer Status History Test is Ended *******");
	}


	/* 
	 * ****************************** Get Error Events Api Test ******************************
	 * 
	 */
	@Test(priority=10)
	public void getErrorEvents_APITest()
	{	
		test.log(LogStatus.INFO	, "******* Get Error Events Test is  started *******");
		List listOfSerialNo = new ArrayList();
		for(int i = 0; i < row.size() ; i++)
		{			
			List rowvalue = row.get(i);
			test.log(LogStatus.PASS	, "******* Test Executed based on Serial_no = " + rowvalue.get(0));
			logger.info(" ******* Test Executed based on Serial_no = " + rowvalue.get(0));

			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().
					get(APIPath.apiPath.setPrintUrl(APIPath.apiPath.GET_ERROR_EVENTS.toString(),rowvalue.get(0).toString(),
							rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);
			//APIVerification.verifyObjectsCountFromResponse(response,"alert" ,"error_Code");

			APIVerification.validateErrorCountObjectWithDB(response,"alert" ,"error_Code",rowvalue.get(0).toString(),
					rowvalue.get(2).toString(),rowvalue.get(3).toString(), listOfSerialNo);

			test.log(LogStatus.PASS	, "########## Get Error Events Test Execution is Passed and Next Started ############# ");

		}
		logger.info("Failing Serial_number list ======>  " + listOfSerialNo);
		test.log(LogStatus.PASS," List of Serial no's are failed----> " + listOfSerialNo );
	}	



}
