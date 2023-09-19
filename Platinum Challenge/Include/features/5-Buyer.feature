Feature: Bargain Product

  Scenario Outline: User bargain a product
    Given user <condition> and view the Homepage
    When user view the product section
    And user select one of the product
    And user click Saya Tertarik dan Ingin Nego button
    And user input Harga Tawar field
    And user click Kirim button
    Then user will be <result>

    Examples: 
      | case_id | condition                                    | result                          |
      | WBUY3   | has not login                                | redirected to Login page        |
      | WBUY4   | has login but has not completed user profile | redirected to User Profile page |
      | WBUY5   | has login and completed user profile         | successfully bargain            |