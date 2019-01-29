package com.volvo.mfg.pagecomponents;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.volvo.mfg.commonutilis.CommonWrapperMethods;

public class LoginPages extends CommonWrapperMethods {
	private WebDriver driver;
//	 RemoteWebDriver driver;
	
	public LoginPages(WebDriver driver) {
		this.driver = driver;
	}
	
	public void LogintoMasweb(String uName,String pWord,String eValue) throws InterruptedException {
		/*if (browser[1] != null)
		{
			driver.switchTo().window(browser[1]);
			return;
		}*/
		urlMasweb=prop.getProperty(Environment + ".URL.MASWEB");
		// Launching the Application URL
		launchUrl(urlMasweb, "");
		
		/*if (driver.findElements(By.xpath(prop.getProperty("Masweb_Home.Login.UserName").replace("&Value",
				uName))).size() <= 0) {
			driver.manage().timeouts().implicitlyWait(Default_Wait_4_Page, TimeUnit.SECONDS);
			*/
		
			

		/*	if (Environment.equalsIgnoreCase("Test") && Browser.equalsIgnoreCase("IE")) {
				//anyClick("masweb link", By.xpath(prop.getProperty("Loginpage.User.Masweb.Link")));
				anyClick("Continue to this link", By.xpath(prop.getProperty("Loginpage.User.Recommended.Link")));
			}*/

			/*String usrName = prop.getProperty(tdrow.get("User_Details") + ".User.Name");
			String pwd = prop.getProperty(tdrow.get("User_Details") + ".User.Password");*/

			if (verifyAuthenticationPopup(uName, decryptPassword(pWord)) == true) {
				// Entering the credentials and click submit
				sendKeys("UserName", By.xpath(prop.getProperty("LoginPage.User.Name")), uName);
				sendKeysPassword("Password", By.xpath(prop.getProperty("LoginPage.User.Password")), pWord);
				anyClick("Submit", By.xpath(prop.getProperty("LoginPage.Login.Button")));
				reportStep("01.Verify the functionality of logging into Masweb application", "pass", false);
//			}

			// Waiting to page load
			Thread.sleep(2000);

			// Verify Page displayed
			verifyPageTitle(eValue);
		} else {
			reportStep(uName + " User already logged in", "Info",
					false);
		}

		// Clearing the Memory
//		tdrow.clear();

	}

	public void LogintoVclas(String uName,String pWord) throws InterruptedException {
		// call excel util and get user creds
		// Load Test Data File
//		tdrow = excelUtils.testCaseRetrieve(testname, sheetName);
		
		/*if (browser[2] != null)
		{
			driver.switchTo().window(browser[2]);
			return;
		}*/

		// Launching the Application URL
		 urlVclas = prop.getProperty(Environment + ".URL.Vclas");
		launchUrl(urlVclas, "");
		
		driver.manage().timeouts().implicitlyWait(Default_Wait_4_Page, TimeUnit.SECONDS);
		if (driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Home.Label"))).size() <= 0) {
			driver.manage().timeouts().implicitlyWait(Default_Wait_4_Page, TimeUnit.SECONDS);
			
			/*String usrName = prop.getProperty(tdrow.get("User_Details") + ".User.Name");
			String pwd = prop.getProperty(tdrow.get("User_Details") + ".User.Password");*/

			// Entering the credentials and click submit
			sendKeys("UserName", By.xpath(prop.getProperty("LoginPage.User.Name")), uName);
			sendKeysPassword("Password", By.xpath(prop.getProperty("LoginPage.User.Password")), pWord);
			anyClick("Submit", By.xpath(prop.getProperty("LoginPage.Login.Button")));
			

//			Thread.sleep(2000);

			// Verify Page displayed
			verifyElementExist("Vclas page displayed", By.xpath(prop.getProperty("Vclas_Home.Home.Label")));
			reportStep("01.Verify the functionality of logging into VCLAS application", "pass", false);

		} else {
			reportStep("User already logged in", "Info", false);
		}
		 if(driver.findElements(By.xpath(prop.getProperty("Vclas_Home.Change.Language.Click"))).size() > 0) {

             driver.manage().timeouts().implicitlyWait(Default_Wait_4_Page, TimeUnit.SECONDS);
             anyClick("Clicking the Language Button", By.xpath(prop.getProperty("Vclas_Home.Change.Language.Click")));

             anyClick("Selecting the DropDown", By.xpath(prop.getProperty("Vclas_Home.Language.Click")));

             selectDropDownByIndex("Language Select", By.xpath(prop.getProperty("Vclas_Home.Select.Language.DropDown")),
                                     "English");
             }else {
            	 reportStep(" language was already changed", "Info", false);
             }



	}

	}
