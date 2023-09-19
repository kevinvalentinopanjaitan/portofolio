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

import internal.GlobalVariable
import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import cucumber.api.java.en.Then
import org.openqa.selenium.Keys as Keys

public class Search_Product {

	@Given("user is in the Homepage")
	public void user_is_in_the_homepage() {
		WebUI.openBrowser('https://secondhand.binaracademy.org/')
		WebUI.maximizeWindow()
		WebUI.verifyElementPresent(findTestObject('Object Repository/Homepage/category-section'), 0)
	}

	@When("user view the category section")
	public void user_view_the_category_section() {
		WebUI.scrollToPosition(619, 268)
		
	}

	@When("user click one of the Category button")
	public void user_click_one_of_the_category_button() {
		WebUI.click(findTestObject('Object Repository/Homepage/select-category'))
	}

	@Then("page will be listed products based on selected category")
	public void page_will_be_listed_products_based_on_selected_category() {
		WebUI.scrollToElement(findTestObject('Object Repository/Homepage/product-section'), 0)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Homepage/result-search-category'), 0)
	}

	@When("user click on Search field")
	public void user_click_on_search_field() {
		WebUI.click(findTestObject('Object Repository/Homepage/input-search'))
	}

	@When("user input product name")
	public void user_input_product_name() {
		WebUI.setText(findTestObject('Object Repository/Homepage/input-search'), 'Album Musik')
	}

	@When("user press Enter")
	public void user_press_enter() {
		WebUI.sendKeys(findTestObject('Object Repository/Homepage/input-search'), Keys.chord(Keys.ENTER))
	}

	@Then("page will be listed products based on keyword")
	public void page_will_be_listed_products_based_on_keyword() {
		WebUI.scrollToElement(findTestObject('Object Repository/Homepage/product-section'), 0)
		WebUI.verifyElementPresent(findTestObject('Object Repository/Homepage/result-search-product'), 0)
		WebUI.closeBrowser()
	}
}