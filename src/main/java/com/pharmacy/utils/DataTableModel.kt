package com.pharmacy.utils

import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.swing.table.DefaultTableModel
import org.apache.commons.text.WordUtils

class DataTableModel {

    /**
     * Display database results and headers in a table.
     *
     * TODO: Replace vectors with arrays/arraylists.
     *
     * @param resultSet database result
     * @return table model that contains data and the data columns
     * @throws SQLException SQL exception thrown when database error occurs
     *
     */
    @Throws(SQLException::class)
    fun buildTableModel(resultSet: ResultSet): DefaultTableModel {
        val metaData = resultSet.metaData
        val columnNames = Vector<String>()
        val columnCount = metaData.columnCount

        // add column names to the table column headers
        for (col in 1..columnCount) {
            // replace underscores with spaces
            var columnName = metaData.getColumnName(col).replace("_", " ")
            // capitalize the first letter of each word
            columnName = WordUtils.capitalizeFully(columnName)

            columnNames.add(columnName)
        }

        val data = Vector<Vector<Any>>()

        while (resultSet.next()) {
            val dataRow = Vector<Any>()

            for (col in 1..columnCount) {
                dataRow.add(resultSet.getObject(col))

                when (columnNames[col - 1]) {
                    "Date", "In Time", "Out Time", "Last Updated" -> {
                        val formatter = DateTimeFormatter.ofPattern("hh:mm a - MMM dd, yyyy", Locale.getDefault())
                        val dateTime = LocalDateTime.parse(dataRow[col - 1].toString())
                        dataRow[col - 1] = dateTime.format(formatter)
                    }

                    "Expiration Date" -> {
                        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
                        val dateTime = LocalDateTime.parse(dataRow[col - 1].toString())
                        dataRow[col - 1] = dateTime.format(formatter)
                    }

                    "Cost Price", "Sell Price", "Total Cost", "Revenue" -> {
                        val price = dataRow[col - 1].toString().toDouble()
                        dataRow[col - 1] = String.format("%.2f", price)
                    }
                } // when
            } // for

            data.add(dataRow)
        }

        return DefaultTableModel(data, columnNames)
    }
}
