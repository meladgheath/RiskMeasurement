package org.softwareengine.modules.sales.controller;

import javafx.scene.control.ListCell;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.modules.sales.repositories.CustomersRepository;
import org.softwareengine.modules.sales.view.CustomersView;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXDialogSuccess;
import org.softwareengine.utils.ui.FXInfoDialog;

import java.sql.SQLException;
import java.util.UUID;

public class CustomersController extends CustomersView {
    public final CustomersRepository customersRepository;

    public CustomersController() {
        customersRepository = new CustomersRepository();
        initListView();

        addButton.setOnAction(event -> addButtonOnAction());
        editButton.setOnAction(event -> editButtonOnAction());
        deleteButton.setOnAction(event -> deleteButtonOnAction());
    }

    private void deleteButtonOnAction() {
        if (customerListView.getSelectionModel().getSelectedItem() == null) return;

        dialogDelete = new FXDialogDelete(root.getScene().getWindow());
        dialogDelete.setMessage(LocaleService.getKey("delete-message"));
        dialogDelete.show();
        dialogDelete.deleteButton.setOnAction(event -> {
            try {
                customersRepository.delete(customerListView.getSelectionModel().getSelectedItem());
                dialogDelete.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                customerListView.setItems(customersRepository.getAll());
                clearLabels();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void editButtonOnAction() {
        if (customerListView.getSelectionModel().getSelectedItem() == null) return;

        nameTextField.setText(customerListView.getSelectionModel().getSelectedItem().name());
        phoneTextField.setText(customerListView.getSelectionModel().getSelectedItem().phone());
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("edit-customer"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.setContent(dialogSurface);
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("edit"));
        dialogContent.setSize(400, 300);
        dialogContent.show();

        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) return;
            try {
                customersRepository.update(new Customer(customerListView.getSelectionModel().getSelectedItem().id(), nameTextField.getText(), phoneTextField.getText()));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                customerListView.setItems(customersRepository.getAll());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            clearInputs();
        });
    }

    private void addButtonOnAction() {
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("add-customer"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.setContent(dialogSurface);
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("add"));
        dialogContent.setSize(400, 300);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) {
                FXInfoDialog infoDialog = new FXInfoDialog();
                infoDialog.message.textProperty().bind(LocaleService.getKey("empty-name-msg"));
                infoDialog.setWidth(400);
                infoDialog.setHeight(300);
                infoDialog.okButton.setOnAction(e -> infoDialog.close());
                infoDialog.show();
                return;
            }
            try {
                if (customersRepository.getByName(nameTextField.getText()) != null
                        || HomeController.suppliersController.suppliersRepository.getByName(nameTextField.getText()) != null) {
                    FXInfoDialog infoDialog = new FXInfoDialog();
                    infoDialog.message.textProperty().bind(LocaleService.getKey("name-exist-msg"));
                    infoDialog.setWidth(400);
                    infoDialog.setHeight(300);
                    infoDialog.okButton.setOnAction(e -> infoDialog.close());
                    infoDialog.show();
                    return;
                }

                customersRepository.save(new Customer(UUID.randomUUID().toString(), nameTextField.getText(), phoneTextField.getText()));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                customerListView.setItems(customersRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            clearInputs();
        });
    }

    private void initListView() {
        try {
            customerListView.setItems(customersRepository.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.name());
            }
        });
        customerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            nameLabel.setText(customerListView.getSelectionModel().getSelectedItem().name());
            phoneLabel.setText(customerListView.getSelectionModel().getSelectedItem().phone());
        });
        if (!customerListView.getItems().isEmpty()) customerListView.getSelectionModel().select(0);
    }

    private void clearLabels() {
        nameLabel.setText(null);
        phoneLabel.setText(null);
    }

    private void clearInputs() {
        nameTextField.clear();
        phoneTextField.clear();
    }
}