Feature: Creation of multiple users

  Scenario Outline: Create multiple user with List
Given Create multiple user using below details using List

| id | userName  | firstname 	| lastname 		| email						   	| password				| phoneNumber | userStatus |
| 22 | testuser3 | firstname3 | lastname3 	| testuser3@gmail.com | test123					| 123456789   | 0					 |
| 23 | testuser4 | firstname4	| lastname4  	| testuser4@gmail.com	| test2123				| 123456789		| 0					 |


When Send request to crete user with list
Then Create user is successful with list
And Fetch the list of user details by "<username1>"
And Fetch the list of user details by "<username2>"

Examples: Valid
  | username1 | username2         |
  | testuser3 | testuser4 |