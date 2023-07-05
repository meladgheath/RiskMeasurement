package org.softwareengine.modules.warehouse.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductsRepository {
    public void save(Product product) throws SQLException {
        String sql = "INSERT INTO product (id, code, name) VALUES (?, ?, ?)";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

        ps.setString(1, product.id());
        ps.setString(2, product.code());
        ps.setString(3, product.name());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public ObservableList<Product> getAll() throws SQLException {
        ObservableList<Product> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            list.add(new Product(
                    resultSet.getString("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name")
            ));
        }
        DatabaseService.closeConnection();
        return list;
    }

    public Product getByCode(String code) throws SQLException {
        ObservableList<Product> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product WHERE code = ?";

        DatabaseService.openConnection();
        PreparedStatement preparedStatement = DatabaseService.connection.prepareStatement(sql);
        preparedStatement.setString(1, code);
        ResultSet resultSet = preparedStatement.executeQuery();
        Product product = null;
        while (resultSet.next()) {
            product = new Product(
                    resultSet.getString("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name")
            );
        }
        DatabaseService.closeConnection();
        return product;
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE product SET code = ?, name = ? WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, product.code());
        ps.setString(2, product.name());
        ps.setString(3, product.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void delete(Product product) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, product.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }
}
