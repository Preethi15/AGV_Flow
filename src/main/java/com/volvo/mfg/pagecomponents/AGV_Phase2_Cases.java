package com.volvo.mfg.pagecomponents;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;
import com.volvo.mfg.commonutilis.DB_Connectivity;
import com.volvo.mfg.commonutilis.QueryInput;

public class AGV_Phase2_Cases extends CommonWrapperMethods implements QueryInput {
	
	private WebDriver driver;
	String Conveyor;
	VclassAssignmentsPages vclassAssignmentsPages;


	public AGV_Phase2_Cases(WebDriver driver) {
		this.driver = driver;
	}
	
	
	
	public void Functionality_Of_Scanning_Fields_By_Copy_Pasting(String Select_Work_Area_Room,String Object,String Full_Task_1,String Shortage_Type_Expected) throws InterruptedException {
		
 		String stRoomValue = Select_Work_Area_Room;
 		String objectId = Object;
		String strFullTaskId = Full_Task_1;
		String strFullType = Shortage_Type_Expected.split(",")[0];
		String strStatusVerify[] = {"Active","Planned","Shortage"};
		String type = "AGVFKOLL";
		String Address = "drop";
		String WrongObjectID = "p15426875";
		String invalidCheckcode= "DOL0001";
		String Correct_Object_Id = "p" + Object;
		String wrongCheckcode = "9578621432";
		String nodeName = "TA FABRIK";
		String ConveyorCode = " ";
		String Cause= "Missing";
		
		vclassAssignmentsPages= new VclassAssignmentsPages(driver);
		
		// Selection Work Area
		vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
		
		reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[0] + "#C", "Info", false);

		// Navigation to Shortage
		anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

		// Waiting to verify the screen is displayed
		waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")), 5);

		//
		vclassAssignmentsPages.Vclas_Shortage_Search(objectId, "", "");

