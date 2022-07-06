Feature: User - Login & Logout Operations

  Scenario Outline: Valid user can login and logout to the application using API
    Given I provide login credentials "<username>" and "<password>"
    When I send request to login
    Then login is successful
    And logout is successful

    Examples: Valid
    |username|password|
    |test|test@123|

  Scenario Outline: Valid user with wrong password can not login to the application using API
    Given I provide login credentials "<username>" and "<password>"
    When I send request to login
    Then login failed

    Examples: Invalid
      | username | password |
      | test     | abc122   |

  Scenario Outline: Unauthorized user can not login to the application using API
    Given I provide login credentials "<username>" and "<password>"
    When I send request to login
    Then login failed

    Examples: Invalid
      |username|password|
      |test-user|abc122|