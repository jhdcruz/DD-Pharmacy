package com.pharmacy.Utils;

import org.apache.commons.text.WordUtils;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

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
            // replace underscores with spaces
            String columnName = metaData.getColumnName(col).replaceAll("_", " ");
            // capitalize the first letter of each word
            columnName = WordUtils.capitalizeFully(columnName);

            columnNames.add(columnName);
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
