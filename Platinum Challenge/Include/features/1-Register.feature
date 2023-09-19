Feature: Register

  Scenario Outline: User Registration
    Given user is in Register page
    When user input <condition> in Register page
    And user click Register button
    Then user <result> register

    Examples: 
      | case_id | condition                                      | result       |
      | WREG001 | valid name, valid email, and valid password    | successfully |
      | WREG002 | empty credentials                              | can not      |
      | WREG003 | valid name                                     | can not      |
      | WREG004 | valid name and valid email                     | can not      |
      | WREG005 | valid name, existing email, and valid password | can not      |
