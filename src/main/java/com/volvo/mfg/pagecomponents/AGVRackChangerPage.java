package com.volvo.mfg.pagecomponents;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



import com.volvo.mfg.commonutilis.CommonWrapperMethods;
import com.volvo.mfg.commonutilis.DB_Connectivity;
import com.volvo.mfg.commonutilis.QueryInput;



public class AGVRackChangerPage extends CommonWrapperMethods implements QueryInput {

	
	private WebDriver driver;

	
	public AGVRackChangerPage(WebDriver driver) {
		this.driver = driver;
	}

	
	public void MachineID_Search(String FID,String plant,String actor ) throws InterruptedException {

		// retrieve data from MasWeb_Page
		// Load Test Data File
//		tdrow = excelUtils.testCaseRetrieve(refTestDataName, sheetName);

		// Enter the Search Value
		
		// Enter the FID value
				if (!FID.equals("")) {
					sendKeys("FID", By.xpath(prop.getProperty("AGV_RackChanger.FID.Text")), FID);
				}
		// Enter Plant to select
		if (!plant.equals("")) {
			selectDropDownValue("Plant dropdown", By.xpath(prop.getProperty("AGV_RackChanger.Plant.DropDown")),
					plant);
		}

		

		// Enter the FID value
		if (!actor.equals("")) {
			selectDropDownValue("Actor dropdown", By.xpath(prop.getProperty("AGV_RackChanger.Actor.DropDown")),
					actor);
		}

		anyClick("Search Machine ID", By.xpath(prop.getProperty("AGV_RackChanger.Display.Button")));
		Thread.sleep(2000);

		// Verify the table is displayed

		verifyElementExist("Machine ID Table Search Data",
				By.xpath(prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[1]"));

		// Clearing the Memory
//		tdrow.clear();

	}

	public String MachineID_Pick(String Status) {

		String strMachineID;

		reportStep("--------AGV #BMACHINE ID#C pick in Rack Changer Initiated----", "Info", false);
		try {
			String tableHeaderColumn = prop.getProperty("AGV_RackChanger.Search.Table") + "/thead/tr";

			// Get the column position of each column
			int colMachineID = htmlTableColumnNamePosition("MachineID", tableHeaderColumn);
			int colStatus = htmlTableColumnNamePosition("Status", tableHeaderColumn);
			int colUserId = htmlTableColumnNamePosition("User Id", tableHeaderColumn);
			int colFID = htmlTableColumnNamePosition("FID", tableHeaderColumn);

			String tableObject = prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr";

			List<WebElement> tableBodyRow = driver.findElements(By.xpath(tableObject));

			int rowCount = tableBodyRow.size();

			// Fetching the Active Machine ID from the screen
			for (int i = 0; i <= rowCount - 1; i++) {
				List<WebElement> tableData = tableBodyRow.get(i).findElements(By.tagName("td"));

				if (tableData.get(colStatus).getText().equalsIgnoreCase(Status)
						&& !tableData.get(colUserId).getText().equalsIgnoreCase("Thread")) {
					if (!tableData.get(colFID).getText().equalsIgnoreCase("")) {
						strMachineID = tableData.get(colMachineID).getText().trim();
						reportStep("AGV picked MACHINE ID: #B" + strMachineID + "#C and FID: #B"
								+ tableData.get(colFID).getText().trim() + "#C", "Pass", true);
						reportStep("AGV picked MACHINE ID: #B" + strMachineID + "#C and FID: #B"
								+ tableData.get(colFID).getText().trim() + "#C", "Info", false);
						// Update the same in Vclas
						prop.setProperty("FID",tableData.get(colFID).getText().trim());
						prop.setProperty("No_of_racks","1");
//						excelUtils.excelUpdateValue("Vclas_tasks", "FID", refTestDataName,tableData.get(colFID).getText().trim()); set FID in property file
//						excelUtils.excelUpdateValue("Vclas_tasks", "No_of_racks", refTestDataName, "1"); set 1 as rack size in property file
						reportStep("--------AGV #BMACHINE ID#C pick in Rack Changer Completed----", "Info", false);
						return strMachineID;
					}
				} else if (i == rowCount - 1) {
					reportStep("No active data available in the table", "fail", true);
					break;
				}

			}

		} catch (Exception e) {
			System.out.println("Machine ID pick failed - exception");
			reportStep("Machine ID pick failed - exception", "fail", true);
		}
		return "";

	}

	public void Agv_Invalid_Input_Validation(String FID,String plant,String actor) throws InterruptedException {

		String strStatus = " ";

		// retrieve data from MasWeb_Page
		// Load Test Data File
//		tdrow = excelUtils.testCaseRetrieve(refTestDataName, sheetName);

		// Verifying invalid plant name

		if (!plant.equals("")) {
			selectDropDownValue("Plant dropdown", By.xpath(prop.getProperty("AGV_RackChanger.Plant.DropDown")),
					plant);

		}
		anyClick("Search Machine ID", By.xpath(prop.getProperty("AGV_RackChanger.Display.Button")));
		if (verifyElementExistReturn(
				By.xpath(prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[2]")) == false) {
			reportStep(" functionality of rackchanger for invalid plant name" + plant + "is verified",
					"pass", true);
		} else
			reportStep("Valid plant name is given", "fail", false);
		anyClick("Clear", By.xpath(prop.getProperty("AGV_RackChanger.Clear.Button")));

		// Verifying invalid FID

		// Enter the FID value
		if (!FID.equals("")) {
			sendKeys("FID", By.xpath(prop.getProperty("AGV_RackChanger.FID.Text")), FID);

		}
		anyClick("Search Machine ID", By.xpath(prop.getProperty("AGV_RackChanger.Display.Button")));
		if (verifyElementExistReturn(
				By.xpath(prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[2]")) == false) {
			reportStep(" functionality of rackchanger for invalid FID:" + FID + " is verified", "pass",
					true);
		} else
			reportStep("Valid FID name is given", "fail", false);
		anyClick("Clear", By.xpath(prop.getProperty("AGV_RackChanger.Clear.Button")));

		// Verifying Actor selection display

		if (!actor.equals("")) {
			selectDropDownValue("Actor dropdown", By.xpath(prop.getProperty("AGV_RackChanger.Actor.DropDown")),
					actor);
		}
		anyClick("Search Machine ID", By.xpath(prop.getProperty("AGV_RackChanger.Display.Button")));

		if (verifyElementExistReturn(
				By.xpath(prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr")) == true) {
			reportStep(" functionality of Actor type:" + actor + " is verified", "pass", true);
		} else
			reportStep("No data with Actor:" + actor, "fail", false);

		anyClick("Clear", By.xpath(prop.getProperty("AGV_RackChanger.Clear.Button")));
		anyClick("Clear", By.xpath(prop.getProperty("AGV_RackChanger.Clear.Button")));

		// Verifying active machine ID search and Edit functionality

		String tableHeaderColumn = prop.getProperty("AGV_RackChanger.Search.Table") + "/thead";
		int colEdit = htmlTableColumnNamePosition("Edit", tableHeaderColumn);
		int colStatus = htmlTableColumnNamePosition("Status", tableHeaderColumn);
		int colReserve = htmlTableColumnNamePosition("Reserve", tableHeaderColumn);
		int colInactive = htmlTableColumnNamePosition("Inactive", tableHeaderColumn);
		int colFID = htmlTableColumnNamePosition("FID", tableHeaderColumn);
		int colActive = htmlTableColumnNamePosition("Active", tableHeaderColumn);

		anyClick("Search Machine ID", By.xpath(prop.getProperty("AGV_RackChanger.Display.Button")));

		if (verifyElementExistReturn(
				By.xpath(prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr")) == true) {
			// Retrieving the xpath of the displayed table
			String tableObject = prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr";

			List<WebElement> rows = driver.findElements(By.xpath(tableObject));
			// getting no. of the rows
			int row_count = rows.size();

			System.out.println(row_count);
			System.out.println(colEdit);
			System.out.println(colStatus);

			for (int i = 0; i <= row_count; i++) {
				int rowInc = i + 1;
				String strTableData = prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[" + rowInc + "]/td";
				List<WebElement> tableData = driver.findElements(By.xpath(strTableData));

				strStatus = tableData.get(colStatus).getText();
				System.out.println(strStatus);
				if (strStatus.equalsIgnoreCase("A")) {

					tableData.get(colEdit).click();
					break;
				}

			}

			verifyElementExist("Edit Page Verification", By.xpath(prop.getProperty("AGV_RackChanger.Edit.Page")));
			if (verifyElementExistReturn(By.xpath(prop.getProperty("AGV_RackChanger.Plant.DropDown"))) == true) {
				String PlantName = driver.findElements(By.xpath(prop.getProperty("AGV_RackChanger.Plant.DropDown")))
						.get(0).getText();

				driver.findElements(By.xpath(prop.getProperty("AGV_RackChanger.Plant.DropDown"))).get(0)
						.sendKeys(plant);

				String newPlantName = driver.findElements(By.xpath(prop.getProperty("AGV_RackChanger.Plant.DropDown")))
						.get(0).getText();
				if (!newPlantName.equals(plant)) {
					reportStep("Plant field is not editable", "pass", true);
				} else {
					reportStep("Plant field is editable", "fail", false);
				}

			} else {
				reportStep("Plant field is not present", "fail", true);
			}
			// Saving edit page and return without doing any changes

			anyClick("Save button of edit page", By.xpath(prop.getProperty("AGV_RackChanger.Edit.Save.Button")));
			anyClick("Return button of edit page", By.xpath(prop.getProperty("AGV_RackChanger.Edit.Return.Button")));

			// Functionality status change verification

			if (verifyElementExistReturn(
					By.xpath(prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr")) == true) {
				reportStep("returned to the page", "pass", false);

				System.out.println(row_count);
				for (int i = 0; i <= row_count; i++) {
					int rowInc = i + 1;
					String strTableData = prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[" + rowInc
							+ "]/td";
					List<WebElement> tableData = driver.findElements(By.xpath(strTableData));

					strStatus = tableData.get(colStatus).getText();
					System.out.println(strStatus);

					// Verifying functionality of status change from Active to Reserve

					if (strStatus.equalsIgnoreCase("A")) {
						String sFID = tableData.get(colFID).getText();
						System.out.println(sFID);
						tableData.get(colReserve).click();
						strTableData = prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[" + rowInc
								+ "]/td";
						tableData = driver.findElements(By.xpath(strTableData));
						String newStatus = tableData.get(colStatus).getText();
						System.out.println(newStatus);
						if (newStatus.equalsIgnoreCase("R")) {
							reportStep("Status  for FID:" + sFID + "changed from Active to Reserve", "pass", true);
						} else {
							reportStep("Status for FID" + sFID + "is not changed", "info", true);
						}

						// Verifying functionality of status change from Reserve to Inactive

						tableData.get(colInactive).click();
						strTableData = prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[" + rowInc
								+ "]/td";
						tableData = driver.findElements(By.xpath(strTableData));
						strStatus = tableData.get(colStatus).getText();
						System.out.println(strStatus);
						if (strStatus.equalsIgnoreCase("I")) {
							reportStep("Status for FID:" + sFID + "changed from Reserve to Inactive", "pass", true);
						} else {
							reportStep("Status for FID:" + sFID + " is not changed from Reserve to Inactive", "info",
									true);
						}

						// verifying functionality of status change from Inactive to Active

						tableData.get(colActive).click();
						strTableData = prop.getProperty("AGV_RackChanger.Search.Table") + "/tbody/tr[" + rowInc
								+ "]/td";
						tableData = driver.findElements(By.xpath(strTableData));
						strStatus = tableData.get(colStatus).getText();
						System.out.println(strStatus);

						if (strStatus.equalsIgnoreCase("A")) {
							reportStep("Status for FID:" + sFID + "changed from Inactive to Active", "pass", true);
						} else {
							reportStep("Status for FID:" + sFID + " is not changed from Inactive to Active", "info",
									true);
						}

						break;
					}

				}

			}

		}

		// Clearing the Memory
//		tdrow.clear();

	}
	
	// Reserve Task Id db verification
	
	public void ReserveID_TaskCreation_Verification(String EmptyTaskId,String FUllTaskId) throws InterruptedException, SQLException {
		//String sheetName="Vclas_Assignments";
		ResultSet rsPMR_473,rsPMR_471,rsPMR_476,rsPMR_478,rsTransport_order,rsTransport_Assignment,rsAGV_Order_Status;
		 String FOrderId="",EOrderId="";
		
		//Excel class object to access the function
		//ExcelUtils excelUtils = new ExcelUtils();
		//tdrow = excelUtils.testCaseRetrieve(refTestDataName, sheetName);
		
		// MASWEB connection details
			String	ClassName = prop.getProperty(Environment + ".MASWEB.MIMER.ClassName");
			String ConnectionString = prop.getProperty(Environment + ".MASWEB.MIMER.Connection.String");
			String UserName = prop.getProperty(Environment + ".MASWEB.MIMER.User.Name");
			String	Password = prop.getProperty(Environment + ".MASWEB.MIMER.Password");
			
			DB_Connectivity db = new DB_Connectivity();

				// MASWEB verification
				 rsPMR_473= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_ReserveTaskID_PMR473.replace("#EmptyTaskID#", EmptyTaskId).replace("#FullTaskTD#", FUllTaskId));
				if(rsPMR_473.next()==true) {
					reportStep("Task Ids were inserted in PMR_473 table", "pass", false);
				}else {
					reportStep("Task Ids were not present in PMR_473 table", "fail", false);
				}
				
				rsPMR_473.close();
				 rsPMR_471= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_ReserveTaskID_PMR471.replace("#EmptyTaskID#",EmptyTaskId).replace("#FullTaskTD#", FUllTaskId));
					if(rsPMR_471.next()==true) {
						reportStep("Task Ids were inserted in PMR_471 table", "pass", false);
					}else {
						reportStep("Task Ids were not present in PMR_471 table", "fail", false);
					}
					
					rsPMR_471.close();
					
					 rsPMR_476= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_ReserveTaskID_PMR476.replace("#EmptyTaskID#", EmptyTaskId).replace("#FullTaskTD#", FUllTaskId));
					if(rsPMR_476.next()==true) {
						reportStep("Task Ids were inserted in PMR_476 table", "pass", false);
					}else {
						reportStep("Task Ids were not present in PMR_476 table", "fail", false);
					}
					
					rsPMR_476.close();
					 rsPMR_478= db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_ReserveTaskID_PMR478.replace("#EmptyTaskID#", EmptyTaskId).replace("#FullTaskTD#", FUllTaskId));
						if(rsPMR_478.next()==true) {
							reportStep("Task Ids were inserted in PMR_478 table", "pass", false);
						}else {
							reportStep("Task Ids were not present in PMR_478 table", "fail", false);
						}
						
						rsPMR_478.close();

					
				Thread.sleep(3000);
				
				// vclas connection details
					 ClassName = prop.getProperty(Environment + ".VCLAS.ORACLE.ClassName");
					 ConnectionString = prop.getProperty(Environment + ".VCLAS.ORACLE.Connection.String");
					 UserName = prop.getProperty(Environment + ".VCLAS.ORACLE.User.Name");
					 Password = prop.getProperty(Environment + ".VCLAS.ORACLE.User.Password");

		
					 //Vclas verification
					 rsTransport_order=db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_ReserveTaskID_TRANSPORT_ORDER.replace("#EmptyTaskID#", EmptyTaskId).replace("#FullTaskTD#", FUllTaskId ));
					
					if(rsTransport_order.next()) {
						  FOrderId=rsTransport_order.getString(1);
						 System.out.println("FOrderId:"+FOrderId);
						  
						 rsTransport_order.next();
						 EOrderId=rsTransport_order.getString(1);
						System.out.println("OrderId2:"+EOrderId); 
						
					}else {
						reportStep("Task Ids were not present in Transport order table", "fail", true);
						
					}rsTransport_order.close();
		
					 rsTransport_Assignment =db.Connect_DB(ClassName, ConnectionString, UserName, Password,AGV_ReserveTaskID_Transport_Assignment.replace("#FOrderId#", FOrderId).replace("#EOrderId#", EOrderId));
					 if(rsTransport_Assignment.next()) {
						 reportStep("AGV Full and Empty orders are inserted in Transport Assignmet table","pass" , false);
						
					 }else {
						 reportStep("AGV Full and Empty orders are not inserted in Transport Assignmet table","fail" , false);
						
					 } rsTransport_Assignment.close();
					 
					 rsAGV_Order_Status=db.Connect_DB(ClassName, ConnectionString, UserName, Password, AGV_ReserveTaskID_AGV_Order_status);
					if( rsAGV_Order_Status.next()){
						 reportStep("Status of created Reserve ID AGV orders is present in AGV_Order_Status table", "pass", false);
						 reportStep("Verify the possibility of creating AGV orders for a Reserved AGV machine ID:2883", "pass", true);
						
					 }else {
						 reportStep("Status of created Reserve ID AGV orders is  not present in AGV_Order_Status table", "fail", false);
						
					 }
					 rsAGV_Order_Status.close();
		
		//Clearing the Memory
		//tdrow.clear();

		
	}
	
	



}
	
		
		