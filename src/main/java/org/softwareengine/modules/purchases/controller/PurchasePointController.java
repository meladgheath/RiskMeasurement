package org.softwareengine.modules.purchases.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.core.model.TransactionsType;
import org.softwareengine.core.repos.TransactionsRepo;
import org.softwareengine.modules.finance.model.DebtBook;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.modules.finance.repositories.DebtBookRepository;
import org.softwareengine.modules.finance.repositories.TreasuryRepository;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.purchases.view.PurchasePointView;
import org.softwareengine.modules.statistics.model.Transaction;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.modules.warehouse.repositories.ProductsRepository;
import org.softwareengine.modules.warehouse.repositories.StockRepository;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXDialogSuccess;
import org.softwareengine.utils.ui.FXInfoDialog;

import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;

public class PurchasePointController extends PurchasePointView {
    private final ProductsRepository productsRepository;
    private final StockRepository stockRepository;
    private final TransactionsRepo transactionsRepo;
    private final TreasuryRepository treasuryRepository;
    private final DebtBookRepository debtBookRepository;

    public PurchasePointController(Stage stage) {
        super(stage);
        storeComboBox.itemsProperty().bind(HomeController.storesController.storeListView.itemsProperty());
        storeComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Store object) {
                return object == null ? null : object.name();
            }

            @Override
            public Store fromString(String string) {
                return null;
            }
        });

        treasuryRepository = new TreasuryRepository();
        treasuryComboBox.itemsProperty().bind(HomeController.treasuryController.treasuryListView.itemsProperty());
        treasuryComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Treasury object) {
                return object == null ? null : object.name();
            }

            @Override
            public Treasury fromString(String string) {
                return null;
            }
        });

        supplierComboBox.itemsProperty().bind(HomeController.suppliersController.supplierListView.itemsProperty());
        supplierComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Supplier object) {
                return object == null ? null : object.name();
            }

            @Override
            public Supplier fromString(String string) {
                return null;
            }
        });

        Pattern doublePattern = Pattern.compile("^$|\\d*|\\d+.\\d*");
//        discountTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));
//        discountTextField.setText("0.0");
        purchasePriceTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));
        salePriceTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));
        discountTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));

        Pattern integerPattern = Pattern.compile("^$|[1-9]\\d*");
        packageTextField.setTextFormatter(new TextFormatter<>(change -> integerPattern.matcher(change.getControlNewText()).matches() ? change : null));
        packageTextField.setText("1");
        quantityTextField.setTextFormatter(new TextFormatter<>(change -> integerPattern.matcher(change.getControlNewText()).matches() ? change : null));
        quantityTextField.setText("1");

        addStockToListButton.setOnAction(event -> addToStockListButtonOnAction());
        productsRepository = new ProductsRepository();

        stockListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                resetBill();
                return;
            }
            codeLabel.setText(stockListView.getSelectionModel().getSelectedItem().product().code());
            purchasePriceLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem().purchasePrice()));
            salePriceLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem().salePrice()));
            packageLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem()._package()));
            quantityLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem().quantity()));
