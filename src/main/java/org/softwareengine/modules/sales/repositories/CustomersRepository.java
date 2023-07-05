package org.softwareengine.modules.sales.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomersRepository {
    public void save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (id, name, phone) VALUES (?, ?, ?)";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, customer.id());
        ps.setString(2, customer.name());
        ps.setString(3, customer.phone());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public Customer getByName(String name) throws SQLException {
        String sql = "SELECT * FROM customer WHERE name = ?";

        DatabaseService.openConnection();
        PreparedStatement preparedStatement = DatabaseService.connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        Customer customer = null;
        while (resultSet.next()) {
            customer = new Customer(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone")
            );
        }
        DatabaseService.closeConnection();
        return customer;
    }

    public ObservableList<Customer> getAll() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            customers.add(new Customer(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone")
            ));
        }
        DatabaseService.closeConnection();
        return customers;
    }

    public void update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name = ?, phone = ? WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, customer.name());
        ps.setString(2, customer.phone());
        ps.setString(3, customer.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void delete(Customer customer) throws SQLException {
        String sql = "DELETE FROM customer WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, customer.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }
}
