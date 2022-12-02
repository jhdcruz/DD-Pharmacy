# DD Pharmacy Inventory and POS System

## Features

- Products
- Customers
- Suppliers
- Users
- Transactions
- Logs

## Dependencies:

All the project dependencies are available in the [`lib`](lib/) directory.

## Structure

The software code has been divided into 4 major packages:

- **`Controllers`**: Contains the data access layer of the software that interacts directly with the database and
  its tables. Used for retrieval and modification of data.

- **`Models`**: Contains the data transfer layer that allows the data to be transferred between the data
  access layer and the UI layer.

- **`Views`**: Contains all the GUI classes making up the interface layer of the software.

- **`Database`**: Contains class and methods that is used to retrieve database connection and user authentication.

## Acknowledgements

The ff. were used as templates in the development of this software:

- [Inventory Management System by Asjad Iqbal](https://github.com/AsjadIqbal/InventoryManagementSystem/tree/fa42a6b59265870256b4d1d0cc13e526ddb9679e)
  _(Unlicensed, at the time of forking)_
