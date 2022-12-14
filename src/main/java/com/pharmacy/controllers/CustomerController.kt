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
            e.printStackTrace()
        }
    }

    val customers: ResultSet?
        get() {
            var resultSet: ResultSet? = null

            try {
                val query = "SELECT * FROM customers"
                resultSet = statement!!.executeQuery(query)
            } catch (e: SQLException) {
                e.printStackTrace()
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
            val validateQuery =
                "SELECT * FROM customers WHERE last_name='" + customerModel.lastName + "' AND first_name='" + customerModel.firstName + "' AND middle_name='" + customerModel.middleName + "'"
            val resultSet: ResultSet = statement!!.executeQuery(validateQuery)

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
            e.printStackTrace()
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
            val query =
                ("UPDATE customers SET customer_code=?, last_name=?,first_name=?, middle_name=?, conditions=?,phone=?"
                    + "WHERE cid=?")

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
            e.printStackTrace()
        }
    }

    /**
     * Delete customer in the database.
     *
     * @param customerCode customer code to be deleted
     */
    fun deleteCustomer(customerCode: String) {
        try {
            val query = "DELETE FROM customers WHERE customer_code='$customerCode'"
            statement!!.executeUpdate(query)

            LogsController().addLogEntry(id, "Deleted customer: $customerCode")
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }


    /**
     * Search for matching customer based on given string.
     *
     * @param searchQuery search query to be matched with customer data
     * @return database result that matches the search query
     */
    fun getCustomerSearch(searchQuery: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query = "SELECT customer_code,last_name,first_name,middle_name,conditions,phone,last_updated," +
                "FROM customers " +
                "WHERE customer_code LIKE '%" + searchQuery + "%' " +
                "OR last_name LIKE '%" + searchQuery + "%'  " +
                "OR first_name LIKE '%" + searchQuery + "%'  " +
                "OR middle_name LIKE '%" + searchQuery + "%'  " +
                "OR " + "conditions LIKE '%" + searchQuery + "%' " +
                "OR phone LIKE '%" + searchQuery + "%'"

            resultSet = statement!!.executeQuery(query)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return resultSet
    }
}