//            totalLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem().quantity() * stockListView.getSelectionModel().getSelectedItem()._package() * stockListView.getSelectionModel().getSelectedItem().purchasePrice()));
            totalLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem().quantity() * stockListView.getSelectionModel().getSelectedItem().purchasePrice()));
        });
        stockListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Stock item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.product().name());
            }
        });

        settingsButton.setOnAction(event -> settingsButtonOnAction());
        addButton.setOnAction(event -> addButtonOnAction());
        deleteButton.setOnAction(event -> deleteButtonOnAction());
        clearButton.setOnAction(event -> clearButtonOnAction());
        stockListView.getItems().addListener((ListChangeListener<Stock>)
                listener -> checkoutButton.textProperty().set(String.valueOf(stockListView.itemsProperty().get().stream().mapToDouble(value -> value.purchasePrice() * (value.quantity() ) ).sum())));

        stockRepository = new StockRepository();
        checkoutButton.setOnAction(event -> checkoutButtonOnAction());

        codeTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                purchasePriceTextField.requestFocus();
        });
        purchasePriceTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                salePriceTextField.requestFocus();
        });
        salePriceTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                packageTextField.requestFocus();
        });
        packageTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                quantityTextField.requestFocus();
        });
        quantityTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addStockToListButton.requestFocus();
        });
        addStockToListButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addToStockListButtonOnAction();
        });

        transactionsRepo = new TransactionsRepo();

        if (!storeComboBox.getItems().isEmpty())
            storeComboBox.getSelectionModel().select(0);
        if (!treasuryComboBox.getItems().isEmpty())
            treasuryComboBox.getSelectionModel().select(0);

        dialogListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.name());
            }
        });
        dialogTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                dialogListView.setItems(HomeController.productsController.productListView.getItems());
                return;
            }
            dialogListView.setItems(FXCollections.observableArrayList(dialogListView.getItems().stream().filter(product -> product.name().contains(newValue)).toList()));
        });

        paymentMethodComboBox.setItems(FXCollections.observableArrayList(
                PaymentMethod.CASH,
                PaymentMethod.LATER
        ));
        paymentMethodComboBox.getSelectionModel().select(0);
        paymentMethodComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(PaymentMethod object) {
                if (object == PaymentMethod.CASH)
                    return LocaleService.getKey("cash").get();
                if (object == PaymentMethod.LATER)
                    return LocaleService.getKey("later").get();
                return null;
            }

            @Override
            public PaymentMethod fromString(String string) {
                return null;
            }
        });

        debtBookRepository = new DebtBookRepository();
    }

    private void settingsButtonOnAction() {
        settingsDialogContent = new FXDialogContent(root.getScene().getWindow());
        settingsDialogContent.primaryButton.textProperty().bind(LocaleService.getKey("ok"));
        settingsDialogContent.setContent(settingsDialogSurface);
        settingsDialogContent.setSize(400, 500);
        settingsDialogContent.show();

        settingsDialogContent.primaryButton.setOnAction(event -> settingsDialogContent.close());
    }

    private void addButtonOnAction() {
        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("add"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 620);
        dialogContent.show();

        dialogContent.primaryButton.setOnAction(event -> {
            codeTextField.setText(dialogListView.getSelectionModel().getSelectedItem().code());
            dialogContent.close();
        });
        purchasePriceTextField.requestFocus();

        dialogListView.setItems(HomeController.productsController.productListView.getItems());
    }

    private void clearButtonOnAction() {
        stockListView.getItems().clear();
        resetBill();
        resetStockInputs();
    }

    private void checkoutButtonOnAction() {
        if (stockListView.getItems().isEmpty()) return;

        try {
            int billNum = transactionsRepo.getLastBillNumber(TransactionsType.PURCHASE) + 1;

            if (paymentMethodComboBox.getSelectionModel().getSelectedItem() == PaymentMethod.LATER) {
                if (supplierComboBox.getSelectionModel().isEmpty()) {
                    FXInfoDialog infoDialog = new FXInfoDialog();
                    infoDialog.message.textProperty().bind(LocaleService.getKey("choose-supplier-msg"));
                    infoDialog.setWidth(400);
                    infoDialog.setHeight(300);
                    infoDialog.okButton.setOnAction(event -> infoDialog.close());
                    infoDialog.show();
                    return;
                }
                debtBookRepository.add(new DebtBook(
                        UUID.randomUUID().toString(),
                        supplierComboBox.getSelectionModel().getSelectedItem(),
                        0,
                        Double.parseDouble(checkoutButton.getText())
                ));
                HomeController.debtBookController.debtBookListView.setItems(HomeController.debtBookController.debtBookRepository.getAll());
            }

            for (Stock stock : stockListView.getItems()) {
                transactionsRepo.save(new Transaction(
                        UUID.randomUUID().toString(),
                        0,
                        stock.product(),
                        stock.purchasePrice(),
                        0,
                        stock.quantity(),
                        storeComboBox.getSelectionModel().getSelectedItem(),
                        treasuryComboBox.getSelectionModel().getSelectedItem(),
                        TransactionsType.PURCHASE,
                        billNum,
                        null,
                        supplierComboBox.getSelectionModel().isEmpty() ? null : supplierComboBox.getSelectionModel().getSelectedItem(),
                        discountTextField.getText().isEmpty() ? 0 : Double.parseDouble(discountTextField.getText()),
                        paymentMethodComboBox.getSelectionModel().getSelectedItem()
                ));
                var newStock = new Stock(
                        stock.id(),
                        storeComboBox.getSelectionModel().getSelectedItem(),
                        stock.product(),
                        stock.purchasePrice(),
                        stock.salePrice(),
                        stock._package(),
                        stock.quantity()
                );
                if (stockRepository.getByStore(newStock.store()).stream().anyMatch(s -> s.product().code().equals(stock.product().code())))
                    stockRepository.incrementQuantity(newStock);
                else
                    stockRepository.save(newStock);
            }

            if (paymentMethodComboBox.getSelectionModel().getSelectedItem() == PaymentMethod.CASH)
                treasuryRepository.decrementAmount(treasuryComboBox.getSelectionModel().getSelectedItem(), Double.parseDouble(checkoutButton.getText()));

            HomeController.treasuryController.treasuryListView.setItems(HomeController.treasuryController.treasuryRepository.getAll());
            HomeController.storesController.storeListView.setItems(HomeController.storesController.storesRepository.getAll());
            new FXDialogSuccess(root.getScene().getWindow()).show();
            stockListView.getItems().clear();
            resetStockInputs();
            resetBill();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteButtonOnAction() {
        if (stockListView.getSelectionModel().getSelectedItem() == null) return;

        dialogDelete = new FXDialogDelete(root.getScene().getWindow());
        dialogDelete.setMessage(LocaleService.getKey("delete-message"));
        dialogDelete.show();
        dialogDelete.deleteButton.setOnAction(event -> {
            stockListView.getItems().remove(stockListView.getSelectionModel().getSelectedItem());
            dialogDelete.close();
            new FXDialogSuccess(root.getScene().getWindow()).show();
        });
    }

    private void addToStockListButtonOnAction() {
        if (storeComboBox.getSelectionModel().isEmpty()
                || treasuryComboBox.getSelectionModel().isEmpty()
                || codeTextField.getText().trim().isEmpty()
                || purchasePriceTextField.getText().isEmpty()
                || salePriceTextField.getText().isEmpty()
                || packageTextField.getText().isEmpty()
                || quantityTextField.getText().isEmpty())
            return;

        try {
            Product product = productsRepository.getByCode(codeTextField.getText());
            if (product == null) {
                return;
            }

            Stock existsStock = stockListView.getItems().stream().filter(stock -> stock.product().code().equals(codeTextField.getText())).findFirst().orElse(null);
            if (!stockListView.getItems().contains(existsStock)) {
                int p = Integer.parseInt(packageTextField.getText()) ;
                int q = Integer.parseInt(quantityTextField.getText()) ;

                Stock newStock = new Stock(UUID.randomUUID().toString(), storeComboBox.getSelectionModel().getSelectedItem(), product, Double.parseDouble(purchasePriceTextField.getText()), Double.parseDouble(salePriceTextField.getText()),
                        Integer.parseInt(packageTextField.getText()),
                        p*q);
                stockListView.getItems().add(newStock);
                stockListView.getSelectionModel().select(newStock);
            } else {
                Stock replacedStock = new Stock(stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).id(), storeComboBox.getSelectionModel().getSelectedItem(), stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).product(), stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).purchasePrice(), stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).salePrice(), stockListView.getItems().get(stockListView.getItems().indexOf(existsStock))._package(), stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).quantity() + Integer.parseInt(quantityTextField.getText()));
                stockListView.getItems().set(stockListView.getItems().indexOf(existsStock), replacedStock);
                stockListView.getSelectionModel().select(replacedStock);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resetStockInputs();
        codeTextField.requestFocus();
    }

    private void resetBill() {
        codeLabel.setText(null);
        purchasePriceLabel.setText(null);
        salePriceLabel.setText(null);
        packageLabel.setText(null);
        quantityLabel.setText(null);
        totalLabel.setText("0.00");
        supplierComboBox.getSelectionModel().clearSelection();
        paymentMethodComboBox.getSelectionModel().select(0);
        discountTextField.setText("0");
    }

    private void resetStockInputs() {
        codeTextField.clear();
        purchasePriceTextField.clear();
        salePriceTextField.clear();
        packageTextField.setText("1");
        quantityTextField.setText("1");
    }

    public void onSceneChanged() {
        codeTextField.requestFocus();
    }
}