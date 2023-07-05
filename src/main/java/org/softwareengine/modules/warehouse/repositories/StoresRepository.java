package org.softwareengine.modules.warehouse.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StoresRepository {
    public void save(Store store) throws SQLException {
        String sql = "INSERT INTO store (id, name) VALUES (?, ?)";
        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, store.id());
        ps.setString(2, store.name());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public ObservableList<Store> getAll() throws SQLException {
        ObservableList<Store> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM store";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            list.add(new Store(resultSet.getString("id"), resultSet.getString("name")));
        }
        DatabaseService.closeConnection();
        return list;
    }

    public void update(Store store) throws SQLException {
        String sql = "UPDATE store SET name = ? WHERE id = ?";
        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, store.name());
        ps.setString(2, store.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void delete(Store store) throws SQLException {
        String sql = "DELETE FROM store WHERE id = ?";
        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, store.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }
}
