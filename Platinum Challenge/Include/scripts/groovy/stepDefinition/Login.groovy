package stepDefinition

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.security.PublicKey

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import ch.qos.logback.core.joran.conditional.ElseAction
import internal.GlobalVariable
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When

public class Login {

	//Verify if the Login form is displayed properly
	@Given("user is in Login page")
	public void user_is_in_login_page() {
		WebUI.openBrowser('https://secondhand.binaracademy.org/users/sign_in')
		WebUI.maximizeWindow()
		WebUI.verifyElementPresent(findTestObject('Object Repository/Login/input-email'), 0)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Login/input-password'), 0)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Login/btn-masuk'), 0)
	}

	//Fill the Email and Password fields in the Login form with the defined conditions
	@When("user input (.*) in Login page")
	public void user_input_in_login_page(String condition) {
		if(condition=="valid credentials") {
			WebUI.setText(findTestObject('Object Repository/Login/input-email'), 'dwnina@gmail.com')
			WebUI.setText(findTestObject('Object Repository/Login/input-password'), 'BinarAcademy')
		}else if(condition=="valid email and invalid password"){
			WebUI.setText(findTestObject('Object Repository/Login/input-email'), 'dwnina@gmail.com')
			WebUI.setText(findTestObject('Object Repository/Login/input-password'), '111111')
		}else if(condition=="invalid email and valid password") {
			WebUI.setText(findTestObject('Object Repository/Login/input-email'), 'blabla@gmail.com')
			WebUI.setText(findTestObject('Object Repository/Login/input-password'), 'BinarAcademy')
		}else if(condition=="empty email") {
			WebUI.setText(findTestObject('Object Repository/Login/input-password'), 'BinarAcademy')
		}else if(condition=="incorrect email format"){
			WebUI.setText(findTestObject('Object Repository/Login/input-email'), 'dwnina')
			WebUI.setText(findTestObject('Object Repository/Login/input-password'), 'BinarAcademy')
		}else if(condition=="empty password") {
			WebUI.setText(findTestObject('Object Repository/Login/input-email'), 'dwnina@gmail.com')
		}
	}

	//Click the "Masuk" button after filling the Email and Password fields
	@When("user click Login button")
	public void user_click_login_button() {
		WebUI.click(findTestObject('Object Repository/Login/btn-masuk'))
	}

	//Verify the result of each test cases conditions
	@Then("user (.*) login")
	public void user_login(String result) {
		if(result=="successfully") {
			WebUI.waitForElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
		}else if(result=="failed") {
			WebUI.waitForElementPresent(findTestObject('Object Repository/Login/warning-mesg-login'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/Login/warning-mesg-login'), 0)
		}else if(result=="see email warning and failed") {
			WebUI.getAttribute(findTestObject('Object Repository/Login/input-email'), 'validationMessage')
		}else if(result=="see password warning and failed") {
			WebUI.getAttribute(findTestObject('Object Repository/Login/input-password'), 'validationMessage')
			WebUI.closeBrowser();
		}
	}
}

