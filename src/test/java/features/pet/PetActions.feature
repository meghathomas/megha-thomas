Feature: Pet - Add a new pet, update pet details, fetch pet details and delete added pet

  Scenario Outline:  Add pet details, update details and fetch details of pet
    Given Create new pet information and validate

      | petId		| categoryId	| categoryName	| petName		| photoUrls		| tagsId	| tagsName	| status			|
      | 100			| 100					| cat						| cat123		| cat.jpg			| 100			| test			| available		|
      | 100			| 200					| mouse					| mouse123	| mouse.jpg		| 200			| mouse			| available		|

    When Update the Pet info with below data and Validate

      | petId		| categoryId	| categoryName	| petName		| photoUrls		| tagsId	| tagsName	| status			|
      | 100			| 200					| mouse					| mouse123	| mouse.jpg		| 200			| mouse			| available		|

    Then Fetch pet info by status and validate if updated pet with "<status>" exists for "<petId>"
    And Fetch pet info with pet ID "<petId>"
    And Upload a pet image "<image>" by "<petId>"
    And Delete the pet by id "<petId>"

    Examples: Valid
      | status    | petId | image                                    |
      | available | 100   | \\src\test\\resources\\image\\image1.jpg |