Feature: Add Product
 
	Scenario Outline: User open the Add Product page from Homepage
    Given user is in Login page
    When user input valid credentials in Login page
    And user click Login button
    And user can succesfully login
		And user is in Homepage
		And user click on the Jual button
		And user view the display of Add Product page
		And user fill all the required fields with <condition>
		And user click the Terbitkan button
		Then <result> displayed
		
		Examples:
		|	case_id	|	condition																										|	result																												|
		|	A01			|	valid format																								| user successfully add product and redirected to Product page	|
		| 	A02			|	photo file size more than 10Mb															| request entity too large																			|
		|	A03			|	except Product Name field blank															|	product Name field cant be blank															|
		|	A04			|	except Category field blank																	|	category field cant be blank																	|
		|	A05			| except Description field blank															|	description field cant be blank																|