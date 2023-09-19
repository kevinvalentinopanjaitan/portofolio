Feature: Edit or update profile feature

  Background: User is in login page and input valid email
    Given user is in Login page
    When user input valid credentials in Login page
    And user click Login button
    Then user can succesfully login

  Scenario Outline: User edit profile on edit profile page
    When user click button profile
    And user click profile account
    And user input <field> with <condition> and user click button simpan
    Then user <result> edit profile

    Examples: 
      | case_id | field     | condition    | result                     |
      | WPRO1   | all field | valid value  | success and back home page |
      | WPRO2   | nama      | empty value  | can not update profile     |
      | WPRO3   | alamat    | blank value  | can not update profile     |
      | WPRO4   | telpon    | kosong value | can not update profile     |
      | WPRO5   | kota      | null value   | can not update profile     |
