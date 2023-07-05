package org.softwareengine.modules.finance.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.modules.finance.model.DebtBook;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DebtBookRepository {
    private void save(DebtBook debtBook) throws SQLException {
        String sql = "INSERT INTO debt_book (id, customer_id, supplier_id, debit, credit) VALUES (?, ?, ?, ?, ?)";
        DatabaseService.openConnection();
        PreparedStatement pre = DatabaseService.connection.prepareStatement(sql);
        pre.setString(1, debtBook.id());
        pre.setString(2, debtBook.customer() == null ? null : debtBook.customer().id());
        pre.setString(3, debtBook.supplier() == null ? null : debtBook.supplier().id());
        pre.setDouble(4, debtBook.debit());
        pre.setDouble(5, debtBook.credit());
        pre.executeUpdate();
        DatabaseService.connection.close();
    }

    private void updateCustomer(DebtBook debtBook) throws SQLException {
        String sql = "UPDATE debt_book SET debit = debit + ?, credit = credit + ? WHERE customer_id = ?";
        DatabaseService.openConnection();
        PreparedStatement pre = DatabaseService.connection.prepareStatement(sql);
        pre.setDouble(1, debtBook.debit());
        pre.setDouble(2, debtBook.credit());
        pre.setString(3, debtBook.customer().id());
        pre.executeUpdate();
        DatabaseService.connection.close();
    }

    private void updateSupplier(DebtBook debtBook) throws SQLException {
        String sql = "UPDATE debt_book SET debit = debit + ?, credit = credit + ? WHERE supplier_id = ?";
        DatabaseService.openConnection();
        PreparedStatement pre = DatabaseService.connection.prepareStatement(sql);
        pre.setDouble(1, debtBook.debit());
        pre.setDouble(2, debtBook.credit());
        pre.setString(3, debtBook.supplier().id());
        pre.executeUpdate();
        DatabaseService.connection.close();
    }

    public void add(DebtBook debtBook) throws SQLException {
        if (debtBook.customer() != null) {
            if (getCustomer(debtBook.customer()) == null) save(debtBook);
            else updateCustomer(debtBook);
        }
        if (debtBook.supplier() != null) {
            if (getSupplier(debtBook.supplier()) == null) save(debtBook);
            else updateSupplier(debtBook);
        }
    }

    public DebtBook getSupplier(Supplier supplier) throws SQLException {
        String sql = """
                SELECT
                id,
                supplier_id,
                (SELECT name FROM supplier WHERE id = debt_book.supplier_id) as supplier_name,
                (SELECT phone FROM supplier WHERE id = debt_book.supplier_id) as supplier_phone,
                debit,
                credit
                FROM debt_book WHERE supplier_id = ?
                """;
        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, supplier.id());
        ResultSet resultSet = ps.executeQuery();
        DebtBook debtBook = null;
        while (resultSet.next()) {
            debtBook = new DebtBook(
                    resultSet.getString("id"),
                    new Supplier(
                            resultSet.getString("supplier_id"),
                            resultSet.getString("supplier_name"),
                            resultSet.getString("supplier_phone")
                    ),
                    resultSet.getDouble("debit"),
                    resultSet.getDouble("credit")
            );
        }
        DatabaseService.closeConnection();
        return debtBook;
    }

    public DebtBook getCustomer(Customer customer) throws SQLException {
        String sql = """
                SELECT
                id,
                customer_id,
                (SELECT name FROM customer WHERE id = debt_book.customer_id) as customer_name,
                (SELECT phone FROM customer WHERE id = debt_book.customer_id) as customer_phone,
                debit,
                credit
                FROM debt_book WHERE customer_id = ?
                """;
        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, customer.id());
        ResultSet resultSet = ps.executeQuery();
        DebtBook debtBook = null;
        while (resultSet.next()) {
            debtBook = new DebtBook(
                    resultSet.getString("id"),
                    new Customer(
                            resultSet.getString("customer_id"),
                            resultSet.getString("customer_name"),
                            resultSet.getString("customer_phone")
                    ),
                    resultSet.getDouble("debit"),
                    resultSet.getDouble("credit")
            );
        }
        DatabaseService.closeConnection();
        return debtBook;
    }

    public  DebtBook getReminig(Customer customer) throws SQLException {
        String sql = "SELECT SUM(debit - credit) as dc , sum(debit) as d  , sum(credit) as c  FROM transactions WHERE transactions_type = 'DEBT_BOOK' AND customer_id = '"+
                customer.id()+"' " ;



        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next() ;
//        double remin = resultSet.getDouble("dc") ;

        DebtBook d = new DebtBook(
                resultSet.getDouble("d"),
                resultSet.getDouble("c")
        );


        resultSet.close();
        DatabaseService.closeConnection();

        return d ;

    }
    public ObservableList<DebtBook> getAll() throws SQLException {
        ObservableList<DebtBook> debtBooks = FXCollections.observableArrayList();
        String sql = """
                SELECT
                id,
                customer_id,
                (SELECT name FROM customer WHERE id = debt_book.customer_id) as customer_name,
                (SELECT phone FROM customer WHERE id = debt_book.customer_id) as customer_phone,
                supplier_id,
                (SELECT name FROM supplier WHERE id = debt_book.supplier_id) as supplier_name,
                (SELECT phone FROM supplier WHERE id = debt_book.supplier_id) as supplier_phone,
                debit,
                credit
                FROM debt_book
                """;
        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            debtBooks.add(new DebtBook(
                    resultSet.getString("id"),
                    new Customer(
                            resultSet.getString("customer_id"),
                            resultSet.getString("customer_name"),
                            resultSet.getString("customer_phone")
                    ),
                    new Supplier(
                            resultSet.getString("supplier_id"),
                            resultSet.getString("supplier_name"),
                            resultSet.getString("supplier_phone")
                    ),
                    resultSet.getDouble("debit"),
                    resultSet.getDouble("credit")
            ));
        }
        DatabaseService.closeConnection();
        return debtBooks;
    }
}
