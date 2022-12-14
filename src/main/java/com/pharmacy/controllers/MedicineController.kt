package com.pharmacy.controllers

import com.pharmacy.database.DatabaseInstance
import com.pharmacy.models.MedicineModel
import java.sql.Connection
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import javax.swing.JOptionPane

class MedicineController(private var logId: Int) {
    private var connection: Connection? = null
    private var statement: Statement? = null

    // Stock availability of certain medicine in inventory
    private var available = false

    init {
        try {
            connection = DatabaseInstance().getConnection()
            statement = connection!!.createStatement()
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    val medicines: ResultSet?
        get() {
            val resultSet: ResultSet?

            try {
                val query = """
                SELECT pid, medicine_code, medicine_name, description, quantity,
                cost_price, sell_price, supplied_by, expiration_date, last_updated
                FROM medicines;
                """.trimIndent()

                resultSet = statement!!.executeQuery(query)
            } catch (e: SQLException) {
                throw SQLException(e)
            }

            return resultSet
        }

    val restockInfo: ResultSet?
        get() {
            val resultSet: ResultSet?

            try {
                val query = """
                SELECT purchase_id, restock.medicine_code,medicine_name,restock.quantity,total_cost,date
                FROM restock
                INNER JOIN medicines
                ON medicines.medicine_code=restock.medicine_code
                ORDER BY date DESC;
                """.trimIndent()

                resultSet = statement!!.executeQuery(query)
            } catch (e: SQLException) {
                throw SQLException(e)
            }

            return resultSet
        }

    private fun checkStock(medicineCode: String?): Boolean {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT quantity FROM medicines
                WHERE medicine_code = ?;
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, medicineCode)

            resultSet = preparedStatement.executeQuery()
            while (resultSet!!.next()) {
                available = true
            }
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return available
    }

    /**
     * Add new medicine to the database
     *
     * @param medicineModel medicine model object
     */
    fun addMedicine(medicineModel: MedicineModel) {
        val resultSet: ResultSet?
        val date = Date(medicineModel.expirationDate!!.time)

        // check if medicine already exists
        try {
            val duplicateQuery = """
                SELECT * FROM medicines
                WHERE medicine_name = ?
                AND cost_price = ?
                AND sell_price = ?
                AND supplied_by = ?;
                """.trimIndent()

            val duplicateStatement: PreparedStatement = connection!!.prepareStatement(duplicateQuery)
            duplicateStatement.setString(1, medicineModel.medicineName)
            duplicateStatement.setDouble(2, medicineModel.costPrice!!)
            duplicateStatement.setDouble(3, medicineModel.sellPrice!!)
            duplicateStatement.setString(4, medicineModel.suppliedBy)

            resultSet = duplicateStatement.executeQuery()
            if (resultSet!!.next()) {
                JOptionPane.showMessageDialog(null, "Medicine record already exists.")
            } else {
                val medicineQuery = "INSERT INTO medicines VALUES(null,?,?,?,?,?,?,?,?,DEFAULT)"

                val preparedStatement: PreparedStatement = connection!!.prepareStatement(medicineQuery)
                preparedStatement.setString(1, medicineModel.medicineCode)
                preparedStatement.setString(2, medicineModel.medicineName)
                preparedStatement.setString(3, medicineModel.description)
                preparedStatement.setDouble(4, medicineModel.costPrice!!)
                preparedStatement.setDouble(5, medicineModel.sellPrice!!)
                preparedStatement.setInt(6, medicineModel.quantity!!)
                preparedStatement.setString(7, medicineModel.suppliedBy)
                preparedStatement.setDate(8, date)

                preparedStatement.executeUpdate()
                LogsController().addLogEntry(
                    logId,
                    "Added new medicine: ${medicineModel.medicineName} supplied by ${medicineModel.suppliedBy}"
                )
            }
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    fun addRestockInfo(medicineModel: MedicineModel) {
        val date = Date(medicineModel.date!!.time)

        try {
            val query = "INSERT INTO restock VALUES(null,?,?,?,?,?)"

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, medicineModel.supplierCode)
            preparedStatement.setString(2, medicineModel.medicineCode)
            preparedStatement.setDate(3, date)
            preparedStatement.setInt(4, medicineModel.quantity!!)
            preparedStatement.setDouble(5, medicineModel.totalCost!!)

            preparedStatement.executeUpdate()
            LogsController().addLogEntry(
                logId,
                "Added new restock info for medicine: ${medicineModel.medicineCode} x${medicineModel.quantity}, supplied by ${medicineModel.supplierCode}"
            )
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        val medCode = medicineModel.medicineCode
        // Check if medicines with stock already exists
        if (checkStock(medCode)) { // available = true
            try {
                val query = "UPDATE medicines SET quantity=quantity+? WHERE medicine_code=?"

                val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
                preparedStatement.setInt(1, medicineModel.quantity!!)
                preparedStatement.setString(2, medCode)

                preparedStatement.executeUpdate()
            } catch (e: SQLException) {
                throw SQLException(e)
            }
        } else if (!checkStock(medCode)) { // available = false
            addMedicine(medicineModel)
        }
    }

    /**
     * Update/Edit existing medicines
     *
     * @param medicineModel medicine model object
     */
    fun updateMedicine(medicineModel: MedicineModel) {
        val date = Date(medicineModel.expirationDate!!.time)

        try {
            val medicineQuery = """
                UPDATE medicines
                SET medicine_code=?,medicine_name=?,description=?,cost_price=?,
                    sell_price=?,quantity=?,expiration_date=?,supplied_by=?
                WHERE pid=?;
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(medicineQuery)
            preparedStatement.setString(1, medicineModel.medicineCode)
            preparedStatement.setString(2, medicineModel.medicineName)
            preparedStatement.setString(3, medicineModel.description)
            preparedStatement.setDouble(4, medicineModel.costPrice!!)
            preparedStatement.setDouble(5, medicineModel.sellPrice!!)
            preparedStatement.setInt(6, medicineModel.quantity!!)
            preparedStatement.setDate(7, date)
            preparedStatement.setString(8, medicineModel.suppliedBy)
            preparedStatement.setInt(9, medicineModel.medicineId!!)

            preparedStatement.executeUpdate()
            LogsController().addLogEntry(
                logId,
                "Updated medicine: [${medicineModel.medicineCode}]  ${medicineModel.medicineName} supplied by ${medicineModel.suppliedBy}"
            )
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    /**
     * Handling of stocks in Purchase page (-)
     * when a restocking info is deleted
     *
     * @param code     medicine code
     * @param quantity quantity of medicine
     */
    fun reduceMedicineStock(code: String, quantity: Int) {
        val resultSet: ResultSet?

        try {
            val medQuery = "SELECT quantity FROM medicines WHERE medicine_code=?"

            val medStatement: PreparedStatement = connection!!.prepareStatement(medQuery)
            medStatement.setString(1, code)

            resultSet = medStatement.executeQuery()
            if (resultSet!!.next()) {
                val updateQuery = "UPDATE medicines SET quantity=quantity-? WHERE medicine_code=?"

                val preparedStatement: PreparedStatement = connection!!.prepareStatement(updateQuery)
                preparedStatement.setInt(1, quantity)
                preparedStatement.setString(2, code)

                preparedStatement.executeUpdate()
            }
            LogsController().addLogEntry(logId, "Medicine stock reduced: $code due to deleted restock info")
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    /**
     * Delete medicine from the database.
     *
     * @param id medicine id to be deleted
     */
    fun deleteMedicine(id: Int, name: String) {
        try {
            val query = "DELETE FROM medicines WHERE pid=?"
            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)

            preparedStatement.setInt(1, id)
            preparedStatement.executeUpdate()

            LogsController().addLogEntry(logId, "Deleted medicine: $name")
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    fun deleteRestockInfo(id: Int) {
        try {
            val query = "DELETE FROM restock WHERE purchase_id=?"
            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)

            preparedStatement.setInt(1, id)

            preparedStatement.executeUpdate()
            LogsController().addLogEntry(logId, "Deleted restock info with id: $id")
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }


    // Search method for medicines
    fun getMedicineSearch(text: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT medicine_code, medicine_name, description, quantity,
                       cost_price, sell_price, supplied_by, expiration_date, last_updated
                FROM medicines
                WHERE medicine_code LIKE ?
                OR medicine_name LIKE ?
                OR description LIKE ?
                OR quantity LIKE ?
                OR cost_price LIKE ?
                OR sell_price LIKE ?
                OR supplied_by LIKE ?
                OR expiration_date LIKE ?
                OR last_updated LIKE ?
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, "%$text%")
            preparedStatement.setString(2, "%$text%")
            preparedStatement.setString(3, "%$text%")
            preparedStatement.setString(4, "%$text%")
            preparedStatement.setString(5, "%$text%")
            preparedStatement.setString(6, "%$text%")
            preparedStatement.setString(7, "%$text%")
            preparedStatement.setString(8, "%$text%")
            preparedStatement.setString(9, "%$text%")

            resultSet = preparedStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }

    fun getMedFromCode(text: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT * FROM medicines
                WHERE medicine_code=?
                LIMIT 1;
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, text)

            resultSet = preparedStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }

    // Search method for purchase logs
    fun getRestockSearch(text: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT purchase_id,restock.medicine_code,medicines.medicine_name,restock.quantity,total_cost
                FROM restock INNER JOIN medicines ON restock.medicine_code=medicines.medicine_code
                INNER JOIN suppliers ON restock.supplier_code=suppliers.supplier_code
                WHERE purchase_id LIKE ?
                OR restock.medicine_code LIKE ?
                OR medicine_name LIKE ?
                OR suppliers.full_name LIKE ?
                OR restock.supplier_code LIKE ?
                OR date LIKE ?
                ORDER BY purchase_id;
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, "%$text%")
            preparedStatement.setString(2, "%$text%")
            preparedStatement.setString(3, "%$text%")
            preparedStatement.setString(4, "%$text%")
            preparedStatement.setString(5, "%$text%")
            preparedStatement.setString(6, "%$text%")

            resultSet = preparedStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }

    fun getMedicineName(code: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT medicine_name
                FROM medicines
                WHERE medicine_code=?
                """.trimIndent()

            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)
            preparedStatement.setString(1, code)

            resultSet = preparedStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }
}
