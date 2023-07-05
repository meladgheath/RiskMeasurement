package org.softwareengine.modules.finance.controller;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.core.model.TransactionsType;
import org.softwareengine.core.repos.TransactionsRepo;
import org.softwareengine.modules.finance.model.DebtBook;
import org.softwareengine.modules.finance.repositories.DebtBookRepository;
import org.softwareengine.modules.finance.view.DebtBookView;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.modules.statistics.model.Transaction;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.service.reportService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogSuccess;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;

public class DebtBookController extends DebtBookView {
    public final DebtBookRepository debtBookRepository;
    private final TransactionsRepo transactionsRepo;

    public DebtBookController() {
        debtBookRepository = new DebtBookRepository();
        initListView();
        addButton.setOnAction(event -> addButtonOnAction());

        customerComboBox.itemsProperty().bindBidirectional(HomeController.customersController.customerListView.itemsProperty());
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

        Pattern pattern = Pattern.compile("\\d*|\\d+.\\d*");
        debitTextField.setTextFormatter(new TextFormatter<>(change -> pattern.matcher(change.getControlNewText()).matches() ? change : null));
        creditTextField.setTextFormatter(new TextFormatter<>(change -> pattern.matcher(change.getControlNewText()).matches() ? change : null));

        debitTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            creditTextField.setText("0");
            debitTextField.setText(newValue);
        });
        creditTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            debitTextField.setText("0");
            creditTextField.setText(newValue);
        });

        transactionsRepo = new TransactionsRepo();
    }

    private void addButtonOnAction() {
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("add-debt"));
        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("add"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 400);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (customerComboBox.getSelectionModel().getSelectedItem() == null) return;
            if (debitTextField.getText().isEmpty() || creditTextField.getText().isEmpty()) return;
            if (Double.parseDouble(debitTextField.getText()) <= 0 && Double.parseDouble(creditTextField.getText()) <= 0)
                return;
            try {
                DebtBook debtBook = new DebtBook(
                        UUID.randomUUID().toString(),
                        customerComboBox.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(debitTextField.getText()),
                        Double.parseDouble(creditTextField.getText())
                );
                debtBookRepository.add(debtBook);
                transactionsRepo.save(new Transaction(
                        UUID.randomUUID().toString(),
                        0,
                        null,
                        debitTextField.getText().isEmpty() || Double.parseDouble(debitTextField.getText()) <= 0 ? 0 : Double.parseDouble(debitTextField.getText()),
                        creditTextField.getText().isEmpty() || Double.parseDouble(creditTextField.getText()) <= 0 ? 0 : Double.parseDouble(creditTextField.getText()),
                        0,
                        null,
                        HomeController.treasuryController.treasuryListView.getItems().get(0),
                        TransactionsType.DEBT_BOOK,
                        0,
                        customerComboBox.getSelectionModel().getSelectedItem(),
                        null,
                        0,
                        PaymentMethod.CASH
                ));

                if (debitTextField.getText().equals("0"))
                    HomeController.treasuryController.treasuryRepository.decrementAmount(
                            HomeController.treasuryController.treasuryListView.getItems().get(0),
                            Double.parseDouble(creditTextField.getText())
                    );
                else if (creditTextField.getText().equals("0"))
                    HomeController.treasuryController.treasuryRepository.incrementAmount(
                            HomeController.treasuryController.treasuryListView.getItems().get(0),
                            Double.parseDouble(debitTextField.getText())
                    );

                HomeController.treasuryController.treasuryListView.setItems(HomeController.treasuryController.treasuryRepository.getAll());
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                debtBookListView.setItems(debtBookRepository.getAll());

                reportService r = new reportService();
                String title = "";
               if (debtBook.debit()== 0) {
                   title = "إيصال دفع";
                   JasperViewer.viewReport(r.getReport(debtBook.customer().name(), debtBook.credit(), title), false);
               }else
                   if (debtBook.credit() == 0) {
                       title = "إيصال قبض" ;
                       JasperViewer.viewReport(r.getReport(debtBook.customer().name(), debtBook.debit(), title), false);
                   }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (JRException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clearInputs();
            clearLabels();
        });
    }

    private void initListView() {
        try {
            debtBookListView.setItems(debtBookRepository.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        debtBookListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(DebtBook item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle(null);
                } else {
                    setText(item.customer().name() == null ? item.supplier().name() : item.customer().name());
                    getStyleClass().add(item.credit() - item.debit() >= 0 ? "green-row" : "red-row");
                }
            }
        });
        debtBookListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            nameLabel.setText(String.valueOf(debtBookListView.getSelectionModel().getSelectedItem().customer().name()));
            totalDebitLabel.setText(String.valueOf(debtBookListView.getSelectionModel().getSelectedItem().debit()));
            totalCreditLabel.setText(String.valueOf(debtBookListView.getSelectionModel().getSelectedItem().credit()));
            double totalCredit = debtBookListView.getSelectionModel().getSelectedItem().credit() - debtBookListView.getSelectionModel().getSelectedItem().debit();
            if (totalCredit > 0) creditLabel.setText(String.valueOf(totalCredit));
            else creditLabel.setText("0");

            double totalDebit = debtBookListView.getSelectionModel().getSelectedItem().credit() - debtBookListView.getSelectionModel().getSelectedItem().debit();

            if (totalDebit < 0) debitLabel.setText(String.valueOf(totalDebit).replace("-", ""));
            else debitLabel.setText("0");
        });
        if (!debtBookListView.getItems().isEmpty()) debtBookListView.getSelectionModel().select(0);
    }

    private void clearInputs() {
        customerComboBox.getSelectionModel().select(-1);
        debitTextField.clear();
        creditTextField.clear();
    }

    private void clearLabels() {
        debtBookListView.getSelectionModel().select(null);
        nameLabel.setText(null);
        totalCreditLabel.setText(null);
        totalDebitLabel.setText(null);
        creditLabel.setText(null);
        debitLabel.setText(null);
    }
}