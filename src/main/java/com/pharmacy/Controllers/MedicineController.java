package com.pharmacy.Controllers;

import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Models.MedicineModel;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MedicineController {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    LogsController logsController;

    // Stock availability of certain medicine in inventory
    boolean available = false;

    int logId;

    public MedicineController(int logId) {
        this.logId = logId;

        try {
            connection = new DatabaseInstance().getConnection();
            statement = connection.createStatement();
            logsController = new LogsController();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean checkStock(String medicineCode) {
        try {
            String query = "SELECT quantity FROM medicines WHERE medicine_code='" + medicineCode + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                available = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return available;
    }

    /**
     * Add new medicine to the database
     *
     * @param medicineModel medicine model object
     */
    public void addMedicine(MedicineModel medicineModel) {
        java.sql.Date date = new java.sql.Date(medicineModel.getExpirationDate().getTime());
        // check if medicine already exists
        try {
            String duplicateQuery = "SELECT * FROM medicines WHERE medicine_name='"
                + medicineModel.getMedicineName()
                + "' AND cost_price='"
                + medicineModel.getCostPrice()
                + "' AND sell_price='"
                + medicineModel.getSellPrice()
                + "' AND supplied_by='"
                + medicineModel.getSuppliedBy()
                + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Product record already exists.");
            } else {
                String medicineQuery = "INSERT INTO medicines VALUES(null,?,?,?,?,?,?,?,?,DEFAULT)";
                preparedStatement = connection.prepareStatement(medicineQuery);

                preparedStatement.setString(1, medicineModel.getMedicineCode());
                preparedStatement.setString(2, medicineModel.getMedicineName());
                preparedStatement.setString(3, medicineModel.getDescription());
                preparedStatement.setDouble(4, medicineModel.getCostPrice());
                preparedStatement.setDouble(5, medicineModel.getSellPrice());
                preparedStatement.setInt(6, medicineModel.getQuantity());
                preparedStatement.setString(7, medicineModel.getSuppliedBy());
                preparedStatement.setDate(8, date);

                preparedStatement.executeUpdate();
                logsController.addLogEntry(logId, "Added new medicine: " + medicineModel.getMedicineCode() + " | " + medicineModel.getMedicineName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRestockInfo(MedicineModel medicineModel) {
        java.sql.Date date = new java.sql.Date(medicineModel.getDate().getTime());

        try {
            String query = "INSERT INTO restock VALUES(null,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, medicineModel.getSupplierCode());
            preparedStatement.setString(2, medicineModel.getMedicineCode());
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, medicineModel.getQuantity());
            preparedStatement.setDouble(5, medicineModel.getTotalCost());

            preparedStatement.executeUpdate();
            logsController.addLogEntry(logId, "Medicine restock: " + medicineModel.getMedicineCode() + " | " + medicineModel.getMedicineName());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        String medCode = medicineModel.getMedicineCode();

        // Check if medicines with stock already exists
        if (checkStock(medCode)) { // available = true
            try {
                String query = "UPDATE medicines SET quantity=quantity+? WHERE medicine_code=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, medicineModel.getQuantity());
                preparedStatement.setString(2, medCode);

                preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        } else if (!checkStock(medCode)) { // available = false
            addMedicine(medicineModel);
        }
    }

    /**
     * Update/Edit existing medicines
     *
     * @param medicineModel medicine model object
     */
    public void updateMedicine(MedicineModel medicineModel) {
        java.sql.Date date = new java.sql.Date(medicineModel.getExpirationDate().getTime());

        try {
            String medicineQuery = """
                UPDATE medicines
                SET medicine_code=?,medicine_name=?,description=?,cost_price=?,
                    sell_price=?,quantity=?,expiration_date=?,supplied_by=?
                WHERE pid=?;""";
            preparedStatement = connection.prepareStatement(medicineQuery);

            preparedStatement.setString(1, medicineModel.getMedicineCode());
            preparedStatement.setString(2, medicineModel.getMedicineName());
            preparedStatement.setString(3, medicineModel.getDescription());
            preparedStatement.setDouble(4, medicineModel.getCostPrice());
            preparedStatement.setDouble(5, medicineModel.getSellPrice());
            preparedStatement.setInt(6, medicineModel.getQuantity());
            preparedStatement.setDate(7, date);
            preparedStatement.setString(8, medicineModel.getSuppliedBy());

            preparedStatement.setInt(9, medicineModel.getMedicineId());

            preparedStatement.executeUpdate();
            logsController.addLogEntry(logId, "Updated medicine: " + medicineModel.getMedicineCode() + " | " + medicineModel.getMedicineName());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Handling of stocks in Purchase page (-)
     * when a restocking info is deleted
     *
     * @param code     medicine code
     * @param quantity quantity of medicine
     */
    public void reduceMedicineStock(String code, int quantity) {
        try {
            String query = "SELECT * FROM medicines WHERE medicine_code='" + code + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String query2 = "UPDATE medicines SET quantity=quantity-? WHERE medicine_code=?";
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setString(2, code);
                preparedStatement.executeUpdate();
            }
            logsController.addLogEntry(logId, "Medicine stock reduced: " + code + " due to deleted restock info");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Delete medicine from the database.
     *
     * @param pid medicine id to be deleted
     */
    public void deleteMedicine(int pid) {
        try {
            String query = "DELETE FROM medicines WHERE pid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pid);

            preparedStatement.executeUpdate();
            logsController.addLogEntry(logId, "Deleted medicine with id: " + pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRestockInfo(int id) {
        try {
            String query = "DELETE FROM restock WHERE purchase_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            logsController.addLogEntry(logId, "Deleted restock info with id: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Products data set retrieval for display
    public ResultSet getMedicines() {
        try {
            String query = """
                SELECT pid, medicine_code, medicine_name, description, quantity, cost_price, sell_price, supplied_by, expiration_date, last_updated FROM medicines;
                """;

            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Purchase table data set retrieval
    public ResultSet getRestockInfo() {
        try {
            String query = """
                SELECT purchase_id, restock.medicine_code,medicine_name,restock.quantity,total_cost,date
                FROM restock
                INNER JOIN medicines
                ON medicines.medicine_code=restock.medicine_code
                ORDER BY date DESC;
                """;
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Search method for medicines
    public ResultSet getMedicineSearch(String text) {
        try {
            String query = "SELECT pid, medicine_code, medicine_name, description, quantity, cost_price, sell_price, supplied_by, expiration_date, last_updated FROM medicines "
                + "WHERE medicine_code LIKE '%" + text + "%' OR medicine_name LIKE '%" + text + "%' OR supplied_by LIKE '%" + text + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getMedFromCode(String text) {
        try {
            String query = "SELECT * FROM medicines "
                + "WHERE medicine_code='" + text + "' LIMIT 1";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Search method for purchase logs
    public ResultSet getRestockSearch(String text) {
        try {
            String query = "SELECT purchase_id,restock.medicine_code,medicines.medicine_name,quantity,total_cost\n"
                + "FROM restock INNER JOIN medicines ON restock.medicine_code=medicines.medicine_code\n"
                + "INNER JOIN suppliers ON restock.supplier_code=suppliers.supplier_code\n"
                + "WHERE purchase_id LIKE '%" + text + "%' OR medicine_code LIKE '%" + text + "%' OR medicine_name LIKE '%" + text + "%'\n"
                + "OR suppliers.full_name LIKE '%" + text + "%' OR restock.supplier_code LIKE '%" + text + "%'\n"
                + "OR date LIKE '%" + text + "%' ORDER BY purchase_id";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getMedicineName(String code) {
        try {
            String query = "SELECT medicine_name FROM medicines WHERE medicine_code='" + code + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }
}
