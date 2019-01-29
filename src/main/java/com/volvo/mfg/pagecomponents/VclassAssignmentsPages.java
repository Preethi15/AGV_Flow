package com.volvo.mfg.pagecomponents;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;
import com.volvo.mfg.commonutilis.ExcelUtils;

public class VclassAssignmentsPages extends CommonWrapperMethods {
	private WebDriver driver;

	public VclassAssignmentsPages(WebDriver driver) {
		this.driver = driver;
	}
	
	String sheetName = "Vclas_Assignments";
	String strFTaskId, tblFData, FObject, TaskId, Conveyor, ConveyorCode;
	

	// Excel class object to access the function
	ExcelUtils excelUtils = new ExcelUtils();

	/*
	 * public LoginPage(WebDriver driver) { this.driver = driver; }
	 */

	public void Vclas_Assignment_Navigate() throws InterruptedException {
		driver.switchTo().window(browser[2]);

		// Navigate the Page
		anyClick("Assignment Navigate", By.xpath(prop.getProperty("Vclas_Home.Assignments.Button")));
		Thread.sleep(5000);

		// Verify the Page is displayed
		verifyElementExist("Assignment Page", By.xpath(prop.getProperty("Vclas_Home.Work.Areas.Button")));

	}

	public void Agv_Vclas_Assignment_Navigate() throws InterruptedException {

		// Navigate the Page
		anyClick("Assignment Navigate", By.xpath(prop.getProperty("Vclas_Home.Assignments.Button")));
		Thread.sleep(5000);

		// Verify the Page is displayed
		verifyElementExist("Assignment Page", By.xpath(prop.getProperty("Vclas_Home.Work.Areas.Button")));

	}

	public void Vclas_Assignment_SelectWorkArea(String areaSelect, String roomSelect) throws InterruptedException {
		try {
			// areaSelect - Room / Zones as parameter

			// Navigate the Page
			anyClick("Work Area Navigate", By.xpath(prop.getProperty("Vclas_Home.Work.Areas.Button")));

			// Click the Rooms
			anyClick("Home tab click", By.xpath(prop.getProperty("Vclas_Home." + areaSelect + ".Tab")));

			// Reseting the Values
			anyClick("Clearing List Values",
					By.xpath(prop.getProperty("Vclas_Home." + areaSelect + ".RemoveAll.Button")));

			String singleRoom[] = roomSelect.split(",");

			for (int i = 0; i <= singleRoom.length - 1; i++) {
				System.out.println(singleRoom[i]);
				// Enter the data to select
				sendKeys("Search Rooms selection",
						By.xpath(prop.getProperty("Vclas_Home." + areaSelect + ".List.Text")), singleRoom[i]);

				// Select drop down
				anyClick("Click the Room", By.xpath(prop.getProperty("Vclas_Home." + areaSelect + ".Select.Item")
						.replace("&Value", singleRoom[i])));

				// Add the Room Value
				anyClick("Adding the Room: " + roomSelect, By.xpath(prop.getProperty("Vclas_Home.Room.Add.Button")));
				Thread.sleep(2000);

			}
		} catch (Exception e) {
			System.out.println("Error in Vclas_Assignment_SelectWorkArea method");
		}

	}

