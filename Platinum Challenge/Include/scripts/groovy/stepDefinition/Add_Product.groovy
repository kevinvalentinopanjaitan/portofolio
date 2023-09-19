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

import groovy.sql.ResultSetMetaDataWrapper
import groovy.swing.impl.DefaultAction
import internal.GlobalVariable
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import stepDefinition.Login

public class Add_Product {
	@When("user is in Homepage")
	public void user_is_in_Homepage() {
		WebUI.verifyElementPresent(findTestObject('Object Repository/User-Profile/icon-profile'), 0)
	}
	@When("user click on the Jual button")
	public void user_click_on_the_Jual_button() {
		WebUI.click(findTestObject('Object Repository/Add-Product/btn-jual'))
	}
	@When("user view the display of Add Product page")
	public void user_view_the_display_of_Add_Product_page() {
		//Wait element button publish appear to validate that user is in Add Product Page
		WebUI.waitForElementPresent(findTestObject('Object Repository/Add-Product/btn-publish'), 0)
	}
	@When("user fill all the required fields with (.*)")
	public void user_fill_all_the_required_fields_with(String condition) {
		if(condition=="valid format") {
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-name'), 'Pertama')
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-price'), '1')
			WebUI.selectOptionByIndex(findTestObject('Add-Product/input-category'), 2)
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-description'), 'Kalimat Pertama')
			WebUI.uploadFile(findTestObject('Object Repository/Add-Product/input-photos'), 'D:\\Patrick2.jpg')
		}else if(condition=="photo file size more than 10Mb") {
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-name'), 'Kedua')
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-price'), '2')
			WebUI.selectOptionByIndex(findTestObject('Add-Product/input-category'), 2)
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-description'), 'Kalimat Kedua')
			WebUI.uploadFile(findTestObject('Object Repository/Add-Product/input-photos'), 'D:\\venom.jpg')
		}else if(condition=="except Product Name field blank") {
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-name'), '')
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-price'), '3')
			WebUI.selectOptionByIndex(findTestObject('Add-Product/input-category'), 2)
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-description'), 'Kalimat Ketiga')
			WebUI.uploadFile(findTestObject('Object Repository/Add-Product/input-photos'), 'D:\\Patrick2.jpg')
		}else if(condition=="except Category field blank") {
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-name'), 'Keempat')
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-price'), '4')
			WebUI.selectOptionByIndex(findTestObject('Add-Product/input-category'), 0)
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-description'), 'Kalimat Keempat')
			WebUI.uploadFile(findTestObject('Object Repository/Add-Product/input-photos'), 'D:\\Patrick2.jpg')
		}else if(condition=="except Description field blank") {
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-name'), 'Kelima')
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-product-price'), '5')
			WebUI.selectOptionByIndex(findTestObject('Add-Product/input-category'), 2)
			WebUI.setText(findTestObject('Object Repository/Add-Product/input-description'), '')
			WebUI.uploadFile(findTestObject('Object Repository/Add-Product/input-photos'), 'D:\\Patrick2.jpg')
		}
	}
	@When("user click the Terbitkan button")
	public void user_click_the_Terbitkan_button() {
		WebUI.click(findTestObject('Object Repository/Add-Product/btn-publish'))
	}
	@Then("(.*) displayed")
	public void displayed(String result) {
		if(result=="user successfully add product and redirected to Product page") {
			WebUI.verifyElementPresent(findTestObject('Object Repository/Add-Product/btn-delete'), 5)
		}else if(result=="request entity too large") {
			WebUI.waitForElementPresent(findTestObject('Object Repository/Add-Product/413-request-entity-too-large'), 5)
			WebUI.getAttribute(findTestObject('Object Repository/Add-Product/413-request-entity-too-large'), '413 Request Entity Too Large')
		}else if(result=="product Name field cant be blank") {
			WebUI.waitForElementPresent(findTestObject('Object Repository/Add-Product/input-product-name-cant-blank'), 5)
			WebUI.getAttribute(findTestObject('Object Repository/Add-Product/input-product-name-cant-blank'), 'Name cant be blank')
		}else if(result=="category field cant be blank") {
			WebUI.waitForElementPresent(findTestObject('Object Repository/Add-Product/input-category-cant-blank'), 5)
			WebUI.getAttribute(findTestObject('Object Repository/Add-Product/input-category-cant-blank'), 'Category must exist')
		}else if(result=="description field cant be blank") {
			WebUI.getAttribute(findTestObject('Object Repository/Add-Product/input-description-cant-blank'), 'Description cant be blank')
			
		}
		WebUI.closeBrowser()
	}
}