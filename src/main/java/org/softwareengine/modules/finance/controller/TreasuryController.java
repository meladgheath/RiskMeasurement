package org.softwareengine.modules.finance.controller;


import javafx.scene.control.ListCell;
import org.softwareengine.modules.finance.model.Profit_Los;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.modules.finance.repositories.TreasuryRepository;
import org.softwareengine.modules.finance.view.TreasuryView;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXDialogSuccess;

import java.sql.SQLException;
import java.util.UUID;

public class TreasuryController extends TreasuryView {

    public final TreasuryRepository treasuryRepository;

    public TreasuryController() {
        treasuryRepository = new TreasuryRepository();
        initProductListView();

        addButton.setOnAction(event -> addButtonOnAction());
        editButton.setOnAction(event -> editButtonOnAction());
//        deleteButton.setOnAction(event -> deleteButtonOnAction());
    }

    public void onSceneChanged() {
        try {
            treasuryListView.setItems(treasuryRepository.getAll());
//            if (!treasuryListView.getItems().isEmpty()) treasuryListView.getSelectionModel().select(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteButtonOnAction() {
        if (treasuryListView.getSelectionModel().getSelectedItem() == null) return;

        dialogDelete = new FXDialogDelete(root.getScene().getWindow());
        dialogDelete.setMessage(LocaleService.getKey("delete-message"));
        dialogDelete.show();
        dialogDelete.deleteButton.setOnAction(event -> {
            try {
                treasuryRepository.delete(treasuryListView.getSelectionModel().getSelectedItem());
                dialogDelete.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                treasuryListView.setItems(treasuryRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            nameTextField.clear();
        });
    }

    private void editButtonOnAction() {
        if (treasuryListView.getSelectionModel().getSelectedItem() == null) return;

        nameTextField.setText(treasuryListView.getSelectionModel().getSelectedItem().name());
        dialogSurface.getTitle().textProperty().bind(LocaleService.getKey("edit-product"));

        dialogContent = new FXDialogContent(root.getScene().getWindow());
        dialogContent.primaryButton.textProperty().bind(LocaleService.getKey("edit"));
        dialogContent.setContent(dialogSurface);
        dialogContent.setSize(400, 300);
        dialogContent.show();
        dialogContent.primaryButton.setOnAction(event -> {
            if (nameTextField.getText().isEmpty()) return;
            try {
              /*  treasuryRepository.update(new Treasury(
                        treasuryListView.getSelectionModel().getSelectedItem().id(),
                        nameTextField.getText(),
//                        Double.parseDouble(amountLabel.getText())
                ));*/
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                treasuryListView.setItems(treasuryRepository.getAll());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
            if (nameTextField.getText().isEmpty())
                return;
            try {
                treasuryRepository.save(new Treasury(UUID.randomUUID().toString(), nameTextField.getText(), 0));
                dialogContent.close();
                new FXDialogSuccess(root.getScene().getWindow()).show();
                treasuryListView.setItems(treasuryRepository.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            nameTextField.clear();
        });
    }

    private void initProductListView() {
        try {
            treasuryListView.setItems(treasuryRepository.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        treasuryListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Treasury item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) setText(null);
                else setText(item.name());
            }
        });
        treasuryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            try {
                Profit_Los l = treasuryRepository.getInfoByTreasury(treasuryListView.getSelectionModel().getSelectedItem());
                expensesLable.setText(l.expenses()+"");
                debitsLable  .setText(l.debits()+"");
                salesLable   .setText(l.sales()+"");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//            amountLabel.setText(String.valueOf(treasuryListView.getSelectionModel().getSelectedItem().amount()));
        });
        if (!treasuryListView.getItems().isEmpty()) treasuryListView.getSelectionModel().select(0);
    }
}