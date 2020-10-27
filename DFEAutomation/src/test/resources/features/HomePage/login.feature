Feature: Login feature

  @login
  Scenario: verify login
    Given user is in login screen
    When user enters credentails
    Then user clicks login