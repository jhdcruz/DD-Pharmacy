# DD Pharmacy Inventory and POS System

## Features

- Inventory System
    - Products
    - Customers
    - Suppliers
    - Users
    - Transactions
- Point-of-Sale System

## Dependencies:

All the project dependencies are available in the [`lib`](lib/) directory.

The software code has been divided into four different packages:

- Data Access Object (DAO): Contains the data access layer of the software that interacts directly with the database and
  its tables. Used for retrieval and modification of data.
- Data Transfer Object (DTO): Contains the data transfer layer that allows the data to be transferred between the data
  access layer and the UI layer.
- Database: Contains the ConnectionFactory class that retrieves the database connection and verifies user credentials
  for the application.
- User Interface (UI): Contains all the GUI classes making up the interface layer of the software.

## Acknowledgements

- Inventory Management System

- Point-of-Sale System
