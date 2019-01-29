package com.volvo.mfg.test;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.testng.annotations.Test;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;
import com.volvo.mfg.pagecomponents.AGVRackChangerPage;
import com.volvo.mfg.pagecomponents.AGV_MachineIDScanPage;
import com.volvo.mfg.pagecomponents.AGV_Phase2_Cases;
import com.volvo.mfg.pagecomponents.LoginPages;
import com.volvo.mfg.pagecomponents.MaswebHomePage;
import com.volvo.mfg.pagecomponents.VclasTaskPage;
import com.volvo.mfg.pagecomponents.VclassAssignmentsPages;



public class TestAssignmentDelivery extends TestBase {
	
	LoginPages loginPages;
	MaswebHomePage maswebHomePage;
	AGVRackChangerPage aGVRackChangerPage;
	CommonWrapperMethods commonWrapperMethods;
	AGV_MachineIDScanPage aGV_MachineIDScanPage;
	VclasTaskPage vclasTaskPage;
	VclassAssignmentsPages vclassAssignmentsPages;
	AGV_Phase2_Cases aGV_Phase2_Cases;
	
	 @Test(description="AssignmentListDelivery",dataProvider="TestDataProvider")
	 public void AssignmentFlows(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
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
		 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestAssignmentDelivery");
		 reportStep(" Verification of possibility of creating AGV orders for a Active AGV machine ID completed #B Test Case ID:2884 #C", "Pass", false);
		 reportStep(" Verification of possibility of creating AGV orders for a Active AGV machine ID  completed #B Test Case ID:2884  #C", "info", false);
		 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
		 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
		 vclassAssignmentsPages.Vclas_Agv_Flow(testData.get("Select_Work_Area_Room"),testData.get("Object"),testData.get("Full_Task_1"),testData.get("Empty_Task_1"),testData.get("Shortage_Type_Expected"));
		 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
		 maswebHomePage.Agv_Navigate_Vclas_Task();
		 vclasTaskPage.Agv_Vclas_VerifyTaskEvents_Inactive(testData.get("No_of_Racks"), "Utfört samt avslutat", testData.get("Full_Task_1"), testData.get("Empty_Task_1"), testData.get("FlowType"));
		 
		 reportStep("Status:'Done as well as completed' is verified successfully ", "pass", false);
		 reportStep("Verification of status from Vclas tasks screen for these Delivery completed AGV Racks #B TestCase ID:2886 #C completed", "Pass", false);
		 reportStep("Verification of status from Vclas tasks screen for these Delivery completed AGV Racks #B TestCase ID:2886 #C completed", "info", false);
		  }

