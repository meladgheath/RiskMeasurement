package org.softwareengine.modules.sales.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.core.model.TransactionsType;
import org.softwareengine.core.repos.TransactionsRepo;
import org.softwareengine.modules.finance.model.DebtBook;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.modules.finance.repositories.DebtBookRepository;
import org.softwareengine.modules.finance.repositories.TreasuryRepository;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.modules.sales.view.SalePointView;
import org.softwareengine.modules.statistics.model.Transaction;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.modules.warehouse.repositories.ProductsRepository;
import org.softwareengine.modules.warehouse.repositories.StockRepository;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.service.reportService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXDialogSuccess;
import org.softwareengine.utils.ui.FXInfoDialog;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

public class SalePointController extends SalePointView {
    private final StockRepository stockRepository;
    private final TransactionsRepo transactionsRepo;
    private final TreasuryRepository treasuryRepository;
    private ProductsRepository repository ;
    private DebtBookRepository debtBookRepository;

    private int b;

    public SalePointController() {


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

        customerComboBox.itemsProperty().bind(HomeController.customersController.customerListView.itemsProperty());
        customerComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Customer object) {
                return object == null ? null : object.name();
            }

            @Override
            public Customer fromString(String string) {
                return null;
            }
        });

        Pattern doublePattern = Pattern.compile("^$|\\d*|\\d+.\\d*");
