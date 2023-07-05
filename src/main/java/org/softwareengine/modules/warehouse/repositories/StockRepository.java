package org.softwareengine.modules.warehouse.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.utils.service.DatabaseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockRepository {
    public void save(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock (id, store_id, product_id, purchase_price, sale_price, package, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);



        ps.setString(1, stock.id());
        ps.setString(2, stock.store().id());
        ps.setString(3, stock.product().id());
        ps.setDouble(4, stock.purchasePrice());
        ps.setDouble(5, stock.salePrice());
        ps.setDouble(6, stock._package());
        ps.setDouble(7, stock.quantity());
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public void incrementQuantity(Stock stock) throws SQLException {
        String sql = "UPDATE stock SET quantity = quantity + ? WHERE store_id = ? AND product_id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setDouble(1, stock.quantity());
        ps.setString(2, stock.store().id());
        ps.setString(3, stock.product().id());
        ps.executeUpdate();
        DatabaseService.closeConnection();
        refresh();
    }

    public void decrementQuantity(Stock stock) throws SQLException {
        String sql = "UPDATE stock SET quantity = quantity - ? WHERE store_id = ? AND product_id = ?";

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setDouble(1, stock.quantity());
        ps.setString(2, stock.store().id());
        ps.setString(3, stock.product().id());
        ps.executeUpdate();
        DatabaseService.closeConnection();

        refresh();
    }

    public void refresh() throws SQLException {
        String sql = "delete from stock where quantity = 0";
        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.executeUpdate();
        DatabaseService.closeConnection();
    }

    public ObservableList<Stock> getByStore(Store store) throws SQLException {
        ObservableList<Stock> list = FXCollections.observableArrayList();
        String sql = """
                SELECT id,
                (SELECT id FROM store WHERE id = stock.store_id) as store_id,
                (SELECT name FROM store WHERE id = stock.store_id) as store_name,
                (SELECT id FROM product WHERE id = stock.product_id) as product_id,
                (SELECT code FROM product WHERE id = stock.product_id) as product_code,
                (SELECT name FROM product WHERE id = stock.product_id) as product_name,
                purchase_price,
                sale_price,
                package,
                quantity
                FROM stock
                WHERE store_id = ?
                """;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, store.id());
        ResultSet resultSet = ps.executeQuery();

        int i = 0 ;
        while (resultSet.next()) {
            list.add(new Stock(
                   ++i,
                    resultSet.getString("id"),
                    new Store(
                            resultSet.getString("store_id"),
                            resultSet.getString("store_name")
                    ),
                    new Product(
                            resultSet.getString("product_id"),
                            resultSet.getString("product_code"),
                            resultSet.getString("product_name")
                    ),
                    resultSet.getDouble("purchase_price"),
                    resultSet.getDouble("sale_price"),
                    resultSet.getDouble("package"),
                    resultSet.getDouble("quantity")
            ));
        }
        DatabaseService.closeConnection();
        return list;
    }

    public Stock getByCode(String code) throws SQLException {
        String sql = """
                SELECT id,
                (SELECT id FROM store WHERE id = stock.store_id) as store_id,
                (SELECT name FROM store WHERE id = stock.store_id) as store_name,
                (SELECT id FROM product WHERE id = stock.product_id) as product_id,
                (SELECT code FROM product WHERE id = stock.product_id) as product_code,
                (SELECT name FROM product WHERE id = stock.product_id) as product_name,
                purchase_price,
                sale_price,
                package,
                quantity
                FROM stock
                WHERE (SELECT code FROM product WHERE id = stock.product_id) = ?
                """;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, code);
        ResultSet resultSet = ps.executeQuery();

        Stock stock = null;
        while (resultSet.next()) {
            stock = new Stock(
                    resultSet.getString("id"),
                    new Store(
                            resultSet.getString("store_id"),
                            resultSet.getString("store_name")
                    ),
                    new Product(
                            resultSet.getString("product_id"),
                            resultSet.getString("product_code"),
                            resultSet.getString("product_name")
                    ),
                    resultSet.getDouble("purchase_price"),
                    resultSet.getDouble("sale_price"),
                    resultSet.getDouble("package"),
                    resultSet.getDouble("quantity")
            );
        }
        DatabaseService.closeConnection();
        return stock;
    }

    public Stock getByProduct(Product product) throws SQLException {
        String sql = """
                SELECT id,
                (SELECT id FROM store WHERE id = stock.store_id) as store_id,
                (SELECT name FROM store WHERE id = stock.store_id) as store_name,
                (SELECT id FROM product WHERE id = stock.product_id) as product_id,
                (SELECT code FROM product WHERE id = stock.product_id) as product_code,
                (SELECT name FROM product WHERE id = stock.product_id) as product_name,
                purchase_price,
                sale_price,
                package,
                quantity
                FROM stock
                WHERE product_id = ?
                """;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
        ps.setString(1, product.id());
        ResultSet resultSet = ps.executeQuery();

        Stock stock = null;
        while (resultSet.next()) {
            stock = new Stock(
                    resultSet.getString("id"),
                    new Store(
                            resultSet.getString("store_id"),
                            resultSet.getString("store_name")
                    ),
                    new Product(
                            resultSet.getString("product_id"),
                            resultSet.getString("product_code"),
                            resultSet.getString("product_name")
                    ),
                    resultSet.getDouble("purchase_price"),
                    resultSet.getDouble("sale_price"),
                    resultSet.getDouble("package"),
                    resultSet.getDouble("quantity")
            );
        }
        DatabaseService.closeConnection();
        return stock;
    }

    public Boolean isHasProduct(Store store , Product product) throws SQLException {
        String sql = """
                SELECT id
                FROM stock
                WHERE product_id = ? and store_id = ?
                """;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
//        ps.setString(1, product.id());
        ps.setString(1,product.id());
        ps.setString(2,store.id());

        ResultSet resultSet = ps.executeQuery();

        boolean yes = resultSet.next() ;


        DatabaseService.closeConnection();
        resultSet.close();

        return yes;
    }

    public int productQuantityInStore(Store store , Product product) throws SQLException {
        String sql = """
                SELECT quantity
                FROM stock
                WHERE product_id = ? and store_id = ?
                """;

        DatabaseService.openConnection();
        PreparedStatement ps = DatabaseService.connection.prepareStatement(sql);
//        ps.setString(1, product.id());
        ps.setString(1,product.id());
        ps.setString(2,store.id());

        ResultSet resultSet = ps.executeQuery();
        int quantity = 0 ;
        if (resultSet.next())
            quantity = resultSet.getInt("quantity");


        DatabaseService.closeConnection();
        resultSet.close();

        return quantity;
    }
    public ObservableList<Stock> getAll() throws SQLException {
        ObservableList<Stock> list = FXCollections.observableArrayList();
        String sql = """
                SELECT id,
                (SELECT id FROM store WHERE id = stock.store_id) as store_id,
                (SELECT name FROM store WHERE id = stock.store_id) as store_name,
                (SELECT id FROM product WHERE id = stock.product_id) as product_id,
                (SELECT code FROM product WHERE id = stock.product_id) as product_code,
                (SELECT name FROM product WHERE id = stock.product_id) as product_name,
                purchase_price,
                sale_price,
                package,
                quantity
                FROM stock
                order by store_name,product_name
                """;

        DatabaseService.openConnection();
        Statement statement = DatabaseService.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int i =  0 ;
        while (resultSet.next()) {
            list.add(new Stock(
                    ++i,
                    resultSet.getString("id"),
                    new Store(
                            resultSet.getString("store_id"),
                            resultSet.getString("store_name")
                    ),
                    new Product(
                            resultSet.getString("product_id"),
                            resultSet.getString("product_code"),
                            resultSet.getString("product_name")
                    ),
                    resultSet.getDouble("purchase_price"),
                    resultSet.getDouble("sale_price"),
                    resultSet.getDouble("package"),
                    resultSet.getDouble("quantity")
            ));
        }
        DatabaseService.closeConnection();
        return list;
    }
}
