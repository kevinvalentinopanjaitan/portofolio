package stepDefinition

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.nio.file.attribute.UserDefinedFileAttributeView
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
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import cucumber.api.java.en.And

public class Register {
	@Given("user is in Register page")
	public void user_is_in_register_page() {
		WebUI.openBrowser('https://secondhand.binaracademy.org/users/sign_up')
		WebUI.maximizeWindow()
		WebUI.verifyElementPresent(findTestObject('Object Repository/Register/input-name'), 0)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Register/input-email'), 0)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Register/input-password'), 0)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Register/btn-Daftar'), 0)
	}

	@When ("user input (.*) in Register page")
	public void user_input_in_register_page(String condition) {
		if(condition=="valid name, valid email, and valid password") {
			WebUI.setText(findTestObject('Object Repository/Register/input-name'), 'Richardo')
			int RandomNumber;
			RandomNumber = (int)(Math.random()*1000)
			WebUI.setText(findTestObject('Object Repository/Register/input-email'), 'Richard'+RandomNumber+'@gmail.com')
			WebUI.setText(findTestObject('Object Repository/Register/input-password'), '12345678')
		}else if(condition=="empty credentials") {
		}else if(condition=="valid name") {
			WebUI.setText(findTestObject('Object Repository/Register/input-name'), 'Richardo')
		}else if(condition=="valid name and valid email") {
			WebUI.setText(findTestObject('Object Repository/Register/input-name'), 'Richardo')
			WebUI.setText(findTestObject('Object Repository/Register/input-email'), 'richardo.gabriel39@gmail.com')
		}else if(condition=="valid name, existing email, and valid password") {
			WebUI.setText(findTestObject('Object Repository/Register/input-name'), 'Richardo')
			WebUI.setText(findTestObject('Object Repository/Register/input-email'), 'richardo.gabriel35@gmail.com')
			WebUI.setText(findTestObject('Object Repository/Register/input-password'), '12345678')
		}
	}

	@When ("user click Register button")
	public void user_click_Register_button() {
		WebUI.click(findTestObject('Object Repository/Register/btn-daftar'))
	}

	@Then ("user (.*) register")
	public void user_register(String result) {
		if(result=="successfully") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
		}else if(result=="can not") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/Register/btn-daftar'), 0)
		}
	}
}
