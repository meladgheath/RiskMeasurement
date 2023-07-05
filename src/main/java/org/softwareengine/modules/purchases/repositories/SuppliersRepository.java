package org.softwareengine.modules.purchases.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SuppliersRepository {
    public void save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier (id, name, phone) VALUES (?, ?, ?)";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

        ps.setString(1, supplier.id());
        ps.setString(2, supplier.name());
        ps.setString(3, supplier.phone());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public Supplier getByName(String name) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE name = ?";

        DatabaseService.openConnection();
        PreparedStatement preparedStatement = DatabaseService.connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        Supplier supplier = null;
        while (resultSet.next()) {
            supplier = new Supplier(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone")
            );
        }
        DatabaseService.closeConnection();
        return supplier;
    }

    public ObservableList<Supplier> getAll() throws SQLException {
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM supplier";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            list.add(new Supplier(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone")
            ));
        }
        DatabaseService.closeConnection();
        return list;
    }

    public void update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET name = ?, phone = ? WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, supplier.name());
        ps.setString(2, supplier.phone());
        ps.setString(3, supplier.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void delete(Supplier supplier) throws SQLException {
        String sql = "DELETE FROM supplier WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, supplier.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }
}
