package org.softwareengine.modules.statistics.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.softwareengine.modules.statistics.model.Transaction;
import org.softwareengine.modules.statistics.view.StoreReportView;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.modules.warehouse.repositories.StockRepository;
import org.softwareengine.modules.warehouse.repositories.StoresRepository;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.service.reportService;

import java.sql.SQLException;

public class StoreReportController extends StoreReportView {

    StoresRepository repository ;
    StockRepository stockRepository ;
    public StoreReportController() {
        repository = new StoresRepository();
        stockRepository = new StockRepository();

        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void init() throws SQLException {

        createTable();
    storeComboBox.setItems(repository.getAll());
    storeComboBox.setConverter(converter());
    storeComboBox.setOnAction(onStoreComboBox());

    }

    public EventHandler<ActionEvent> onStoreComboBox() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    tableView.setItems(stockRepository.getByStore(storeComboBox.getSelectionModel().getSelectedItem() ));
                    reportService report = new reportService();
                    JasperViewer.viewReport(report.getStoreReport(tableView.getItems()));

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (JRException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
    public void createTable() throws SQLException {
        TableColumn<Stock,Integer> seq  = new TableColumn<>("#");
        TableColumn<Stock,String> name  = new TableColumn<>();
        TableColumn<Stock,String> prodcut = new TableColumn<>();
        TableColumn<Stock,Double> amount = new TableColumn<>();


//        payment_method.textProperty().bind(LocaleService.getKey("payment-method"));
        name.textProperty().bind(LocaleService.getKey("name"));
        prodcut.textProperty().bind(LocaleService.getKey("products"));
        amount.textProperty().bind(LocaleService.getKey("amount"));

        seq  .setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().seq()));
        name.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().store().name()));
        prodcut.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().product().name()));
        amount.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().quantity()));

        seq.setMaxWidth(50);
        seq.setMinWidth(25);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getColumns().add(seq);
        tableView.getColumns().add(name);
        tableView.getColumns().add(prodcut);
        tableView.getColumns().add(amount);

//        stockRepository.getByStore(storeComboBox.getSelectionModel().getSelectedItem()) ;
        tableView.setItems(stockRepository.getAll());

    }

    public StringConverter<Store> converter() {
        return new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                if (object == null)
                return null;
                else
                   return object.name();
            }
            @Override
            public Store fromString(String string) {
                return null;
            }
        };
    }
}
