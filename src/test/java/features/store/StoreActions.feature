Feature: Store - create new order, view & delete created order

  Scenario Outline: Create new order, view & delete created order
    Given Place an order for a  pet

      | url					| orderId	  | petId	| quantity	| shipdate										| status		|	complete	|
      | /store/order| 1 				| 1			| 1					| 2021-02-10T08:39:11.959Z		| placed		| true			|


    When Fetch order information by "<orderid>"
    Then Delete order by "<orderid>"

    Examples: Valid
      | orderid	|
      | 1				|