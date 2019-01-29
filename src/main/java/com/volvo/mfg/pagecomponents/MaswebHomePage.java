package com.volvo.mfg.pagecomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;;

public class MaswebHomePage extends CommonWrapperMethods {



	private WebDriver driver;

	
	public MaswebHomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void Navigate_AGV_RackChanger(String eValue) {
		
		//retrieve data from MasWeb_Page
		//Load Test Data File
//		tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
		
		//Navigate the Page
		anyClick("Vclas Menu", By.xpath(prop.getProperty("Masweb_Home.Vclas.Menu")));
		anyClick("AGV/PLC Menu", By.xpath(prop.getProperty("Masweb_Home.AGV.Menu")));
		anyClick("Rack changer Menu", By.xpath(prop.getProperty("Masweb_Home.AGV.RackChanger.Menu")));
		
		//Verify the Page is displayed
		verifyElementExist("Rackchanger Page", By.xpath(prop.getProperty("AGV_RackChanger.FID.Text")));
		
		//Clearing the Memory
		
		
	}
	
	public void Navigate_Vclas_Task() throws InterruptedException {
		//Switching to Masweb window 
		driver.manage().window().maximize();
		driver.switchTo().window(browser[1]);
		driver.manage().window().maximize();
		
		//Navigate the Page
		anyClick("Vclas Menu", By.xpath(prop.getProperty("Masweb_Home.Vclas.Menu")));
		Thread.sleep(2000);
		anyClick("Vclas Task", By.xpath(prop.getProperty("Masweb_Home.Vclas.Task.Menu")));
				
		//Verify the Page is displayed
		verifyElementExist("Vclas tasks Page", By.xpath(prop.getProperty("Vclas_tasks.TaskId.Text")));
		
	}
	
	public void Agv_Navigate_Vclas_Task() throws InterruptedException {
		
		//Navigate the Page
		anyClick("Vclas Menu", By.xpath(prop.getProperty("Masweb_Home.Vclas.Menu")));
		Thread.sleep(2000);
		anyClick("Vclas Task", By.xpath(prop.getProperty("Masweb_Home.Vclas.Task.Menu")));
				
		//Verify the Page is displayed
		verifyElementExist("Vclas tasks Page", By.xpath(prop.getProperty("Vclas_tasks.TaskId.Text")));
		
	}
	
	
	public void Navigate_Vclas_CreateNewRack()
	
	{
		//Navigate the Page
				anyClick("Vclas Menu", By.xpath(prop.getProperty("Masweb_Home.Vclas.Menu")));
				anyClick("Sekadm Menu", By.xpath(prop.getProperty("Vclass.sekadm.menu")));
				anyClick("Create New Rack Menu", By.xpath(prop.getProperty("Vclass.sekadm.createnewrack.menu")));
				//Verify the Page is displayed
				verifyElementExist("MASweb - Sekadm Page", By.xpath(prop.getProperty("Vclass.sekadm.new.create.button")));
				
	}
	
	public void Navigate_Admin_Simulator_Assembly_Line(String eValue)
	{
		driver.switchTo().window(browser[1]);

	//retrieve data from Admin_Page
			//Load Test Data File
//			tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
//			
			//Navigate the Page

			anyClick(" Admin Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Menu")));
			anyClick("Simulator Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Simulator.Menu")));
			anyClick("Assembly Line", By.xpath(prop.getProperty("Masweb_Home.Simulator.Assembly_line.Menu")));
			
			//Verify the Page is displayed
			verifyPageTitle(eValue);
			
			//Clearing the Memory
//			tdrow.clear();
			

	}
	
	public void Navigate_AGV_MachineIDScan() {
		
		//Navigate the Page
		anyClick("Vclas Menu", By.xpath(prop.getProperty("Masweb_Home.Vclas.Menu")));
		anyClick("AGV/PLC Menu", By.xpath(prop.getProperty("Masweb_Home.AGV.Menu")));
		anyClick("Rack changer Menu", By.xpath(prop.getProperty("Masweb_Home.AGV.MachineIDScan.Menu")));
		
		//Verify the Page is displayed
		verifyElementExist("Machine ID Page", By.xpath(prop.getProperty("AGV_MachineID.Scan.Text")));
		
	}
	public void Navigate_Production_Control_Codes() {
		
		//retrieve data from MasWeb_Page
		//Load Test Data File
//		tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
		
		//Navigate the Page
		anyClick(" Production Menu", By.xpath(prop.getProperty("Masweb_Home.Production.Menu")));
		anyClick("Control_Codes Menu", By.xpath(prop.getProperty("Production_Control_codes.Menu")));
		
		
		//Verify the Page is displayed
		//verifyPageTitle(tdrow.get("Expected_Value"));
		verifyElementExist("Control codes Page", By.xpath(prop.getProperty("Production_Control_codes.Partnumber")));
		
		
		//Clearing the Memory
//		tdrow.clear();
		
	}
	
	public void Navigate_Production_Plockan() {
			
			//Navigate to the page
			anyClick("production Menu", By.xpath(prop.getProperty("Masweb_Home.Production.Menu")));
			anyClick("Plockan Menu", By.xpath(prop.getProperty("Production_Plockan.Menu")));
			
			//verify the page is displayed or not
			verifyElementExist("Plockan page", By.xpath(prop.getProperty("Production_Plockan.PartNo")));
			
		}

	public void Navigate_Production_Consumption() {
		
		//Navigate to the page
		anyClick("Production Menu", By.xpath(prop.getProperty("Masweb_Home.Production.Menu")));
		anyClick("Consumption Menu", By.xpath(prop.getProperty("Production_Consumption.menu")));
		
		//verify the page is displayed or not
		verifyElementExist("Consumption page",By.xpath(prop.getProperty("Production_Consumption.Partnumber")));
    }

	public void Navigate_Production_LocationCodes() {
		
		//Navigate to the page
		anyClick("Production Menu", By.xpath(prop.getProperty("Masweb_Home.Production.Menu")));
		anyClick("Location Codes Menu", By.xpath(prop.getProperty("Production_LocationCodes.menu")));
		
		// verify the page is displayed or not
		verifyElementExist("Location Codes page", By.xpath(prop.getProperty("Production_Location_Codes.LocationCode")));
	}

	
