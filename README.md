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

The software code are divided into multiple packages for modularity:

- **`Controllers`**: Contains the data access layer of the software that interacts directly with the database and
  its tables. Used for retrieval and modification of data.

- **`Models`**: Contains the data transfer layer that allows the data to be transferred between the data
  access layer and the UI layer.

- **`Views`**: Contains all the GUI classes making up the interface layer of the software.

- **`Views/Dialogs`**: Contains all the GUI dialog classes in support to the `Views`.

- **`Database`**: Contains class and methods that is used to retrieve database connection and user authentication.

- **`Utils`**: Shared utility classes used by the other packages.


## Development

The main method is located in `com.pharmacy.Main`.

### Compiling

#### Requires:
 - Maven
 - JDK 17

Install dependencies:

```sh
mvn install
```

Running the ff. command will produce a `.jar` file inside `./target` folder

```sh
mvn clean compile assembly:single
```

> The UI _(Views)_ were made in NetBeans, and are not compatible with IntelliJ IDEA.

## Usage

### Setting up the database

**Requires**:
 - MySQL 8 Database

Create a new user:
- Username: dd_pharmacy
- Password: ddpharmacy

> The above are the default that will be used by the software.
> 
> You can manually change it in `src/main/resources/database/datasource.properties`,
> and recompile the software.

Prepare the database with the software's schema located in:
`src/main/resources/database/dd_pharmacy_schema.sql`

Or,

You can use `src/main/resources/database/dd_pharmacy_dump.sql` instead for
pre-populated database (sample data)

```
Default admin account:
user: admin
pass: adminadmin

Create your own personal admin account, then delete the default account
for security purposes.
```

After, you can now use the software.

> NOTE: Make sure the database are running in the background when
> your using the software.

### Running the app (.jar)

- Usually, double-clicking the `.jar` file is enough.

- Running through the terminal:

  > ```
  > java -jar ./target/DD-Pharmacy-1.0.0.jar
  > ```

- Through an IDE's `Run Project` tool.

## Acknowledgements

This software was based and heavily modified on the ff.

- [Inventory Management System by Asjad Iqbal](https://github.com/AsjadIqbal/InventoryManagementSystem/tree/fa42a6b59265870256b4d1d0cc13e526ddb9679e)
  _(Unlicensed, at the time of forking)_
