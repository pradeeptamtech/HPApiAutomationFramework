package hp.Seals.APITest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
   
import org.apache.log4j.LogManager;  
import org.apache.log4j.Logger;  

import apiConfig.APIPath;
import apiConfig.HeaderConfigs;
import apiVerification.APIVerification;
import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import utils.ExcelFileSetFromProp;
import utils.Excel_Utility;
import utils.ExtentReportListener;
import utils.UtilityApiMethods;

public class MaintenanceTaskAPITest extends BaseTest {
	
	final static Logger logger = LogManager.getLogger(MaintenanceTaskAPITest.class); 
	
	HeaderConfigs header = new HeaderConfigs();
	ExcelFileSetFromProp  xlProp = new ExcelFileSetFromProp();
	
	List<List> row = new ArrayList<List>();
		
	@BeforeTest
	public void readExcel()
	{
		String excelPath = "./Data/MaintenanceData.xlsx";
		String sheetName = "Sheet1";
		//String excelPath = "./Data/TestData.xlsx";
		//String sheetName = "Sheet2";

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
	 * ***************** Get Maintenance task Api Test ****count no 'progress_Percentage' ********************** 
	 * 
	 */

	//@Test(priority=1)
	public void getMaintenance_progress_Percentage()
	{	
		test.log(LogStatus.INFO	, "******* Get Maintenance task ****Verify 'progress_Percentage' ***");
		
		List listOfSerialNo = new ArrayList();
		for(int i = 0; i < row.size(); i++)
		{			
			List rowvalue = row.get(i);
			test.log(LogStatus.PASS	, "<============ Test Executed based on Serial_no === " + rowvalue.get(0));
						
			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setGetMaintenanceTaskUrl(APIPath.apiPath.GET_MAINTENANCE_TASK.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);
			
			UtilityApiMethods.ResponseKeyValidationFromJsonObject(response, "serial_Number");
			UtilityApiMethods.verifyKeyValueFromResponse(response, "list_maintenances", "progress_Percentage" );
			
			APIVerification.validateProgress_percentageCountApiWithDB(response, "list_maintenances", "progress_Percentage", rowvalue.get(0).toString(),
					rowvalue.get(1).toString(),rowvalue.get(2).toString(),rowvalue.get(3).toString(), listOfSerialNo);

			test.log(LogStatus.PASS	, "########## Get progress_Percentage Test Execution is Passed  ############# ");
			
		}
		logger.info("Failing Serial_number list ======>  " + listOfSerialNo);
		test.log(LogStatus.PASS," List of Serial no's are failed----> " + listOfSerialNo );
		test.log(LogStatus.INFO	, "******* Get Maintenance Task Test is Ended *******");
	}
	
	/* 
	 * ********************* Get Maintenance task Api Test *****No of 'status' count********************  
	 */

	@Test(priority=2)
	public void getMaintenance_status()
	{	
				
		test.log(LogStatus.INFO	, "******* Get Maintenance task **** verify 'status' ****************");
		for(int i=0; i<row.size(); i++)
		{			
			List rowvalue=row.get(i);
			test.log(LogStatus.PASS	, "<<<<<<<<<< Test Executed based on Serial_no = " + rowvalue.get(0));
			logger.info( "<<===== Test Executed based on Serial_no :: " + rowvalue.get(0));
			
			Response response = RestAssured.given().headers(header.HeadersWithToken()).when().get(APIPath.apiPath.setGetMaintenanceTaskUrl(APIPath.apiPath.GET_MAINTENANCE_TASK.toString(),rowvalue.get(0).toString(),rowvalue.get(1).toString(),rowvalue.get(2).toString()));

			UtilityApiMethods.responseBodyValidation(response);
			UtilityApiMethods.responseCodeValidation(response, 200);
			UtilityApiMethods.Validate_ResponseTime(response);

			UtilityApiMethods.ResponseKeyValidationFromJsonObject(response, "serial_Number");
			UtilityApiMethods.verifyKeyValueFromResponse(response,"list_maintenances", "status");

			//APIVerification.verify_Key_Value_Response(response, "status");
			logger.info("<====================== one Test execution is ENDed ========================> " );
			
		}
		test.log(LogStatus.INFO	, "******* Get Maintenance Task Test is Ended *******");
	}

	
}
