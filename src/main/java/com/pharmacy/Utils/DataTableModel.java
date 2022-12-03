package com.pharmacy.Utils;

import jdk.jfr.*;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;

public class DataTableModel {

    /**
     * Display database results and headers in a table.
     *
     * @param resultSet database result
     * @return table model that contains data and the data columns
     * @throws SQLException SQL exception thrown when database error occurs
     */
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        // add column names to the table column headers
        for (int col = 1; col <= columnCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> dataRow = new Vector<>();

            for (int col = 1; col <= columnCount; col++) {
                dataRow.add(resultSet.getObject(col));
            }

            data.add(dataRow);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
