package org.softwareengine.modules.statistics.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.softwareengine.modules.statistics.model.Transaction;
//import org.softwareengine.modules.statistics.model.Transactionss;
import org.softwareengine.modules.statistics.repositories.ReportRepositories;
import org.softwareengine.modules.statistics.view.ReportsView;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.service.reportService;

import java.sql.SQLException;

public class ReportsController extends ReportsView {

    public final ReportRepositories repositories;


    public ReportsController() {
        repositories = new ReportRepositories();
        try {
            initListView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    private void initListView() throws SQLException {

        try {
            TableCreation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        printButton.textProperty().bind(LocaleService.getKey("print"));
        printButton.setOnAction(onPrintButton());

    }

    public void refresh() throws SQLException {
        tableView.setItems(repositories.getAll());
    }

    private EventHandler onPrintButton() {
        return event -> {
            reportService r = new reportService();
            try {
                JasperViewer.viewReport(r.getDistrubumentReport(tableView.getItems(),"تقرير كلي"), false);
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void TableCreation() throws SQLException {
        //        TableColumn<transactions,Integer> id = new TableColumn<>("#"); // here is not the real
        TableColumn<Transaction, Integer> seq = new TableColumn<>("#");

        TableColumn<Transaction, String> product_id = new TableColumn<>();
        product_id.textProperty().bind(LocaleService.getKey("products"));

        TableColumn<Transaction,Double> debit = new TableColumn<>();
        debit.textProperty().bind(LocaleService.getKey("debit"));
//
        TableColumn<Transaction,Double> credit = new TableColumn<>();
        credit.textProperty().bind(LocaleService.getKey("credit"));
//
        TableColumn<Transaction,Double> quantity = new TableColumn<>();
        quantity.textProperty().bind(LocaleService.getKey("quantity"));
//
        TableColumn<Transaction,String> store = new TableColumn<>();
        store.textProperty().bind(LocaleService.getKey("store"));
//
        TableColumn<Transaction,String> treasury = new TableColumn<>();
        treasury.textProperty().bind(LocaleService.getKey("treasury"));
//
        TableColumn<Transaction,String> transactions_type = new TableColumn<>();
        transactions_type.textProperty().bind(LocaleService.getKey("transactions-type"));
//
        TableColumn<Transaction,Integer> bill_num = new TableColumn<>();
        bill_num.textProperty().bind(LocaleService.getKey("bill"));
//
//        TableColumn<Transactionss,String> create_at = new TableColumn<>();
//        create_at.textProperty().bind(LocaleService.getKey("created-at"));
//
//        TableColumn<Transactionss,String> update_at  = new TableColumn<>();
//        update_at.textProperty().bind(LocaleService.getKey("updated-at"));
//
        TableColumn<Transaction,String> customer  = new TableColumn<>();
        customer.textProperty().bind(LocaleService.getKey("customer"));
//
        TableColumn<Transaction,String> supplier  = new TableColumn<>();
        supplier.textProperty().bind(LocaleService.getKey("supplier"));
//
        TableColumn<Transaction,String> payment_method  = new TableColumn<>();
        payment_method.textProperty().bind(LocaleService.getKey("payment-method"));


        seq.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().seq()));
        product_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().product().name()));
        debit.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().debit()));
        credit.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().credit()));
        quantity.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().quantity()));
        store.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().store().name()));
        treasury.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().treasury().name()));
        transactions_type.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().type().name()));
        bill_num.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().billNum()));
//        create_at.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCreate_at()));
//        update_at.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUpdate_at()));
        customer.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().customer().name()));
        supplier.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().supplier().name()));
        payment_method.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().paymentMethod().name()));
        seq.setMaxWidth(50);
        seq.setMinWidth(25);

        tableView.getColumns().add(seq);
        tableView.getColumns().add(product_id);
        tableView.getColumns().add(debit);
        tableView.getColumns().add(credit);
        tableView.getColumns().add(quantity);
        tableView.getColumns().add(store);
        tableView.getColumns().add(treasury);
        tableView.getColumns().add(transactions_type);
        tableView.getColumns().add(bill_num);
        tableView.getColumns().add(customer);
        tableView.getColumns().add(supplier);
        tableView.getColumns().add(payment_method);
//        tableView.getColumns().add(create_at);
//        tableView.getColumns().add(update_at);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setItems(repositories.getAll());
    }

}