public void Navigate_Admin_ScannedGoods() {
		
		//Navigate the Page
		anyClick("Admin Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Menu")));
		anyClick("ScannedGoods Menu", By.xpath(prop.getProperty("Admin.Scanned_goods.Menu")));
		
		//Verify the Page is displayed
		//verifyPageTitle(tdrow.get("Expected_Value"));
		verifyElementExist("ScannedGoods_Page", By.xpath(prop.getProperty("Admin.Scanned_goods.controlno.Text")));
		
	}

	
		public void Navigate_Admin_ControlParameters()
		{
	
			String sheetName1="Admin_ControlParameter";
			
			//Load Test Data File
//			tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName1);
			
			//Navigate the Page
			anyClick(" Admin Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Menu")));
			anyClick("Control Parameters Menu", By.xpath(prop.getProperty("Admin.Control_parameters.Menu")));
			
			//Verify the Page is displayed
			verifyElementExist("Control parameter Page", By.xpath(prop.getProperty("Admin.Control_parameter.ControlParameter.Text")));
			
		}

	public void Navigate_Admin_TestPrinter()
	{
			
			String sheetName1="Admin_TestPrinter";
			
			//Load Test Data File
//			tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName1);
			
			//Navigate the Page
			anyClick(" Admin Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Menu")));
			anyClick("TestPrinter Menu", By.xpath(prop.getProperty("Admin.Test_Printer.Menu")));
			
			//Verify the Page is displayed
			verifyElementExist("Test printer Page", By.xpath(prop.getProperty("Admin.Test_Printer.Add.button")));
			
			
			//Clearing the Memory
//			tdrow.clear();
			
			
	}
	public void Navigate_Admin_Manage_ManageAlarms()
	{

	//retrieve data from Admin_Page
			//Load Test Data File
//			tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
			
			//Navigate the Page
			
			anyClick("Admin Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Menu")));
			anyClick("Manage Alarm menu", By.xpath(prop.getProperty("Admin.Manage_Alarms.Menu")));
			anyClick("Manage Alarm menu", By.xpath(prop.getProperty("Admin.Manage_Alarms.Manage_Alarm.Menu")));
			
			//Verify the Page is displayed
			//verifyPageTitle(tdrow.get("Expected_Value"));
			verifyElementExist("Manage Alarms Page", By.xpath(prop.getProperty("Admin.Manage_Alarms.Plant.Dropdown")));
			
			
			//Clearing the Memory
//			tdrow.clear();
			

	}

	public void Navigate_Admin_Manage_ManageAlarmids()
	{

	//retrieve data from Admin_Page
			//Load Test Data File
//			tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
			
			//Navigate the Page
			
			anyClick("Admin Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Menu")));
			anyClick("Manage Alarm menu", By.xpath(prop.getProperty("Admin.Manage_Alarms.Menu")));
			anyClick("Manage Alarmids menu", By.xpath(prop.getProperty("Admin.Manage_Alarms.Manage_alarm_ids.Menu")));
			
			//Verify the Page is displayed
			//verifyPageTitle(tdrow.get("Expected_Value"));
			verifyElementExist("Manage Alarms ids Page", By.xpath(prop.getProperty("Admin.Manage_alarm_ids.Create.Button")));
			
			//Clearing the Memory
