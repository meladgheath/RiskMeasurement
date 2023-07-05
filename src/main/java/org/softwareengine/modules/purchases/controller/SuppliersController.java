package org.softwareengine.modules.purchases.controller;

import javafx.scene.control.ListCell;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.purchases.repositories.SuppliersRepository;
import org.softwareengine.modules.purchases.view.SuppliersView;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXDialogSuccess;
import org.softwareengine.utils.ui.FXInfoDialog;

import java.sql.SQLException;
import java.util.UUID;

public class SuppliersController extends SuppliersView {
    public final SuppliersRepository suppliersRepository;

    public SuppliersController() {
        suppliersRepository = new SuppliersRepository();
        initListView();

        addButton.setOnAction(event -> addButtonOnAction());
        editButton.setOnAction(event -> editButtonOnAction());
        deleteButton.setOnAction(event -> deleteButtonOnAction());
    }

    private void deleteButtonOnAction() {
        if (supplierListView.getSelectionModel().getSelectedItem() == null) return;

        dialogDelete = new FXDialogDelete(root.getScene().getWindow());
        dialogDelete.setMessage(LocaleService.getKey("delete-message"));
        dialogDelete.show();
        dialogDelete.deleteButton.setOnAction(event -> {
            try {
                suppliersRepository.delete(supplierListView.getSelectionModel().getSelectedItem());
                dialogDelete.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                supplierListView.setItems(suppliersRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nameTextField.clear();
            phoneTextField.clear();
        });
    }

    private void editButtonOnAction() {
        if (supplierListView.getSelectionModel().getSelectedItem() == null) return;

        nameTextField.setText(supplierListView.getSelectionModel().getSelectedItem().name());
        phoneTextField.setText(supplierListView.getSelectionModel().getSelectedItem().phone());
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("edit-supplier"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("edit"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 300);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) return;
            try {
                suppliersRepository.update(new Supplier(
                        supplierListView.getSelectionModel().getSelectedItem().id(),
                        nameTextField.getText(),
                        phoneTextField.getText()
                ));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                supplierListView.setItems(suppliersRepository.getAll());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nameTextField.clear();
            phoneTextField.clear();
        });
    }

    private void addButtonOnAction() {
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("add-supplier"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("add"));
        dialogContent.setContent(dialogSurface);
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
                if (suppliersRepository.getByName(nameTextField.getText()) != null
                        || HomeController.customersController.customersRepository.getByName(nameTextField.getText()) != null) {
                    FXInfoDialog infoDialog = new FXInfoDialog();
                    infoDialog.message.textProperty().bind(LocaleService.getKey("name-exist-msg"));
                    infoDialog.setWidth(400);
                    infoDialog.setHeight(300);
                    infoDialog.okButton.setOnAction(e -> infoDialog.close());
                    infoDialog.show();
                    return;
                }

                suppliersRepository.save(new Supplier(
                        UUID.randomUUID().toString(),
                        nameTextField.getText(),
                        phoneTextField.getText()
                ));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                supplierListView.setItems(suppliersRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nameTextField.clear();
            phoneTextField.clear();
        });
    }

    private void initListView() {
        try {
            supplierListView.setItems(suppliersRepository.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        supplierListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Supplier item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.name());
            }
        });
        supplierListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            nameLabel.setText(supplierListView.getSelectionModel().getSelectedItem().name());
            phoneLabel.setText(supplierListView.getSelectionModel().getSelectedItem().phone());
        });
        if (!supplierListView.getItems().isEmpty())
            supplierListView.getSelectionModel().select(0);
    }
}
