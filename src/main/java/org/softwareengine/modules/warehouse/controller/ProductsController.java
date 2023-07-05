package org.softwareengine.modules.warehouse.controller;

import javafx.scene.control.ListCell;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.modules.warehouse.repositories.ProductsRepository;
import org.softwareengine.modules.warehouse.view.ProductsView;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXDialogSuccess;

import java.sql.SQLException;
import java.util.UUID;

public class ProductsController extends ProductsView {
    private final ProductsRepository productsRepository;

    public ProductsController() {
        productsRepository = new ProductsRepository();
        initProductListView();

        addButton.setOnAction(event -> addButtonOnAction());
        editButton.setOnAction(event -> editButtonOnAction());
        deleteButton.setOnAction(event -> deleteButtonOnAction());
    }

    private void deleteButtonOnAction() {
        if (productListView.getSelectionModel().getSelectedItem() == null) return;

        dialogDelete = new FXDialogDelete(root.getScene().getWindow());
        dialogDelete.setMessage(LocaleService.getKey("delete-message"));
        dialogDelete.show();
        dialogDelete.deleteButton.setOnAction(event -> {
            try {
                productsRepository.delete(productListView.getSelectionModel().getSelectedItem());
                dialogDelete.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                productListView.setItems(productsRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            codeTextField.clear();
            nameTextField.clear();
        });
    }

    private void editButtonOnAction() {
        if (productListView.getSelectionModel().getSelectedItem() == null) return;

        codeTextField.setText(productListView.getSelectionModel().getSelectedItem().code());
        nameTextField.setText(productListView.getSelectionModel().getSelectedItem().name());
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("edit-product"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("edit"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 300);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) return;
            try {
                productsRepository.update(new Product(productListView.getSelectionModel().getSelectedItem().id(), codeTextField.getText(), nameTextField.getText()));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                productListView.setItems(productsRepository.getAll());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            codeTextField.clear();
            nameTextField.clear();
        });
    }

    private void addButtonOnAction() {
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("add-product"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("add"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 300);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) return;
            try {
                productsRepository.save(new Product(UUID.randomUUID().toString(), codeTextField.getText(), nameTextField.getText()));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                productListView.setItems(productsRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            codeTextField.clear();
            nameTextField.clear();
        });
    }

    private void initProductListView() {
        try {
            productListView.setItems(productsRepository.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        productListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.name());
            }
        });
        productListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            codeLabel.setText(productListView.getSelectionModel().getSelectedItem().code());
            nameLabel.setText(productListView.getSelectionModel().getSelectedItem().name());
        });
        if (!productListView.getItems().isEmpty()) productListView.getSelectionModel().select(0);
    }
}