//			tdrow.clear();
			

	}
	
	public void Navigate_Admin_ShowClose() {
		
		//Navigate the Page
		anyClick(" Admin Menu", By.xpath(prop.getProperty("Masweb_Home.Admin.Menu")));
		anyClick("ShowClose Menu", By.xpath(prop.getProperty("Admim.ShowClose.Menu")));
		
		//Verify the Page is displayed
		verifyElementExist("Show Task Page", By.xpath(prop.getProperty("Admin_ShowClose.Tasks.Text")));
				
	}
	/*public void Navigate_Bluebox_Best()
	{

	//retrieve data from Admin_Page
			//Load Test Data File
//			tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
			
			//Navigate the Page
			
			anyClick(" BlueBox Menu", By.xpath(prop.getProperty("Masweb_Home.BlueBox.Menu")));
			anyClick("Daily Processing Menu", By.xpath(prop.getProperty("Masweb_Home.BlueBox.Daily.Menu")));
			anyClick("Best", By.xpath(prop.getProperty("Masweb_Home.BlueBox.Best.Menu")));
			
			//Verify the Page is displayed
			verifyPageTitle(tdrow.get("Expected_Value"));
			
			//Clearing the Memory
			tdrow.clear();
			

	}
*/

/*public void Navigate_V60_ScanRFID_NormalStation() {
		
	//Load Test Data File
	tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
		//Navigate the Page
		anyClick("V60 Menu", By.xpath(prop.getProperty("Masweb_Home.V60.Menu")));
		anyClick("ScanRFID Normal Station Menu", By.xpath(prop.getProperty("V60_ScanRFID_NormalStation.Menu")));
		
		//Verify the Page is displayed
		verifyElementExist("ScanRFID Normal Station Page", By.xpath(prop.getProperty("V60_ScanRFID_NormalStation.Text")));
				
		//Clearing the Memory
		tdrow.clear();
	}

public void Navigate_V60_ScanRFID_RepairStation() {
	
	//Load Test Data File
		tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
	//Navigate the Page
	anyClick("V60 Menu", By.xpath(prop.getProperty("Masweb_Home.V60.Menu")));
	anyClick("ScanRFID Reapir Station Menu", By.xpath(prop.getProperty("V60_ScanRFID_RepairStation.Menu")));
	
	//Verify the Page is displayed
	verifyElementExist("ScanRFID Reapir Station Page", By.xpath(prop.getProperty("V60_ScanRFID_RepairStation.Text")));
	//Clearing the Memory
			tdrow.clear();
			
	}


public void Navigate_V90_ScanV90_RFIDTag() {

	// retrieve data from Admin_Page
	// Load Test Data File
	tdrow = excelUtils.testCaseRetrieve(refTestDataName, sheetName);

	// Navigate the Page
	anyClick("V90 Menu", By.xpath(prop.getProperty("Masweb_Home.V90.Menu")));
	anyClick("ScanRFID Normal Station Menu", By.xpath(prop.getProperty("V90.ScanV90_RFIDTag.Menu")));

	// Verify the Page is displayed
	verifyElementExist("Scan V90 RFID Tag Page", By.xpath(prop.getProperty("V90.ScanV90_RFIDTag.RFID.Text")));

	// Clearing the Memory
	tdrow.clear();

}


public void Navigate_V90_DeliverV90Tasks()
{

//retrieve data from Admin_Page
		//Load Test Data File
		tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
		
		//Navigate the Page
		
		anyClick("V90 Menu", By.xpath(prop.getProperty("Masweb_Home.V90.Menu")));
		anyClick("Deliver V90 Tasks menu", By.xpath(prop.getProperty("V90.DeliverV90_Tasks.Menu")));

		
		//Verify the Page is displayed
		//verifyPageTitle(tdrow.get("Expected_Value"));
		verifyElementExist("Deliver V90 Tasks Page", By.xpath(prop.getProperty("V90.DeliverV90_Tasks.BodyId.Text")));
		
		//Clearing the Memory
		tdrow.clear();
		

}

public void Navigate_V90_V90PackagingNote()
{

//retrieve data from Admin_Page
		//Load Test Data File
		tdrow= excelUtils.testCaseRetrieve(refTestDataName,sheetName);
		
		//Navigate the Page
		
		anyClick("V90 Menu", By.xpath(prop.getProperty("Masweb_Home.V90.Menu")));
		anyClick("V90 Packaging Note menu", By.xpath(prop.getProperty("V90.V90Packaging_Note.Menu")));

		
		//Verify the Page is displayed
		//verifyPageTitle(tdrow.get("Expected_Value"));
		verifyElementExist("V90 Packaging Note Page", By.xpath(prop.getProperty("V90.V90Packaging_Note.TrailerId.Text")));
		
		//Clearing the Memory
		tdrow.clear();
		

}
	
public void Navigate_V90_EditV90_DeliveryNote() {

	// retrieve data from Admin_Page
	// Load Test Data File
	tdrow = excelUtils.testCaseRetrieve(refTestDataName, sheetName);

	// Navigate the Page
	anyClick("V90 Menu", By.xpath(prop.getProperty("Masweb_Home.V90.Menu")));
	anyClick("Edit V90 Delivery Note menu", By.xpath(prop.getProperty("V90.EditV90_DeliveryNote.Menu")));

	// Verify the Page is displayed
	verifyElementExist("Edit V90 Delivery Note Page",
			By.xpath(prop.getProperty("V90.EditV90_DeliveryNote.Delivery_Note_Id.Text")));

	// Clearing the Memory
	tdrow.clear();
}*/


	
	
	}
