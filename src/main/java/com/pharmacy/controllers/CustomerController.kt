package com.pharmacy.controllers

import com.pharmacy.database.DatabaseInstance
import com.pharmacy.models.CustomerModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.swing.JOptionPane

class CustomerController(private val id: Int) {
    private var connection: Connection? = null
    private var statement: Statement? = null

    init {
        try {
            connection = DatabaseInstance().getConnection()
            statement = connection!!.createStatement()
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    val customers: ResultSet?
        get() {
            val resultSet: ResultSet?

            try {
                val query = "SELECT * FROM customers"
                resultSet = statement!!.executeQuery(query)
            } catch (e: SQLException) {
                throw SQLException(e)
            }

            return resultSet
        }

    /**
     * Add customer to database.
     *
     * @param customerModel Customer to be added (derived from CustomerModel)
     */
    fun addCustomer(customerModel: CustomerModel) {
        try {
            // check if customer already exists
            val validateQuery = """
                SELECT * FROM customers
                WHERE customer_code = ?
                AND last_name = ?
                AND first_name = ?
                AND middle_name = ?
                AND phone = ?;
                """.trimIndent()

            val validateStatement: PreparedStatement = connection!!.prepareStatement(validateQuery)
            validateStatement.setString(1, customerModel.code)
            validateStatement.setString(2, customerModel.lastName)
            validateStatement.setString(3, customerModel.firstName)
            validateStatement.setString(4, customerModel.middleName)
            validateStatement.setString(5, customerModel.phone)

            val resultSet: ResultSet = validateStatement.executeQuery()

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Customer already exists.")
            } else {
                // else, save customer to database
                val insertQuery = "INSERT INTO customers VALUES(null,?,?,?,?,?,?,DEFAULT)"

                val preparedStatement: PreparedStatement = connection!!.prepareStatement(insertQuery)
                preparedStatement.setString(1, customerModel.code)
                preparedStatement.setString(2, customerModel.lastName)
                preparedStatement.setString(3, customerModel.firstName)
                preparedStatement.setString(4, customerModel.middleName)
                preparedStatement.setString(5, customerModel.conditions)
                preparedStatement.setString(6, customerModel.phone)
                preparedStatement.executeUpdate()

                LogsController().addLogEntry(id, "Added customer " + customerModel.name + " to database.")
            }
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    /**
     * Edit/update customer data.
     *
     * @param customerModel Customer to be edited/updated (derived from
     * CustomerModel)
     */
    fun updateCustomer(customerModel: CustomerModel) {
        try {
            val query = """
                UPDATE customers SET customer_code=?,last_name=?,first_name=?,middle_name=?,conditions=?,phone=?
                WHERE cid=?
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, customerModel.code)
            preparedStatement.setString(2, customerModel.lastName)
            preparedStatement.setString(3, customerModel.firstName)
            preparedStatement.setString(4, customerModel.middleName)
            preparedStatement.setString(5, customerModel.conditions)
            preparedStatement.setString(6, customerModel.phone)
            preparedStatement.setInt(7, customerModel.id)
            preparedStatement.executeUpdate()

            LogsController().addLogEntry(id, "Updated customer " + customerModel.name)
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    /**
     * Delete customer in the database.
     *
     * @param customerCode customer code to be deleted
     */
    fun deleteCustomer(customerCode: Int, customerName: String) {
        try {
            val query = """
                DELETE FROM customers
                WHERE cid=?;
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setInt(1, customerCode)
            prepStatement.executeUpdate()

            LogsController().addLogEntry(id, "Deleted customer: $customerName ($customerCode)")
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }


    /**
     * Search for matching customer based on given string.
     *
     * @param searchQuery search query to be matched with customer data
     * @return database result that matches the search query
     */
    fun getCustomerSearch(search: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT * FROM customers
                WHERE customer_code LIKE ?
                OR last_name LIKE ?
                OR first_name LIKE ?
                OR middle_name LIKE ?
                OR conditions LIKE ?
                OR phone LIKE ?;
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setString(1, "%$search%")
            prepStatement.setString(2, "%$search%")
            prepStatement.setString(3, "%$search%")
            prepStatement.setString(4, "%$search%")
            prepStatement.setString(5, "%$search%")
            prepStatement.setString(6, "%$search%")

            resultSet = prepStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }
}