	public void Vclas_Shortage_Search(String objectId, String seqNo, String flowName) throws InterruptedException {

		String searchData;
		System.out.println("seq no:" + seqNo);
		if (flowName.equalsIgnoreCase("JISJIT")) {
			if (seqNo.equalsIgnoreCase("")) {
				searchData = objectId;
			} else {
				searchData = objectId + seqNo;
			}
		} else {
			if (seqNo.equalsIgnoreCase("")) {
				searchData = objectId;
			} else {
				searchData = objectId + " " + seqNo;
			}
		}

		// Verify the Shortage screen is displayed

		if (driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text"))).size() > 0) {

			clearByLocator(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")));
			// Enter the Object Id
			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text"))).get(0)
					.sendKeys(objectId);

			waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Button")), 5);

			// Clicking the Search Button
			retryingFindClick(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Button")));

			reportStep("Shortage screen is displayed successfully", "Pass", true);

			// Search data in the table with sequence no.
			String objVerify = prop.getProperty("Vclas_Home.Shortage.Table.TD.Verify").replace("&Value", searchData);
			System.out.println(objVerify);
			String nextButton = prop.getProperty("Vclas_Home.Shortage.Next.Button");
			String firstButton = prop.getProperty("Vclas_Home.Shortage.First.Button");

			int intTemp = 1;
			do {

				// Clicking the button to verify the table is displayed
				anyClick("Search Areas", By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Button")));
				Thread.sleep(4000);
				if (verifyElementExistReturn(By.xpath(objVerify)) == true) {
					reportStep(searchData + " exist in the Shortage screen", "Pass", true);
					break;
				}

				// Verifying if it available in Next Screen
				while (verifyElementExistReturn(By.xpath(nextButton)) == true) {

					// Clicking the next button
					anyClick("Shortage Next Button", By.xpath(nextButton));

					if (verifyElementExistReturn(By.xpath(objVerify)) == true) {
						reportStep(searchData + " exist in the Shortage screen", "Pass", true);
						return;
					}
				}

				if (verifyElementExistReturn(By.xpath(firstButton)) == true) {
					// Clicking the first button
					anyClick("Shortage First Button", By.xpath(firstButton));
				}

				intTemp = intTemp + 1;

			} while (!(intTemp == 3));

			if (intTemp == 3) {
				reportStep(objectId + " Object id search not displayed in the screen (60 seconds waited)", "fail",
						true);
			}
		}
	}

	public void Vclas_Shortage_TypeVerify(String ObjectId, String TaskId, String Type, String Status)
			throws InterruptedException {

		// Search Object Id and Task Id in the table
		String objIdVerify = prop.getProperty("Vclas_Home.Shortage.Object_Id.Verify").replace("&Value", ObjectId)
				.replace("&1Value", TaskId);

		// Verifying if it is available in the screen
		if (verifyElementExistReturn(By.xpath(objIdVerify)) == true) {

			reportStep("--------Shortage screen verification: Flow: #B" + ObjectId + "#C Task Id: #B" + TaskId
					+ "#C Type: #B" + Type + "#C and Status: #B" + Status + "#C Initiated----", "Info", false);
			reportStep(ObjectId + TaskId + "exists in the Shortage Screen ", "PASS", true);
			String objType = prop.getProperty("Vclas_Home.Shortage.Object_Id_Type.Verify").replace("&Value", ObjectId)
					.replace("&1Value", TaskId);
			String newType = driver.findElement(By.xpath(objType)).getText();
			System.out.println(newType);
			String newStatus;

			// Verifying the type is available in the screen
			if (newType.equalsIgnoreCase(Type)) {
				reportStep(Type + "type is verified", "PASS", true);
				int iTemp = 0;
				do {
					String objStatus = prop.getProperty("Vclas_Home.Shortage.Object_Id_Type_Status.Verify")
							.replace("&Value", ObjectId).replace("&1Value", TaskId);
					waitForElement(driver, By.xpath(objStatus), 5);
					newStatus = driver.findElement(By.xpath(objStatus)).getText();
					System.out.println(newStatus);
					// Status Verification
					if (newStatus.equalsIgnoreCase(Status)) {
						reportStep("--------Shortage screen verification: Flow: #B" + ObjectId + "#C Task Id: #B"
								+ TaskId + "#C Type: #B" + Type + "#C and Status: #B" + Status
								+ "#C Completed. PASSED----", "Info", false);
						reportStep(Status + " Status is Verified successfully ", "PASS", true);
						break;
					}
					iTemp = iTemp + 1;

				} while (!(iTemp == 3));
				if (iTemp == 3) {

					reportStep(
							"--------Shortage screen verification: Flow: #B" + ObjectId + "#C Task Id: #B" + TaskId
									+ "#C Type: #B" + Type + "#C and Status: #B" + Status + "#C Completed. Failed----",
							"Info", false);
					reportStep("Status is not verified successfully. The Status to be expected:" + Status
							+ "The Actual Status is:" + String.valueOf(newStatus), "fail", true);
				}

			} else {
				reportStep("Type is not verified successfully. The type to be expected:" + Type + "The Actual Type is:"
						+ String.valueOf(newType), "fail", true);
			}
		} else {
			reportStep(ObjectId + TaskId + "doesn't exists in the Shortage Screen", "fail", true);
		}
		reportStep("--------Shortage screen verification: Flow: #B" + ObjectId + "#C Task Id: #B" + TaskId
				+ "#C Type: #B" + Type + "#C and Status: #B" + Status + "#C Completed. ----", "Info", false);

	}

	public void vclassshortagecheck() {
		driver.switchTo().window(browser[2]);
		String newTime = "2";
		System.out.println(newTime);

		// Navigation to Shortage
		anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

	}

	public long Vclas_Shortage_Time_Verify(String report, String objectId, String seqNo, String flowName)
			throws InterruptedException {
		driver.switchTo().window(browser[2]);
		long newTime = 0;
		String searchData;

		if (flowName.equalsIgnoreCase("JISJIT")) {
			if (seqNo.equalsIgnoreCase("")) {
				searchData = objectId;
			} else {
				searchData = objectId + seqNo;
			}
		} else {
			if (seqNo.equalsIgnoreCase("")) {
				searchData = objectId;
			} else {
				searchData = objectId + " " + seqNo;
			}
		}

		/*
		 * if (seqNo.equalsIgnoreCase("")) { searchData = objectId; } else { searchData
		 * = objectId + seqNo; }
		 */
		driver.switchTo().window(browser[2]);

		// Navigation to Shortage
		anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

		// Verify the Shortage screen is displayed
		if (driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text"))).size() > 0) {
			// Enter the Object Id
			driver.findElement(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text"))).click();
			driver.findElement(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")))
					.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.DELETE);
			// driver.findElement(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text"))).sendKeys(Keys.LEFT_CONTROL
			// + "a" );
			// driver.findElement(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text"))).sendKeys
			// (Keys.DELETE);
			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text"))).get(0)
					.sendKeys(objectId);

			// Clicking the Search Button

			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Button"))).get(0).click();

			reportStep("Shortage screen is displayed", "Pass", true);

			// Retrieving the time
			String timeVerify = prop.getProperty("Vclas_Home.Shortage.Time_Verify").replace("&Value", searchData);
			newTime = Integer.parseInt(driver.findElement(By.xpath(timeVerify)).getText());
			System.out.println(newTime);
			reportStep("Picking the time for Line Movement of the Rack: " + objectId + " Time: " + newTime, "pass",
					true);
			// Update in the excel sheet
			excelUtils.excelUpdateValue("Vclas_Assignments", "NewTime", refTestDataName, String.valueOf(newTime));
			return newTime;

		} else {
			reportStep("Shortage screen is not displayed", "Fail", true);
			return newTime;
		}
	}

	public void Vclas_Assignment_Areas() {

		// Navigation to Assignment Areas
		anyClick("Assignment Areas", By.xpath(prop.getProperty("Vclas_Home.Assignment.Areas.Button")));

	}

	public void Vclas_AssignmentList_Search() throws InterruptedException {
		// navigate to assignment page

		// Verify the assignment area table is displayed

		if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Table"))) == true)

		{

			// Verify the obj ID displayed in the table
			String tableHeaderColumn = prop.getProperty("Vclas_Home.AssignmentList.Table") + "/thead/tr";

			// Get the column position of each column
			int colType = htmlTableColumnNamePosition("Type", tableHeaderColumn);
			int colStatus = htmlTableColumnNamePosition("Status", tableHeaderColumn);

			int colObject = htmlTableColumnNamePosition("Object", tableHeaderColumn);

			String tableObject = prop.getProperty("Vclas_Home.AssignmentList.Table") + "/tbody/tr";
			List<WebElement> tableBodyRow = driver.findElements(By.xpath(tableObject));
			int rowCount = tableBodyRow.size();
			for (int i = 0; i <= rowCount - 1; i++) {
				List<WebElement> tableData = tableBodyRow.get(i).findElements(By.tagName("td"));
				if (tableData.get(colObject).getText().equalsIgnoreCase("MFH70")
						&& tableData.get(colType).getText().equalsIgnoreCase("FULL_RACK")) {

					anyClick("pickup", By.xpath(
							prop.getProperty("Vclas_Home.AssignmentList.Click".replace("&Value", String.valueOf(i)))));

					anyClick("pickup",
							By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Click".replace("&Value", "1"))));

				}
			}
		}
	}

	// **************************************************************************************************
	// @Method Name: Vclas_AssignmentList
	// @Description: To deliver racks in assignment list
	// @Input Parameters: nil
	// @Output Parameters: nil
	// @Created by: Sruthi,Preethi
	// @Date Created: 13-03-2018
	// @Last Modified:
	// **************************************************************************************************
	/*public String Vclas_Scan(String option,String rackNo) {
		String sheetName = "Vclas_Assignments";
		

		String obj;

		if (option == "tugger") {

			int scanlop = (Integer.parseInt(tdrow.get("lopnr")) + Integer.parseInt(tdrow.get("Line_Movement")));
			obj = "T " + "MFH" + tdrow.get("rack") + scanlop;
			return obj;
		}
		if (option == "Agv") {
			obj = "";
			return obj;
		} else if (option == "Jisjit") {
			obj = "";
			return obj;
		}
		return "";
	}*/

	

	public void vclas_Agv_Rackpickup(String rackType, String objectId) throws InterruptedException {
		System.out.println("Search racksubmission for " + rackType + objectId);

		// navigate to assignment page
		// anyClick("Assignment list Areas",
		// By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button")));
		driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button"))).get(0).click();

		reportStep("Object Id: #B" + objectId + "#C  Rack Type: #B" + rackType
				+ "#C Click for Submission from #B Assignment List #C screen", "Info", false);

		String rackName = prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId)
				.replace("&1Value", rackType);
		System.out.println("rack name" + rackName);

		// Verify the assignment area table is displayed
		if (verifyElementExistReturn(By.xpath(rackName)) == true) {

			// Selecting the Rack
			// driver.findElements(By.xpath(rackName));
			driver.findElements(By.xpath(rackName)).get(0).click();
			reportStep("#B" + objectId + "and the racktype:" + rackType
					+ " has been picked up for deliver from Assignment List screen #C", "pass", true);
			Thread.sleep(5000);
			System.out.println("rack name" + rackName);
			// driver.findElements(By.xpath(rackName));
			driver.findElements(By.xpath(rackName)).get(0).click();
			Thread.sleep(5000);

		} else {
			reportStep("Assignment list table does not exist", "fail", true);
		}

	}

	// Yes - sendType
	public void Vclas_Agv_Delivery(String Address, String type,String object) throws InterruptedException {
		try {

		//String sheetName = "Vclas_Assignments";
		

		String Object_Id = "p" + object;
		

		// Verify it is Full KOLL or EmptyKoll
		if (type.equalsIgnoreCase("AGVFKOLL")) {
			if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))) == true) {

				// Verify it is to Drop or Pick Up
				if (Address.equalsIgnoreCase("drop")) {

					Conveyor = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).getText();
					
					  //clicking on Cancel button in AssignmentList screen
					  //anyClick("Vclas Cancel button click",By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click")));
					   waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click")), 3);
 					 driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click"))).get(0).click(); //navigate to
					 // node screen to fetch conveyor code
 					ConveyorCode= Nodes_Conveyor_Data_Pickup(Conveyor);
					 
					// clicking on pick up button
					
					  driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", object) .replace("&1Value", type))).get(0).click();
					 
					reportStep("Conveyor Id: #B" + Conveyor + "#C ObjectId: #B" + Object_Id
							+ "#C entered to move from Forklift to Conveyor", "Info", false);
					/*
					 * if (driver.findElements(By.xpath(prop.getProperty(
					 * "Vclas.AssignmentList.ManualInput."+sendType))).size() <> 0) {
					 * anyClick("Manual Input:No",
					 * By.xpath(prop.getProperty("Vclas.AssignmentList.ManualInput.Yes"))); }
					 */
					//sendKeys("scan", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")),tdrow.get(Conveyor.toUpperCase()));
					 sendKeys("scan",By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")),ConveyorCode);

					// driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0).sendKeys(Keys.TAB);
					// anyClick("click to send conveyor Id value",By.xpath("Vclas.AssignmentList.ManualInput"));

					// waiting for element
					waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.CheckCode.Label")),10);
					String ConveyorId = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.CheckCode.Label"))).getText();

					if (ConveyorId.equalsIgnoreCase(Conveyor.toUpperCase())){
						reportStep("ConveyorId been sent", "pass", false);
					} else {
						reportStep("ConveyorId doesn't sent", "fail", false);
					}
					
					Thread.sleep(5000);
					// driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0).sendKeys(ObjectId);
					sendKeys("Object Id to send: " + Object_Id,By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), Object_Id);
					System.out.println("ObjectId: "+Object_Id);

					// driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0).sendKeys(Keys.TAB);
					// anyClick("click to send Object Id value", By.xpath("Vclas.AssignmentList.ManualInput"));
				} else {

					Conveyor = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.PickupAddress.Label"))).getText();

					reportStep("Conveyor Id: #B" + Conveyor + "#C ObjectId: #B" + Object_Id
							+ "#C entered to move from Conveyor to AGV", "Info", false);
					// sendKeys("scan", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")),tdrow.get(Conveyor.toUpperCase()));
					//driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0).sendKeys(tdrow.get(Conveyor.toUpperCase()));
					driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0).sendKeys(ConveyorCode);


					// sendKeys("scan",
					// By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")),ConveyorCode);
					// driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0).sendKeys(Keys.TAB);
					// anyClick("click to send conveyer Id value", By.xpath("Vclas.AssignmentList.ManualInput"));
				}

			}

			else {
				reportStep("Scan text box doesn't exists", "fail", false);
				return;
			}
		}

