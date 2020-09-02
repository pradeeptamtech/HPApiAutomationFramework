package com.amazonaws.samples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.Properties;
//import java.sql.Connection;

import com.amazonaws.entity.MaintenanceTaskDetails;
import com.amazonaws.entity.PrinterDetails;
import com.amazonaws.entity.PrinterStateResult;

//import com.amazonaws.services.databasemigrationservice.model.Connection;

public class PostgreSqlConnection {

	static final String dbURL = "jdbc:postgresql://sealsprod.cx4d1xjawowi.us-west-2.redshift.amazonaws.com:5439/dev";
	static final String username = "pradeepta.panigrahi@hp.com";
	static final String userPassword = "5$ONqldNV100P";
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	private String query_custCd = "Select top 1 srl_nr ,prod_nr, cust_cd From app_bm_graphics_lf_telemetry.prntr_log_dtl WHERE srl_nr = 'SG92K11001' AND prod_nr = '4DC17A'";
	//private String querry_errorCount = "SELECT SUM(records) FROM ( SELECT COUNT(cust_cd) AS records FROM app_bm_graphics_lf_telemetry.prntr_log_dtl where srl_nr ='SG92K11001' and evt_ocrd_ts BETWEEN '2020-07-01 00:00:00Z' AND '2020-07-10 23:59:59' GROUP BY cust_cd  HAVING COUNT(*) > 0 ) ";

