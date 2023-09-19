package stepDefinition

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

import ch.qos.logback.core.joran.conditional.ElseAction
import internal.GlobalVariable
import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import cucumber.api.java.en.Then
import groovy.json.StringEscapeUtils

public class Buyer {

	@Given("user (.*) and view the Homepage")
	public void user_and_view_the_homepage(String condition) {

		if(condition=="has not login") {
			WebUI.openBrowser('https://secondhand.binaracademy.org/')
			WebUI.maximizeWindow()
		}else if(condition=="has login but has not completed user profile") {
			WebUI.openBrowser('https://secondhand.binaracademy.org/users/sign_in')
			WebUI.maximizeWindow()
			WebUI.setText(findTestObject('Object Repository/Login/input-email'), 'riri2@gmail.com')
			WebUI.setText(findTestObject('Object Repository/Login/input-password'), '123456')
			WebUI.click(findTestObject('Object Repository/Login/btn-masuk'))
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
		}else if(condition=="has login and completed user profile") {
			WebUI.openBrowser('https://secondhand.binaracademy.org/users/sign_in')
			WebUI.maximizeWindow()
			WebUI.setText(findTestObject('Object Repository/Login/input-email'), 'ariska.bio@gmail.com')
			WebUI.setText(findTestObject('Object Repository/Login/input-password'), 'Ariskadewi03')
			WebUI.click(findTestObject('Object Repository/Login/btn-masuk'))
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
		}
	}

	@When("user view the product section")
	public void user_view_the_product_section() {
		WebUI.scrollToElement(findTestObject('Object Repository/Homepage/product-section'), 0)
	}

	@When("user select one of the product")
	public void user_select_one_of_the_product() {
		WebUI.click(findTestObject('Object Repository/Homepage/select-product'))
	}

	@When("user click Saya Tertarik dan Ingin Nego button")
	public void user_click_saya_tertarik_dan_ingin_nego_button() {
		WebUI.click(findTestObject('Object Repository/Product-Detail/btn-offer'))
	}

	@When("user input Harga Tawar field")
	public void user_input_Harga_Tawar_field() {
		WebUI.clearText(findTestObject('Object Repository/Product-Detail/input-offer-price'))
		WebUI.setText(findTestObject('Object Repository/Product-Detail/input-offer-price'), '7000')
	}

	@When("user click Kirim button")
	public void user_click_kirim_button() {
		WebUI.click(findTestObject('Object Repository/Product-Detail/btn-send'))
	}

	@Then("user will be (.*)")
	public void user_will_be(String result) {

		if(result=="redirected to Login page") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/Login/input-email'), 0)
		}else if(result=="redirected to User Profile page") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/input-nama-profile'), 0)
		}else if(result=="successfully bargain") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/Product-Detail/offer-submitted'), 0)
			WebUI.closeBrowser()
		}
	}
}