		Thread.sleep(5000);
		//waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")), 5);
		// Verify the OK button exist
		if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click"))) == true) {
			retryingFindClick(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")));
			System.out.println();
			//anyClick("OK click", By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")));
		} else {
			reportStep("Ok button doesn't exists", "fail", false);
		}

		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

	public void Vclas_Agv_Flow(String Select_Work_Area_Room,String Object,String Full_Task_1,String Empty_Task_1,String Shortage_Type_Expected) throws InterruptedException {

//		tdrow = excelUtils.testCaseRetrieve(refTestDataName, "Vclas_Assignments");
		// Getting the Room Value
		String stRoomValue = Select_Work_Area_Room;
		String objectId = Object;
		String strFullTaskId = Full_Task_1;
		String strEmptyTaskId = Empty_Task_1;
		String strFullType = Shortage_Type_Expected.split(",")[0];
		String strEmptyType = Shortage_Type_Expected.split(",")[1];
		String strStatusVerify[] = { "Active", "Planned" };

		// Submitting the Full Racks
		for (int i = 0; i <= 1; i++) {

			// Selection Work Area
			Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[i]);

			reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[i] + "#C", "Info", false);

			// Navigation to Shortage
			anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

			// Waiting to verify the screen is displayed
			waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")), 5);