	private Connection getConnection() {

		try {
			con = DriverManager.getConnection(dbURL, username, userPassword);
			// Open a connection and define properties.
			//System.out.println("\n****************************************\n");
			//System.out.println("Connecting to database......and....System Tables.....");
			Properties props = new Properties();
			props.setProperty("user", username);
			props.setProperty("password", userPassword);
			con = DriverManager.getConnection(dbURL, props);

			// Try a simple query.
			//System.out.println("\nListing system tables..........");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/*  *****************************************************************************************************
	 *  getting cust_cd  from DB 
	 *  
	 *  */
	public PrinterDetails getPrintResult() {
		PrinterDetails printDetail = new PrinterDetails();
		// Try a simple query.
		try {
			//System.out.println("Listing system tables...");
			con = getConnection();
			stmt = con.createStatement();
			String sql = query_custCd;
			//String sql = "Select top 1 srl_nr ,prod_nr, cust_cd From app_bm_graphics_lf_telemetry.prntr_log_dtl WHERE srl_nr = 'SG6B71N008' AND prod_nr = 'K0Q45A'";
			rs = stmt.executeQuery(sql);

			// Get the data from the result set.			
			while (rs.next()) {
				printDetail.setCust_cd(rs.getString("cust_cd"));
				//System.out.println("\n event_Code======> " + printDetail.getCust_cd());
				printDetail.getCust_cd();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return printDetail;
	}


	/*  
	 * ***********************************************************************************************************
	 * Count no of objects for error count cust_cd from DB
	 * 
	 */

	public PrinterDetails getNoOfErrorCount(String serialNo,String startDate,String endDate)
	{
		PrinterDetails printDetail = new PrinterDetails();
		StringBuilder sb = new StringBuilder();

		//SELECT SUM(records) FROM ( SELECT COUNT(cust_cd) AS records FROM app_bm_graphics_lf_telemetry.prntr_log_dtl where srl_nr ='SG92K11001' and evt_ocrd_ts BETWEEN '2020-07-01 00:00:00Z' AND '2020-07-10 23:59:59' GROUP BY cust_cd  HAVING COUNT(*) > 0 ) ";

		// Write query.
		try {
			//System.out.println("Listing system tables...");
			sb.append("SELECT SUM(records) FROM ( SELECT COUNT(cust_cd) AS records FROM app_bm_graphics_lf_telemetry.prntr_log_dtl where srl_nr =");
			sb.append("'" + serialNo + "'");
			sb.append("and evt_ocrd_ts BETWEEN");
			sb.append(" '" + startDate +"'" +" AND " + "'" + endDate + "'");
			sb.append(" GROUP BY cust_cd  HAVING COUNT(*) > 0 ) ");

			con = getConnection();
			stmt = con.createStatement();
			String sql = sb.toString();
			//System.out.println("\n<=======Query====> \n" + sql + "\n" );

			rs = stmt.executeQuery(sql);

			// Get the data from the result set.			
			while (rs.next()) {
				printDetail.setSum(rs.getInt("sum"));
				//System.out.println("Error Count from DB ===> " + printDetail.getSum() );
				printDetail.getSum();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return printDetail;

	} // Method END	
	

	/*
	 *  getting Maintenanace Task object count #########################################################
	 * 
	 */
	public  MaintenanceTaskDetails getCountProgressPercentage(String serial_no,String product_no,String startDate,String endDate) 
	{
	
		MaintenanceTaskDetails maintance = new MaintenanceTaskDetails();
		StringBuilder sb = new StringBuilder();

//		SELECT SUM(records) FROM	( 
//				SELECT COUNT(maintenance_percentage) AS records FROM app_bm_graphics_lf_telemetry.maintenance_pwxl_dtl 
//				where serial_no='MY91A1T004' AND product_no='3XD61A' AND 
//				insert_ts between '2020-08-27 00:00:00' and '2020-08-27 23:59:59'
//				GROUP BY maintenance_percentage HAVING COUNT(*) >=1 
//				);
		
		// Write query.
		try {
			//System.out.println("Listing system tables...");
			sb.append("SELECT SUM(records) FROM ( SELECT COUNT(maintenance_percentage) AS records FROM app_bm_graphics_lf_telemetry.maintenance_pwxl_dtl where serial_no=");
			sb.append("'" + serial_no + "'" + "and product_no=" + "'" +product_no +"'" );
			sb.append("and insert_ts BETWEEN ");
			sb.append("'" + startDate +"'" +" AND " + "'" + endDate + "'");
			sb.append(" GROUP BY maintenance_percentage  HAVING COUNT(*) > 0 ) ");

			con = getConnection();
			stmt = con.createStatement();
			String sql = sb.toString();
			//System.out.println("\n<=======Query====> \n" + sql + "\n" );

			rs = stmt.executeQuery(sql);

			// Get the data from the result set.			
			while (rs.next()) {
				maintance.setSum(rs.getInt("sum"));
				//System.out.println("progress_percentage Count from DB ===> " + maintance.getSum() );
				maintance.getSum();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return maintance;

	} // Method END	

	
	
	/*  
	 * ***********************************************************************************************************
	 * getting 'status' and 'sub_Status' value  from DB
	 * 
	 */

	public List<PrinterStateResult> getStatusAndSubStatus( ) 
	{		
		PrinterStateResult printerStateResult =  null; // new PrinterStateResult();
		StringBuilder sb = new StringBuilder();
		List<PrinterStateResult>  listPrinterStateResult = new ArrayList<PrinterStateResult>();
		
		//SELECT product_no,serial_no ,start_ts,end_ts ,sub_status, status FROM  printer_state_result where serial_no='SG92K11001' AND start_ts BETWEEN '2020-07-01 00:00:00Z' AND '2020-07-05 23:59:59Z'; 
		// Write query.
		try {
			
			con = getConnection();
			stmt = con.createStatement();
			String sql = "SELECT product_no,serial_no ,start_ts,end_ts ,sub_status, status FROM  app_bm_graphics_lf_telemetry.printer_state_result where serial_no='SG92K11001'AND product_no='4DC17A' AND  START_TS >='2020-07-01 00:00:00Z' AND END_TS <= '2020-07-05 23:59:59' order by START_TS DESC ";
			//System.out.println("\n<=======Query====> \n" + sql + "\n" );

			rs = stmt.executeQuery(sql);
						
			// Get the data from the result set.			
			while (rs.next()) 
			{	
				printerStateResult =new PrinterStateResult();
				printerStateResult.setStatus(rs.getString("status"));
				printerStateResult.setSub_Status(rs.getString("sub_Status"));
				
				listPrinterStateResult.add(printerStateResult);
				
				//System.out.println("Error Count from DB ===> " + printDetail.getSum() );
				//printDetail.getSum();
				
			}
			
			System.out.println("Read from DB ===> " + listPrinterStateResult );
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listPrinterStateResult;

	} // Method END	



	/*
	 * ******************************** DRIVER MAIN METHOD ******************************************
	 * Driver Main Method   
	 */
//	public static void main(String[] args) {
//		PostgreSqlConnection ob = new PostgreSqlConnection();
		
		//ob.getCountProgressPercentage("MY91A1T004", "3XD61A", "2020-08-27T00:00:00Z", "2020-08-27T23:59:59Z");
		//ob.getStatusAndSubStatus();
		//ob.getPrintResult();
		
		//ob.getNoOfErrorCount("MY91A1T004","2020-07-01T00:00:00Z","2020-07-10 00:00:00Z");
		//ob.getNoOfErrorCount("SG5371P001","2020-07-01T00:00:00Z","2020-07-10 00:00:00Z");


//	}

}
