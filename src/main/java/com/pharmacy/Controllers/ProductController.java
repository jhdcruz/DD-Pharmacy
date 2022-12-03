package com.pharmacy.Controllers;

import com.pharmacy.Models.ProductModel;
import com.pharmacy.Database.DatabaseInstance;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;

public class ProductController {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    Statement statement = null;
    Statement statement2 = null;
    ResultSet resultSet = null;

    String supplierCode;
    String productCode;
    String customerCode;

    // Stock availability of certain product in inventory
    boolean available = false;

    public ProductController() {
        try {
            connection = new DatabaseInstance().getConnection();
            statement = connection.createStatement();
            statement2 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ResultSet getSupplierInfo() {
        try {
            String query = "SELECT * FROM suppliers";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getCustomerInfo() {
        try {
            String query = "SELECT * FROM customers";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getProductStock() {
        try {
            String query = "SELECT * FROM currentstock";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getProductInfo() {
        try {
            String query = "SELECT * FROM products";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public Double getProductCost(String productCode) {
        Double costPrice = null;
        try {
            String query = "SELECT costprice FROM products WHERE productcode='" + productCode + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                costPrice = resultSet.getDouble("costprice");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return costPrice;
    }

    public Double getProductSellPrice(String productCode) {
        Double sellPrice = null;
        try {
            String query = "SELECT sellprice FROM products WHERE productcode='" + productCode + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                sellPrice = resultSet.getDouble("sellprice");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sellPrice;
    }


    public String getSupplierCode(String supplierName) {
        try {
            String query = "SELECT suppliercode FROM suppliers WHERE fullname='" + supplierName + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                supplierCode = resultSet.getString("suppliercode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierCode;
    }


    public String getProductCode(String productName) {
        try {
            String query = "SELECT productcode FROM products WHERE productname='" + productName + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                supplierCode = resultSet.getString("productcode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productCode;
    }


    public String getCustomerCode(String customerName) {
        try {
            String query = "SELECT customercode FROM suppliers WHERE fullname='" + customerName + "'";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                supplierCode = resultSet.getString("customercode");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerCode;
    }


    public boolean checkStock(String productCode) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + productCode + "'";
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
     * Add new product to the database
     *
     * @param productModel product model object
     */
    public void addProduct(ProductModel productModel) {
        // check if product already exists
        try {
            String duplicateQuery = "SELECT * FROM products WHERE productname='"
                + productModel.getProductName()
                + "' AND costprice='"
                + productModel.getCostPrice()
                + "' AND sellprice='"
                + productModel.getSellPrice()
                + "' AND brand='"
                + productModel.getBrand()
                + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Product record already exists.");
            } else {
                String productQuery = "INSERT INTO products VALUES(null,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(productQuery);
                preparedStatement.setString(1, productModel.getProductCode());
                preparedStatement.setString(2, productModel.getProductName());
                preparedStatement.setDouble(3, productModel.getCostPrice());
                preparedStatement.setDouble(4, productModel.getSellPrice());
                preparedStatement.setString(5, productModel.getBrand());

                String stockQuery = "INSERT INTO currentstock VALUES(?,?)";
                preparedStatement2 = connection.prepareStatement(stockQuery);
                preparedStatement2.setString(1, productModel.getProductCode());
                preparedStatement2.setInt(2, productModel.getQuantity());

                preparedStatement.executeUpdate();
                preparedStatement2.executeUpdate();

                JOptionPane.showMessageDialog(null, "Product added and ready for sale.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPurchaseInfo(ProductModel productModel) {
        try {
            String query = "INSERT INTO purchaseinfo VALUES(null,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productModel.getSupplierCode());
            preparedStatement.setString(2, productModel.getProductCode());
            preparedStatement.setString(3, productModel.getDate());
            preparedStatement.setInt(4, productModel.getQuantity());
            preparedStatement.setDouble(5, productModel.getTotalCost());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Purchase log added.");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        String prodCode = productModel.getProductCode();

        // check if a there is a stock
        if (checkStock(prodCode)) {
            try {
                String query = "UPDATE currentstock SET quantity=quantity+? WHERE productcode=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, productModel.getQuantity());
                preparedStatement.setString(2, prodCode);

                preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        } else if (!checkStock(prodCode)) {
            try {
                String query = "INSERT INTO currentstock VALUES(?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, productModel.getProductCode());
                preparedStatement.setInt(2, productModel.getQuantity());

                preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

        deleteStock();
    }

    /**
     * Update/Edit existing products
     *
     * @param productModel product model object
     */
    public void updateProduct(ProductModel productModel) {
        try {
            String productQuery = "UPDATE products SET productname=?,costprice=?,sellprice=?,brand=? WHERE productcode=?";
            preparedStatement = connection.prepareStatement(productQuery);
            preparedStatement.setString(1, productModel.getProductName());
            preparedStatement.setDouble(2, productModel.getCostPrice());
            preparedStatement.setDouble(3, productModel.getSellPrice());
            preparedStatement.setString(4, productModel.getBrand());
            preparedStatement.setString(5, productModel.getProductCode());

            String stockQuery = "UPDATE currentstock SET quantity=? WHERE productcode=?";
            preparedStatement2 = connection.prepareStatement(stockQuery);
            preparedStatement2.setInt(1, productModel.getQuantity());
            preparedStatement2.setString(2, productModel.getProductCode());

            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();

            JOptionPane.showMessageDialog(null, "Product details updated.");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Handling of stocks in Purchase page
     * (-)
     *
     * @param code     product code
     * @param quantity quantity of product
     */
    public void updatePurchaseStock(String code, int quantity) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + code + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String query2 = "UPDATE currentstock SET quantity=quantity-? WHERE productcode=?";
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setString(2, code);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Handling of stocks in Sales page
     * (+)
     *
     * @param code     product code
     * @param quantity quantity of product
     */
    public void updateSoldStock(String code, int quantity) {
        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + code + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String query2 = "UPDATE currentstock SET quantity=quantity+? WHERE productcode=?";
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setInt(1, quantity);
                preparedStatement.setString(2, code);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Delete product stocks
     */
    public void deleteStock() {
        try {
            String query = "DELETE FROM currentstock WHERE productcode NOT IN(SELECT productcode FROM purchaseinfo)";
            String query2 = "DELETE FROM salesinfo WHERE productcode NOT IN(SELECT productcode FROM products)";

            statement.executeUpdate(query);
            statement.executeUpdate(query2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Delete product from the database.
     *
     * @param code product code to be deleted
     */
    public void deleteProduct(String code) {
        try {
            String query = "DELETE FROM products WHERE productcode=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);

            String query2 = "DELETE FROM currentstock WHERE productcode=?";
            preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setString(1, code);

            preparedStatement.executeUpdate();
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        deleteStock();
    }

    public void deletePurchaseInfo(int id) {
        try {
            String query = "DELETE FROM purchaseinfo WHERE purchaseID=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Transaction has been removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        deleteStock();
    }

    public void deleteSalesInfo(int id) {
        try {
            String query = "DELETE FROM salesinfo WHERE salesID=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Transaction has been removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        deleteStock();
    }

    // Sales transaction handling
    public void sellProduct(ProductModel productModel, String username) {
        int quantity = 0;
        String prodCode = null;

        try {
            String query = "SELECT * FROM currentstock WHERE productcode='" + productModel.getProductCode() + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                prodCode = resultSet.getString("productcode");
                quantity = resultSet.getInt("quantity");
            }

            if (productModel.getQuantity() > quantity) {
                JOptionPane.showMessageDialog(null, "Insufficient stock for this product.");
            } else if (productModel.getQuantity() <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
            } else {
                String stockQuery = "UPDATE currentstock SET quantity=quantity-'"
                    + productModel.getQuantity()
                    + "' WHERE productcode='"
                    + productModel.getProductCode()
                    + "'";

                String salesQuery = "INSERT INTO salesinfo(date,productcode,customercode,quantity,revenue,soldby)" +
                    "VALUES('" + productModel.getDate() + "','" + productModel.getProductCode() + "','" + productModel.getCustomerCode() +
                    "','" + productModel.getQuantity() + "','" + productModel.getTotalRevenue() + "','" + username + "')";

                statement.executeUpdate(stockQuery);
                statement.executeUpdate(salesQuery);
                JOptionPane.showMessageDialog(null, "Product sold.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Products data set retrieval for display
    public ResultSet getProducts() {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products ORDER BY pid";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Purchase table data set retrieval
    public ResultSet getPurchaseInfo() {
        try {
            String query = "SELECT PurchaseID,purchaseinfo.ProductCode,ProductName,Quantity,Totalcost " +
                "FROM purchaseinfo INNER JOIN products " +
                "ON products.productcode=purchaseinfo.productcode ORDER BY purchaseid;";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Stock table data set retrieval
    public ResultSet getCurrentStockInfo() {
        try {
            String query = """
                SELECT currentstock.ProductCode,products.ProductName,
                currentstock.Quantity,products.CostPrice,products.SellPrice
                FROM currentstock INNER JOIN products
                ON currentstock.productcode=products.productcode;
                """;
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Sales table data set retrieval
    public ResultSet getSalesInfo() {
        try {
            String query = """
                SELECT salesid,salesinfo.productcode,productname,
                salesinfo.quantity,revenue,users.name AS Sold_by
                FROM salesinfo INNER JOIN products
                ON salesinfo.productcode=products.productcode
                INNER JOIN users
                ON salesinfo.soldby=users.username;
                """;
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Search method for products
    public ResultSet getProductSearch(String text) {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products " +
                "WHERE productcode LIKE '%" + text + "%' OR productname LIKE '%" + text + "%' OR brand LIKE '%" + text + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getProdFromCode(String text) {
        try {
            String query = "SELECT productcode,productname,costprice,sellprice,brand FROM products " +
                "WHERE productcode='" + text + "' LIMIT 1";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Search method for sales
    public ResultSet getSalesSearch(String text) {
        try {
            String query = "SELECT salesid,salesinfo.productcode,productname,\n" +
                "salesinfo.quantity,revenue,users.name AS Sold_by\n" +
                "FROM salesinfo INNER JOIN products\n" +
                "ON salesinfo.productcode=products.productcode\n" +
                "INNER JOIN users\n" +
                "ON salesinfo.soldby=users.username\n" +
                "INNER JOIN customers\n" +
                "ON customers.customercode=salesinfo.customercode\n" +
                "WHERE salesinfo.productcode LIKE '%" + text + "%' OR productname LIKE '%" + text + "%' " +
                "OR users.name LIKE '%" + text + "%' OR customers.fullname LIKE '%" + text + "%' ORDER BY salesid;";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Search method for purchase logs
    public ResultSet getPurchaseSearch(String text) {
        try {
            String query = "SELECT PurchaseID,purchaseinfo.productcode,products.productname,quantity,totalcost " +
                "FROM purchaseinfo INNER JOIN products ON purchaseinfo.productcode=products.productcode " +
                "INNER JOIN suppliers ON purchaseinfo.suppliercode=suppliers.suppliercode" +
                "WHERE PurchaseID LIKE '%" + text + "%' OR productcode LIKE '%" + text + "%' OR productname LIKE '%" + text + "%' " +
                "OR suppliers.fullname LIKE '%" + text + "%' OR purchaseinfo.suppliercode LIKE '%" + text + "%' " +
                "OR date LIKE '%" + text + "%' ORDER BY purchaseid";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getProductName(String code) {
        try {
            String query = "SELECT productname FROM products WHERE productcode='" + code + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public String getSuppName(int ID) {
        String name = null;

        try {
            String query = "SELECT fullname FROM suppliers " +
                "INNER JOIN purchaseinfo ON suppliers.suppliercode=purchaseinfo.suppliercode " +
                "WHERE purchaseid='" + ID + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                name = resultSet.getString("fullname");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return name;
    }

    public String getCustName(int ID) {
        String name = null;
        try {
            String query = "SELECT fullname FROM customers " +
                "INNER JOIN salesinfo ON customers.customercode=salesinfo.customercode " +
                "WHERE salesid='" + ID + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                name = resultSet.getString("fullname");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return name;
    }

    public String getPurchaseDate(int ID) {
        String date = null;
        try {
            String query = "SELECT date FROM purchaseinfo WHERE purchaseid='" + ID + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                date = resultSet.getString("date");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return date;
    }

    public String getSaleDate(int ID) {
        String date = null;
        try {
            String query = "SELECT date FROM salesinfo WHERE salesid='" + ID + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                date = resultSet.getString("date");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return date;
    }


    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int col = 1; col <= colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
