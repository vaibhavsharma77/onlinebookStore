
# Online Book Store

Online book store application which provides user to register and login , allow user to add book into cart and also help them to process the orders.


## Features
- **User Registration Process:** User Can registration and login securely
- **Books Management:** Store book into H2 DB and retrieve a list of available books.
- **Shopping Cart Management:** Add, modify, and remove items from the shopping cartItem.
- **Order Processing:** Submit and view order details.

### Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/vaibhavsharma77/onlinebookStore.git
   cd oonlinebookStore# Simple Online Bookstore Backend (Spring Boot)

### Prerequisites

Before you start, ensure you have the following installed:
1. **Java Development Kit (JDK)**: Version 17 or above.
2. **Maven**: Build automation tool to manage dependencies and package the application.
3. **Database**: In Memory H2 DB running along when app bootstrap.

### Build and Run Project:
1. You can build the project using Maven. Open a terminal in the project directory and run **mvn clean install**
2. Run the Application: **mvn spring-boot:run**

3. You can run the application using your IDE or through the command line. To run it from the command line, execute

The application will start on http://localhost:8080.

### API Endpoints

- **User Registration Process:**
```bash
1. *User Registration : http://localhost:8080/api/auth/registration

**Request Body:**

{
    "userName":"newUser",
    "password":"Password"
}

User Login : http://localhost:8080/api/auth/login

**Request Body:**

{
    "userName":"userName",
    "password":"Password"
}


### Books Management

Book Management : Add Book , Retrieve aLL books and based on book Id

1.Add Book: Adds a new book to the bookstore.

Endpoint: POST http://localhost:8080/api/books/add

Request Body:

{
"title": "title-1",
"author": "author-1",
"price": 30
}

2.Retrieve All Books : Retrieves a list of all available books.

Endpoint: GET http://localhost:8080/api/books/all

3. Find Book By ID : Retrieves details of a specific book by its ID.

Endpoint: GET http://localhost:8080/api/books/1

### Books Management

Cart Management : Add Book in to Cart , Retrieve cart items by user Id update cart and remove item from cart

1.AddToCart: When user wants to add books into cart.

Endpoint: POST http://localhost:8080/api/cart/add

Request Body:

{
    "bookId": 1,
    "userId": 1,
    "quantity": 2
}

2.Retrieve books for cart : FetchCartItemByUserId

Endpoint: GET http://localhost:8080/api/cart/user/1

3. Update Cart : Update Cart based on Quantity.

Endpoint: PUT http://localhost:8080/api/cart/update

{
    "cartItemId":1,
    "userId": 1,
    "bookId": 1,
    "quantity": 3
}

4.Remove Book from cart

Endpoint : http://localhost:8080/api/cart/remove?bookId=1&userId=1


## Order Processing

Order Checkout : If a user add two books with different quantites this api will tell how much it cost to user and when the order is placed

Endpoint : POST http://localhost:8080/api/orders/checkout

PAYLOAD

{
"userId":1
}