	 catch(Exception e) {
		 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
	 }
	 }
	 
	 
	 @Test(description="FunctionalityOfScanningFieldsByCopyPasting",dataProvider="TestDataProvider")
	 public void FunctionalityOfScanningFieldsByCopyPasting(LinkedHashMap<String,String>testData) throws IOException, InterruptedException{	 
		  try {
		 
		 loginPages = new LoginPages(driver);
		 maswebHomePage=new MaswebHomePage(driver);
		 aGVRackChangerPage=new AGVRackChangerPage(driver);
		 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
		 vclasTaskPage=new VclasTaskPage(driver);
		 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
		 aGV_Phase2_Cases= new AGV_Phase2_Cases(driver);
		 

		 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
		 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
		 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
		 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
		 System.out.println("strFID: "+strFID);
		 maswebHomePage.Navigate_AGV_MachineIDScan();
		 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
		 maswebHomePage.Agv_Navigate_Vclas_Task();
		 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
		 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestAssignmentDelivery");
		 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
		 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
		 aGV_Phase2_Cases.Functionality_Of_Scanning_Fields_By_Copy_Pasting(testData.get("Select_Work_Area_Room"),testData.get("Object"),testData.get("Full_Task_1"),testData.get("Shortage_Type_Expected"));
		 
		  }

			 catch(Exception e) {
				 e.printStackTrace();
				 reportStep("@Method " + this.testName + " exception to be handled" +e, "Pass", true);
			 }
			 }
	 
	 
	 @Test(description="Functionality of delivering of a moved AGV order",dataProvider="TestDataProvider")
	 public void FunctionalityofDeliveringMovedAGVOrder(LinkedHashMap<String,String>testData) throws IOException, InterruptedException{	 
		  try {
		 
		 loginPages = new LoginPages(driver);
		 maswebHomePage=new MaswebHomePage(driver);
		 aGVRackChangerPage=new AGVRackChangerPage(driver);
		 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
		 vclasTaskPage=new VclasTaskPage(driver);
		 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
		 aGV_Phase2_Cases= new AGV_Phase2_Cases(driver);
		 

		 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
		 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
		 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
		 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
		 System.out.println("strFID: "+strFID);
		 maswebHomePage.Navigate_AGV_MachineIDScan();
		 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
		 maswebHomePage.Agv_Navigate_Vclas_Task();
		 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
		 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestAssignmentDelivery");
		 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
		 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
		 aGV_Phase2_Cases.Delivering_Moved_AGV_Order(testData.get("Select_Work_Area_Room"),testData.get("Object"),testData.get("Full_Task_1"),testData.get("Shortage_Type_Expected"),testData.get("Empty_Task_1"));
		 
		  }

			 catch(Exception e) {
				 e.printStackTrace();
				 reportStep("@Method " + this.testName + " exception to be handled" +e, "Pass", true);
			 }
			 }
	 
	 
	//TestCase:Verification the functionality of scanning the invalid AGV conveyor code for ex  alphabets or 123456 completed#B Test case Id:2985
		 //Verify the functionality of scanning other than AGV conveyor code  which is not part of AGV node itself
		 @Test(priority=1,description="InvalidConveyorCodeValidation Test case Id:2985 and 2898",dataProvider="TestDataProvider")
		 public void InvalidConveyorCodeValidation(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
		 {	 
			  try {
			 
			 loginPages = new LoginPages(driver);
			 maswebHomePage=new MaswebHomePage(driver);
			 aGVRackChangerPage=new AGVRackChangerPage(driver);
			 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
			 vclasTaskPage=new VclasTaskPage(driver);
			 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
			 aGV_Phase2_Cases= new AGV_Phase2_Cases(driver);
			 
			 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
			 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
			 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
			 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
			 System.out.println("strFID: "+strFID);
			 maswebHomePage.Navigate_AGV_MachineIDScan();
			 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
			 maswebHomePage.Agv_Navigate_Vclas_Task();
			 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
			 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestAssignmentDelivery");
			
			 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
			 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
			 aGV_Phase2_Cases.InvalidConveyorCode(testData.get("Select_Work_Area_Room"),testData.get("Object"),"AGVFKOLL",testData.get("Jisjit_CheckCode"));
			
			  }

		 catch(Exception e) {
			 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
		 }
		 }
		 

		 //TestCase: Verify the functionality of scanning AGV conveyor code which is linked to AGV mad but not planned by system Id:2897
		 @Test(priority=2,description="Changing Conveyor directly and not diretly linked to the Mad Testcase Id:2897",dataProvider="TestDataProvider")
		 public void ConveyorMadLinkFunctionality(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
		 {	 
			  try {
			 
			 loginPages = new LoginPages(driver);
			 maswebHomePage=new MaswebHomePage(driver);
			 aGVRackChangerPage=new AGVRackChangerPage(driver);
			 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
			 vclasTaskPage=new VclasTaskPage(driver);
			 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
			 aGV_Phase2_Cases= new AGV_Phase2_Cases(driver);
			 
			 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
			 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
			 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
			 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
			 System.out.println("strFID: "+strFID);
			 maswebHomePage.Navigate_AGV_MachineIDScan();
			 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
			 maswebHomePage.Agv_Navigate_Vclas_Task();
			 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
			 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestAssignmentDelivery");
			 
			 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
			 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
			 aGV_Phase2_Cases.ConveyorMadLinkChange(testData.get("Select_Work_Area_Room"),testData.get("Object"),"AGVFKOLL",testData.get("UserName"),testData.get("Full_Task_1"),testData.get("Empty_Task_1"));
			 aGV_Phase2_Cases.ConveyorMadLinkChange_DB_Verify(testData.get("UserName"),testData.get("Full_Task_1"),testData.get("Empty_Task_1"));
			
			  }

		 catch(Exception e) {
			 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
		 }
		 }
		 
		 
		 
		 //TestCase: 16 Verify the functionality of Confirmation window When user scans the valid AGV conveyor code  which is not directly linked to AGV mad Test case Id:2896
		 @Test(priority=2,description="Changing Conveyor which is not linked to the Mad Testcase Id:2896",dataProvider="TestDataProvider")
		 public void ConveyorIndirectMadLinkFunctionality(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
		 {	 
			  try {
			 
			 loginPages = new LoginPages(driver);
			 maswebHomePage=new MaswebHomePage(driver);
			 aGVRackChangerPage=new AGVRackChangerPage(driver);
			 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
			 vclasTaskPage=new VclasTaskPage(driver);
			 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
			 aGV_Phase2_Cases= new AGV_Phase2_Cases(driver);
			 
			 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
			 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
			 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
			 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
			 System.out.println("strFID: "+strFID);
			 maswebHomePage.Navigate_AGV_MachineIDScan();
			 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
			 maswebHomePage.Agv_Navigate_Vclas_Task();
			 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
			 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestAssignmentDelivery");
			 
			 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
			 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
			 aGV_Phase2_Cases.ConveyorChangeIndirectLink(testData.get("Select_Work_Area_Room"),testData.get("Object"),"AGVFKOLL",testData.get("UserName"),testData.get("Full_Task_1"),testData.get("Empty_Task_1"));
			
			  }

		 catch(Exception e) {
			 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
		 }
		 }
		 
		 //need to do test case no:19
		 //TestCase: 16 Verify the functionality of Confirmation window When user scans the valid AGV conveyor code  which is not directly linked to AGV mad Test case Id:2896
		 @Test(priority=2,description="",dataProvider="TestDataProvider")
		 public void YellowExclamotoryFromScanningWindow(LinkedHashMap<String,String>testData) throws IOException, InterruptedException
		 {	 
			  try {
			 
			 loginPages = new LoginPages(driver);
			 maswebHomePage=new MaswebHomePage(driver);
			 aGVRackChangerPage=new AGVRackChangerPage(driver);
			 aGV_MachineIDScanPage=new AGV_MachineIDScanPage(driver);
			 vclasTaskPage=new VclasTaskPage(driver);
			 vclassAssignmentsPages=new VclassAssignmentsPages(driver);
			 aGV_Phase2_Cases= new AGV_Phase2_Cases(driver);
			 
			 loginPages.LogintoMasweb(testData.get("UserName"),testData.get("Password"),testData.get("Expected"));
			 maswebHomePage.Navigate_AGV_RackChanger(testData.get("Expected"));
			 aGVRackChangerPage.MachineID_Search(testData.get("FID"), testData.get("Plant"), testData.get("Actor"));
			 String strFID = aGVRackChangerPage.MachineID_Pick("A");//string to be passed
			 System.out.println("strFID: "+strFID);
			 maswebHomePage.Navigate_AGV_MachineIDScan();
			 aGV_MachineIDScanPage.MachineID_Scan_Submit(strFID,testData.get("ChangeColor"));
			 maswebHomePage.Agv_Navigate_Vclas_Task();
			 vclasTaskPage.Vclas_Task_Search(testData.get("TaskId"),testData.get("objectId"),testData.get("FPOS"),testData.get("TPOS"),testData.get("PartNumber"),testData.get("Factory"),testData.get("FlowType"),testData.get("TaskType"),testData.get("CreatedFromDate"),testData.get("CreatedToDate"),testData.get("ShowEntries"));
			 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestAssignmentDelivery");
			 
			 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
			 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
			 aGV_Phase2_Cases.ConveyorChangeIndirectLink(testData.get("Select_Work_Area_Room"),testData.get("Object"),"AGVFKOLL",testData.get("UserName"),testData.get("Full_Task_1"),testData.get("Empty_Task_1"));
			
			  }

		 catch(Exception e) {
			 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
		 }
		 }

		 
	 
}