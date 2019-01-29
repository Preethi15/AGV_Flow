package com.volvo.mfg.pagecomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;

public class AGV_MachineIDScanPage extends CommonWrapperMethods {
	
	private WebDriver driver;

	
	public AGV_MachineIDScanPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void MachineID_Scan_Submit(String FID,String cColor) {

		boolean temp;
		// retrieve data from MasWeb_Page
		// Load Test Data File
		

		// Send the value
		sendKeys("Machine ID field", By.xpath(prop.getProperty("AGV_MachineID.Scan.Text")), FID);

		// Click the Page
		anyClick("Machine ID Submit", By.xpath(prop.getProperty("AGV_MachineID.PushButton.Form")));

		// Verify the Page Color is displayed
		temp = verifyStringCompare("Machine scan Page Color", cColor.replace(" ", ""),
				driver.findElement(By.className(prop.getProperty("AGV_MachineID.PageColor.Div"))).getAttribute("style").replace(" ", ""));  
		System.out.println("Colour verified: "+temp);
		if (temp == false) {
			throw new RuntimeException("Failed - Machine ID Submission");
		}

		
		
		
	

}
}