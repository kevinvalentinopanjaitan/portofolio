Feature: Search Product

  Scenario: WBUY1-User view products based on category
    Given user is in the Homepage
    When user view the category section
    And user click one of the Category button
    Then page will be listed products based on selected category

  Scenario: WBUY2-User searches for a product
    Given user is in the Homepage
    When user click on Search field
    And user input product name
    And user press Enter
    Then page will be listed products based on keyword