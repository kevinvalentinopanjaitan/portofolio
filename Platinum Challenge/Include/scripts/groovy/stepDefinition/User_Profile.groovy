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
import groovy.json.StringEscapeUtils
import groovy.transform.ConditionalInterrupt

public class Editorupdateprofilefeature {

	@When("user click button profile")
	public void user_click_button_profile() {
		WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
		WebUI.click(findTestObject('Object Repository/User-Profile/icon-profile'))
	}

	@When("user click profile account")
	public void user_click_profile_account() {
		WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/nama-profile'), 0)
		WebUI.click(findTestObject('Object Repository/User-Profile/nama-profile'))
	}

	@When("user input (.*) with (.*) and user click button simpan")
	public void user_input_with_and_user_click_button_simpan(String field, condition) {
		if(field=="all field" && condition=="valid value") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-alamat'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-nama-profile'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-ponsel'), 0)
			WebUI.uploadFile(findTestObject('Object Repository/User-Profile/image'), 'C:\\Users\\AJI_10\\Downloads\\Flayer.jpeg')
			WebUI.setText(findTestObject('Object Repository/User-Profile/input-nama-profile'), 'bisa yuk')
			WebUI.selectOptionByIndex(findTestObject('Object Repository/User-Profile/select-kota'), '3')
			WebUI.setText(findTestObject('Object Repository/User-Profile/input-alamat'), 'Jakarta')
			WebUI.setText(findTestObject('Object Repository/User-Profile/input-ponsel'), '000123344')
			WebUI.click(findTestObject('Object Repository/User-Profile/btn-simpan'))
		}
		else if (field=="nama" && condition=="empty value") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-nama-profile'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-alamat'), 0)
			WebUI.clearText(findTestObject('Object Repository/User-Profile/input-nama-profile'))
			WebUI.click(findTestObject('Object Repository/User-Profile/btn-simpan'))
		}
		else if (field=="alamat" && condition=="blank value") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-alamat'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-nama-profile'), 0)
			WebUI.clearText(findTestObject('Object Repository/User-Profile/input-alamat'))
			WebUI.click(findTestObject('Object Repository/User-Profile/btn-simpan'))
		}
		else if (field=="telpon" && condition=="kosong value") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-alamat'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-nama-profile'), 0)
			WebUI.clearText(findTestObject('Object Repository/User-Profile/input-ponsel'))
			WebUI.click(findTestObject('Object Repository/User-Profile/btn-simpan'))
		}
		else if (field=="kota" && condition=="null value") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-alamat'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-nama-profile'), 0)
			WebUI.selectOptionByIndex(findTestObject('Object Repository/User-Profile/select-kota'), 0)
			WebUI.click(findTestObject('Object Repository/User-Profile/btn-simpan'))
		}
	}
	@Then("user (.*) edit profile")
	public void user_edit_profile(String result) {
		if(result=="success and back home page") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
			WebUI.closeBrowser()
		}
		else if(result=="can not update profile") {
			WebUI.getAttribute(findTestObject('Object Repository/User-Profile/input-nama-profile'),'Please fill in this field')
			WebUI.getAttribute(findTestObject('Object Repository/User-Profile/input-alamat'),'Please fill in this field')
			WebUI.getAttribute(findTestObject('Object Repository/User-Profile/input-ponsel'),'Please fill in this field')
			WebUI.getAttribute(findTestObject('Object Repository/User-Profile/select-kota'),'Please select an item in the list')
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-nama-profile'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-alamat'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-ponsel'), 0)
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/select-kota'), 0)
			WebUI.closeBrowser()
		}
	}
}

