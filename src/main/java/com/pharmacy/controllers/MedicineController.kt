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
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    val medicines: ResultSet?
        get() {
            var resultSet: ResultSet? = null

            try {
                val query = """
                SELECT pid, medicine_code, medicine_name, description, quantity,
                cost_price, sell_price, supplied_by, expiration_date, last_updated
                FROM medicines;
                """.trimIndent()

                resultSet = statement!!.executeQuery(query)
            } catch (throwables: SQLException) {
                throwables.printStackTrace()
            }

            return resultSet
        }

    val restockInfo: ResultSet?
        get() {
            var resultSet: ResultSet? = null

            try {
                val query = """
                SELECT purchase_id, restock.medicine_code,medicine_name,restock.quantity,total_cost,date
                FROM restock
                INNER JOIN medicines
                ON medicines.medicine_code=restock.medicine_code
                ORDER BY date DESC;

                """.trimIndent()
                resultSet = statement!!.executeQuery(query)
            } catch (throwables: SQLException) {
                throwables.printStackTrace()
            }

            return resultSet
        }

    private fun checkStock(medicineCode: String?): Boolean {
        val resultSet: ResultSet?

        try {
            val query = "SELECT quantity FROM medicines WHERE medicine_code='$medicineCode'"
            resultSet = statement!!.executeQuery(query)

            while (resultSet!!.next()) {
                available = true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
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
            val duplicateQuery = ("SELECT * FROM medicines WHERE medicine_name='"
                + medicineModel.medicineName
                + "' AND cost_price='"
                + medicineModel.costPrice
                + "' AND sell_price='"
                + medicineModel.sellPrice
                + "' AND supplied_by='"
                + medicineModel.suppliedBy
                + "'")
            resultSet = statement!!.executeQuery(duplicateQuery)

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
                    "Added new medicine: " + medicineModel.medicineCode + " | " + medicineModel.medicineName
                )
            }
        } catch (e: SQLException) {
            e.printStackTrace()
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
                "Medicine restock: " + medicineModel.medicineCode + " | " + medicineModel.medicineName
            )
        } catch (throwable: SQLException) {
            throwable.printStackTrace()
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
            } catch (throwable: SQLException) {
                throwable.printStackTrace()
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
                "Updated medicine: " + medicineModel.medicineCode + " | " + medicineModel.medicineName
            )
        } catch (throwable: SQLException) {
            throwable.printStackTrace()
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
            val medQuery = "SELECT * FROM medicines WHERE medicine_code='$code'"
            resultSet = statement!!.executeQuery(medQuery)

            if (resultSet!!.next()) {
                val updateQuery = "UPDATE medicines SET quantity=quantity-? WHERE medicine_code=?"

                val preparedStatement: PreparedStatement = connection!!.prepareStatement(updateQuery)
                preparedStatement.setInt(1, quantity)
                preparedStatement.setString(2, code)

                preparedStatement.executeUpdate()
            }
            LogsController().addLogEntry(logId, "Medicine stock reduced: $code due to deleted restock info")
        } catch (throwables: SQLException) {
            throwables.printStackTrace()
        }
    }

    /**
     * Delete medicine from the database.
     *
     * @param pid medicine id to be deleted
     */
    fun deleteMedicine(pid: Int) {
        try {
            val query = "DELETE FROM medicines WHERE pid=?"
            val preparedStatement: PreparedStatement = connection!!.prepareStatement(query)

            preparedStatement.setInt(1, pid)
            preparedStatement.executeUpdate()

            LogsController().addLogEntry(logId, "Deleted medicine with id: $pid")
        } catch (e: SQLException) {
            e.printStackTrace()
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
            e.printStackTrace()
        }
    }


    // Search method for medicines
    fun getMedicineSearch(text: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query =
                ("SELECT pid, medicine_code, medicine_name, description, quantity, cost_price, sell_price, supplied_by, expiration_date, last_updated FROM medicines "
                    + "WHERE medicine_code LIKE '%" + text + "%' OR medicine_name LIKE '%" + text + "%' OR supplied_by LIKE '%" + text + "%'")
            resultSet = statement!!.executeQuery(query)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return resultSet
    }

    fun getMedFromCode(text: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query = ("SELECT * FROM medicines "
                + "WHERE medicine_code='" + text + "' LIMIT 1")
            resultSet = statement!!.executeQuery(query)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return resultSet
    }

    // Search method for purchase logs
    fun getRestockSearch(text: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query = """
                SELECT purchase_id,restock.medicine_code,medicines.medicine_name,quantity,total_cost
                FROM restock INNER JOIN medicines ON restock.medicine_code=medicines.medicine_code
                INNER JOIN suppliers ON restock.supplier_code=suppliers.supplier_code
                WHERE purchase_id LIKE '%$text%'
                OR medicine_code LIKE '%$text%'
                OR medicine_name LIKE '%$text%'
                OR suppliers.full_name LIKE '%$text%'
                OR restock.supplier_code LIKE '%$text%'
                OR date LIKE '%$text%'
                ORDER BY purchase_id
                """.trimIndent()
            resultSet = statement!!.executeQuery(query)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return resultSet
    }

    fun getMedicineName(code: String): ResultSet? {
        var resultSet: ResultSet? = null

        try {
            val query = "SELECT medicine_name FROM medicines WHERE medicine_code='$code'"
            resultSet = statement!!.executeQuery(query)
        } catch (throwables: SQLException) {
            throwables.printStackTrace()
        }

        return resultSet
    }
}
