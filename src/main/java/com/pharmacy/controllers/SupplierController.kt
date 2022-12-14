package com.pharmacy.controllers

import com.pharmacy.database.DatabaseInstance
import com.pharmacy.models.SupplierModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.util.*
import javax.swing.DefaultComboBoxModel
import javax.swing.JOptionPane

class SupplierController(var logId: Int) {
    private var connection: Connection? = null
    private var statement: Statement? = null

    private var supplierCode: String? = null

    init {
        try {
            connection = DatabaseInstance().getConnection()
            statement = connection!!.createStatement()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    val suppliers: ResultSet?
        get() {
            var resultSet: ResultSet? = null

            try {
                val query = "SELECT supplier_code, full_name, location, contact, last_updated FROM suppliers"
                resultSet = statement!!.executeQuery(query)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return resultSet
        }

    /**
     * Add a new supplier to the database
     *
     * @param supplierModel populate the supplier model
     */
    fun addSupplier(supplierModel: SupplierModel) {
        val resultSet: ResultSet?

        try {
            // check if supplier already exists
            val duplicateQuery = ("SELECT * FROM suppliers WHERE full_name='"
                + supplierModel.supplierName
                + "' AND location='"
                + supplierModel.location
                + "' AND contact='"
                + supplierModel.phone
                + "'")
            resultSet = statement!!.executeQuery(duplicateQuery)

            if (resultSet!!.next()) {
                JOptionPane.showMessageDialog(null, "This supplier already exists.")
            } else {
                // else, add new supplier
                val insertQuery = "INSERT INTO suppliers VALUES(null,?,?,?,?,DEFAULT)"

                val preparedStatement: PreparedStatement = connection!!.prepareStatement(insertQuery)
                preparedStatement.setString(1, supplierModel.supplierCode)
                preparedStatement.setString(2, supplierModel.supplierName)
                preparedStatement.setString(3, supplierModel.location)
                preparedStatement.setString(4, supplierModel.phone)

                preparedStatement.executeUpdate()
                LogsController().addLogEntry(logId, "Added new supplier: " + supplierModel.supplierName)
            }
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }
    }

    /**
     * Update existing supplier
     *
     * @param supplierModel populated supplier model
     */
    fun updateSupplier(supplierModel: SupplierModel, oldCode: String?) {
        try {
            val query = "UPDATE suppliers SET supplier_code=?, full_name=?,location=?,contact=? WHERE supplier_code=?"

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, supplierModel.supplierCode)
            preparedStatement.setString(2, supplierModel.supplierName)
            preparedStatement.setString(3, supplierModel.location)
            preparedStatement.setString(4, supplierModel.phone)
            preparedStatement.setString(5, oldCode)

            preparedStatement.executeUpdate()
            LogsController().addLogEntry(
                logId,
                "Updated supplier: " + supplierModel.supplierCode + " - " + supplierModel.supplierName
            )
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    /**
     * Delete existing supplier
     *
     * @param supplierCode supplier code to be deleted
     */
    fun deleteSupplier(supplierCode: String, supplierName: String) {
        try {
            val query = "DELETE FROM suppliers WHERE supplier_code='$supplierCode'"

            statement!!.executeUpdate(query)
            LogsController().addLogEntry(logId, "Deleted supplier: $supplierName")
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }
    }


    fun getSupplierCode(supplierName: String): String? {
        val resultSet: ResultSet?

        try {
            val query = "SELECT supplier_code FROM suppliers WHERE full_name='$supplierName'"
            resultSet = statement!!.executeQuery(query)

            while (resultSet!!.next()) {
                supplierCode = resultSet.getString("supplier_code")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return supplierCode
    }

    /**
     * Search for suppliers that matches the search term
     *
     * @param searchText search keywords to be matched
     * @return ResultSet of matched suppliers
     */
    fun searchSuppliers(searchText: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query = ("SELECT supplier_code, full_name, location, contact FROM suppliers "
                + "WHERE supplier_code LIKE '%" + searchText + "%' OR location LIKE '%" + searchText + "%' "
                + "OR full_name LIKE '%" + searchText + "%' OR contact LIKE '%" + searchText + "%'")
            resultSet = statement!!.executeQuery(query)
        } catch (sqlException: SQLException) {
            sqlException.printStackTrace()
        }

        return resultSet
    }

    @Throws(SQLException::class)
    fun setComboItems(resultSet: ResultSet): DefaultComboBoxModel<String> {
        val supplierNames: MutableList<String> = ArrayList()

        while (resultSet.next()) {
            supplierNames.add(resultSet.getString("full_name"))
        }
        return DefaultComboBoxModel(supplierNames.toTypedArray())
    }
}
