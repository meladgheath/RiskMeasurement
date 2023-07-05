package org.softwareengine.modules.finance.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.core.model.TransactionsType;
import org.softwareengine.modules.finance.model.Profit_Los;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.modules.statistics.model.Transaction;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TreasuryRepository {
    public void save(Treasury product) throws SQLException {
        String sql = "INSERT INTO treasury (id, name) VALUES (?, ?)";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

        ps.setString(1, product.id());
        ps.setString(2, product.name());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public ObservableList<Treasury> getAll() throws SQLException {
        ObservableList<Treasury> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM treasury";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            list.add(new Treasury(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("amount")
            ));
        }
        DatabaseService.closeConnection();
        return list;
    }

    public void incrementAmount(Treasury treasury, double amount) throws SQLException {
        String sql = "UPDATE treasury SET  amount = amount + ? WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setDouble(1, amount);
        System.out.println("what is the treasury here "+treasury.id()+"   "+amount);
        ps.setString(2, treasury.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void decrementAmount(Treasury treasury, double amount) throws SQLException {
        String sql = "UPDATE treasury SET  amount = amount - ? WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setDouble(1, amount);
        ps.setString(2, treasury.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void update(Treasury product) throws SQLException {
        String sql = "UPDATE treasury SET  name = ? WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, product.name());
        ps.setString(2, product.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void delete(Treasury treasury) throws SQLException {
        String sql = "DELETE FROM treasury WHERE id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, treasury.id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public Profit_Los getInfoByTreasury(Treasury treasury) throws SQLException {
        ObservableList<Transaction> list = FXCollections.observableArrayList();


        String sql = "SELECT (SELECT SUM(credit - debit) FROM transactions WHERE  transactions_type = 'EXPENSES' and treasury_id = '"+treasury.id()+"' ) as ex ,"+
                "(SELECT SUM(credit - debit) FROM transactions  WHERE transactions_type = 'SALE' and treasury_id = '"+treasury.id()+"') as sales ,\n" +
                "(SELECT SUM(credit - debit) FROM transactions  WHERE transactions_type = 'DEBT_BOOK' and treasury_id = '"+treasury.id()+"') as debit_book,\n" +
                "(SELECT SUM(credit  - debit) FROM transactions where transactions_type = 'PURCHASE' and treasury_id = '"+treasury.id()+"') as purchase\n" +
                ", SUM(credit - debit) as sums  FROM transactions WHERE treasury_id = '"+treasury.id()+"'" ;


        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int i = 0 ;
        resultSet.next();
        Profit_Los l = new Profit_Los(
             resultSet.getDouble("ex"),
             resultSet.getDouble("sales"),
                resultSet.getDouble("debit_book"),
                resultSet.getDouble("purchase"),
                resultSet.getDouble("sums"));

                DatabaseService.closeConnection();
        return l;
    }
}