//        discountTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));
//        discountTextField.setText("0.0");
        salePriceTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));
        discountTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));

        Pattern integerPattern = Pattern.compile("^$|[1-9]\\d*");

        packageTextField.setTextFormatter(new TextFormatter<>(change -> integerPattern.matcher(change.getControlNewText()).matches() ? change : null));
        packageTextField.setText("1");
        quantityTextField.setTextFormatter(new TextFormatter<>(change -> doublePattern.matcher(change.getControlNewText()).matches() ? change : null));
        quantityTextField.setText("1");

        addStockToListButton.setOnAction(event -> addToStockButtonOnAction());

        stockListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                resetBill();
                return;
            }
            billNumberLabel.setText(b+"");
            codeLabel.setText(observable.getValue().product().code());
            salePriceLabel.setText(observable.getValue().salePrice()+"");
            packageLabel.setText(observable.getValue()._package()+"");
            quantityLabel.setText(observable.getValue().quantity()+"");
            totalLabel.setText(String.valueOf(stockListView.getSelectionModel().getSelectedItem().quantity() * stockListView.getSelectionModel().getSelectedItem()._package() * stockListView.getSelectionModel().getSelectedItem().salePrice()));
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
        printButton.setOnAction(event -> printButtonAction());
        stockListView.getItems().addListener((ListChangeListener<Stock>) listener -> checkoutButton.textProperty().set(String.valueOf(stockListView.itemsProperty().get().stream().mapToDouble(value -> value.salePrice() * (value.quantity() * value._package())).sum())));

        stockRepository = new StockRepository();
        checkoutButton.setOnAction(event -> checkoutButtonOnAction());

        codeTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                salePriceTextField.requestFocus();
                try {
                    var product = stockRepository.getByStore(storeComboBox.getSelectionModel().getSelectedItem()).stream().filter(stock -> stock.product().code().equals(codeTextField.getText())).findAny();
                    product.ifPresent(stock -> salePriceTextField.setText(String.valueOf(stock.salePrice())));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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
        checkBox.setOnAction(event -> {
            repository = new ProductsRepository();
            if (((CheckBox) event.getSource()).isSelected()) {
                try {
            Product p = repository.getByCode(codeTextField.getText());
                    if (p != null)
            packageTextField.setText(stockRepository.getByProduct(p)._package()+"");
                    else
                        packageTextField.setText("1");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }else
                packageTextField.setText("1");


        });
        addStockToListButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addToStockButtonOnAction();
        });


        transactionsRepo = new TransactionsRepo();

        if (!storeComboBox.getItems().isEmpty())
            storeComboBox.getSelectionModel().select(0);
        if (!treasuryComboBox.getItems().isEmpty())
            treasuryComboBox.getSelectionModel().select(0);

        dialogListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Stock item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.product().name());
            }
        });
        dialogTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                try {
                    dialogListView.setItems(stockRepository.getByStore(storeComboBox.getSelectionModel().getSelectedItem()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            dialogListView.setItems(FXCollections.observableArrayList(dialogListView.getItems().stream().filter(stock -> stock.product().name().contains(newValue)).toList()));
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

        returnBillButton.setOnAction(event-> onReturnButton());

        debtBookRepository = new DebtBookRepository();

        try {
            b = transactionsRepo.getLastBillNumber(TransactionsType.SALE)+1 ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void onReturnButton () {
        FXDialogContent fx = new FXDialogContent(root.getScene().getWindow());
        fx.setContent(returnDialog);
        fx.primaryButton.textProperty().bind(LocaleService.getKey("return"));
        fx.primaryButton.setOnAction(event -> returnAction());
        fx.show();
    }

    private void returnAction() {

    }
    private void settingsButtonOnAction() {
        settingsDialogContent = new FXDialogContent(root.getScene().getWindow());
        settingsDialogContent.primaryButton.textProperty().bind(LocaleService.getKey("ok"));
        settingsDialogContent.setContent(settingsDialogSurface);
        settingsDialogContent.setSize(400, 500);
        settingsDialogContent.show();

        settingsDialogContent.primaryButton.setOnAction(event -> settingsDialogContent.close());
    }

    private void printButtonAction()  {
        int size = stockListView.getItems().size();
        int i  = 0 ;
        for (Stock stoke :stockListView.getItems()) {
            try {
                System.out.println(stoke._package());
                stockRepository.incrementQuantity(stoke);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        stockListView.getItems().clear();
        resetBill();
        resetStockInputs();

    }
    private void addButtonOnAction() {
        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("add"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 620);
        dialogContent.show();

        dialogContent.primaryButton.setOnAction(event -> {
            codeTextField.setText(dialogListView.getSelectionModel().getSelectedItem().product().code());
            salePriceTextField.setText(String.valueOf(dialogListView.getSelectionModel().getSelectedItem().salePrice()));
            dialogContent.close();
        });
        salePriceTextField.requestFocus();

        try {
            dialogListView.setItems(stockRepository.getByStore(storeComboBox.getSelectionModel().getSelectedItem()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearButtonOnAction() {
        stockListView.getItems().clear();
        resetBill();
        resetStockInputs();
    }

    private void checkoutButtonOnAction() {
        if (stockListView.getItems().isEmpty()) return;
        try {
            int billNum = transactionsRepo.getLastBillNumber(TransactionsType.SALE) + 1;

            if (paymentMethodComboBox.getSelectionModel().getSelectedItem() == PaymentMethod.LATER) {
                if (customerComboBox.getSelectionModel().isEmpty()){
                    FXInfoDialog infoDialog = new FXInfoDialog();
                    infoDialog.message.textProperty().bind(LocaleService.getKey("choose-customer-msg"));
                    infoDialog.setWidth(400);
                    infoDialog.setHeight(300);
                    infoDialog.okButton.setOnAction(event -> infoDialog.close());
                    infoDialog.show();
                    return;
                }
                debtBookRepository.add(new DebtBook(
                        UUID.randomUUID().toString(),
                        customerComboBox.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(checkoutButton.getText()),
                        0
                ));
                HomeController.debtBookController.debtBookListView.setItems(HomeController.debtBookController.debtBookRepository.getAll());
            }

            for (Stock stock : stockListView.getItems()) {
                transactionsRepo.save(new Transaction(
                        UUID.randomUUID().toString(),
                        0,
                        stock.product(),
                        0,
                        stock.salePrice(),
                        stock.quantity(),
                        storeComboBox.getSelectionModel().getSelectedItem(),
                        treasuryComboBox.getSelectionModel().getSelectedItem(),
                        TransactionsType.SALE,
                        billNum,
                        customerComboBox.getSelectionModel().isEmpty() ? null : customerComboBox.getSelectionModel().getSelectedItem(),
                        null,
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
                stockRepository.decrementQuantity(newStock);
            }

            if (paymentMethodComboBox.getSelectionModel().getSelectedItem() == PaymentMethod.CASH)
                treasuryRepository.incrementAmount(treasuryComboBox.getSelectionModel().getSelectedItem(), Double.parseDouble(checkoutButton.getText()));

            HomeController.treasuryController.treasuryListView.setItems(HomeController.treasuryController.treasuryRepository.getAll());
            HomeController.storesController.storeListView.setItems(HomeController.storesController.storesRepository.getAll());
            new FXDialogSuccess(root.getScene().getWindow()).show();
            stockListView.getItems().clear();
            resetStockInputs();
            resetBill();
            BillsReport(billNum);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    private void BillsReport(int bill) throws SQLException, JRException {
        Alert message = new Alert(Alert.AlertType.CONFIRMATION);
        message.setTitle("عرض فاتورة");
        message.setHeaderText("هل تريد عرض فاتورة ؟ ");
        message.setContentText("إذا ضغطت علي زر ok سيتم عرض فاتورة و إلا فلا . . . ");
        Optional<ButtonType> t =  message.showAndWait() ;
        reportService r = new reportService();
        if (t.get().getButtonData().isDefaultButton())
        JasperViewer.viewReport(r.getDistrubumentReport(transactionsRepo.getBillReport(bill),"عرض فاتورة"),false);

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

    private void addToStockButtonOnAction() {
        if (storeComboBox.getSelectionModel().isEmpty()
                || treasuryComboBox.getSelectionModel().isEmpty()
                || codeTextField.getText().trim().isEmpty()
                || salePriceTextField.getText().isEmpty()
                || packageTextField.getText().isEmpty()
                || quantityTextField.getText().isEmpty())
            return;

        try {
            Stock oneStock = stockRepository.getByCode(codeTextField.getText());
            if (oneStock == null)
                return;

            Stock existsStock = stockListView.getItems().stream().filter(stock -> stock.product().code()
                    .equals(codeTextField.getText())).findFirst().orElse(null);

            if (!stockListView.getItems().contains(existsStock)) {
                Stock newStock = new Stock(UUID.randomUUID().toString(),
                        storeComboBox.getSelectionModel().getSelectedItem(),
                        oneStock.product(),
                        oneStock.purchasePrice(),
                        Double.parseDouble(salePriceTextField.getText()),
                        Double.parseDouble(packageTextField.getText()),
                        Double.parseDouble(quantityTextField.getText())) ;
                stockListView.getItems().add(newStock);
                stockListView.getSelectionModel().select(newStock);
            } else {
                Stock replacedStock = new Stock(stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).id(),
                        stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).store(),
                        stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).product(),
                        stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).purchasePrice(),
                        stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).salePrice(),
                        stockListView.getItems().get(stockListView.getItems().indexOf(existsStock))._package(), stockListView.getItems().get(stockListView.getItems().indexOf(existsStock)).quantity() + Double.parseDouble(quantityTextField.getText()));
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
        salePriceLabel.setText(null);
        packageLabel.setText(null);
        quantityLabel.setText(null);
        totalLabel.setText("0.00");
        customerComboBox.getSelectionModel().clearSelection();
        paymentMethodComboBox.getSelectionModel().select(0);
        discountTextField.setText("0");
    }

    private void resetStockInputs() {
        codeTextField.clear();
        salePriceTextField.clear();
        packageTextField.setText("1");
        quantityTextField.setText("1");
        checkBox.setSelected(false);
    }

    public void onSceneChanged() {
        codeTextField.requestFocus();
    }
}
