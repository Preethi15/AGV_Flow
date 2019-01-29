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

public class TestRackChangerInputValidations extends TestBase {
	LoginPages loginPages;
	MaswebHomePage maswebHomePage;
	AGVRackChangerPage aGVRackChangerPage;
	CommonWrapperMethods commonWrapperMethods;
	AGV_MachineIDScanPage aGV_MachineIDScanPage;
	VclasTaskPage vclasTaskPage;
	VclassAssignmentsPages vclassAssignmentsPages;
	//TestCase: Verify the functionality of Rack changer screen which will display  all the configured  AGV machine ids :2881
	 @Test(description="RackChanger Invalid Input Validations Test Case:2881",dataProvider="TestDataProvider")
	 public void InValidInputValidation(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
	 {	 
		  try {
		 
		 loginPages = new LoginPages(driver);
		 maswebHomePage=new MaswebHomePage(driver);
		 aGVRackChangerPage=new AGVRackChangerPage(driver);
		
		 
		 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
		 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
		 aGVRackChangerPage.Agv_Invalid_Input_Validation(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
		
		  }

	 catch(Exception e) {
		 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
	 }
	 }
	 //TestCase:Verification of possibility of creating  AGV orders for Inactive Machine ID  TestCase ID:2882 
	 @Test(description="Inactive MachineId Task Creation Possibility Test Case:2882",dataProvider="TestDataProvider")
	 public void AGV_Inactive_OrderCreation(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
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
		 String strFID = aGVRackChangerPage.MachineID_Pick("I");//string to be passed
		 System.out.println("strFID: "+strFID);
		 maswebHomePage.Navigate_AGV_MachineIDScan();
		 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
		 maswebHomePage.Agv_Navigate_Vclas_Task();
		 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
		 vclasTaskPage.Vclas_getAGVTask_Inactive_MID(testData.get("FID"));
		 
		  }

	 catch(Exception e) {
		 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
	 }
	 }

	 //TestCase:03 Verify the possibility of creating AGV orders for a Reserved AGV machine ID:2883
	 
	 @Test(description="Reserve MachineId Task Creation verification Test Case:2883",dataProvider="TestDataProvider")
	 public void AGV_Reserve_OrderCreation(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
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
		 String strFID = aGVRackChangerPage.MachineID_Pick("R");//string to be passed
		 System.out.println("strFID: "+strFID);
		 maswebHomePage.Navigate_AGV_MachineIDScan();
		 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
		 maswebHomePage.Agv_Navigate_Vclas_Task();
		 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
		 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestRackChangerInputValidations");
		 aGVRackChangerPage.ReserveID_TaskCreation_Verification(testData.get("Empty_Task_1"), testData.get("Full_Task_1"));
		 
		  }

	 catch(Exception e) {
		 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
	 }
	 }
	 

}
