@Login
Feature: Login

  Scenario Outline: User fills email and password in login page
    Given user is in Login page
    When user input <condition> in Login page
    And user click Login button
    Then user <result> login
    Examples: 
      | case_id | condition                        | result 													|
      | WLOG1		| valid credentials 						 	 | successfully  										|
      | WLOG2   | valid email and invalid password | failed     											|
      | WLOG3   | invalid email and valid password | failed 													|
      | WLOG4   | empty email              	 			 | see email warning and failed	 		| 
      | WLOG5   | incorrect email format           | see email warning and failed			|
      | WLOG6   | empty password                   | see password warning and failed	|
      
