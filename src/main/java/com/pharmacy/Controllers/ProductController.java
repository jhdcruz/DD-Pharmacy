package com.pharmacy.Controllers;

import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Models.ProductModel;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


    public Double getProductSellPrice(String productCode) {
        Double sellPrice = null;

        try {
            String query = "SELECT sell_price FROM products WHERE product_code='" + productCode + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                sellPrice = resultSet.getDouble("sell_price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sellPrice;
    }


    public String getSupplierCode(String supplierName) {
        try {
            String query = "SELECT supplier_code FROM suppliers WHERE full_name='" + supplierName + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                supplierCode = resultSet.getString("supplier_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierCode;
    }


    public boolean checkStock(String productCode) {
        try {
            String query = "SELECT quantity FROM products WHERE product_code='" + productCode + "'";
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
            String duplicateQuery = "SELECT * FROM products WHERE product_name='"
                + productModel.getProductName()
                + "' AND cost_price='"
                + productModel.getCostPrice()
                + "' AND sell_price='"
                + productModel.getSellPrice()
                + "' AND brand='"
                + productModel.getBrand()
                + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Product record already exists.");
            } else {
                String productQuery = "INSERT INTO products VALUES(null,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(productQuery);
                preparedStatement.setString(1, productModel.getProductCode());
                preparedStatement.setString(2, productModel.getProductName());
                preparedStatement.setDouble(3, productModel.getCostPrice());
                preparedStatement.setInt(3, productModel.getQuantity());
                preparedStatement.setDouble(4, productModel.getSellPrice());
                preparedStatement.setString(5, productModel.getBrand());

                preparedStatement.executeUpdate();
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

        // Check if products with stock already exists
        if (checkStock(prodCode)) { // available = true
            try {
                String query = "UPDATE products SET quantity=quantity+? WHERE product_code=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, productModel.getQuantity());
                preparedStatement.setString(2, prodCode);

                preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        } else if (!checkStock(prodCode)) { // available = false
            addProduct(productModel);
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
            String productQuery = "UPDATE products SET product_name=?,cost_price=?,sell_price=?,brand=?,quantity=? WHERE product_code=?";
            preparedStatement = connection.prepareStatement(productQuery);
            preparedStatement.setString(1, productModel.getProductName());
            preparedStatement.setDouble(2, productModel.getCostPrice());
            preparedStatement.setDouble(3, productModel.getSellPrice());
            preparedStatement.setString(4, productModel.getBrand());
            preparedStatement.setString(5, productModel.getProductCode());
            preparedStatement.setInt(6, productModel.getQuantity());

            preparedStatement.executeUpdate();
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
            String query = "SELECT * FROM products WHERE product_code='" + code + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String query2 = "UPDATE products SET quantity=quantity-? WHERE product_code=?";
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
            String query = "SELECT * FROM products WHERE product_code='" + code + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String query2 = "UPDATE products SET quantity=quantity+? WHERE product_code=?";
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
            String productsQuery = "DELETE FROM products WHERE product_code NOT IN(SELECT product_code FROM purchaseinfo)";
            String salesInfoQuery = "DELETE FROM salesinfo WHERE product_code NOT IN(SELECT product_code FROM products)";

            statement.executeUpdate(productsQuery);
            statement.executeUpdate(salesInfoQuery);
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
            String query = "DELETE FROM products WHERE product_code=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, code);

            String query2 = "DELETE FROM products WHERE product_code=?";
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
            String query = "DELETE FROM purchaseinfo WHERE purchase_id=?";
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
            String query = "DELETE FROM salesinfo WHERE sales_id=?";

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
            String query = "SELECT * FROM products WHERE product_code='" + productModel.getProductCode() + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                prodCode = resultSet.getString("product_code");
                quantity = resultSet.getInt("quantity");
            }

            if (productModel.getQuantity() > quantity) {
                JOptionPane.showMessageDialog(null, "Insufficient stock for this product.");
            } else if (productModel.getQuantity() <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
            } else {
                String stockQuery = "UPDATE products SET quantity=quantity-'"
                    + productModel.getQuantity()
                    + "' WHERE product_code='"
                    + productModel.getProductCode()
                    + "'";

                String salesQuery = "INSERT INTO salesinfo(date,product_code,customer_code,quantity,revenue,sold_by)" +
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
            String query = "SELECT product_code,product_name,cost_price,sell_price,brand FROM products ORDER BY pid";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    // Purchase table data set retrieval
    public ResultSet getPurchaseInfo() {
        try {
            String query = "SELECT purchase_id, purchaseinfo.product_code,product_name,purchaseinfo.quantity,total_cost " +
                "FROM purchaseinfo INNER JOIN products " +
                "ON products.product_code=purchaseinfo.product_code ORDER BY purchase_id;";
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
                SELECT sales_id,salesinfo.product_code,product_name,
                salesinfo.quantity,revenue,users.name AS sold_by
                FROM salesinfo INNER JOIN products
                ON salesinfo.product_code=products.product_code
                INNER JOIN users
                ON salesinfo.sold_by=users.username;
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
            String query = "SELECT product_code,product_name,cost_price,sell_price,brand FROM products " +
                "WHERE product_code LIKE '%" + text + "%' OR product_name LIKE '%" + text + "%' OR brand LIKE '%" + text + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getProdFromCode(String text) {
        try {
            String query = "SELECT product_code,product_name,cost_price,sell_price,brand FROM products " +
                "WHERE product_code='" + text + "' LIMIT 1";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Search method for sales
    public ResultSet getSalesSearch(String text) {
        try {
            String query = "SELECT sales_id,salesinfo.product_code,product_name,\n" +
                "salesinfo.quantity,revenue,users.name AS Sold_by\n" +
                "FROM salesinfo INNER JOIN products\n" +
                "ON salesinfo.product_code=products.product_code\n" +
                "INNER JOIN users\n" +
                "ON salesinfo.sold_by=users.username\n" +
                "INNER JOIN customers\n" +
                "ON customers.customer_code=salesinfo.customer_code\n" +
                "WHERE salesinfo.product_code LIKE '%" + text + "%' OR product_name LIKE '%" + text + "%' " +
                "OR users.name LIKE '%" + text + "%' OR customers.full_name LIKE '%" + text + "%' ORDER BY sales_id;";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Search method for purchase logs
    public ResultSet getPurchaseSearch(String text) {
        try {
            String query = "SELECT purchase_id,purchaseinfo.product_code,products.product_name,quantity,total_cost\n" +
                "FROM purchaseinfo INNER JOIN products ON purchaseinfo.product_code=products.product_code\n" +
                "INNER JOIN suppliers ON purchaseinfo.supplier_code=suppliers.supplier_code\n" +
                "WHERE purchase_id LIKE '%" + text + "%' OR product_code LIKE '%" + text + "%' OR product_name LIKE '%" + text + "%'\n" +
                "OR suppliers.full_name LIKE '%" + text + "%' OR purchaseinfo.supplier_code LIKE '%" + text + "%'\n" +
                "OR date LIKE '%" + text + "%' ORDER BY purchase_id";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getProductName(String code) {
        try {
            String query = "SELECT product_name FROM products WHERE product_code='" + code + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }
}
