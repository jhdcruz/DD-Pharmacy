# D&D Pharmacy Inventory Management System

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=jhdcruz_DD-Pharmacy&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=jhdcruz_DD-Pharmacy) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=jhdcruz_DD-Pharmacy&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=jhdcruz_DD-Pharmacy) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jhdcruz_DD-Pharmacy&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=jhdcruz_DD-Pharmacy)

In Compliance to CIT 201: System Analysis and Design.

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=jhdcruz_DD-Pharmacy)](https://sonarcloud.io/summary/new_code?id=jhdcruz_DD-Pharmacy)

## Features

- Medicine Inventory
- Customers
- Suppliers
- Users<sup>*</sup>
- Restock
- Basic User Timesheet
- System/Application Event Logs

> _* Password encryption/decryption is supported/implemented (AES/GCM/NoPadding)_

## Structure

The software code are divided into multiple packages for modularity:

- **`controllers`**: Contains the controllers that interacts directly with the database and
  its tables. Used for retrieval and modification of data abd interaction with `views`.

- **`models`**: Contains the models that allows the data to be transferred between the controllers and the views.

- **`views`**: Contains all the GUI classes making up the interface layer of the software.

- **`views/dialogs`**: Contains all the GUI dialog classes in support to the `Views`.

- **`database`**: Contains class and methods that is used to retrieve database connection and user authentication.

- **`utils`**: Shared utility classes used by the other packages.

## Development

The main method is located in `com.pharmacy.Main`.

### Compiling

#### Requires:

- Maven 3.8
- JDK 17
- Kotlin 1.8.0

Install dependencies:

```sh
mvn install
```

Running the ff. command will produce a `.jar` file inside `./target` folder

```sh
mvn clean compile assembly:single
```

> The UI _(views)_ were made in NetBeans, and are not compatible with IntelliJ IDEA.

## Usage

### Setting up the database

**Requires**:

- MySQL/MariaDB 8 Database

Create a new user:

- Username: `dd_pharmacy`
- Password: `ddpharmacy`

> The above are the default that will be used by the software.
>
> You can manually change it in `src/main/resources/database/datasource.properties`,
> and recompile the software.

Prepare the database with the software's schema located in:
`src/main/resources/database/dd_pharmacy_schema.sql`

Default admin account:

- user: `admin`
- pass: `admin`

> Create your own personal admin account,
> then delete the default account for security purposes.

After, you can now use the software.

### Running the app (.jar)

- Usually, double-clicking the `.jar` file is enough _(assuming JRE is installed properly)_.

- Running through the terminal:

  > ```
  > java -jar ./target/DD-Pharmacy-vX.X.X.jar
  > ```

- Through an IDE's `Run Project` tool.

> NOTE: Make sure the database are running in the background when
> your using the software.

## Acknowledgements

This software was based and heavily modified of the ff.

- [Inventory Management System by Asjad Iqbal](https://github.com/AsjadIqbal/InventoryManagementSystem/tree/fa42a6b59265870256b4d1d0cc13e526ddb9679e)
  _(Unlicensed, at the time of cloning)_
