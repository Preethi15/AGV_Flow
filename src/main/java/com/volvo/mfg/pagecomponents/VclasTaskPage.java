package com.volvo.mfg.pagecomponents;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;
import com.volvo.mfg.commonutilis.ExcelUtils;

public class VclasTaskPage extends CommonWrapperMethods {
	WebDriver driver;
	public int colTaskEvents, tomracksTaskId;
	public String tableIndex="";
	//Excel class object to access the function
		ExcelUtils excelUtils = new ExcelUtils();
		
	
	
	public VclasTaskPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
public void Vclas_Task_Search(String taskID,String objectId,String fPos,String tPos,String partNumber,String factory,String flowType,String taskType,String createdFromDate,String createdToDate,String showEntries ) throws InterruptedException {
try {
		//retrieve data from MasWeb_Page
		//Load Test Data File
		//tdrow=excelUtils.testCaseRetrieve(refTestDataName,sheetName);
		
		//Enter the Search Value
		//Enter task id
		if (!taskID.equals(""))
		{
			sendKeys("TaskID", By.xpath(prop.getProperty("Vclas_tasks.TaskId.Text")), taskID);
		}
		
		//Enter Object
		if (!objectId.equals(""))
		{
			sendKeys("Object", By.xpath(prop.getProperty("Vclas_tasks.Object.Text")), objectId);
		}
		
		//Enter Fpos
		if (!fPos.equals(""))
		{
			sendKeys("Fpos", By.xpath(prop.getProperty("Vclas_tasks.Fpos.Text")), fPos);
		}
		
		//Enter Tpos
		if (!tPos.equals(""))
		{
			sendKeys("Tpos", By.xpath(prop.getProperty("Vclas_tasks.Tpos.Text")), tPos);
		}
		
		//Enter PartNumber
		if (!partNumber.equals(""))
		{
			sendKeys("PartNumber", By.xpath(prop.getProperty("Vclas_tasks.Part.Number.Text")), partNumber);
		}
		
		//Select Radio button
		if (!factory.equals(""))
		{
			selectRadioButtonByValue("Factory", By.xpath(prop.getProperty("Vclas_tasks.Factory.Radio")), factory);
		}
		
		//Select Radio button
		if (!flowType.equals(""))
		{
			selectRadioButtonByValue("Flow_Type", By.xpath(prop.getProperty("Vclas_tasks.Flow.Type.Radio")), flowType);
		}
		
		//Select Radio button
		if (!taskType.equals(""))
		{
			selectRadioButtonByValue("Task_Type", By.xpath(prop.getProperty("Vclas_tasks.Task.Type.Radio")), taskType);
		}
		
		//Enter Created from time
		if (!createdFromDate.equals(""))
		{
			sendKeys("Created_From_Date", By.xpath(prop.getProperty("Vclas_tasks.Created.From.Text")), createdFromDate);
		}
		
		//Enter Created to time
		if (!createdToDate.equals(""))
		{
			sendKeys("Created_To_Date", By.xpath(prop.getProperty("Vclas_tasks.Created.To.Text")),createdToDate);
		}
		
		
		
		//Select the drop down entries
		if (!showEntries.equals(""))
		{
			selectDropDownValue("Show_Entries", By.xpath(prop.getProperty("Vclas_tasks.Entries.Text")), showEntries);
		}
		
		int iTemp = 0;
		do {
			
			anyClick("Search Vclas task - Display Button", By.xpath(prop.getProperty("Vclas_tasks.Display.Button")));
			Thread.sleep(2000);
			if (driver.findElements(By.xpath(prop.getProperty("Vclas_tasks.RCPAS.Table")+"/tbody/tr")).size() > 0)
			{
				reportStep("Vclas_task-Table displayed successfully", "PASS", false);
				break;
			}
			iTemp = iTemp + 1;
		
		} while(!(iTemp==20));
		
		if (iTemp ==20)
		{
			reportStep("Vclas_task-Table displayed successfully", "Fail", true);
		}
		
}
catch(Exception e) {
	e.printStackTrace();
}
		
	}
	
