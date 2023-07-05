package org.softwareengine.modules.finance.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.core.model.TransactionsType;
import org.softwareengine.core.repos.TransactionsRepo;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.modules.finance.repositories.TreasuryRepository;
import org.softwareengine.modules.finance.view.expensesView;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.modules.statistics.model.Transaction;
import org.softwareengine.utils.ui.FXDialogSuccess;

import java.sql.SQLException;
import java.util.IllegalFormatCodePointException;
import java.util.UUID;

public class ExpensesController extends expensesView {


    public TransactionsRepo repositors ;
    public TreasuryRepository treasuryRepository ;
    public Stage primaryStage ;
    public ExpensesController(Stage stage) {
        repositors = new TransactionsRepo();
        treasuryRepository = new TreasuryRepository();
        primaryStage = stage ;
        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void init() throws SQLException {

        expensesTreasury.itemsProperty().bindBidirectional(HomeController.treasuryController.treasuryListView.itemsProperty());
        expensesTreasury.setConverter(new StringConverter<Treasury>() {
            @Override
            public String toString(Treasury object) {
                if (object == null)
                    return null ;
                else
                return  object.name() ;
            }
            @Override
            public Treasury fromString(String string) {
                return null;
            }
        });
        expensesDesc.setOnKeyPressed(Foxing());
        expensesValue.setOnKeyPressed(Foxing());
        save.setOnAction(onSave());

    }
    private EventHandler<KeyEvent> Foxing() {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() != KeyCode.ENTER)
                    return;

                if (event.getSource().equals(expensesDesc)) {
                    expensesValue.requestFocus();
                    return;
                }
                if (event.getSource().equals(expensesValue))
                    save.requestFocus();


            }
        };
    }

    private void clear() {
        expensesTreasury.getSelectionModel().clearSelection();
        expensesValue.clear();
        expensesDesc.clear();
    }
       private EventHandler<ActionEvent> onSave() {
            return new  EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {


                        repositors.save(new Transaction(
                                UUID.randomUUID().toString(),
                        /*        0,
                                null,*/
                                Double.parseDouble(expensesValue.getText()),
                                /*0,
                                0,
                                null,*/
                                expensesTreasury.getSelectionModel().getSelectedItem() ,
//                                expensesTreasury.getSelectionModel().getSelectedItem().
                                TransactionsType.EXPENSES,
                                /*0,
                                null,
                                null,
                                0,*/
                                PaymentMethod.CASH,
                                expensesDesc.getText()
                        ));

                        clear();
                        FXDialogSuccess fx = new FXDialogSuccess(primaryStage);
                        fx.show();

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            } ;
        } // end of onSave
    }
