package org.softwareengine.modules.warehouse.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.modules.warehouse.repositories.StockRepository;
import org.softwareengine.modules.warehouse.repositories.StoresRepository;
import org.softwareengine.modules.warehouse.view.StoresView;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.service.reportService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXDialogSuccess;

import java.sql.SQLException;
import java.util.UUID;

public class StoresController extends StoresView {
    public final StoresRepository storesRepository;
    private final StockRepository stockRepository;

    public StoresController()  {
        storesRepository = new StoresRepository();
        stockRepository = new StockRepository();
        initListView();

        addButton.setOnAction(event -> addButtonOnAction());
        editButton.setOnAction(event -> editButtonOnAction());
        deleteButton.setOnAction(event -> deleteButtonOnAction());
        printButton.setOnAction(printButton());
    }
    private EventHandler<ActionEvent> printButton()  {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                reportService re = new reportService();
                ObservableList<Stock> stocks = null;
                try {
                    if (storeListView.getSelectionModel().isEmpty() )

                        stocks = stockRepository.getAll() ;
                    else

                        stocks = stockListView.getItems() ;


                    JasperViewer.viewReport(re.getStoreReport(stocks),false);

                    clearAll();

                } catch (JRException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        };
    }

    public void clearAll() {
        clearLabels();
        storeListView.getSelectionModel().select(-1);
        stockListView.getItems().removeAll(stockListView.getItems());
    }
    private void deleteButtonOnAction() {
        if (storeListView.getSelectionModel().getSelectedItem() == null) return;

        dialogDelete = new FXDialogDelete(root.getScene().getWindow());
        dialogDelete.setMessage(LocaleService.getKey("store-delete-message"));
        dialogDelete.show();
        dialogDelete.deleteButton.setOnAction(event -> {
            try {
                storesRepository.delete(storeListView.getSelectionModel().getSelectedItem());
                dialogDelete.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                storeListView.setItems(storesRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nameTextField.clear();
        });
    }

    private void editButtonOnAction() {
        if (storeListView.getSelectionModel().getSelectedItem() == null) return;

        nameTextField.setText(storeListView.getSelectionModel().getSelectedItem().name());
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("edit-store"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("edit"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 300);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) return;
            try {
                storesRepository.update(new Store(storeListView.getSelectionModel().getSelectedItem().id(), nameTextField.getText()));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                storeListView.setItems(storesRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nameTextField.clear();
        });
    }

    private void addButtonOnAction() {
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("add-store"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("add"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 300);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) return;
            try {
                storesRepository.save(new Store(UUID.randomUUID().toString(), nameTextField.getText()));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                storeListView.setItems(storesRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nameTextField.clear();
        });
    }

    private void  initListView() {
        try {
            storeListView.setItems(storesRepository.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        storeListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Store item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.name());
            }
        });
        storeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            try {
                ObservableList<Stock> stocks = stockRepository.getByStore(storeListView.getSelectionModel().getSelectedItem());
                stockListView.setItems(stocks);

                stockListView.setCellFactory(param -> new ListCell<>() {
                    @Override
                    protected void updateItem(Stock item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) setText(null);
                        else setText(item.product().name());
                    }
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        stockListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                clearLabels();
                return;
            }
//            codeLabel.setText(stockListView.getSelectionModel().getSelectedItem().product().code());
            codeLabel.setText(observable.getValue().product().code());
            purchasePriceLabel.setText(observable.getValue().purchasePrice()+"");
            salePriceLabel.setText(observable.getValue().salePrice()+"");
            packageLabel.setText(observable.getValue()._package()+"");
            quantityLabel.setText(observable.getValue().quantity()+"");
//            quantityLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem().quantity()));
        });
//        if (!storeListView.getItems().isEmpty()) storeListView.getSelectionModel().select(0);
    }

    private void clearLabels() {
        codeLabel.setText(null);
        purchasePriceLabel.setText(null);
        salePriceLabel.setText(null);
        packageLabel.setText(null);
        quantityLabel.setText(null);
    }
}