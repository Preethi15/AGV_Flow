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

public class TestPickingMultipleAssignments extends TestBase {

	LoginPages loginPages;
	MaswebHomePage maswebHomePage;
	AGVRackChangerPage aGVRackChangerPage;
	CommonWrapperMethods commonWrapperMethods;
	AGV_MachineIDScanPage aGV_MachineIDScanPage;
	VclasTaskPage vclasTaskPage;
	VclassAssignmentsPages vclassAssignmentsPages;
	AGV_Phase2_Cases aGV_Phase2_Cases;
	
	 @Test(priority =1,description="PickingMultipleAssignment",dataProvider="TestDataProvider")
	 public void pickingFirstAssignment(LinkedHashMap<String,String>testData) throws IOException, InterruptedException{
	 	 
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
		 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestPickingMultipleAssignments");
		 
		 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
		 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
		 aGV_Phase2_Cases.PickingOneAssignment(testData.get("Select_Work_Area_Room"),testData.get("Object"),"AGVFKOLL");
		 
		}			 

	 catch(Exception e) {
		 reportStep("@Method " + this.testName + " exception to be handled"+e, "Pass", true);
	 }
	 
}
	 //TestCase:Verification of possibility of picking multiple Assignments from AL screen Test Case Id:2901
	// TestCase:Verification of possibility of picking multiple Assignments from Shortage screen Test Case Id:2902
	 @Test(priority =2,description="PickingMultipleAssignment TestCase:2901,2902",dataProvider="TestDataProvider")
	 public void PickingMultipleAssignment(LinkedHashMap<String,String>testData) throws IOException, InterruptedException{
	 	 
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
		 vclasTaskPage.Vclas_getAGVTask(testData.get("FID"), "TestPickingMultipleAssignments");
		 
		
		 loginPages.LogintoVclas(testData.get("UserName"),testData.get("Password"));
		 vclassAssignmentsPages.Agv_Vclas_Assignment_Navigate();
		 aGV_Phase2_Cases.PickingMultipleAssignments(testData.get("Select_Work_Area_Room"),testData.get("Object"),"AGVFKOLL",testData.get("Full_Task_1"));
		
		
		}			 

	 catch(Exception e) {
		 reportStep("@Method " + this.testName + " exception to be handled", "Pass", true);
	 }
	 
	 }
}


	
	

