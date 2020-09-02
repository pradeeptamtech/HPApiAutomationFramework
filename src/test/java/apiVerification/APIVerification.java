package apiVerification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.amazonaws.entity.PrinterStateResult;
import com.amazonaws.samples.PostgreSqlConnection;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;
import utils.ExtentReportListener;
import utils.UtilityApiMethods;

public class APIVerification extends ExtentReportListener
{
	final static Logger logger = LogManager.getLogger(APIVerification.class);
	
	static PostgreSqlConnection objSql = new PostgreSqlConnection();

	static SoftAssert softAssert = new SoftAssert();

	// public methods

	/*
	 * Invalid Error Code  validation*********************************************** 
	 */
	public static  void verifyInvalidErrorCode(Response response, String listObj, String key )
	{
		List<Map<String, String>> list = response.jsonPath().getList(listObj);
		test.log(LogStatus.PASS, "No of Json Objects ===> " + list.size());
		List alist = new ArrayList();

		int count = 0 ;
		try {
			if (list.size() > 0) 
			{
				for (int i = 0; i < list.size(); i++) 
				{
					String actualVal = list.get(i).get(key);
					alist.add(actualVal);
					try {																						
						if("0000-0000-0000".equals(actualVal)) {

							logger.info(".Failed...error code...And....."+ "object_no->"+ (i+1) + " ::" +key +": " + actualVal);
							test.log(LogStatus.INFO,"FAIL...Wrong error code...and Object_no: "+ (i+1) +"And "+ key + " : " + actualVal);
						}
						else if( "Unknown".equalsIgnoreCase(actualVal) ) {

							logger.info("Failed...error code...And....."+ "object_no->"+ (i+1) + " ::" + key +": " + actualVal);
							test.log(LogStatus.INFO,"FAIL...Wrong error code...and Object_no: "+ (i+1) +"And "+ key + " : " + actualVal);
						}
						else if( actualVal == null || actualVal.isEmpty() ) {

							logger.info("Failed...error code...And....."+ "object_no->"+ (i+1) + " ::" + key +": " + actualVal);
							test.log(LogStatus.INFO,"FAIL...Wrong error code...and Object_no: "+ (i+1) +" And "+ key + " : " + actualVal);
						}

					}catch(Exception e) {
						test.log(LogStatus.FAIL, e.fillInStackTrace());
					}						

					count = i + 1; 
				}
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

		logger.info("List values are =====> " + alist );
		test.log(LogStatus.PASS, "Key values Lists are ===> "+ key + "::>> " + alist);
		test.log(LogStatus.PASS	, "########## Get Error Events Test Execution is Passed ############# ");
	}

	// Invalid Key_Value='errorCode check *******************************************
	public static  void validate_Keyvalue(Response response, String listObj, String key , String expVal)
	{
		List<Map<String, String>> list = response.jsonPath().getList(listObj);
		test.log(LogStatus.PASS, "No of Json Objects ===> " + list.size());
		List alist = new ArrayList();
		int count = 0 ;
		try {
			if (list.size() > 0) 
			{
				for (int i = 0; i < list.size(); i++) 
				{
					String actualVal = list.get(i).get(key);
					alist.add(actualVal);

					try {																						
						if( expVal != null && actualVal != null && expVal.equals(actualVal) ) 
						{	 
							logger.info(".Failed......And....."+ "object_no->"+ (i+1) + " ::" + key +": " + actualVal);
							test.log(LogStatus.INFO,"FAIL...Wrong error code...and Object_no: "+ (i+1) +"And "+ key + " : " + actualVal);
						}
						else if( expVal != null && actualVal != null && expVal.equalsIgnoreCase(actualVal) ) 
						{

							logger.info("Failed.....And....."+ "object_no->"+ (i+1) + " ::" + key + ": " + actualVal);
							test.log(LogStatus.INFO,"FAIL......and Object_no: "+ (i+1) +"And "+ key + " : " + actualVal);
						}
						else if( actualVal == expVal ) 
						{	 
							logger.info("Failed......And....."+ "object_no->"+ (i+1) + " ::" + key +": " + actualVal);
							test.log(LogStatus.INFO,"FAIL......and Object_no: "+ (i+1) +" And "+ key + " : " + actualVal);
						}
						else if( actualVal != null && actualVal.isEmpty() ) 
						{
							logger.info("Failed.....And....."+ "object_no->"+ (i+1) + " ::" + key +": " + actualVal);
							test.log(LogStatus.INFO,"FAIL......and Object_no: "+ (i+1) +" And "+ key + " : " + actualVal);
						}

					}catch(Exception e) {
						test.log(LogStatus.FAIL, e.fillInStackTrace());
					}										
					count = i + 1; 
				}
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

		logger.info("List value are =====> " + alist );
		test.log(LogStatus.PASS, "List of Key values are ===> "+ key + "::>> " + alist);
		//System.out.println("***********************************************************************\n");
	}


	// Invalid Error code check *******************************************
	public static  void validate_KeyValueCode(Response response, String listObj, String key , int index , String expVal)
	{
		List<Map<String, String>> list = response.jsonPath().getList(listObj);
		test.log(LogStatus.PASS, "No of Json Objects ===> " + list.size());
		List alist = new ArrayList();
		int count = 0 ;
		try {
			if (list.size() > 0) 
			{
				for (int i = 0; i < list.size(); i++) 
				{
					String actualVal = list.get(i).get(key);
					alist.add(actualVal);
					switch(index)
					{
					case 1: try {
						if(expVal.equals(actualVal)) {
							logger.info("Failed.....And....."+ "object_no-> "+ (i+1) + " :: " +key +": " + actualVal);
							test.log(LogStatus.INFO,"FAIL...Wrong error code...and Object_no: "+ (i+1) +"And "+ key + " : " + actualVal);
						}
					}catch(Exception e) {
						test.log(LogStatus.FAIL, e.fillInStackTrace());
					}
					break;
					case 2: 
						try {
							if( expVal.equalsIgnoreCase(actualVal) )  {
								logger.info("Failed......And....."+ "object_no-> "+ (i+1) + " ::" + key +": " + actualVal);
								test.log(LogStatus.INFO,"FAIL...Wrong error code...and Object_no: "+ (i+1) +"And "+ key + " : " + actualVal);
							}
						}catch(Exception e) {
							test.log(LogStatus.FAIL, e.fillInStackTrace());
						}
						break;
					case 3:	
						try {
							if( actualVal == expVal || actualVal.isEmpty() ) {
								logger.info("Failed...error code...And....."+ "object_no-> "+ (i+1) + " ::" +key +" : " + actualVal);
								test.log(LogStatus.INFO,"FAIL...Wrong error code...and Object_no: "+ (i+1) +"And "+ key + " : " + actualVal);
							}
						}catch(Exception e) {
							test.log(LogStatus.FAIL, e.fillInStackTrace());
						}					
						break;
					}
					count = i + 1; 
				}
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

		logger.info("List value are =====> " + alist );
		test.log(LogStatus.PASS, "List of Key values are ===> "+ key + "::>> " + alist);
		logger.info(" ***********************************************************************\n");
	}

	/* **********************************************************************************************************
	 * Return List of objects from API Response  
	 * 
	 */
	public static List getListVauesfromAPi(Response response, String listObj, String key1, String key2)
	{
		List<Map<String, String>> list = response.jsonPath().getList(listObj);
		test.log(LogStatus.PASS, "No of Json Objects ==> " + list.size());
		PrinterStateResult printerStateResult = null;
		List<PrinterStateResult> listResult = new ArrayList<PrinterStateResult>();
		int count = 0;
		try {
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					printerStateResult = new PrinterStateResult();
					String sub_status = list.get(i).get(key1);
					String status = list.get(i).get(key2);
					printerStateResult.setSub_Status(sub_status);
					printerStateResult.setStatus(status);
					//test.log(LogStatus.PASS,"Validated Keys--> " + key1 + " : " + sub_status + "\t\t" + key2 + ": " + status);
					//System.out.println("Validated Keys--> " + key1 + " : " + sub_status + "\t\t" + key2 + ": " + status);
					count = i + 1;

					listResult.add(printerStateResult);
				}
				test.log(LogStatus.PASS,"<==========Response Object key value list is=========== \n" + listResult +"\n");
				logger.info("<---------Response Object key value list is --------> \n" + listResult + "\n");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
		return listResult;
	}

	// Getting more keys from Response and Compare with Database   *************************
	public static void verifyMoreKeysFromResponseAndDb(Response response, String listObj, String key1, String key2)
	{		
		
		List<PrinterStateResult> resultObjApi = getListVauesfromAPi(response, listObj, key1, key2);  // Calling Api response List method

		List<PrinterStateResult> resultFromDB = objSql.getStatusAndSubStatus(); // calling DB Method
		try {
			if(resultObjApi == resultFromDB)
			{
				Assert.assertEquals(resultObjApi, resultFromDB, "DB and API Response object count is not matched");
				for(int i = 0; i < resultObjApi.size(); i ++)
				{
					if(	(resultObjApi.get(i).getStatus()).equals((resultFromDB.get(i).getStatus())) ) 
					{
						logger.info("< ===== Key value 'status' PASSED from Response & DB  ====>" );
						test.log(LogStatus.PASS, "< ===== Key value 'status' PASSED from Response & DB  ====> " );
					}else {
						logger.info(" Failed Object Iteration => " + resultObjApi.get(i) + "::");
						logger.info("From API 'status': " + resultObjApi.get(i).getStatus() + "AND From DB 'status' :" + resultFromDB.get(i).getStatus() );
						test.log(LogStatus.PASS,"From API 'status': " + resultObjApi.get(i).getStatus() + "AND From DB 'status' :" + resultFromDB.get(i).getStatus() );
					}

					if(	 (resultObjApi.get(i).getSub_Status()).equals ((resultFromDB.get(i).getSub_Status()))  ) 
					{
						logger.info("< ===== Key value 'sub_Status' PASSED from Response & DB  ====>" );
						test.log(LogStatus.PASS, "<===== Key value 'sub_Status' PASSED from Response & DB  ====> " );
					}else {
						
						logger.info(" FAILED  Object Iteration count=> " + resultObjApi.get(i) + "::");
						logger.info(" From API 'sub_Status': " + resultObjApi.get(i).getSub_Status() + "AND From DB 'sub_Status' :" + resultFromDB.get(i).getSub_Status() );
						test.log(LogStatus.PASS," From API 'sub_Status': " + resultObjApi.get(i).getSub_Status() + "AND From DB 'sub_Status' :" + resultFromDB.get(i).getSub_Status() );
					
					}
				}
			}
			else {
				softAssert.assertEquals(resultObjApi, resultFromDB, "DB and API Response object count is not matched");
				logger.info(" 'Failed': Object count is not matched => " + resultObjApi + " != " + resultFromDB + "\n");
				//System.out.print("From API 'status': " + resultObjApi.get(i).getStatus() + "AND From DB 'status' :" + resultFromDB.get(i).getStatus() );
				test.log(LogStatus.FAIL,"Failed Object count is " + resultObjApi +" != " + resultFromDB );	

			}
		}catch(Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());	
		}
	}

	/*=========================================================================================================== */

	/*
	 * count number of objects from Response
	 */
	public static Integer verifyObjectsCountFromResponse(Response response, String listObj, String key) 
	{
		int responseCount = 0;
		int KeyValueCount = 0;
		try {
			List<Map<String, String>> list = response.jsonPath().getList(listObj);
			test.log(LogStatus.PASS, "No of Key value count from Response ====> " + list.size());
			responseCount = list.size();
			
			if (list.size() > 0) 
			{
				for (int i = 0; i < list.size(); i++) 
				{
					String actualVal = String.valueOf(list.get(i).get(key));
					if( key != null && actualVal != null ) 
					{
						//test.log(LogStatus.PASS, "Key and value is present--> " + key +" : " + actualVal);
						KeyValueCount = i + 1;
					}
				}
			}
			
			Assert.assertEquals(responseCount, KeyValueCount, "API Response object count is not matched");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
		//System.out.println("Response object count:: " + responseCount);
		return responseCount;
	}

	public static void verifyObjectsKey_Response(Response response, String listObj, String key, int choice) 
	{	
		int responseCount = 0;
		int KeyValueCount = 0;
		List<Map<String, String>> list = response.jsonPath().getList(listObj);
		
		test.log(LogStatus.PASS, "No of Key value count from Response ====> " + list.size());
		responseCount = list.size();
				
		try {
			if (list.size() > 0) 
			{
				for (int i = 0; i < list.size(); i++) 
				{
					String actualVal = String.valueOf( list.get(i).get(key));
					//System.out.println("key---> " + key + "ActualValue:: " + actualVal);

					if( key != null && actualVal != null ) 
					{
						switch(choice) {
						case 1: 					
							KeyValueCount += 1;
							logger.info("KeyValueCount= " + KeyValueCount + " and object_no->"+ (i+1) + " :: " + key +": " + actualVal );							
							break;
						case 2:	
							KeyValueCount += 1;
							logger.info("KeyValueCount= " + KeyValueCount + " and object_no->"+ (i+1) + " :: " + key +" : " + actualVal );							
							 break;						
						case 3:
							KeyValueCount += 1;
							logger.info("KeyValueCount= " + KeyValueCount + " and object_no->"+ (i+1) + " :: " + key +" : " + actualVal );							
						    break;
						case 4:
							KeyValueCount += 1;
							logger.info("KeyValueCount= " + KeyValueCount + " and object_no->"+ (i+1) + " :: " + key +" : " + actualVal );							
							break;
						case 5:						
								KeyValueCount += 1;
								logger.info("KeyValueCount= " + KeyValueCount + " and object_no->"+ (i+1) + " ::" + key +" : " + actualVal );							
								break;							
						case 6:
							KeyValueCount += 1;
							logger.info("KeyValueCount= " + KeyValueCount + " and object_no-> "+ (i+1) + " ::" + key +" : " + actualVal );							
							break;		
						case 7:
							KeyValueCount += 1;
							//logger.info("KeyValueCount= " + KeyValueCount + " and object_no->"+ (i+1) + " ::" + date1 +" : " + actualVal );							
							break;						
						}
						responseCount = i + 1; 
					}
				}
			}

		} catch (Exception e) {

			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

		//return responseCount;
	}


	/*
	 * ***************** Verify key  value from Response ********************************************
	 */
	public static String verifyKey_Response(Response response, String key)
	{
		String keyVal = "";
		String result = response.asString();
		// System.out.println("Body====> " + result);
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(result);

			keyVal = UtilityApiMethods.getKey(jsonObj, key);

			// getKey(jsonObj,key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//System.out.println("<--------Key Value----------> " + keyVal);
		return keyVal;
	}


	/*
	 * Verify key value from Response ***************** N+1 level***********************************************
	 */
	public static Map<Integer, HashMap<String, String>>   verify_Key_Value_Response(Response response, String key)
	{	
		Map<Integer, HashMap<String, String>>    keyValMap = new HashMap<Integer, HashMap<String,String>>();
		String result = response.asString();

		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(result);
			keyValMap = UtilityApiMethods.getKey1(jsonObj, key);

			test.log(LogStatus.PASS, "<=========== Key value from Response========> \n " + keyValMap + "\n");

		} catch (JSONException e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());	
		}
		logger.info("\n<===============Key and Value =====================> \n" + keyValMap + "\n");
		test.log(LogStatus.INFO,"<========================== One Test execution is ENDed ===========================> " );
		return keyValMap;
	}

	/*
	 * Verify key value from Response **********Check N + N Level ***********************************************
	 */

	public static Map<Integer, HashMap<String, String>>   verify_KeyValue(Response response, String key)
	{	
		Map<Integer, HashMap<String, String>>    keyValMap = null;
		//String keyValMap ="";
		String result = response.getBody().asString();
		//System.out.println("result:>>> " + result );
		JSONObject jsonObj = null;
		try {
			UtilityApiMethods.counter = 0;
			//UtilityApiMethods.map=new HashMap<Integer, HashMap<String,String>>();
			jsonObj = new JSONObject(result);

			keyValMap = UtilityApiMethods.get_Key(jsonObj, key);

			test.log(LogStatus.PASS, " Key value from Response  ====>  " + keyValMap);

		} catch (JSONException e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());	
		}
		
		logger.info(" Object count-------> " + UtilityApiMethods.counter);
		logger.info("\n<------------Key and Value ------> \n" + keyValMap + "\n");

		return keyValMap;
	}


	/*
	 * *************************** Compare DB and API Response  ******************************************
	 */

	/*
	 * Validation a perticular field value from DB and API Response body
	 */
	public static String getValueFromDB() {
		
		return objSql.getPrintResult().getCust_cd();
	}

	public static void validateKeyFromResponseWithDB(Response response, String key) {
		test.log(LogStatus.INFO, "***********Checking  value from Response compare with Database *****************");

		try {
			
			String actualValueFromDB = getValueFromDB();
			logger.info("Key Value From DB------> " + actualValueFromDB);
			String expectedValuefromApi = verifyKey_Response(response, key);
			logger.info("Key Value From Api------> " + expectedValuefromApi);
			Assert.assertEquals(actualValueFromDB, expectedValuefromApi,"key value is not matched from DB and response body");

		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Compare error count from API response and DB  **************************************
	 */
	public static int getErrorCountFromDB(String serialNo, String startDate, String endDate)
	{
		test.log(LogStatus.PASS, "No of Key value count from Database ====> " + objSql.getNoOfErrorCount(serialNo, startDate, endDate).getSum());
		return objSql.getNoOfErrorCount(serialNo, startDate, endDate).getSum();
	}

	public static void validateErrorCountObjectWithDB(Response response, String listObj, String key, 
			String serialNo,String startDate, String endDate, List serialNoList) 
	{
		logger.info("***Compare error code count from API response and DB ****\n");
		try {
			test.log(LogStatus.INFO,"***********Compare Error code count from API response and Database *****************");
			int errorCodeCountFromDB = getErrorCountFromDB(serialNo, startDate, endDate);
			logger.info("Error count From Database------> " + errorCodeCountFromDB);

			int errorCountFromApi = verifyObjectsCountFromResponse(response, listObj, key);
			logger.info("Error Count From API Response------> " + errorCountFromApi + "\n");

			if (errorCodeCountFromDB == errorCountFromApi) {
				Assert.assertEquals(errorCodeCountFromDB, errorCountFromApi, "ErrorCode count is not matched");
				test.log(LogStatus.INFO, "<===PASS=======Error count is matched  from API and DB =================> \n");
			} else {
				softAssert.assertEquals(errorCodeCountFromDB, errorCountFromApi, "ErrorCode count is not matched");
				test.log(LogStatus.INFO, "<============TEST CASE FAILED =================> \n");
				test.log(LogStatus.INFO, "<============Error count is not same from API and DB =================> \n");
				logger.info("<============Error count is not same from API and DB =================>");
				serialNoList.add(serialNo);
				
				// System.out.println("Fail Serial no list ======> " + lstObj);
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

	}
	
	/*
	 * Compare Maintenance Task progress_percentage count from API response and DB  **************************************
	 */
	public static int getProgress_percentageCountFromDB(String serialNo,String productNo, String startDate, String endDate)
	{
		test.log(LogStatus.PASS, "No of Key value count from Database ====> " + objSql.getCountProgressPercentage(serialNo,productNo, startDate, endDate).getSum());
		return objSql.getCountProgressPercentage(serialNo,productNo, startDate, endDate).getSum();
	}

	public static void validateProgress_percentageCountApiWithDB(Response response, String listObj, String key, 
			String serialNo,String productNo ,String date, String startDate,  List serialNoList) 
	{
		logger.info("***Compare progress_Percentage count from API response and DB *****\n");
		try {
			test.log(LogStatus.INFO,"***********Compare progress_Percentage count from API response and Database *****************");
			int progressPercentageCountFromDB = getProgress_percentageCountFromDB(serialNo,productNo, startDate, date);
			logger.info("progress_Percentage count From Database------> " + progressPercentageCountFromDB);

			int progressPercentageCountFromApi = verifyObjectsCountFromResponse(response, listObj, key);
			logger.info("progress_Percentage Count From API Response------> " + progressPercentageCountFromApi + "\n");

			if (progressPercentageCountFromDB == progressPercentageCountFromApi) {
				Assert.assertEquals(progressPercentageCountFromDB, progressPercentageCountFromApi, "ErrorCode count is not matched");
				test.log(LogStatus.INFO, "<===PASS=======Error count is matched  from API and DB =================> \n");
			} else {
				softAssert.assertEquals(progressPercentageCountFromDB, progressPercentageCountFromApi, "ErrorCode count is not matched");
				test.log(LogStatus.INFO, "<============TEST CASE FAILED =================> \n");
				test.log(LogStatus.INFO, "<============progress_Percentage count is not same from API and DB =================> \n");
				logger.info( "<============progress_Percentage count is not same from API and DB =================> \n");
				
				serialNoList.add(serialNo);
				
				 logger.info("Fail Serial no list ======> " + serialNoList);
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

	}

	


} // End Class
