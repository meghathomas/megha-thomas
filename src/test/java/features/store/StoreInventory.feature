Feature: Store - Inventory

  Scenario Outline:  Validate inventory - Positive Scenario
    Given I hit the "<url>"
    When Request should submit and Positive API response should received "<response>"
    Then Inventory data should display

    Examples: Valid
      | url								| response	|
      | /store/inventory	| 200				|

  Scenario Outline:  Validate inventory - Negative Scenario
    Given I hit the "<url>"
    When Request should submit and Positive API response should received "<response>"
    Then Inventory data should display

    Examples: Invalid
      | url           | response |
      | /store/invent | 404      |