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
    Statement statement = null;
    ResultSet resultSet = null;

    // Stock availability of certain product in inventory
    boolean available = false;

    public ProductController() {
        try {
            connection = new DatabaseInstance().getConnection();
            statement = connection.createStatement();
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
        java.sql.Date date = new java.sql.Date(productModel.getExpirationDate().getTime());
        // check if product already exists
        try {
            String duplicateQuery = "SELECT * FROM products WHERE product_name='"
                + productModel.getProductName()
                + "' AND cost_price='"
                + productModel.getCostPrice()
                + "' AND sell_price='"
                + productModel.getSellPrice()
                + "' AND supplied_by='"
                + productModel.getSuppliedBy()
                + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Product record already exists.");
            } else {
                String productQuery = "INSERT INTO products VALUES(null,?,?,?,?,?,?,?,?,DEFAULT)";
                preparedStatement = connection.prepareStatement(productQuery);

                preparedStatement.setString(1, productModel.getProductCode());
                preparedStatement.setString(2, productModel.getProductName());
                preparedStatement.setString(3, productModel.getDescription());
                preparedStatement.setDouble(4, productModel.getCostPrice());
                preparedStatement.setDouble(5, productModel.getSellPrice());
                preparedStatement.setInt(6, productModel.getQuantity());
                preparedStatement.setString(7, productModel.getSuppliedBy());
                preparedStatement.setDate(8, date);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRestockInfo(ProductModel productModel) {
        java.sql.Date date = new java.sql.Date(productModel.getDate().getTime());

        try {
            String query = "INSERT INTO purchaseinfo VALUES(null,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productModel.getSupplierCode());
            preparedStatement.setString(2, productModel.getProductCode());
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, productModel.getQuantity());
            preparedStatement.setDouble(5, productModel.getTotalCost());

            preparedStatement.executeUpdate();
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
    }

    /**
     * Update/Edit existing products
     *
     * @param productModel product model object
     */
    public void updateProduct(ProductModel productModel) {
        java.sql.Date date = new java.sql.Date(productModel.getExpirationDate().getTime());

        try {
            String productQuery = """
                UPDATE products
                SET product_code=?,product_name=?,description=?,cost_price=?,
                    sell_price=?,quantity=?,expiration_date=?,supplied_by=?
                WHERE pid=?;""";
            preparedStatement = connection.prepareStatement(productQuery);

            preparedStatement.setString(1, productModel.getProductCode());
            preparedStatement.setString(2, productModel.getProductName());
            preparedStatement.setString(3, productModel.getDescription());
            preparedStatement.setDouble(4, productModel.getCostPrice());
            preparedStatement.setDouble(5, productModel.getSellPrice());
            preparedStatement.setInt(6, productModel.getQuantity());
            preparedStatement.setDate(7, date);
            preparedStatement.setString(8, productModel.getSuppliedBy());

            preparedStatement.setInt(9, productModel.getProductId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Handling of stocks in Purchase page (-)
     * when a restocking info is deleted
     *
     * @param code     product code
     * @param quantity quantity of product
     */
    public void reduceProductStock(String code, int quantity) {
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
     * Handling of stocks in Sales page (+)
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
     * Delete product from the database.
     *
     * @param pid product id to be deleted
     */
    public void deleteProduct(int pid) {
        try {
            String query = "DELETE FROM products WHERE pid=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pid);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRestockInfo(int id) {
        try {
            String query = "DELETE FROM purchaseinfo WHERE purchase_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSalesInfo(int id) {
        try {
            String query = "DELETE FROM salesinfo WHERE sales_id=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sales transaction handling
    public void sellProduct(ProductModel productModel, String username) {
        java.sql.Date date = new java.sql.Date(productModel.getDate().getTime());
        int quantity = 0;

        try {
            String query = "SELECT * FROM products WHERE product_code='" + productModel.getProductCode() + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
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

                String salesQuery = "INSERT INTO salesinfo(date,product_code,customer_code,quantity,revenue,sold_by)"
                    + "VALUES('" + date + "','" + productModel.getProductCode() + "','" + productModel.getCustomerCode()
                    + "','" + productModel.getQuantity() + "','" + productModel.getTotalRevenue() + "','" + username + "')";

                statement.executeUpdate(stockQuery);
                statement.executeUpdate(salesQuery);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Products data set retrieval for display
    public ResultSet getProducts() {
        try {
            String query = """
                SELECT pid, product_code, product_name, description, quantity, cost_price, sell_price, supplied_by, expiration_date, last_updated FROM products;
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
                SELECT purchase_id, purchaseinfo.product_code,product_name,purchaseinfo.quantity,total_cost,date
                FROM purchaseinfo
                INNER JOIN products
                ON products.product_code=purchaseinfo.product_code
                ORDER BY date DESC;
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
                SELECT sales_id, products.product_name AS products_code,
                customers.full_name AS customer_code, salesinfo.quantity, revenue,
                date, users.name AS sold_by
                FROM salesinfo
                INNER JOIN products ON salesinfo.product_code = products.product_code
                INNER JOIN customers ON salesinfo.customer_code = customers.customer_code
                INNER JOIN users ON salesinfo.sold_by = users.name
                ORDER BY date DESC;
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
            String query = "SELECT pid, product_code, product_name, description, quantity, cost_price, sell_price, supplied_by, expiration_date, last_updated FROM products "
                + "WHERE product_code LIKE '%" + text + "%' OR product_name LIKE '%" + text + "%' OR supplied_by LIKE '%" + text + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getProdFromCode(String text) {
        try {
            String query = "SELECT * FROM products "
                + "WHERE product_code='" + text + "' LIMIT 1";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Search method for sales
    public ResultSet getSalesSearch(String text) {
        try {
            String query = """
                SELECT sales_id, products.product_name AS products_code,
                customers.full_name AS customer_code, salesinfo.quantity, revenue,
                date, users.name AS sold_by
                FROM salesinfo
                INNER JOIN products ON salesinfo.product_code = products.product_code
                INNER JOIN customers ON salesinfo.customer_code = customers.customer_code
                INNER JOIN users ON salesinfo.sold_by = users.name
                WHERE salesinfo.product_code LIKE '%""" + text + "%'"
                + "OR product_name LIKE '%" + text + "%' "
                + "OR users.name LIKE '%" + text + "%'"
                + "OR customers.full_name LIKE '%" + text + "%'"
                + "ORDER BY sales_id;";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // Search method for purchase logs
    public ResultSet getRestockSearch(String text) {
        try {
            String query = "SELECT purchase_id,purchaseinfo.product_code,products.product_name,quantity,total_cost\n"
                + "FROM purchaseinfo INNER JOIN products ON purchaseinfo.product_code=products.product_code\n"
                + "INNER JOIN suppliers ON purchaseinfo.supplier_code=suppliers.supplier_code\n"
                + "WHERE purchase_id LIKE '%" + text + "%' OR product_code LIKE '%" + text + "%' OR product_name LIKE '%" + text + "%'\n"
                + "OR suppliers.full_name LIKE '%" + text + "%' OR purchaseinfo.supplier_code LIKE '%" + text + "%'\n"
                + "OR date LIKE '%" + text + "%' ORDER BY purchase_id";

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
