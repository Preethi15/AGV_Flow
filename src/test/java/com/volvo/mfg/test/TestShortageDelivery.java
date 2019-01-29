package com.volvo.mfg.test;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.testng.annotations.Test;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;
import com.volvo.mfg.pagecomponents.AGVRackChangerPage;
import com.volvo.mfg.pagecomponents.AGV_MachineIDScanPage;
import com.volvo.mfg.pagecomponents.LoginPages;
import com.volvo.mfg.pagecomponents.MaswebHomePage;
import com.volvo.mfg.pagecomponents.VclasTaskPage;
import com.volvo.mfg.pagecomponents.VclassAssignmentsPages;



public class TestShortageDelivery extends TestBase {
	
	LoginPages loginPages;
	MaswebHomePage maswebHomePage;
	AGVRackChangerPage aGVRackChangerPage;
	CommonWrapperMethods commonWrapperMethods;
	AGV_MachineIDScanPage aGV_MachineIDScanPage;
	VclasTaskPage vclasTaskPage;
	VclassAssignmentsPages vclassAssignmentsPages;
	
	 @Test(description="ShortageDelivery",dataProvider="TestDataProvider")
	 public void ShortageFlows(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
	 {	 
		  try {
		 
		 loginPages = new LoginPages(driver);
		 maswebHomePage=new MaswebHomePage(driver);
		 aGVRackChangerPage=new AGVRackChangerPage(driver);
		 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
		 vclasTaskPage=new VclasTaskPage(driver);
		 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
		 
		 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
		 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
		 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
		 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
		 System.out.println("strFID: "+strFID);
		 maswebHomePage.Navigate_AGV_MachineIDScan();
		 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
		 maswebHomePage.Agv_Navigate_Vclas_Task();
		 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
		 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestShortageDelivery");
		 reportStep(" Verification of possibility of creating AGV orders for a Active AGV machine ID completed #B Test Case ID:2884 #C", "Pass", false);
		 reportStep(" Verification of possibility of creating AGV orders for a Active AGV machine ID  completed #B Test Case ID:2884  #C", "info", false);
		 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
		 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
		 vclassAssignmentsPages.Vclas_Agv_Shortage_Screen_Delivery(testData.get("Select_Work_Area_Room"),testData.get("Object"),testData.get("Full_Task_1"),testData.get("Empty_Task_1"),testData.get("Shortage_Type_Expected"));
		 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
		 maswebHomePage.Agv_Navigate_Vclas_Task();
		 vclasTaskPage.Agv_Vclas_VerifyTaskEvents_Inactive(testData.get("No_of_Racks"), "Allt utfört samtidigt", testData.get("Full_Task_1"), testData.get("Empty_Task_1"), testData.get("FlowType"));
		 
		 reportStep("Status:'Everything done simultaneously' is verified successfully ", "pass", false);
         reportStep("Verification of functionality of Delivering a AGVFKOLL and AGVEKOLL from Shortage screen #B TestCase ID:2887 #C", "info", false);
		  }

	 catch(Exception e) {
		 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
	 }
	 }
	 
	//TestCase: 23 Verify the functionality of displaying assignments in Shortage when there is a AGV assignment without Transport Order Testcase ID:2903
		 @Test(description=" displaying assignments in Shortage when there is a AGV assignment without Transport Order Testcase Id:2903",dataProvider="TestDataProvider")
		 public void AGV_AssignmentsWithoutTO(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
		 {	 
			  try {
			 
			 loginPages = new LoginPages(driver);
			 maswebHomePage=new MaswebHomePage(driver);
			 aGVRackChangerPage=new AGVRackChangerPage(driver);
			 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
			 vclasTaskPage=new VclasTaskPage(driver);
			 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
			 
			 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
			 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
			 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
			 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
			 System.out.println("strFID: "+strFID);
			 maswebHomePage.Navigate_AGV_MachineIDScan();
			 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
			 maswebHomePage.Agv_Navigate_Vclas_Task();
			 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
			 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestShortageDelivery");
			
			
			 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
			 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
			 //
			 }

		 catch(Exception e) {
			 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
		 }
		 }
	 
}