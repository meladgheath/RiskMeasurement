package org.softwareengine.modules.warehouse.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.softwareengine.config.Constants;
import org.softwareengine.config.Paths;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.FXToolBar;

import java.util.Objects;

public abstract class StoresView {

    public final VBox root;
    public final ListView<Store> storeListView;
    public final ListView<Stock> stockListView;
    public Label codeLabel;
    public Label purchasePriceLabel;
    public Label salePriceLabel;
    public Label packageLabel;
    public Label quantityLabel;
    public final Button addButton;
    public final Button editButton;
    public final Button deleteButton;

    public Button printButton ;
    public FXSurface dialogSurface;
    public TextField nameTextField;
    public FXDialogContent dialogContent;
    public FXDialogDelete dialogDelete;

    public StoresView() {
        FXSurface surfaceStoresList = new FXSurface();
        surfaceStoresList.addTitle(LocaleService.getKey("stores-list"));
        storeListView = new ListView<>();
        ImageView placeholder = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder.setFitWidth(100);
        placeholder.setFitHeight(100);
        storeListView.setPlaceholder(placeholder);
        surfaceStoresList.addRow(storeListView);

        FXSurface surfaceStockList = new FXSurface();
        surfaceStockList.addTitle(LocaleService.getKey("stock-list"));
        stockListView = new ListView<>();
        ImageView placeholder2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder2.setFitWidth(100);
        placeholder2.setFitHeight(100);
        stockListView.setPlaceholder(placeholder2);
        surfaceStockList.addRow(stockListView);

        FXSurface surfaceStockData = new FXSurface();
        surfaceStockData.addTitle(LocaleService.getKey("stock-data"));
        codeLabel = surfaceStockData.addRowWithLabel(LocaleService.getKey("code"));
        surfaceStockData.addSeparator();
        purchasePriceLabel = surfaceStockData.addRowWithLabel(LocaleService.getKey("purchase-price"));
        surfaceStockData.addSeparator();
        salePriceLabel = surfaceStockData.addRowWithLabel(LocaleService.getKey("sale-price"));
        surfaceStockData.addSeparator();
        packageLabel = surfaceStockData.addRowWithLabel(LocaleService.getKey("package"));
        surfaceStockData.addSeparator();
        quantityLabel = surfaceStockData.addRowWithLabel(LocaleService.getKey("quantity"));


        FXSurface surface = new FXSurface();
        printButton = surface.addButton(LocaleService.getKey("print")) ;

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().add(surfaceStockData);
        vBox.getChildren().add(surface);

//        HBox hBox = new HBox(surfaceStoresList, surfaceStockList, surfaceStockData);
        HBox hBox = new HBox(surfaceStoresList, surfaceStockList, vBox);

        hBox.setSpacing(25);
        hBox.setAlignment(Pos.TOP_CENTER);

        FXToolBar fxToolBar = new FXToolBar();
        deleteButton = fxToolBar.addButton(Constants.DELETE_ICON.getValue());
        editButton = fxToolBar.addButton(Constants.EDIT_ICON.getValue());
        addButton = fxToolBar.addButton(Constants.ADD_ICON.getValue());

        root = new VBox(fxToolBar, hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");
        root.setAlignment(Pos.CENTER);

        dialogSurface = new FXSurface();
        dialogSurface.addTitle(LocaleService.getKey("add-store"));
        nameTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("name"));
    }
}
