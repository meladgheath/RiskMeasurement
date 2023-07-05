package org.softwareengine.modules.warehouse.controller;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import org.softwareengine.core.controller.HomeController;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.modules.warehouse.repositories.StockRepository;
import org.softwareengine.modules.warehouse.repositories.StoresRepository;
import org.softwareengine.modules.warehouse.view.TransformsView;

import java.sql.SQLException;
import java.util.UUID;

public class TransformsController extends TransformsView {


    public StoresRepository repository ;
    public StockRepository stockRepository ;
    public TransformsController() {
        repository = new StoresRepository();
        stockRepository = new StockRepository();

        try {
            init() ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void init() throws SQLException {
        store1.itemsProperty().bindBidirectional(HomeController.storesController.storeListView.itemsProperty());
        store2.itemsProperty().bindBidirectional(HomeController.storesController.storeListView.itemsProperty());

        store1.setConverter(converter());
        store2.setConverter(converter());

        store1.setOnAction(onStore1());
        store2.setOnAction(onStore1());

        stock1.getSelectionModel().selectedItemProperty().addListener(stockListener());
        stock2.getSelectionModel().selectedItemProperty().addListener(stockListener());
        stock1.setOnMouseClicked(getMouseEvent());
        stock2.setOnMouseClicked(getMouseEvent());

        fromButtonLeft.setOnAction(getButtonEvent());
        fromButtonRight.setOnAction(getButtonEvent());


        amount.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                double quantity = 0 ;
                if (stock1.getSelectionModel().isEmpty())
                quantity = stock2.getSelectionModel().getSelectedItem().quantity();
                else
                    if (stock2.getSelectionModel().isEmpty())
                        quantity = stock1.getSelectionModel().getSelectedItem().quantity();

                int amounts = Integer.parseInt(amount.getText()) ;

                if (quantity >= amounts)
                    return;
                amount.setText(quantity+"");

            }
        });

    }

    public EventHandler<ActionEvent> getButtonEvent()  {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Stock stock = null ;
                Store store = null ;
                Store to    = null ;
                if (event.getSource().equals(fromButtonLeft)) {
                    stock = stock1.getSelectionModel().getSelectedItem();
                    store = store1.getSelectionModel().getSelectedItem() ;
                    to    = store2.getSelectionModel().getSelectedItem() ;
                }
                else {
                    stock = stock2.getSelectionModel().getSelectedItem();
                    store = store2.getSelectionModel().getSelectedItem();
                    to    = store1.getSelectionModel().getSelectedItem() ;
                }


                Stock s = new Stock(UUID.randomUUID().toString(),
//                        store2.getSelectionModel().getSelectedItem(),
                        to,
                        stock.product(),
                        stock.purchasePrice(),
                        stock.salePrice(),
                        stock._package(),
                        Integer.parseInt(amount.getText()));
                try {
                    if (stockRepository.productQuantityInStore(store,stock.product()) < Integer.parseInt(amount.getText())) {
                        System.out.println("here the dialog message and clear after that ");
                        System.out.println("القيمة المدخلة أكبر من القيمة الفعلية !!");
                        clear();
                        return;
                    }
                    if(stockRepository.isHasProduct(to,stock.product()))
                    stockRepository.incrementQuantity(s);
                    else
                        stockRepository.save(s);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                s = new Stock(s.id(),
//                        stock1.getSelectionModel().getSelectedItem().store(),
                        store,
                        stock.product(),
                        stock.purchasePrice(),
                        stock.salePrice(),
                        stock._package(),
                        Integer.parseInt(amount.getText()));

                try {
                    stockRepository.decrementQuantity(s);
                    clear() ;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }}} ;

                }
    public EventHandler<MouseEvent> getMouseEvent() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if(event.getSource().equals(stock1)) {
                    stock2.getSelectionModel().select(-1);
                }
                else
                    if (event.getSource().equals(stock2))
                        stock1.getSelectionModel().select(-1);
            }
        };
    }
    public ChangeListener<Stock> stockListener() {
   return new ChangeListener<Stock>() {
       @Override
       public void changed(ObservableValue<? extends Stock> observable, Stock oldValue, Stock newValue) {
           if (newValue == null)
               return;
           amount.setText(observable.getValue().getQuantity()+"");
       }
   };
    }

    public EventHandler<ActionEvent> onStore1() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ObservableList<Stock> stocks = null ;
                try {
                    if (event.getSource().equals(store1)) {
                        if (store1.getSelectionModel().isEmpty())
                            return;
                        stock1.setItems(stockRepository.getByStore(store1.getSelectionModel().getSelectedItem()));
                        stock1.setCellFactory(param -> getPara());
                    }else
                        if (event.getSource().equals(store2)) {
                            if (store2.getSelectionModel().isEmpty())
                                return;
                            stock2.setItems(stockRepository.getByStore(store2.getSelectionModel().getSelectedItem()));
                            stock2.setCellFactory(param -> getPara());
                        }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    public ListCell<Stock> getPara() {
        return new ListCell<Stock>() {
            @Override
            protected void updateItem(Stock item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else
                    setText(item.product().name());
            }
        };
    }
    public StringConverter<Stock> converterStock() {
        return new StringConverter<Stock>() {
            @Override
            public String toString(Stock object) {
                if (object == null)
                    return null ;
                else
                    return object.product().name() ;
            }
            @Override
            public Stock fromString(String string) {
                return null;
            }
        } ;
    }
    public StringConverter<Store> converter(){
        return new StringConverter<Store>() {
            @Override
            public String toString(Store object) {
                if (object == null)
                    return null ;
                else
                    return object.name();
            }

            @Override
            public Store fromString(String string) {
                return null;
            }
        };
    }


    public void clear() throws SQLException {
        store1.getSelectionModel().clearSelection();
        store2.getSelectionModel().clearSelection();
        stock1.getItems().clear();
        stock2.getItems().clear();
        amount.clear();
    }

}
