# Room Occupancy Manager

This project preserves a solution for occupancy optimization application.  The default room count is set to 10 for each Premium and Economy category.

The default port is **7101**

## Environment Variables

> CURRENCY

Can be set to define a currency. Default is EUR.


> PREMIUM_PAY_LIMIT

This parameter is a money limit to categorize customers as Premium or Economy. Default is 100.


> PREMIUM_ROOM_COUNT

Defines the Premium room count


> ECONOMY_ROOM_COUNT

Defines the Economy room count


## How to use it?

Customer categories are divided into two as **Economy** and **Premium**. These categories are represented with an enum called **CategoryType** in the application. In API calls **0** stands for **Economy**, **1** stands for **Premium**.

    public enum CategoryType {  
        ECONOMY("Economy"),  
	    PREMIUM("Premium");
    }

There are two main REST controllers that are **RoomController** and **ReservationController**.

## Provided APIs for RoomController are:

### - /room/count
is a POST method. It updates the room count. The requested body example is given below. The count stands for the number of rooms, and the type is the category of the room.

The below example will update the premium room count to 7.

    {
    	"count":  7,
    	"type":  1
    }

Below code snippet demonstrates the result of the API call:

    {
        "value":  "Premium room count successfully updated to 7.",
        "httpStatus":  "OK",
        "httpStatusCode":  200
    }

### - /room/checkOutAllRooms
is a PUT method. It checkouts all the guests. Below code snippet shows the API call result:

    {
	    "value":  "All guests checked out!.",
	    "httpStatus":  "OK",
	    "httpStatusCode":  200
    }

### - /room/counts
is a GET method. It returns the room counts. Below code snippet shows the API call result:

    Premium Room Count: 10, Economy Room Count: 10

## Provided APIs for ReservationController are:

### - /reservation/checkin

is a POST method. It processes check-in progress. The requested body example is given below.

    {
    	"payments":  [23,  45,  155,  374,  22,  99.99,  100,  101,  115,  209]
    }

An example response is:

    Usage Premium: 6 (EUR 1,054.00)
    Usage Economy: 4 (EUR 189.99)



## Domain Explanation



**CustomerCheckInDto:**

CustomerCheckInDto is a specified check-in request dto which includes the following field:


>payments

payments field is a List\<Double> variable.

Example input is:


> [23,  45,  155,  374,  22,  99.99,  100,  101,  115,  209]


**RoomCountUpdateRequestDto:**

RoomCountUpdateRequestDto is used to update room count through API.

> count
> type

**count** is an **int** variable. Which stands for the number of rooms.
**type** is the enum of **CategoryType**. It specifies the rooms' category.

> "count":  7, "type":  1

**ApiResponse:**

ApiResponse is specified response dto with following fields:

> value: final value
> httpStatus: response status
> httpStatusCode: response status code

**value** is result
**httpStatus** is response status
**httpStatusCode** is response status code

E.g:

    {
	    "value":  "Economy room count successfully updated to 7.",
	    "httpStatus":  "OK",
	    "httpStatusCode":  200
    }


**RoomReservationResult**

This class holds the result of processed users.

> customerPayments
> categoryType
> currency

**customerPayments** is List\<Double> of user payments
**categoryType** is CategoryType which stands for customer category
**currency** is the currency that is used among application


## Enums



**CategoryType**


>  ECONOMY
>  PREMIUM


## HOW IT WORKS?

This application has a specific strategy for occupying rooms for customers.

First, it categorizes the customers as premium and economy, depending on the payments they are willing to pay. Also, it orders requests depending on payments.

Then, it reserves available premium rooms among premium customers.
Next, the same process repeated for economy rooms among economy customers.

After this process, if there are any unassigned economy customers left and also if there are available premium rooms, most paying economy customers will be upgraded to the premium rooms, and their rooms are reserved for most paying unassigned customers.



## Logging



Logging is handled by Spring AOP.



With the implementation of the logging aspect, the application prints log with the values in each step before and after a method call.


## Testing
There are two test classes ReservationControllerTest and RoomServiceTest.

Coverage:

>  100% classes  
>  83% lines.

