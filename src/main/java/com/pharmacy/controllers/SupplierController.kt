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

class SupplierController(private var logId: Int) {
    private var connection: Connection? = null
    private var statement: Statement? = null

    private var supplierCode: String? = null

    init {
        try {
            connection = DatabaseInstance().getConnection()
            statement = connection!!.createStatement()
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    val suppliers: ResultSet?
        get() {
            val resultSet: ResultSet?

            try {
                val query = "SELECT sid, supplier_code, full_name, location, contact, last_updated FROM suppliers"
                resultSet = statement!!.executeQuery(query)
            } catch (e: SQLException) {
                throw SQLException(e)
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
            val duplicateQuery = """
                SELECT * FROM suppliers
                WHERE full_name = ?
                AND location = ?
                AND contact = ?
                """.trimIndent()

            val duplicateStatement = connection!!.prepareStatement(duplicateQuery)
            duplicateStatement.setString(1, supplierModel.name)
            duplicateStatement.setString(2, supplierModel.location)
            duplicateStatement.setString(3, supplierModel.phone)

            resultSet = duplicateStatement.executeQuery()
            if (resultSet!!.next()) {
                JOptionPane.showMessageDialog(null, "This supplier already exists.")
            } else {
                // else, add new supplier
                val insertQuery = "INSERT INTO suppliers VALUES(null,?,?,?,?,DEFAULT)"

                val preparedStatement: PreparedStatement = connection!!.prepareStatement(insertQuery)
                preparedStatement.setString(1, supplierModel.code)
                preparedStatement.setString(2, supplierModel.name)
                preparedStatement.setString(3, supplierModel.location)
                preparedStatement.setString(4, supplierModel.phone)

                preparedStatement.executeUpdate()
                LogsController().addLogEntry(logId, "Added new supplier: " + supplierModel.name)
            }
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    /**
     * Update existing supplier
     *
     * @param supplierModel populated supplier model
     */
    fun updateSupplier(supplierModel: SupplierModel) {
        try {
            val query = "UPDATE suppliers SET supplier_code=?, full_name=?,location=?,contact=? WHERE sid=?"

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, supplierModel.code)
            preparedStatement.setString(2, supplierModel.name)
            preparedStatement.setString(3, supplierModel.location)
            preparedStatement.setString(4, supplierModel.phone)
            preparedStatement.setInt(5, supplierModel.id!!)

            preparedStatement.executeUpdate()
            LogsController().addLogEntry(
                logId,
                "Updated supplier: [${supplierModel.code}] ${supplierModel.name}"
            )
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    /**
     * Delete existing supplier
     *
     * @param supplierCode supplier code to be deleted
     */
    fun deleteSupplier(id: String, name: String) {
        try {
            val query = """
                DELETE FROM suppliers
                WHERE sid = ?
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, id)
            preparedStatement.executeUpdate()

            LogsController().addLogEntry(logId, "Deleted supplier: $name")
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }


    fun getSupplierCode(supplierName: String): String? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT supplier_code FROM suppliers
                WHERE full_name = ?
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, supplierName)

            resultSet = preparedStatement.executeQuery()
            while (resultSet!!.next()) {
                supplierCode = resultSet.getString("supplier_code")
            }
        } catch (e: SQLException) {
            throw SQLException(e)
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
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT supplier_code, full_name, location, contact
                FROM suppliers
                WHERE supplier_code LIKE ?
                OR location LIKE ?
                OR full_name LIKE ?
                OR contact LIKE ?
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, "%$searchText%")
            preparedStatement.setString(2, "%$searchText%")
            preparedStatement.setString(3, "%$searchText%")
            preparedStatement.setString(4, "%$searchText%")

            resultSet = preparedStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
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