			//
			Vclas_Shortage_Search(objectId, "", "");

			// Verify the Task id in Shortage screen
			Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[i]);
			reportStep(
					"Object Id: #B" + objectId
							+ "#C  Rack Type: #B  AGVFKOLL  #C delivery  from Assignment List screen initiated",
					"Info", false);
			// Rack Pick/Drop in the Assignment List
			vclas_Agv_Rackpickup(strFullType, objectId);
			// Nodes_Conveyor_Data_Pickup();

			if (i == 0) {
				// Deliver depends on Drop id of Conveyer
				Vclas_Agv_Delivery("drop", strFullType,Object);
			} else {
				// Deliver depends on Pick id of Conveyer
				Vclas_Agv_Delivery("pick", strFullType,Object);
				Thread.sleep(2000);
				String rackName = prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId)
						.replace("&1Value", strFullType);

				if (driver.findElements(By.xpath(rackName)).size() > 0) {
					driver.findElements(By.xpath(rackName)).get(0).click();

					Vclas_Agv_Delivery("", "",Object);
				}
			}
			reportStep(
					"Object Id: #B" + objectId
							+ "#C  Rack Type: #B AGVFKOLL #C delivery  from Assignment List screen completed",
					"Info", false);
		}

		// Submitting the Empty Racks
		for (int j = 0; j <= 1; j++) {

			// Navigation to Shortage
			anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

			Vclas_Shortage_Search(objectId, "", "");

			// Verify the Task id in Shortage screen
			Vclas_Shortage_TypeVerify(objectId, strEmptyTaskId, strEmptyType, strStatusVerify[j]);
			reportStep(
					"Object Id: #B" + objectId
							+ "#C  Rack Type: #B AGVEKOLL #C delivery  from Assignment List screen initiated",
					"Info", false);
			// Rack Pick/Drop in the Assignment List
			vclas_Agv_Rackpickup(strEmptyType, objectId);

			reportStep("---Submitting Empty Rack: #B" + strEmptyType + "#C Object Id: #B" + objectId + "#C----", "Info",
					false);
			// Deliver depends on Drop id of Conveyer
			Vclas_Agv_Delivery("", strEmptyType,Object);

			if (j == 0) {
				// Select Room in the list
				Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[j]);

				reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[j] + "#C", "Info", false);
			}

		}
		reportStep(
				"Object Id: #B" + objectId
						+ "#C  Rack Type: #B AGVEKOLL #C delivery  from Assignment List screen completed",
				"Info", false);

	}

	public void Vclas_Shortage_Delivery_Selection(String TaskId,String Object) throws InterruptedException {

//		tdrow = excelUtils.testCaseRetrieve(refTestDataName, "Vclas_Assignments");
		String objectId = Object;

		// Clicking for the object in Shortage Screen to Pick-Up
		if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
				.replace("&Value", objectId).replace("&1Value", TaskId))) == true) {

			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
					.replace("&Value", objectId).replace("&1Value", TaskId))).get(0).click();

			reportStep("PickUp Button for the #B" + TaskId + " #C is Verified and clicked Successfully", "pass", false);
		} else {
			reportStep("PickUp button for the #B" + TaskId + "#C is not enabled", "fail", true);
		}

		// Clicking for the object in Shortage Screen to Deliver
		if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
				.replace("&Value", objectId).replace("&1Value", TaskId))) == true) {
			
			System.out.println("Shortage element:"+By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button").replace("&Value", objectId).replace("&1Value", TaskId)));

			waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")), 3);
			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
					.replace("&Value", objectId).replace("&1Value", TaskId))).get(0).click();

			reportStep("Deliver Button for the #B" + TaskId + " #C is Verified and clicked Successfully", "pass",
					false);
		} else {
			reportStep("Deliver button for the #B" + TaskId + "#C is not enabled", "fail", true);
			System.out.println("Shortage element:"+By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button").replace("&Value", objectId).replace("&1Value", TaskId)));
		}

		// Clicking on the Cancel Button
		if (driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.CancelButton.Click"))).size() > 0) {

			// Waiting to verify the screen is displayed
			waitForElement(driver, By.xpath(prop.getProperty("Vclas.Shortage.CancelButton.Click")), 3);

			driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.CancelButton.Click"))).get(0).click();
			Thread.sleep(3000);
			reportStep("Cancel Button for the #B" + TaskId + "#C is enabled and clicked Successfully", "pass", false);

		} else {
			reportStep("Cancel button for the #B" + TaskId + "#C is not enabled", "fail", true);
		}

		// Clicking for the object in Shortage Screen to Deliver
		if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
				.replace("&Value", objectId).replace("&1Value", TaskId))) == true) {

			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
					.replace("&Value", objectId).replace("&1Value", TaskId))).get(0).click();

			reportStep("Deliver Button for the #B" + TaskId + "#C is Verified and clicked Successfully", "pass", false);
		} else {
			reportStep("Deliver button for the  #B" + TaskId + "#C is not enabled", "fail", true);
		}

		// Clicking on the OK Button
		if (driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click"))).size() > 0) {

			// Waiting to verify the screen is displayed
			waitForElement(driver, By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")), 3);
			System.out.println("Ok button:"+By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")));

			// retryingFindClick(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")));

			driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click"))).get(0).click();
			Thread.sleep(3000);
			reportStep("Ok Button for the #B" + TaskId + "#C is enabled and clicked Successfully", "pass", false);

		} else {
			reportStep("Ok button for the #B" + TaskId + "#C is not enabled", "fail", true);
			System.out.println("Ok button:"+By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")));
		}

	}

	public void Vclas_Agv_Shortage_Screen_Delivery(String Select_Work_Area_Room,String Object,String Full_Task_1,String Empty_Task_1,String Shortage_Type_Expected) throws InterruptedException {

		
		// Getting the Room Value
		String stRoomValue = Select_Work_Area_Room;
		String objectId = Object;
		String strFullTaskId = Full_Task_1;
		String strEmptyTaskId = Empty_Task_1;
		String strFullType = Shortage_Type_Expected.split(",")[0];
		String strEmptyType = Shortage_Type_Expected.split(",")[1];
		String strStatusVerify = "Active";
		String Seqno = "";
		// Submitting the Full Racks
		for (int i = 0; i <= 1; i++) {

			// Selection Work Area
			Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[i]);

			reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[i] + "#C", "Info", false);

			// Navigation to Shortage
			anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

			// Searching the objectId in Shortage screen
			Vclas_Shortage_Search(objectId, String.valueOf(Seqno), "");

			// Verify the Task id in Shortage screen
			Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify);

			// Delivering the AGVFKOLL
			Vclas_Shortage_Delivery_Selection(strFullTaskId,Object);

			reportStep("Delivery of task ID: #B" + strFullTaskId + "#C and #B" + strFullType
					+ "#C is completed in #B Shortage screen #C", "Info", false);

			break;
		}

		// Submitting the Empty Racks
		for (int j = 0; j <= 1; j++) {

			// Searching the objectId in Shortage screen
			Vclas_Shortage_Search(objectId, String.valueOf(Seqno), "");

			// Verify the Task id in Shortage screen
			Vclas_Shortage_TypeVerify(objectId, strEmptyTaskId, strEmptyType, strStatusVerify);

			// Delivering the AGVEKOLL
			Vclas_Shortage_Delivery_Selection(strEmptyTaskId,Object);

			break;
		}
		reportStep("Delivery of task ID: #B" + strEmptyTaskId + "#C and #B" + strEmptyType
				+ "#C is completed in #B Shortage screen #C", "Info", false);

	}

	public void Vclas_Inprogress_Delivery(String Select_Work_Area_Room,String Object,String Full_Task_1,String Empty_Task_1,String Shortage_Type_Expected,String sheName) throws InterruptedException {

//		tdrow = excelUtils.testCaseRetrieve(refTestDataName, "Vclas_Assignments");
		// Getting the Room Value
		String stRoomValue = Select_Work_Area_Room;
		String objectId = Object;
		String strFullTaskId = Full_Task_1;
		String strEmptyTaskId = Empty_Task_1;
		String strFullType = Shortage_Type_Expected.split(",")[0];
		String strEmptyType = Shortage_Type_Expected.split(",")[1];
		String strStatusVerify[] = { "Active", "Planned" };

		// Submitting the 1st Full and Empty Rack in Assignment List Screen
		for (int i = 0; i <= 1; i++) {

			// Selecting the Work Area
			Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[i]);

			reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[i] + "#C", "Info", false);

			// Navigation to Shortage
			anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

			// Searching for the object
			Vclas_Shortage_Search(objectId, "", "");

			// Verify the Task id in Shortage screen
			Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[i]);

			if (i == 0) {

				// Rack Pick/Drop in the Assignment List
				vclas_Agv_Rackpickup(strFullType, objectId);

				// Deliver depends on Drop id of Conveyer
				Vclas_Agv_Delivery("drop", strFullType,Object);
			} else {

				// Rack Pick/Drop in the Assignment List
				vclas_Agv_Rackpickup(strEmptyType, objectId);

				// Deliver depends on Pick id of Conveyer
				Vclas_Agv_Delivery("", strEmptyType,Object);
			}
		}
		// Navigation to Shortage
		anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

		// Searching for the object
		Vclas_Shortage_Search(objectId, "", "");

		// Verify the Task id in Shortage screen
		Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, "Planned");

		// Delivering the AGVFKOLL
		Vclas_Shortage_Delivery_Selection(strFullTaskId,Object);

		// Selecting the Work Area
		Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);

		// Navigation to Shortage
		anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

		// Searching for the object
		Vclas_Shortage_Search(objectId, "", "");

		// Verify the Task id in Shortage screen
		Vclas_Shortage_TypeVerify(objectId, strEmptyTaskId, strEmptyType, "Planned");

		// Delivering the AGVFKOLL
		Vclas_Shortage_Delivery_Selection(strEmptyTaskId,Object);

	}

	public String Nodes_Conveyor_Data_Pickup(String Conveyor) throws InterruptedException {

		// clicking Vclas Home button
		anyClick("Vclas Home button click", By.xpath(prop.getProperty("Vclas_Home.Home.Button")));

		// clicking on the Administration button
		anyClick("Vclas Administration button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Button")));

		// clicking on Nodes button
		anyClick("Vclas Nodes button click", By.xpath(prop.getProperty("Vclas_Administration.Nodes.Button")));

		// verifying search textbox
		if (verifyElementExistReturn(
				By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox"))) == true) {
			//Conveyor="IN3";
			// sending conveyor code to search textbox
			sendKeys("Search textbox", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox")),Conveyor);
			
			// clicking search button
			anyClick("Vclas Node screen search button click",
					By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Button")));

			// fetching conveyor code from nodes screen
			ConveyorCode = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Checkcode.Textbox"))).getAttribute("value");
			
			// to scroll down the page to view the checkcode
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebElement Checkcode = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Checkcode.Textbox")));
			js.executeScript("arguments[0].scrollIntoView();",Checkcode );
			
			
			reportStep("Conveyercode fetched from #B nodes screen  #C successfully  "+Conveyor+":  "+ConveyorCode, "pass", true);
			reportStep("Conveyercode fetched from nodes screen successfully", "info", false);
			System.out.println("ConveyorCode: " + ConveyorCode);
			// clicking Assignments button
			Agv_Vclas_Assignment_Navigate();

			// clicking Assignment list button
			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button"))).get(0).click();

		} else {
			reportStep("Search textbox doesn't present", "fail", false);
		}
return ConveyorCode;
	}	}