		// Verify the Task id in Shortage screen
		vclassAssignmentsPages.Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[0]);
		reportStep("Object Id: #B" + objectId+ "#C  Rack Type: #B  AGVFKOLL  #C delivery  from Assignment List screen initiated","Info", false);
		
		// Rack Pick/Drop in the Assignment List
		vclassAssignmentsPages.vclas_Agv_Rackpickup(strFullType, objectId);
		
		// Verify scan field in Scanning window
		if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))) == true) {

					//Sending correct object ID without prefix "p"
					sendKeys("Entering correct Object Id without prefix 'p': " + Object,By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), Object);
					Thread.sleep(4000);
					System.out.println("ObjectId without prefix p: "+Object);
					reportStep("Scanning correct object Id without prefix 'p'" +Object, "pass", false);
					String errorMessage=driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Objectid.Scan.Error.Message"))).getText();
					Thread.sleep(4000);
					System.out.println("Error message display for scanning objectid without prefix 'p':" +errorMessage);
					reportStep("Error Message:" +errorMessage, "pass", false);
					
					//Sending wrong objectID with prefix "p"
					sendKeys("Entering wrong Object Id with prefix 'p': " + WrongObjectID,By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), WrongObjectID);
					Thread.sleep(3000);
					System.out.println("wrong Object Id with prefix 'p': "+WrongObjectID);
					reportStep("Scanning wrong object Id with prefix 'p':" +WrongObjectID, "pass", false);
					
					//Sending Correct object id with prefix "p"
					sendKeys("Entering correct Object Id prefix 'p': " + Correct_Object_Id,By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), Correct_Object_Id);
					Thread.sleep(3000);
					System.out.println("correct Object Id prefix 'p': "+Correct_Object_Id);
					reportStep("Scanning correct object Id with prefix 'p':" +Correct_Object_Id, "pass", false);
					
					//Sending invalid checkcode like JISJIT,Tugger
					sendKeys("Entering invalid checkcode like JISJIT, Tugger:" +invalidCheckcode, By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), invalidCheckcode);
					System.out.println("Invalid check code:" +invalidCheckcode);
					reportStep("Scanning invalid checkcode like JISJIT,Tugger:" +invalidCheckcode, "pass", false);
					Thread.sleep(3000);
					
					//Sending wrong check code
					sendKeys("Entering wrong checkcode:" +wrongCheckcode, By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), wrongCheckcode);
					System.out.println("wrong check code:" +wrongCheckcode);
					reportStep("Scanning wrong checkcode:" +wrongCheckcode, "pass", false);
					Thread.sleep(3000);
					
					//Sending valid check code
					Conveyor = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).getText();
					System.out.println("Conveyor:" +Conveyor);
		
					//scanning valid check code which is not directly linked to AGV node
					switch (Conveyor) {
					
					case "In1": Conveyor = "1693746289";
							break;
					case "In2": Conveyor = "1693746289";
							break;
					case "In3": Conveyor = "1374385916";
							break;
					
					}
					//Scanning another check code which is not directly linked to that AGV delivery
					sendKeys("Entering another checkcode:" +Conveyor, By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), Conveyor);
					System.out.println("Conveyor:" +Conveyor);
					Thread.sleep(3000);
					
					//Clicking on Red button
					driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Conveyor.Change.Red.Button"))).get(0).click();
					
					//Scanning another check code which is not directly linked to that AGV delivery
					sendKeys("Entering another checkcode:" +Conveyor, By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), Conveyor);
					System.out.println("Conveyor:" +Conveyor);
					
					//Clicking on green button
					//driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Conveyor.Change.Green.Button"))).get(0).click();
					retryingFindClick(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Conveyor.Change.Green.Button")));
					//Clicking on ok button
					if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click"))) == true) {
						
						retryingFindClick(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")));
						System.out.println();
						Thread.sleep(3000);
						
					} else {
						reportStep("Ok button doesn't exists", "fail", false);
					}
					
					
	}
		reportStep("#B Verified the functionality of scanning required fields by copy pasting in Scan field with Manual Input NO TestCase ID:2893 #C", "pass", false);
					
					//Test Case Id: 2900
					//node screen navigation:
					// clicking Vclas Home button
					anyClick("Vclas Home button click", By.xpath(prop.getProperty("Vclas_Home.Home.Button")));

					// clicking on the Administration button
					anyClick("Vclas Administration button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Button")));

					// clicking on Nodes button
					anyClick("Vclas Nodes button click", By.xpath(prop.getProperty("Vclas_Administration.Nodes.Button")));

					// verifying search textbox
					if (verifyElementExistReturn(
							By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox"))) == true) {
						
						// sending node name to search textbox
						sendKeys("Search textbox", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox")),nodeName);
						
						// clicking search button
						anyClick("Vclas Node screen search button click",By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Button")));
						
						//Clicking on parameters button expansion
						anyClick("Parameters expansion button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Paramters.Expand.Button")));
						
						
						if(verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Administration.Nodes.MoveButton.Checkbox.Cancel")))) {
							
							//Clicking on save button
							anyClick("Save button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Save.Button")));
							
						}else {
						
						//Clicking on use move button to active, to enable yellow button in scanning window of AGV order
						driver.findElements(By.xpath(prop.getProperty("Vclas_Administration.Nodes.MoveButton.Inherited.Checkbox"))).get(0).click();
						Thread.sleep(4000);
						
						driver.findElements(By.xpath(prop.getProperty("Vclas_Administration.Nodes.MoveButton.Value.Checkbox"))).get(0).click();
						Thread.sleep(4000);
						
						//Clicking on save button in nodes screen
						anyClick("Save button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Save.Button")));
						}
					}
					
					// Navigate the Page
					anyClick("Assignment Navigate", By.xpath(prop.getProperty("Vclas_Home.Assignments.Button")));
					Thread.sleep(5000);

					// Verify the Page is displayed
					verifyElementExist("Assignment Page", By.xpath(prop.getProperty("Vclas_Home.Work.Areas.Button")));
					
					//Selecting AGV room to deliver 2nd subtask of AGVFKOLL
					vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[1]);
					
					reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[1] + "#C", "Info", false);

					// Navigation to Shortage
					anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

					// Waiting to verify the screen is displayed
					waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")), 5);

					//searching for the presence of created task using object Id
					vclassAssignmentsPages.Vclas_Shortage_Search(objectId, "", "");

					// Verify the Task id in Shortage screen
					vclassAssignmentsPages.Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[1]);
					reportStep("Object Id: #B" + objectId+ "#C  Rack Type: #B  AGVFKOLL  #C delivery  from Assignment List screen initiated","Info", false);
					
					// Rack Pick/Drop in the Assignment List
					vclassAssignmentsPages.vclas_Agv_Rackpickup(strFullType, objectId);
					
					//Scanning valid check code
					Conveyor = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.PickupAddress.Label"))).getText();
					System.out.println("Conveyor:" +Conveyor);
					reportStep("Conveyor Id: #B" + Conveyor + "#C ObjectId: #B" + objectId+ "#C entered to move from Conveyor to AGV", "Info", false);
					
					//Clicking on scanning window cancel button
					waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click")), 3);
					driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click"))).get(0).click(); 
					
					//navigate to node screen to fetch conveyor code
					ConveyorCode=vclassAssignmentsPages.Nodes_Conveyor_Data_Pickup(Conveyor);
					
					// clicking on pick up button and entering object id
					retryingFindClick(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId) .replace("&1Value", type)));
					System.out.println(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId) .replace("&1Value", type)));
					reportStep("Conveyor Id: #B" + Conveyor + "#C ObjectId: #B" + objectId+ "#C entered to move from Forklift to Conveyor", "Info", false);
					Thread.sleep(4000);
					
					//Scanning valid conveyer code fetched from nodes screen
					sendKeys("scan",By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")),ConveyorCode);
					
					//Clicking on yellow Exclamatory button
					anyClick("Clicking on yellow button in scanning window", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button")));
					
					//Verify yellow button confirmation window opens
					if(verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window")))) {
						
						//clicking on no change drodown
						anyClick("No change dropdown click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Node.Change.Dropdown")));
						anyClick("Selecting option ", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Node.Change.List")));
						reportStep("Selecting No change dropdown", "pass", false);
						
						//Clciking on cause dropdown
						anyClick("Cause dropdown click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Cause.Dropdown")));
						Thread.sleep(4000);
						anyClick("Cause dropdown click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Cause.List")));
						Thread.sleep(4000);
						reportStep("Selecting Cause dropdown", "pass", false);
						
						//Clicking on green button in yellow button confirmation window
						anyClick("Green button click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Green.button")));
						Thread.sleep(4000);
						
					}else {
						reportStep("Yellow button confirmation window not displayed", "fail", false);
					}
						
						//Clicking on shortage tab
						//searching for the presence of created task using object Id
						vclassAssignmentsPages.Vclas_Shortage_Search(objectId, "", "");
						
						//Verify the status as 'shortage' in shortage screen
						vclassAssignmentsPages.Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[2]);
						
						//Clicking on the 2nd subtask of AGVFKOLL in shortage screen for which the alarm generated
						if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
								.replace("&Value", objectId).replace("&1Value", strFullTaskId))) == true) {

							driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
									.replace("&Value", objectId).replace("&1Value", strFullTaskId))).get(0).click();

							reportStep("PickUp Button for the #B" + strFullTaskId + " #C is Verified and clicked Successfully", "pass", false);
						} else {
							reportStep("PickUp button for the #B" + strFullTaskId + "#C is not enabled", "Pass", true);
						}
						
						// navigate to AssignmentList page
						driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button"))).get(0).click();
						reportStep("Assignment tab is clicked successfully ", "pass", false);
						
						// Rack Pick/Drop in the Assignment List
						vclassAssignmentsPages.vclas_Agv_Rackpickup(strFullType, objectId);
						
						//Clicking on ok button
						if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click"))) == true) {
							
							retryingFindClick(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")));
							System.out.println();
							Thread.sleep(3000);
							
						} else {
							reportStep("Ok button doesn't exists", "fail", false);
						}
						
						//Selecting Alarms tab
						anyClick("Alarms tab click", By.xpath(prop.getProperty("Vclas_Home.Alarms.Button")));
						
						//Finding for the alarm raised using taskid and object id
						driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Object_Id.Verify").replace("&Value", strFullTaskId).replace("&1Value", Cause))).get(0).click();
						
						//verfying the confirmation window disply in Alarms screen
						if(verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Alarms.Confirmation.Window.Verify")))==true) {
							
							//Clicking on green button
							anyClick("Green button click", By.xpath(prop.getProperty("Vclas_Home.Alarms.Confirmation.Window.Ok.Button")));
							
						}
						
					
					
					// clicking Vclas Home button
					anyClick("Vclas Home button click", By.xpath(prop.getProperty("Vclas_Home.Home.Button")));

					// clicking on the Administration button
					anyClick("Vclas Administration button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Button")));

					// clicking on Nodes button
					anyClick("Vclas Nodes button click", By.xpath(prop.getProperty("Vclas_Administration.Nodes.Button")));
					
				
					// verifying search textbox
					if (verifyElementExistReturn(
							By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox"))) == true) {
						
						// sending node name to search textbox
						sendKeys("Search textbox", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox")),nodeName);
						
						// clicking search button
						anyClick("Vclas Node screen search button click",By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Button")));
						
						//Clicking on parameters button expansion
						anyClick("Parameters expansion button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Paramters.Expand.Button")));
						
						//Clicking on use move button to deactivate
						driver.findElements(By.xpath(prop.getProperty("Vclas_Administration.Nodes.MoveButton.Checkbox.Cancel"))).get(0).click();
						Thread.sleep(2000);
						
						//Clicking on save button in nodes screen
						anyClick("Save button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Save.Button")));
					}
		
		reportStep("#B Verified the functionality of moving a rack or assignment with a Reason or cause by using Yellow exclamatory after scanning all details TestCase ID:2900 #C", "pass", false);

	
}
	
	
	public void Delivering_Moved_AGV_Order(String Select_Work_Area_Room,String Object,String Full_Task_1,String Shortage_Type_Expected,String Empty_Task_1) throws InterruptedException, SQLException {
		
		String stRoomValue = Select_Work_Area_Room;
 		String objectId = Object;
		String strFullTaskId = Full_Task_1;
		String strEmptyTaskId = Empty_Task_1;
		String strFullType = Shortage_Type_Expected.split(",")[0];
		String strEmptyType = Shortage_Type_Expected.split(",")[1];
		String strStatusVerify[] = {"Active","Planned","Shortage"};
		String type = "AGVFKOLL";
		String Address = "drop";
		String Correct_Object_Id = "p" + Object;
		String ConveyorCode = " ";
		String nodeName = "TA FABRIK";
		
		vclassAssignmentsPages= new VclassAssignmentsPages(driver);
		
		// Selection Work Area
		vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
		
		reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[0] + "#C", "Info", false);

		// Navigation to Shortage
		anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

		// Waiting to verify the screen is displayed
		waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")), 5);

		//
		vclassAssignmentsPages.Vclas_Shortage_Search(objectId, "", "");

		// Verify the Task id in Shortage screen
		vclassAssignmentsPages.Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[0]);
		reportStep("Object Id: #B" + objectId+ "#C  Rack Type: #B  AGVFKOLL  #C delivery  from Assignment List screen initiated","Info", false);
		
		// Rack Pick/Drop in the Assignment List
		vclassAssignmentsPages.vclas_Agv_Rackpickup(strFullType, objectId);
		
		// Verify scan field in Scanning window
		if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))) == true) {
		
			//Scanning valid check code
			Conveyor = driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).getText();
			System.out.println("Conveyor:" +Conveyor);
			reportStep("Conveyor Id: #B" + Conveyor + "#C ObjectId: #B" + objectId+ "#C entered to move from Conveyor to AGV", "Info", false);
			
			//Clicking on scanning window cancel button
			waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click")), 3);
			driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click"))).get(0).click(); 
			
			//navigate to node screen to fetch conveyor code
			ConveyorCode=vclassAssignmentsPages.Nodes_Conveyor_Data_Pickup(Conveyor);
			
			// clicking on pick up button and entering object id
			retryingFindClick(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId) .replace("&1Value", type)));
			System.out.println(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId) .replace("&1Value", type)));
			reportStep("Conveyor Id: #B" + Conveyor + "#C ObjectId: #B" + objectId+ "#C entered to move from Forklift to Conveyor", "Info", false);
			Thread.sleep(4000);
			
			//Sending Correct object id with prefix "p"
			sendKeys("Entering correct Object Id prefix 'p': " + Correct_Object_Id,By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), Correct_Object_Id);
			Thread.sleep(3000);
			System.out.println("correct Object Id prefix 'p': "+Correct_Object_Id);
			reportStep("Scanning correct object Id with prefix 'p':" +Correct_Object_Id, "pass", false);
			
			//scanning valid check code which is not directly linked to AGV node
			switch (Conveyor) {
			
			case "In1": Conveyor = "1693746289";
					break;
			case "In2": Conveyor = "1693746289";
					break;
			case "In3": Conveyor = "1374385916";
					break;
			
			}
			//Scanning another check code which is not directly linked to that AGV delivery
			sendKeys("Entering another checkcode:" +Conveyor, By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")), Conveyor);
			System.out.println("Conveyor:" +Conveyor);
			Thread.sleep(3000);
			
			
			//Clicking on ok button
			if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click"))) == true) {
				
				retryingFindClick(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")));
				System.out.println();
				Thread.sleep(3000);
				
			} else {
				reportStep("Ok button doesn't exists", "fail", false);
			}
		}
			//Submitting 2nd Subtask of AGVFKOLL
			// clicking Vclas Home button
			anyClick("Vclas Home button click", By.xpath(prop.getProperty("Vclas_Home.Home.Button")));

			// clicking on the Administration button
			anyClick("Vclas Administration button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Button")));

			// clicking on Nodes button
			anyClick("Vclas Nodes button click", By.xpath(prop.getProperty("Vclas_Administration.Nodes.Button")));

			// verifying search textbox
			if (verifyElementExistReturn(
					By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox"))) == true) {
				
				// sending node name to search textbox
				sendKeys("Search textbox", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Textbox")),nodeName);
				
				// clicking search button
				anyClick("Vclas Node screen search button click",By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Search.Button")));
				
				//Clicking on parameters button expansion
				anyClick("Parameters expansion button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Paramters.Expand.Button")));
				
				
				if(verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Administration.Nodes.MoveButton.Checkbox.Cancel")))) {
					
					//Clicking on save button
					anyClick("Save button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Save.Button")));
					
				}else {
				
				//Clicking on use move button to active, to enable yellow button in scanning window of AGV order
				driver.findElements(By.xpath(prop.getProperty("Vclas_Administration.Nodes.MoveButton.Inherited.Checkbox"))).get(0).click();
				Thread.sleep(4000);
				
				driver.findElements(By.xpath(prop.getProperty("Vclas_Administration.Nodes.MoveButton.Value.Checkbox"))).get(0).click();
				Thread.sleep(4000);
				
				//Clicking on save button in nodes screen
				anyClick("Save button click", By.xpath(prop.getProperty("Vclas_Home.Administration.Nodes.Save.Button")));
				}
			}
			
			// Navigate the Page
			anyClick("Assignment Navigate", By.xpath(prop.getProperty("Vclas_Home.Assignments.Button")));
			Thread.sleep(5000);

			// Verify the Page is displayed
			verifyElementExist("Assignment Page", By.xpath(prop.getProperty("Vclas_Home.Work.Areas.Button")));
			
			//Selecting AGV room to deliver 2nd subtask of AGVFKOLL
			vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[1]);
			
			reportStep(" Selected Work Area: #B" + stRoomValue.split(",")[1] + "#C", "Info", false);

			// Navigation to Shortage
			anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

			// Waiting to verify the screen is displayed
			waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")), 5);

			//searching for the presence of created task using object Id
			vclassAssignmentsPages.Vclas_Shortage_Search(objectId, "", "");

			// Verify the Task id in Shortage screen
			vclassAssignmentsPages.Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[1]);
			reportStep("Object Id: #B" + objectId+ "#C  Rack Type: #B  AGVFKOLL  #C delivery  from Assignment List screen initiated","Info", false);
			
			// Rack Pick/Drop in the Assignment List
			vclassAssignmentsPages.vclas_Agv_Rackpickup(strFullType, objectId);
			
			// clicking on pick up button 
			retryingFindClick(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId) .replace("&1Value", type)));
			System.out.println(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", objectId) .replace("&1Value", type)));
			reportStep("Conveyor Id: #B" + Conveyor + "#C ObjectId: #B" + objectId+ "#C entered to move from Forklift to Conveyor", "Info", false);
			Thread.sleep(4000);
			
			//Scanning valid conveyer code fetched from nodes screen
			sendKeys("scan",By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")),ConveyorCode);
			
			//Clicking on yellow Exclamatory button
			//anyClick("Clicking on yellow button in scanning window", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button")));
			retryingFindClick(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button")));
			
			//Verify yellow button confirmation window opens
			if(verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window")))) {
				
				//clicking on no change drodown
				anyClick("No change dropdown click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Node.Change.Dropdown")));
				anyClick("Selecting option ", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Node.Change.List")));
				reportStep("Selecting No change dropdown", "pass", false);
				
				//Clciking on cause dropdown
				//anyClick("Cause dropdown click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Cause.Dropdown")));
				retryingFindClick(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Cause.Dropdown")));
				Thread.sleep(4000);
				//anyClick("Cause dropdown click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Cause.List")));
				retryingFindClick(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Cause.List")));
				Thread.sleep(4000);
				reportStep("Selecting Cause dropdown", "pass", false);
				
				//Clicking on green button in yellow button confirmation window
				anyClick("Green button click", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Yellow.Button.Confirmation.Window.Green.button")));
				Thread.sleep(4000);
				
			}else {
				reportStep("Yellow button confirmation window not displayed", "fail", false);
			}
				
				//Clicking on shortage tab
				// Navigation to Shortage
				anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));

				// Waiting to verify the screen is displayed
				waitForElement(driver, By.xpath(prop.getProperty("Vclas_Home.Shortage.Search.Text")), 5);
				
				//searching for the presence of created task using object Id
				vclassAssignmentsPages.Vclas_Shortage_Search(objectId, "", "");
				
				//Verify the status as 'shortage' in shortage screen
				vclassAssignmentsPages.Vclas_Shortage_TypeVerify(objectId, strFullTaskId, strFullType, strStatusVerify[1]);
				
				//Clicking on the 2nd subtask of AGVFKOLL in shortage screen for which the alarm generated
				if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
						.replace("&Value", objectId).replace("&1Value", strFullTaskId))) == true) {

					driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
							.replace("&Value", objectId).replace("&1Value", strFullTaskId))).get(0).click();

					reportStep("PickUp Button for the #B" + strFullTaskId + " #C is Verified and clicked Successfully", "pass", false);
				} else {
					reportStep("PickUp button for the #B" + strFullTaskId + "#C is not enabled", "Pass", true);
				}
				
				// Clicking for the object in Shortage Screen to Deliver
				if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
						.replace("&Value", objectId).replace("&1Value", strFullTaskId))) == true) {

					driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
							.replace("&Value", objectId).replace("&1Value", strFullTaskId))).get(0).click();

					reportStep("Deliver Button for the #B" + strFullTaskId + "#C is Verified and clicked Successfully", "pass", false);
				} else {
					reportStep("Deliver button for the  #B" + strFullTaskId + "#C is not enabled", "fail", true);
				}
				
				// Clicking on the OK Button
				if (driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click"))).size() > 0) {

					// Waiting to verify the screen is displayed
					waitForElement(driver, By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")), 3);

					// retryingFindClick(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")));

					driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click"))).get(0).click();
					Thread.sleep(3000);
					reportStep("Ok Button for the #B" + strFullTaskId + "#C is enabled and clicked Successfully", "pass", false);

		}
		///////////////////////////////////////////////////////////////////////////////		
				//searching for the presence of created task using object Id
				vclassAssignmentsPages.Vclas_Shortage_Search(objectId, "", "");
				
				//Verify the status as '' in shortage screen
				vclassAssignmentsPages.Vclas_Shortage_TypeVerify(objectId, strEmptyTaskId, strEmptyType, strStatusVerify[0]);
				
				//Clicking on the 2nd subtask of AGVFKOLL in shortage screen for which the alarm generated
				if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
						.replace("&Value", objectId).replace("&1Value", strEmptyTaskId))) == true) {

					driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Pickup.Button")
							.replace("&Value", objectId).replace("&1Value", strEmptyTaskId))).get(0).click();

					reportStep("PickUp Button for the #B" + strEmptyTaskId + " #C is Verified and clicked Successfully", "pass", false);
				} else {
					reportStep("PickUp button for the #B" + strEmptyTaskId + "#C is not enabled", "Pass", true);
				}
				
				// Clicking for the object in Shortage Screen to Deliver
				if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
						.replace("&Value", objectId).replace("&1Value", strEmptyTaskId))) == true) {

					driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Shortage.Deliver.Button")
							.replace("&Value", objectId).replace("&1Value", strEmptyTaskId))).get(0).click();

					reportStep("Deliver Button for the #B" + strEmptyTaskId + "#C is Verified and clicked Successfully", "pass", false);
				} else {
					reportStep("Deliver button for the  #B" + strEmptyTaskId + "#C is not enabled", "fail", true);
				}
				
				// Clicking on the OK Button
				if (driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click"))).size() > 0) {

					// Waiting to verify the screen is displayed
					waitForElement(driver, By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")), 3);

					// retryingFindClick(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click")));

					driver.findElements(By.xpath(prop.getProperty("Vclas.Shortage.OkButton.Click"))).get(0).click();
					Thread.sleep(3000);
					reportStep("Ok Button for the #B" + strFullTaskId + "#C is enabled and clicked Successfully", "pass", false);

		}
				
				
				 FPOS_TPOS_Name_Change_DB_Verify(strFullTaskId);
				 reportStep("Verified the functionality of delivering of a moved AGV order TestCase ID:3461", "pass", false);
				 reportStep("Verified the functionality of cancelling  of a moved AGV order TestCase ID:3462", "pass", false);
		}	
	
	public void FPOS_TPOS_Name_Change_DB_Verify(String strFullTaskId) throws InterruptedException,SQLException{
        ResultSet rsVCLAS_FPOS_TPOS;
        
        // vclas connection details
        String ClassName = prop.getProperty(Environment + ".VCLAS.ORACLE.ClassName");
        String ConnectionString = prop.getProperty(Environment + ".VCLAS.ORACLE.Connection.String");
        String UserName = prop.getProperty(Environment + ".VCLAS.ORACLE.User.Name");
        String Password = prop.getProperty(Environment + ".VCLAS.ORACLE.User.Password");
        
         DB_Connectivity db = new DB_Connectivity();
        
         rsVCLAS_FPOS_TPOS= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_Changed_FPOS_TPOS_Name.replace("#FullTaskTD#", strFullTaskId));
        if(rsVCLAS_FPOS_TPOS.next()==true) {
               reportStep("TA_Delivery_Change table is inserted with one row", "pass", false);
        }else {
               reportStep("TA_Delivery_Change table is not inserted with one row", "fail", false);
        }
        rsVCLAS_FPOS_TPOS.close();
        //MASWEB connection details
        ResultSet rsMASWEB_FPOS_TPOS;
    	ClassName = prop.getProperty(Environment + ".MASWEB.MIMER.ClassName");
		ConnectionString = prop.getProperty(Environment + ".MASWEB.MIMER.Connection.String");
		UserName = prop.getProperty(Environment + ".MASWEB.MIMER.User.Name");
		Password = prop.getProperty(Environment + ".MASWEB.MIMER.Password");
	
        
         rsMASWEB_FPOS_TPOS= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_Changed_FPOS_TPOS_Name_MASWEB.replace("#FullTaskTD#", strFullTaskId));
        if(rsMASWEB_FPOS_TPOS.next()==true) {
               reportStep("TA_Delivery_Change table is inserted with one row", "pass", false);
        }else {
               reportStep("TA_Delivery_Change table is not inserted with one row", "fail", false);
        }
        rsMASWEB_FPOS_TPOS.close();
      
 }

	
	/*---------------------------------
	 * delivering by other user methods starts
	----------------------------------- */
	
	public void PickingOneAssignment(String Select_Work_Area_Room,String ObjectId,String RackType) throws InterruptedException{
		String stRoomValue = Select_Work_Area_Room;
	
		vclassAssignmentsPages= new VclassAssignmentsPages(driver);
		// selecting AGV-Forklift room
		vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
		//navigate to Assignment List Tab
		driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button"))).get(0).click();
		reportStep("Object Id: #B" + ObjectId + "#C  Rack Type: #B" + RackType
				+ "#C Click for Submission from #B Assignment List #C screen", "Info", false);
		anyClick("AL screen Rack pick up", By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId).replace("&1Value", RackType)));
		//driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId).replace("&1Value", RackType))).get(0).click();

		reportStep("Object Id: #B" + ObjectId + "#C  Rack Type: #B" + RackType
				+ "#C is clicked", "Info", true);
		
		

	}
		
		public void DeliveryByOtherUser(String Select_Work_Area_Room,String ObjectId,String RackType,String TaskID ) throws InterruptedException{
			
			String stRoomValue = Select_Work_Area_Room;
			String Seqno = "";
			
			
			
			
			vclassAssignmentsPages= new VclassAssignmentsPages(driver);
			
			// selecting AGV-Forklift room
			vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
			
			//navigate to Assignment List Tab
			driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button"))).get(0).click();
			
			// trying to deliver the picked up assignment by other user in AL Screen
			// Verify the assignment area table is displayed
			
			String rackName = prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId)
					.replace("&1Value", RackType);
			System.out.println("rack name" + rackName);
			
			// need to get picked up userID
			String UserID = driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.pickedup.UserID").replace("&Value",ObjectId ).replace("&1Value", RackType))).get(0).getText();
			System.out.println("UserId:"+UserID);
			reportStep("User In VCLAS Application with some AGV Orders", "pass", true);
			reportStep("Verification of possibility of delivering AGV order from Assignment List screen picked up other user #B Test Case Id:2891#C -- Started ", "info", false);
			

			if (verifyElementExistReturn(By.xpath(rackName)) == true) {

				// Selecting the Rack
				// driver.findElements(By.xpath(rackName));
				reportStep("#B" + ObjectId + "and the racktype:" + RackType+ " has been picked up by other user: "+UserID+ "#C", "pass", true);
				
				driver.findElements(By.xpath(rackName)).get(0).click();
				reportStep("#B" + ObjectId + "and the racktype:" + RackType
						+ " unable to pick up #C", "pass", true);
				reportStep("Verification of possibility of delivering  AGV order from Assignment List screen picked up other user: "+UserID+" Test Case Id:2891 ", "Pass", true);
				reportStep("Verification of possibility of delivering AGV order from Assignment List screen picked up other user#B Test Case Id:2891#C -- completed ", "info", false);
				Thread.sleep(5000);
				System.out.println("rack name" + rackName);
			} else {
				reportStep("Assignment list table does not exist", "fail", true);
			}
			
			// delivering through Shortage Screen
			//navigating to shortage screen
			
			reportStep("Verification of possibility of delivering AGV order from Shortage screen picked up other user#B Test Case Id:2892#C -- Started ", "info", false);
			
			anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));
			// Searching the objectId in Shortage screen
			vclassAssignmentsPages.	Vclas_Shortage_Search(ObjectId, String.valueOf(Seqno), "");
			vclassAssignmentsPages.Vclas_Shortage_Delivery_Selection(TaskID,ObjectId);
			reportStep("Verification of possibility of delivering AGV order from shortage screen picked up other user#B Test Case Id:2892#C ", "pass", true);
			reportStep("Verification of possibility of delivering AGV order from Shortage screen picked up other user#B Test Case Id:2892#C -- completd ", "info", false);
			
			
		}
		
		/*---------------------------------
		 * delivering by other user methods Ends
		----------------------------------- */
		
		
		
		public void SendType_Selection_Scan(String sendType, String scan) throws InterruptedException {
			vclassAssignmentsPages= new VclassAssignmentsPages(driver);
			if (verifyElementExistReturn(By.xpath(prop.getProperty("Vclas.AssignmentList.ManualInput." + sendType))) == false) {
				if (sendType.equalsIgnoreCase("Yes")) {
					// driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.ManualInput.No"))).get(0).click();
					Thread.sleep(2000);
					vclassAssignmentsPages.clickByLocator(By.xpath(prop.getProperty("Vclas.AssignmentList.ManualInput.No")));
					driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).sendKeys(scan);
					driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0)
							.sendKeys(Keys.TAB);

				} else {
					driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.ManualInput.Yes"))).get(0).click();
					driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).sendKeys(scan);

				}
			} else if (sendType.equalsIgnoreCase("No")) {

				driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).sendKeys(scan);

			} else {
				Thread.sleep(2000);
				driver.findElement(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).sendKeys(scan);
				driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text"))).get(0)
						.sendKeys(Keys.TAB);
			}

		}
		
		/*---------------------------------
		 * Invalid Conveyer code validation method
		----------------------------------- */
		
	public void InvalidConveyorCode(String Select_Work_Area_Room,String ObjectId,String RackType,String JisjitCheckCode) throws InterruptedException{
		String stRoomValue = Select_Work_Area_Room;
		String ConveyorId,CheckCode;
		String object = "p"+ObjectId;
		vclassAssignmentsPages= new VclassAssignmentsPages(driver);

		// selecting AGV-Forklift room
		vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
		
		//navigate to Assignment List Tab
		anyClick("Assignment List Tab", By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button")));
		
		// picking up order 
		vclassAssignmentsPages.vclas_Agv_Rackpickup(RackType, ObjectId);
		
		//verifying the scan window
		if(verifyElementExist("Scan text feild in Scanning window", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")))==true) {
			reportStep("Pop-up window is displayed ", "pass", true);
			
			ConveyorId=driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).get(0).getText();
			System.out.println("ConveyorId:"+ConveyorId);
			SendType_Selection_Scan("Yes",object);
			
			// entering and validting the wrong check code in alphabets
			CheckCode="shfbjkehjke";
			Thread.sleep(2000);
			SendType_Selection_Scan("Yes",CheckCode);
			if(verifyElementExist("Alphabetic Check code Error msg", By.xpath(prop.getProperty("Vclas_home.AssignmentList.AlphabeticCheckCode.ErrorMsg")))==true) {
				String ErrorMsg=driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.AlphabeticCheckCode.ErrorMsg"))).get(0).getText();
				reportStep("Error Message for alphabetic check codein the scan pop up window"+ErrorMsg, "pass", true);
			}else {
				reportStep("Error message is not verified", "fail", true);
			}
			
			//entering wrong checkcode values;
			
			CheckCode="123456789";
			SendType_Selection_Scan("Yes",CheckCode);
			if(verifyElementExist(" Check code Error msg", By.xpath(prop.getProperty("Vclas_home.AssignmentList.WrongCheckCode.ErrorMsg")))==true) {
				String ErrorMsg=driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.WrongCheckCode.ErrorMsg"))).get(0).getText();
				reportStep("Error Message for wrong check code value in the scan pop up window : "+ErrorMsg, "pass", true);
				reportStep("Verification the functionality of scanning the invalid AGV conveyor code for ex  alphabets or 123456 completed#B Test case Id:2985#C", "pass", true);
			}else {
				reportStep("Error message is not verified", "fail", true);
			}
			
	// sending jisjit check code B-TVN AEA
	//navigating to nodes screen to fetch the other flow check code say jisjit node		
			 /* waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click")), 3);
				 driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click"))).get(0).click();
				 CheckCode= vclassAssignmentsPages.Nodes_Conveyor_Data_Pickup(JisjitCheckCode);
				 driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId) .replace("&1Value", RackType))).get(0).click();*/
			
			//SendType_Selection_Scan("Yes",object);
			SendType_Selection_Scan("Yes",JisjitCheckCode);
			// need to verify the error msg and add report step
			if(verifyElementExist(" Check code Error msg", By.xpath(prop.getProperty("Vclas_home.AssignmentList.WrongCheckCode.ErrorMsg")))==true) {
				String ErrorMsg=driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.WrongCheckCode.ErrorMsg"))).get(0).getText();
				reportStep("Error Message for scanning the checkcode which is not the part of AGV node itself : "+ErrorMsg, "pass", true);
				reportStep("Verification the functionality of scanning other than AGV conveyor code  which is not part of AGV node itself completed#B Test Case Id:2898#C", "pass", true);
			}else {
				reportStep("Error message is not verified", "fail", true);
			}
			
			
			
			
			
		}else {
			reportStep("Scanning window does not exist", "fail", true);
		}
		
			
		
		
		
	}
	/*
	 * picking multiple assignment methods
	 *
	 */
	
	public void PickingMultipleAssignments(String Select_Work_Area_Room,String ObjectId,String RackType,String TaskId) throws InterruptedException{
		String stRoomValue = Select_Work_Area_Room;
	//	String Seqno = "",ConveyorId,CheckCode;
		//String object = "p"+ObjectId;
		vclassAssignmentsPages= new VclassAssignmentsPages(driver);

		// selecting AGV-Forklift room
		vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
		
		//navigate to Assignment List Tab
		anyClick("Assignment List Tab", By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button")));
		
		//verification of picking multiple assignmetns from AL screen
		String rackName = prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId)
				.replace("&1Value", RackType);
		System.out.println("rack name" + rackName);
		if (verifyElementExistReturn(By.xpath(rackName)) == true) {

			driver.findElements(By.xpath(rackName)).get(0).click();
			reportStep("#B" + ObjectId + "and the racktype:" + RackType+ " unable to pick up from Assignment List Screen #C", "pass", true);
			reportStep("Verification of possibility of picking multiple Assignments from AL screen #BTest Case Id:2901#C ", "Pass", true);
			reportStep("Verification of possibility of picking multiple Assignments from AL screen  #BTest Case Id:2901#C ", "info", false);
			Thread.sleep(5000);
			System.out.println("rack name" + rackName);
		} else {
			reportStep("Assignment list table does not exist", "fail", true);
		}
		
		//verification of picking multiple assignments from Shortage screen
		
		//navigating to shortage screen
		anyClick("Shortage Screen", By.xpath(prop.getProperty("Vclas_Home.Shortage.Button")));
		String pickupButton = prop.getProperty("Vclas_Home.Shortage.Pickup.Button").replace("&Value", ObjectId)
				.replace("&1Value", TaskId);
		System.out.println("rack name" + pickupButton);
		if (verifyElementExistReturn(By.xpath(pickupButton)) == true) {

			driver.findElements(By.xpath(pickupButton)).get(0).click();
			if (verifyElementExistReturn(By.xpath(pickupButton)) == true) {
				reportStep("#B ObjectId:" + ObjectId + "and TaskId:" + TaskId+ " unable to pick up from Shortage screen #C", "pass", true);
				reportStep("Verification of possibility of picking multiple Assignments from Shortage screen #B Test Case Id:2902 #C", "Pass", true);
				reportStep("Verification of possibility of picking multiple Assignments from Shortage screen #B Test Case Id:2902#C ", "info", false);
			}
			
			Thread.sleep(5000);
			System.out.println("rack name" + rackName);
		} else {
			reportStep("pickup button of the assignment with objectId:"+ObjectId+" and TaskId:  "+TaskId+" is not present", "fail", true);
		}
		
	}
	
	/*
	 * method to deliver AGV 1st full subtask with Conveyor code linked to AGV but not planned by the system
	 *
	 */
	
	public void ConveyorMadLinkChange(String Select_Work_Area_Room,String ObjectId,String RackType,String UserId,String FullTaskID,String EmptyTaskID) throws InterruptedException, SQLException{
		String stRoomValue = Select_Work_Area_Room;
		String PlannedConveyor,CheckCode,newConveyor="";
		String object = "p"+ObjectId;
		vclassAssignmentsPages= new VclassAssignmentsPages(driver);

		// selecting AGV-Forklift room
		vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
		
		//navigate to Assignment List Tab
		anyClick("Assignment List Tab", By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button")));
		
		// picking up order 
		//vclassAssignmentsPages.vclas_Agv_Rackpickup(RackType, ObjectId);
		
		//verification of picking multiple assignmetns from AL screen
				String rackName = prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId)
						.replace("&1Value", RackType);
				System.out.println("rack name" + rackName);
				if (verifyElementExistReturn(By.xpath(rackName)) == true) {

					driver.findElements(By.xpath(rackName)).get(0).click();
					driver.findElements(By.xpath(rackName)).get(0).click();
					reportStep("#B" + ObjectId + "and the racktype:" + RackType+ " unable to pick up from Assignment List Screen #C", "pass", true);
					reportStep("Verification of possibility of picking multiple Assignments from AL screen #BTest Case Id:2901#C ", "Pass", true);
					reportStep("Verification of possibility of picking multiple Assignments from AL screen  #BTest Case Id:2901#C ", "info", false);
					Thread.sleep(5000);
					System.out.println("rack name" + rackName);
				} else {
					reportStep("Assignment list table does not exist", "fail", true);
				}

		
		
		
		//verifying the scan window
		if(verifyElementExist("Scan text feild in Scanning window", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")))==true) {
			reportStep("Pop-up window is displayed ", "pass", true);
			
			PlannedConveyor=driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).get(0).getText();
			System.out.println("PlannedConveyorId:"+PlannedConveyor);
			reportStep("#B AGV Conveyor Planned by the system: "+PlannedConveyor +"#C", "pass", true);
			
			//changing the conveyor code which linked to mad but not planned by the system
			switch (PlannedConveyor) {
			case "In1":
				newConveyor="In2";
				break;
			case "In2":
				newConveyor="In1";
				break;
			case "In3":
				newConveyor="In2";
				break;
			}
		// navigating to nodes screen to fetch the conveyor code value
			  waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click")), 3);
				 driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click"))).get(0).click();
				 CheckCode= vclassAssignmentsPages.Nodes_Conveyor_Data_Pickup(newConveyor);
				 driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId) .replace("&1Value", RackType))).get(0).click();
			
			SendType_Selection_Scan("Yes",object);
			SendType_Selection_Scan("Yes",CheckCode);
			
		// verifying the confimation window and conveyor change
			if(verifyElementExist("confirmation window ", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label")))==true) {
				newConveyor= driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).get(0).getText();
				if(!PlannedConveyor.equalsIgnoreCase(newConveyor)) {
					reportStep("#B Conveyor has changed from "+PlannedConveyor+" to"+newConveyor+"#C", "pass", true);
				}else {
					reportStep("Conveyor has not changed from "+PlannedConveyor+" to"+newConveyor, "fail", true);
				}
				 waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")), 3);
				 driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click"))).get(0).click();
			
				 // need to check with db 
				 ConveyorMadLinkChange_DB_Verify( UserId, FullTaskID, EmptyTaskID);
				 reportStep(" Verification of functionality of scanning AGV conveyor code which is linked to AGV mad but not planned by system Id:2897", "pass", false);
			}
			
		}
		
	}
	
	public void ConveyorMadLinkChange_DB_Verify(String UserId,String FullTaskID,String EmptyTaskID) throws InterruptedException,SQLException{
		ResultSet rsTaDeliveryChange,rsAgvOrderStatus,rsTransportAssignment;
		
		// vclas connection details
		 String ClassName = prop.getProperty(Environment + ".VCLAS.ORACLE.ClassName");
		 String ConnectionString = prop.getProperty(Environment + ".VCLAS.ORACLE.Connection.String");
		 String UserName = prop.getProperty(Environment + ".VCLAS.ORACLE.User.Name");
		 String Password = prop.getProperty(Environment + ".VCLAS.ORACLE.User.Password");
		 
		 DB_Connectivity db = new DB_Connectivity();
		 
		 rsTaDeliveryChange= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_Conveyor_Ta_Delivery_Change.replace("#UserId#", UserId));
		 if(rsTaDeliveryChange.next()==true) {
			 reportStep("TA_Delivery_Change table is inserted with one row", "pass", false);
		 }else {
			 reportStep("TA_Delivery_Change table is not inserted with one row", "fail", false);
		 }
		 rsTaDeliveryChange.close();
		 
		 rsAgvOrderStatus=db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_Conveyor_AGV_Order_Status.replace("#FullTaskID#", FullTaskID).replace("#EmptyTaskID#", EmptyTaskID));
		 if(rsAgvOrderStatus.next()==true) {
			String FullOrderNo= rsAgvOrderStatus.getString(2);
			System.out.println("FullOrderNo:"+FullOrderNo);
			rsAgvOrderStatus.next();
			String EmptyOrderNo=rsAgvOrderStatus.getString(2);
			System.out.println("EmptyOrderNo:"+EmptyOrderNo);
			 reportStep("2nd Subtask of AGVFKOLL with order no:"+FullOrderNo+ "and 1st Subtask of AGVEKOLL with order no:"+FullOrderNo+ "is present in AGV_ORDER_STATUS  table ", "pass", false);
		 }
		 rsAgvOrderStatus.close();
		 rsTransportAssignment= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_Conveyor_Transport_Assignment.replace("#FullTaskID#", FullTaskID).replace("#EmptyTaskID#", EmptyTaskID));
		if(rsTransportAssignment.next()==true) {
			String FromNode=rsTransportAssignment.getString(3);
			System.out.println("FromNode:"+FromNode);
			reportStep("From node of 2nd subtask of AGVFKOLL is changed to new conveyor code: "+FromNode+" in Trasnport Assignment table", "pass", false);
			
		}
		rsTransportAssignment.close();
		
	}
	
	
	public void ConveyorChangeIndirectLink(String Select_Work_Area_Room,String ObjectId,String RackType,String UserId,String FullTaskID,String EmptyTaskID) throws InterruptedException, SQLException{
		String stRoomValue = Select_Work_Area_Room;
		String PlannedConveyor,CheckCode,newConveyor="";
		String object = "p"+ObjectId;
		vclassAssignmentsPages= new VclassAssignmentsPages(driver);

		// selecting AGV-Forklift room
		vclassAssignmentsPages.Vclas_Assignment_SelectWorkArea("Room", stRoomValue.split(",")[0]);
		
		//navigate to Assignment List Tab
		anyClick("Assignment List Tab", By.xpath(prop.getProperty("Vclas_Home.Assignment.List.Button")));
		
		// picking up order 
		//vclassAssignmentsPages.vclas_Agv_Rackpickup(RackType, ObjectId);
		
		//verification of picking multiple assignmetns from AL screen
				String rackName = prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId)
						.replace("&1Value", RackType);
				System.out.println("rack name" + rackName);
				if (verifyElementExistReturn(By.xpath(rackName)) == true) {

					driver.findElements(By.xpath(rackName)).get(0).click();
					driver.findElements(By.xpath(rackName)).get(0).click();
					
					
					
					Thread.sleep(5000);
					System.out.println("rack name" + rackName);
				} else {
					reportStep("Assignment list table does not exist", "fail", true);
				}

		
		
		
		//verifying the scan window
		if(verifyElementExist("Scan text feild in Scanning window", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.Text")))==true) {
			reportStep("Pop-up window is displayed ", "pass", true);
			
			PlannedConveyor=driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).get(0).getText();
			System.out.println("PlannedConveyorId:"+PlannedConveyor);
			reportStep("#B AGV Conveyor Planned by the system: "+PlannedConveyor +"#C", "pass", true);
			
			//changing the conveyor code which is not directly linked to mad
			switch (PlannedConveyor) {
			case "In1":
				newConveyor="In3";
				break;
			case "In2":
				newConveyor="In3";
				break;
			case "In3":
				newConveyor="In1";
				break;
			}
		// navigating to nodes screen to fetch the conveyor code value
			  waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click")), 3);
				 driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.CancelButton.Click"))).get(0).click();
				 CheckCode= vclassAssignmentsPages.Nodes_Conveyor_Data_Pickup(newConveyor);
				 driver.findElements(By.xpath(prop.getProperty("Vclas_home.AssignmentList.Column.Type").replace("&Value", ObjectId) .replace("&1Value", RackType))).get(0).click();
			
			SendType_Selection_Scan("Yes",object);
			SendType_Selection_Scan("Yes",CheckCode);
			if(verifyElementExist("Pop up window for conveyor change", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Conveyor.Change.Green.Button")))==true) {
				//Clicking on Red button
				driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Conveyor.Change.Red.Button"))).get(0).click(); 
				reportStep("Check code is changed to '-1' by default", "pass", true);
				SendType_Selection_Scan("Yes",CheckCode);
				//Clicking on green button
				//driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Conveyor.Change.Green.Button"))).get(0).click();
				retryingFindClick(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Conveyor.Change.Green.Button"))); 

			}
			
			
		// verifying the confimation window and conveyor change
			if(verifyElementExist("confirmation window ", By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label")))==true) {
				newConveyor= driver.findElements(By.xpath(prop.getProperty("Vclas_Home.AssignmentList.Scan.DropAddress.Label"))).get(0).getText();
				if(!PlannedConveyor.equalsIgnoreCase(newConveyor)) {
					reportStep("#B Conveyor has changed from "+PlannedConveyor+" to"+newConveyor+"#C", "pass", true);
				}else {
					reportStep("Conveyor has not changed from "+PlannedConveyor+" to"+newConveyor, "fail", true);
				}
				 waitForElement(driver, By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click")), 3);
				 driver.findElements(By.xpath(prop.getProperty("Vclas.AssignmentList.OkButton.Click"))).get(0).click();
			
				 ConveyorMadLinkChange_DB_Verify( UserId, FullTaskID, EmptyTaskID);
				 reportStep(" Verification of functionality of Confirmation window When user scans the valid AGV conveyor code  which is not directly linked to AGV mad Test case Id:2896", "pass", false);
				
				 
			}
			
		}
		
	}
	
	
	/*
	 * verification of displaying assignments in shortage without transport order and alarm tab verification
	 */
	
	
}

