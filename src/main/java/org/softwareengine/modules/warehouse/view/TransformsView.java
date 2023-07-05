package org.softwareengine.modules.warehouse.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.softwareengine.config.Paths;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXSurface;

import java.util.Objects;

public class TransformsView {

    public ComboBox<Store> store1 ;
    public ListView<Stock> stock1 ;
    public ComboBox<Store> store2 ;
    public ListView<Stock> stock2 ;
    public TextField amount ;
    public VBox left ;
    public VBox right ;


    public Button fromButtonLeft;
    public Button fromButtonRight ;
    public Button toButton ;
    public VBox middle ;
    public BorderPane root;

    public Label message ;

    public TransformsView() {

        ImageView placeholder = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder.setFitWidth(100);
        placeholder.setFitHeight(100);

        ImageView placeholder2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder2.setFitWidth(100);
        placeholder2.setFitHeight(100);

        store1 = new ComboBox<>();
        stock1 = new ListView<>();
        stock1.setPlaceholder(placeholder);

        FXSurface leftSurface = new FXSurface();
        leftSurface.addRowWithComboBox(store1 , LocaleService.getKey("store"));
        leftSurface.addRow(stock1);
        fromButtonLeft = leftSurface.addButton(LocaleService.getKey("transforms"));

        FXSurface middleSurface = new FXSurface();
        amount = middleSurface.addRowWithTextField(LocaleService.getKey("quantity")) ;
        message = new Label("") ;
        middleSurface.addRow(message);


        store2 = new ComboBox<>();
        stock2 = new ListView<>();
        stock2.setPlaceholder(placeholder2);

        FXSurface rightSurface = new FXSurface();
        rightSurface.addRowWithComboBox(store2,LocaleService.getKey("store"));
        rightSurface.addRow(stock2);
        fromButtonRight = rightSurface.addButton(LocaleService.getKey("transforms")) ;

        left = new VBox();
        left.getChildren().add(leftSurface);
//        left.setAlignment(Pos.CENTER);

        middle =  new VBox();
        middle.getChildren().add(middleSurface);
//        middle.setAlignment(Pos.CENTER);

        right = new VBox();
        right.getChildren().add(rightSurface);
//        right.setAlignment(Pos.CENTER);

        root = new BorderPane();
        root.setPadding(new Insets(150,25,0,25));
        root.setLeft(left);
        root.setRight(right);
        root.setCenter(middle);

        BorderPane.setAlignment(root, Pos.CENTER);

//        root.setMaxSize(800,400);
//        root.setMinSize(800,400);
    }

}