	//Vclas task to retrieve the assigned task
	public void Vclas_getAGVTask(String fID,String sheName) throws InterruptedException, ParseException, IOException {
		
		//Load Test Data Filex
//		tdrow=excelUtils.testCaseRetrieve(refTestDataName,sheetName);
		try {
		
		String tableHeaderColumn = prop.getProperty("Vclas_tasks.RCPAS.Table")+"/tbody/tr[1]";
		int colTask = htmlTableColumnNamePosition("Taskid",tableHeaderColumn);
		int colTaskInformation = htmlTableColumnNamePosition("Task information",tableHeaderColumn);
		int colTaskEvents = htmlTableColumnNamePosition("Task events", tableHeaderColumn);
		String fullTaskId ="", emptyTaskId="", strObjectValue="";
		int iTemp = 0;
		System.out.println("");
		
		reportStep("-------- VCLAS Task creation Verify Initiated --------", "Info", false);
		do { 
			//nullify the Default wait time.
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			if (driver.findElements(By.xpath(prop.getProperty("Vclas_tasks.AGV.Data.Table").replace("&Value", fID))).size() > 0)
			{
				//Reset to Default timeout
				driver.manage().timeouts().implicitlyWait(Default_Wait_4_Page, TimeUnit.SECONDS);
				List<WebElement> tableRows = driver.findElements(By.xpath(prop.getProperty("Vclas_tasks.RCPAS.Table")+"/tbody/tr"));
				int tableSize=driver.findElements(By.xpath(prop.getProperty("Vclas_tasks.RCPAS.Table"))).size();
				System.out.println(tableSize);
				if(tableSize==2) {
					tableIndex ="2";
				}else {
					tableIndex="1";
				}
				
				
				for (int i =2;i<=tableRows.size();i++)
				{
					
					String objectTableData = prop.getProperty("Vclas_tasks.RCPAS.Table")+"["+tableIndex+"]/tbody/tr["+i+"]/td["+(colTask+1)+"]";
					String objectTableTaskInfo = prop.getProperty("Vclas_tasks.RCPAS.Table")+"["+tableIndex+"]/tbody/tr["+i+"]/td["+(colTaskInformation+1)+"]/table/tbody/tr";
					//String objectTableTaskInfo = prop.getProperty("Vclas_tasks.RCPAS.Table")+"/tbody/tr["+i+"]/td["+(TaskEvents+1)+"]/table/tbody/tr";
					
					//To get the task Id from Vclas_task
					String objTaskTable = objectTableData + "/table/tbody/tr[1]/td[1]"; //Take the value of Task ID fullTaskId = driver.findElement(By.xpath(objTaskTable)).getText();
					String objTaskRow = objectTableData + "/table/tbody/tr[1]/td[2]/table/tbody/tr";
					
					List<WebElement> tableTaskRows = driver.findElements(By.xpath(objTaskRow));
					
					for (int k=0; k <= tableTaskRows.size()-1; k++)
					{
						
						if (tableTaskRows.get(k).findElements(By.tagName("td")).get(0).getText().toLowerCase().contains(fID))
						{
							if (tableTaskRows.get(0).findElements(By.tagName("td")).get(0).findElements(By.tagName("strong")).get(0).getText().toLowerCase().contains("full"))
							{
								System.out.println();
								fullTaskId = driver.findElement(By.xpath(objTaskTable)).getText();
								strObjectValue = getObjectId(objectTableTaskInfo, "object");
								reportStep("ObjectId: "+ strObjectValue +" Full task id: " + fullTaskId + " full rack created second successfully", "Info", false);
								break;
							}
							else if(tableTaskRows.get(0).findElements(By.tagName("td")).get(0).findElements(By.tagName("strong")).get(0).getText().toLowerCase().contains("empty"))
							{
								emptyTaskId = driver.findElement(By.xpath(objTaskTable)).getText();
								reportStep("ObjectId: "+ strObjectValue +" Empty task id: " + emptyTaskId + " Empty Rack Created First successfully", "Info", false);
								break;
							}
							else if(k==tableTaskRows.size()-1)
							{
								reportStep("Not task generated for FID: "+fID, "Fail", true);
								reportStep("Empty task id: "+emptyTaskId+" Full task id: "+fullTaskId+" tasks not genereated","Fail",false);
								break;
							}
							
								
						}
					}
					if ((!emptyTaskId.equals("")) && (!fullTaskId.equals(""))) {
						reportStep("Empty task id: "+emptyTaskId+" Full task id: "+fullTaskId+" tasks genereated successfully","Pass",false);
						excelUtils.UpdateValuesToExcel(sheName, "Full_Task_1", sheName, fullTaskId);
						excelUtils.UpdateValuesToExcel(sheName, "Empty_Task_1", sheName, emptyTaskId);
						excelUtils.UpdateValuesToExcel(sheName, "Object", sheName, strObjectValue);
						break;
					}
				}
				if (emptyTaskId.equals("") || fullTaskId.equals(""))
				{
					reportStep("Empty task id: "+emptyTaskId+" Full task id: "+fullTaskId+" tasks not genereated successfully","Fail",true);
					return;
				}
				else
				{
					if (Vclas_VerifyTaskEvents_Ack(emptyTaskId, prop.getProperty("Vclas_tasks.RCPAS.Table"), colTaskEvents) == true)
					{
						reportStep("AGV Object Id.: #B"+ strObjectValue +"#C Empty task id: #B" + emptyTaskId + "#C tasks ACKNOWLEDGED successfully", "Pass", false);
						reportStep("AGV Object Id.: #B"+ strObjectValue +"#C Empty task id: #B" + emptyTaskId + "#C tasks ACKNOWLEDGED successfully", "Info", false);
					}
					else
					{
						reportStep("AGV Object Id.:"+ strObjectValue +" Empty task id: " + emptyTaskId + " tasks ACKNOWLEDGED successfully", "Fail", true);
					}
					
					if (Vclas_VerifyTaskEvents_Ack(fullTaskId, prop.getProperty("Vclas_tasks.RCPAS.Table"), colTaskEvents) == true)
					{
						reportStep("AGV Object Id.: #B"+ strObjectValue +"#C Full task id: #B" + fullTaskId+ "#C tasks ACKNOWLEDGED successfully", "Pass", false);
						reportStep("AGV Object Id.: #B"+ strObjectValue +"#C Full task id: #B" + fullTaskId+ "#C tasks ACKNOWLEDGED successfully", "Info", false);
					}
					else
					{
						reportStep("AGV Object Id.:"+ strObjectValue +" Full task id: " + fullTaskId+ " tasks ACKNOWLEDGED successfully", "Fail", false);
					}
					reportStep("-------- VCLAS Task creation Verify Completed --------", "Info", false);
					
					return;
				}
				
			}
			else
			{
				anyClick("Loop to Search Vclas task Submitted Machine Id- Display Button", By.xpath(prop.getProperty("Vclas_tasks.Display.Button")));
				Thread.sleep(2000);
				iTemp = iTemp + 1;
			}
		} while (!(iTemp == 45));
		
		if (iTemp == 45)
		{
			reportStep("FID: "+fID+" not reflected in the Vclas tasks", "Fail", true);
			reportStep("-------- VCLAS Task creation Verify Failed --------", "Info", false);
		}
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Clear the excel object
//		tdrow.clear();
		
	}
	public String getObjectId(String tableObj, String compValue)	
	{
		String sReturn = "";
		
		List<WebElement> tableTaskRows = driver.findElements(By.xpath(tableObj));
		
		for (int i = 0; i<=tableTaskRows.size()-1;i++)
		{
			//flash(tableTaskRows.get(i).findElements(By.tagName("td")).get(1));
			if (tableTaskRows.get(i).findElements(By.tagName("td")).get(0).getText().toLowerCase().contains(compValue.toLowerCase()))
			{
				
				sReturn = tableTaskRows.get(i).findElements(By.tagName("td")).get(1).getText().trim();
				System.out.println(compValue +" Object Value: "+ sReturn);
				break; 
			}
		}
		
		return sReturn;
	}
	

	public boolean Vclas_VerifyTaskEvents_Ack(String taskId, String tableObj, int colPosition) throws InterruptedException, ParseException {
		boolean bReturn = false;
		int iTemp = 0;
		
		
		anyClick("Clear button", By.xpath(prop.getProperty("Vclas_tasks.Clear.Button")));
		
		Thread.sleep(2000);
		
		sendKeys("Task Id", By.xpath(prop.getProperty("Vclas_tasks.TaskId.Text")),
					taskId);
					
	
		do {
			
			anyClick("Display button", By.xpath(prop.getProperty("Vclas_tasks.Display.Button")));
			
			Thread.sleep(2000);
			
			String objectTableTaskEvents = tableObj + "["+tableIndex+"]/tbody/tr[2]/td[" + (colPosition+1) + "]/table/tbody/tr";
			List<WebElement> tableTaskEvents = driver.findElements(By.xpath(objectTableTaskEvents));
			int taskEventCount = tableTaskEvents.size();
			System.out.println(taskEventCount);
			
			String lastRow = tableTaskEvents.get(taskEventCount-1).findElements(By.tagName("td")).get(1).getText();
			//String lastRow = driver.findElement(By.xpath(objectTableTaskEvents + "[" + taskEventCount + "]/td[2]")).getText();
			
			if (lastRow.equals("ACK")) {
				reportStep("Ack is reflected in the Vclas tasks", "Pass", true);
				bReturn = true; 
				break;
			}
			iTemp = iTemp + 1;
			
		}while(!(iTemp==30));
		
		if(iTemp == 30) {
			reportStep("Acknowledge is not reflected in the Vclas tasks", "Fail", true);
		}
		
		
	
		//Vclas_TasksEvents_Timeverify(taskId,tableObj,colPosition);
		//newTime_Difference(taskId,tableObj,colPosition);
		Vclas_TasksEvents_Timeverify(taskId, tableObj, colPosition);
		
		anyClick("Clear button", By.xpath(prop.getProperty("Vclas_tasks.Clear.Button")));
			
		return bReturn;
	}
	
	public boolean Vclas_TasksEvents_Timeverify(String taskId, String tableObj, int colPosition) {
		boolean bReturn = false;
		
		String NewTime="",SentTime="",AckTime="",tasksEvents;
		int NewSentDiff,SentAckDiff;
		
		String objectTableTaskEvents = tableObj + "["+tableIndex+"]/tbody/tr[2]/td[" + (colPosition+1) + "]/table/tbody/tr";
		List<WebElement> tableTaskEvents = driver.findElements(By.xpath(objectTableTaskEvents));
		int taskEventCount = tableTaskEvents.size();
		System.out.println(taskEventCount);
		
		for(int i=0;i<=taskEventCount-1;i++) {
			 tasksEvents=tableTaskEvents.get(i).findElements(By.tagName("td")).get(1).getText();
			System.out.println(tasksEvents);
			if(tasksEvents.equalsIgnoreCase("New")) {
				 //NewTime=tableTaskEvents.get(i).findElements(By.tagName("td")).get(0).getText().substring(10,14);
				 NewTime=tableTaskEvents.get(i).findElements(By.tagName("td")).get(0).getText();
				System.out.println(NewTime);
			}else if(tasksEvents.equalsIgnoreCase("Sent")){
				//SentTime=tableTaskEvents.get(i).findElements(By.tagName("td")).get(0).getText().substring(10, 14);
				SentTime=tableTaskEvents.get(i).findElements(By.tagName("td")).get(0).getText();
				System.out.println(SentTime);
			}else if(tasksEvents.equalsIgnoreCase("Ack")) {
				//AckTime=tableTaskEvents.get(i).findElements(By.tagName("td")).get(0).getText().substring(10,14);
				AckTime=tableTaskEvents.get(i).findElements(By.tagName("td")).get(0).getText();
				System.out.println(AckTime);
			}
		}
		
		//getDateDiff(SentTime,AckTime);
		NewSentDiff=Integer.parseInt(SentTime.substring(10, 14)) - Integer.parseInt(NewTime.substring(10, 14));
		SentAckDiff=Integer.parseInt(AckTime.substring(10, 14)) - Integer.parseInt(SentTime.substring(10, 14));
		
		System.out.println("newsent diff:" +NewSentDiff+ " sent ack diff"+SentAckDiff);
		
		
		
		if(SentAckDiff<60) {
			reportStep("Time difference between Sent and Acknowledgement: #B "+SentAckDiff+"#C Seconds", "pass", true);
		}else {
			reportStep("Time difference between Sent and Acknowledgement is more than 60 seconds: #B "+SentAckDiff+"#C Seconds", "fail", true);
		}
		
		
		return bReturn;
	}
	
	
public boolean Agv_Vclas_VerifyTaskEvents_Inactive( String No_of_Racks, String Status,String Full_Task,String Empty_Task,String FlowType) throws InterruptedException {
		
		
		//driver.manage().timeouts().implicitlyWait(Default_Wait_4_Page, TimeUnit.SECONDS);  
	
		// Load Test Data
		
//		tdrow = excelUtils.testCaseRetrieve(refTestDataName, sheetName);
		boolean bReturn = false;
		//tableIndex="1";
		String tableHeaderColumn = prop.getProperty("Vclas_tasks.RCPAS.Table") + "["+tableIndex+"]/tbody/tr[1]";
		int colTaskStatusInfo = htmlTableColumnNamePosition("Task status information", tableHeaderColumn);
		int colTask = htmlTableColumnNamePosition("Taskid", tableHeaderColumn);
		int rackCount = Integer.parseInt(No_of_Racks);
		
		
		// Select Radio button
		if (!FlowType.equals("")) {
			selectRadioButtonByValue("Flow_Type", By.xpath(prop.getProperty("Vclas_tasks.Flow.Type.Radio")),
					FlowType);
		}

		// Select Radio button
		selectRadioButtonByValue("Task_Type", By.xpath(prop.getProperty("Vclas_tasks.Task.Type.Radio")),
					"inaktiva");
		
		
		
		//tdrow = excelUtils.testCaseRetrieve(refTestDataName, "Vclas_Assignments");
		
		for (int i = 1; i <= rackCount; i++) {
			
			
			Vclas_VerifyTaskEvents_Status(Full_Task, colTask);
			
			if(!Status.equalsIgnoreCase("")) {
				Vclas_VerifyTaskInformation_Completed(Full_Task , prop.getProperty("Vclas_tasks.RCPAS.Table"), colTaskStatusInfo,Status);	
			}
			
			Vclas_VerifyTaskEvents_Status(Empty_Task , colTask);
			
			if(!Status.equalsIgnoreCase("")) {
				Vclas_VerifyTaskInformation_Completed(Empty_Task , prop.getProperty("Vclas_tasks.RCPAS.Table"), colTaskStatusInfo,Status);
			}
		}

		return bReturn;
	}	

public boolean Vclas_VerifyTaskEvents_Status(String taskId, int colPosition) throws InterruptedException {
	boolean bReturn = false;
	
	reportStep("----VCLAS Task Inactive status verify - TaskID: "+ taskId +"----", "Info", false);
	
	anyClick("Clear button", By.xpath(prop.getProperty("Vclas_tasks.Clear.Button")));
	
	Thread.sleep(2000);
	
	sendKeys("Task Id", By.xpath(prop.getProperty("Vclas_tasks.TaskId.Text")),
				taskId);
				
	anyClick("Display button", By.xpath(prop.getProperty("Vclas_tasks.Display.Button")));
	
	// Verify the table is displayed
	verifyElementExist("Table Search Data",
			By.xpath(prop.getProperty("Vclas_tasks.RCPAS.Table") + "["+tableIndex+"]/tbody/tr[2]"));
	
	String fetchTaskId = driver
			.findElement(By.xpath(
					prop.getProperty("Vclas_tasks.RCPAS.Table") + "["+tableIndex+"]/tbody/tr[2]/td[1]/table/tbody/tr[1]/td[1]"))
			.getText();
	System.out.println(fetchTaskId);

	if (fetchTaskId.equals(taskId)) {
		reportStep("Task Id: " + fetchTaskId + " is verified as Inactive successfully", "Pass", true);

		String objectTableData = prop.getProperty("Vclas_tasks.RCPAS.Table") + "["+tableIndex+"]/tbody/tr[2]/td["
				+ (colPosition + 1) + "]";
		String objTaskRow = objectTableData + "/table/tbody/tr[1]/td[2]/table/tbody/tr";
		List<WebElement> tableTaskRows = driver.findElements(By.xpath(objTaskRow));

		String fullRack = tableTaskRows.get(0).findElements(By.tagName("td")).get(0)
				.findElements(By.tagName("strong")).get(0).getText().substring(0, 5);
		if (fullRack.toLowerCase().contains("racks")) {
			reportStep("Task ID " + fetchTaskId + " is Full Rack" + fullRack + " verified successfully", "Pass",
					false);
		} else if (tableTaskRows.get(0).findElements(By.tagName("td")).get(0).findElements(By.tagName("strong"))
				.get(0).getText().toLowerCase().contains("tomracks")) {
			reportStep("Task ID " + fetchTaskId + " is empty Rack verified successfully", "Pass", true);
		}
	} else {
		reportStep("Task Id: " + fetchTaskId + " is not present in the table as inactive", "fail", false);
	}
		
	return bReturn;
}


public boolean Vclas_VerifyTaskInformation_Completed(String taskId, String tableObj, int colPosition,String Status) throws InterruptedException {
	boolean bReturn = false;
	int iTemp = 0;
	String strongValue="";
	
	
	String objectTableTaskInformation = tableObj + "["+tableIndex+"]/tbody/tr[2]/td[" + (colPosition+1) + "]/table/tbody/tr";
		
	List<WebElement> tableTaskInformation=driver.findElements(By.xpath(objectTableTaskInformation));
	
		
	int taskInfoCount = tableTaskInformation.size();
	System.out.println(taskInfoCount);
	
	for(WebElement taskCount:tableTaskInformation) {
		
		List<WebElement> strong= taskCount.findElements(By.tagName("strong"));
		
		int taskStrongCount=strong.size();
		
		for(WebElement strongCount:strong) {
			
			 strongValue=strongCount.getText();
		
			if(strongValue.equalsIgnoreCase(Status)) {
				iTemp = 1;
				System.out.println("Value Present "+strongValue);
				reportStep("Status:" + strongValue+ "is verified successfully in vclas tasks", "pass", true);
				
				break;
				
			}
		
		}
		if (iTemp == 1)
		{
			break;
		}
	}
	if (iTemp ==0)
	{
		reportStep("Status:" +strongValue+"is not present in vclas tasks", "fail", true);
	}
	return bReturn;
}

	// Inactive Machine Task creation verification method

	//Vclas task to retrieve the assigned task
		public void Vclas_getAGVTask_Inactive_MID(String FID) throws InterruptedException {

			// Load Test Data Filex
			//tdrow = excelUtils.testCaseRetrieve(refTestDataName, sheetName);

			String tableHeaderColumn = prop.getProperty("Vclas_tasks.RCPAS.Table") + "/tbody/tr[1]";
			int colTask = htmlTableColumnNamePosition("Taskid", tableHeaderColumn);
			int colTaskInformation = htmlTableColumnNamePosition("Task information", tableHeaderColumn);
			int colTaskEvents = htmlTableColumnNamePosition("Task events", tableHeaderColumn);
			String fullTaskId = "", emptyTaskId = "", strObjectValue = "";
			
			System.out.println("");

			reportStep("-------- VCLAS Task creation Verify Initiated --------", "Info", false);
		
				// nullify the Default wait time.
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				if (driver
						.findElements(By
								.xpath(prop.getProperty("Vclas_tasks.AGV.Data.Table").replace("&Value", FID)))
						.size() <= 0) {
					reportStep("AGV orders are  not created for Inactive MID -- verified TestCase ID:2882", "pass", true);
					reportStep("Verification of possibility of creating  AGV orders for Inactive Machine ID #B TestCase ID:2882 #C","info", false);
					
					
				} else {
					reportStep("AGV order creation  for Inactive MID verification failed--TestCase ID:2882 ", "fail", true);
				}
			

			// Clear the excel object
		//	tdrow.clear();

		}




}
