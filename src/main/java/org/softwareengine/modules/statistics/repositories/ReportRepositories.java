package org.softwareengine.modules.statistics.repositories;

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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportRepositories {

    public ObservableList<Transaction> getAll() throws SQLException {
        ObservableList<Transaction> list = FXCollections.observableArrayList();
        String sql = "SELECT id , (select name from product where id = product_id) as product " +
                "                , debit , credit , quantity , " +
                "                (select name from store where id = store_id) as store," +
                "                (select name from treasury where id = treasury_id ) as treasury ," +
                "                 transactions_type , bill_num, created_at,updated_at ," +
                "coalesce((select name from customer where id = customer_id),'-----') as customer ," +
                "coalesce((select name from supplier where id = supplier_id),'-----') as supplier ," +
                "payment_method, discount FROM transactions";

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int i = 0 ;

        while (resultSet.next()) {
            System.out.println(resultSet.getString("payment_method"));
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
