package org.softwareengine.core.repos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.core.model.TransactionsType;
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

public class TransactionsRepo {
    public void save(Transaction transaction) throws SQLException {
        String sql = """
                INSERT INTO transactions (
                    id,
                    product_id,
                    debit,
                    credit,
                    quantity,
                    store_id,
                    treasury_id,
                    transactions_type,
                    bill_num,
                    customer_id,
                    supplier_id,
                    discount,
                    payment_method,
                    description
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)
                """;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);

        ps.setString(1, transaction.id());
        ps.setString(2, transaction.product() == null ? null : transaction.product().id());
        ps.setDouble(3, transaction.debit());
        ps.setDouble(4, transaction.credit());
        ps.setDouble(5, transaction.quantity());
        ps.setString(6, transaction.store() == null ? null : transaction.store().id());
        ps.setString(7, transaction.treasury().id());
        ps.setString(8, transaction.type().toString());
        ps.setInt(9, transaction.billNum());
        ps.setString(10, transaction.customer() == null ? null : transaction.customer().id());
        ps.setString(11, transaction.supplier() == null ? null : transaction.supplier().id());
        ps.setDouble(12, transaction.discount());
        ps.setString(13, transaction.paymentMethod() == null ? null : transaction.paymentMethod().toString());
        ps.setString(14,transaction.desc() == null ? null : transaction.desc());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public int getLastBillNumber(TransactionsType type) throws SQLException {
//        String sql = "SELECT count(bill_num) FROM transactions w ORDER BY created_at DESC LIMIT 1";
        String sql = "SELECT count(bill_num) as b FROM transactions where transactions_type = '"+type.getName()+"'";
        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int billNum = 0;
        System.out.println(sql);
        while (resultSet.next()) {
            billNum = resultSet.getInt("b");
        }
        DatabaseService.closeConnection();
        return billNum;
    }
    public ObservableList<Transaction> getBillReport(int billNum) throws SQLException {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        String sql = "SELECT id , (select name from product where id = product_id) as product " +
                "                , debit , credit , quantity , " +
                "                (select name from store where id = store_id) as store," +
                "                (select name from treasury where id = treasury_id ) as treasury ," +
                "                 transactions_type , bill_num, created_at,updated_at ," +
                "coalesce((select name from customer where id = customer_id),'-----') as customer ," +
                "coalesce((select name from supplier where id = supplier_id),'-----') as supplier ," +
                "payment_method, discount FROM transactions where bill_num = "+billNum;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int i = 0 ;
        while (resultSet.next()) {
            list.add(new Transaction(
                    resultSet.getString("id"),
                    ++i,
                    new Product(null,null,resultSet.getString("product")),
                    resultSet.getDouble("debit"),
                    resultSet.getDouble("credit"),
                    resultSet.getInt("quantity"),
                    new Store(null,resultSet.getString("store")),
                    new Treasury(null,resultSet.getString("treasury"),0),
                    TransactionsType.valueOf(resultSet.getString("transactions_type")),
                    resultSet.getInt("bill_num"),
                    new Customer(null,resultSet.getString("customer"),null),
                    new Supplier(null,resultSet.getString("supplier"),null),
                    resultSet.getDouble("discount"),
                    PaymentMethod.valueOf(resultSet.getString("payment_method"))
            ));
        }
        DatabaseService.closeConnection();
        return list;
    }
}