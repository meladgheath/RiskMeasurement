package org.softwareengine.modules.purchases.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.softwareengine.config.Constants;
import org.softwareengine.config.Paths;
import org.softwareengine.core.model.PaymentMethod;
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.modules.warehouse.model.Stock;
import org.softwareengine.modules.warehouse.model.Store;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.FXToolBar;

import java.util.Objects;

public abstract class PurchasePointView {
    public final VBox root;
    public final ListView<Stock> stockListView;
    public final Button checkoutButton;
    public final FXSurface dialogSurface;
    public final FXSurface settingsDialogSurface;
    public final ListView<Product> dialogListView;
    public final TextField dialogTextField;
    public final Button settingsButton;
    public final ComboBox<PaymentMethod> paymentMethodComboBox;
    public FXDialogContent dialogContent;
    public FXDialogContent settingsDialogContent;
    public ComboBox<Treasury> treasuryComboBox;
    public ComboBox<Supplier> supplierComboBox;
    public TextField discountTextField;
    public ComboBox<Store> storeComboBox;
    public TextField codeTextField;
    public TextField purchasePriceTextField;
    public TextField salePriceTextField;
    public TextField packageTextField;
    public TextField quantityTextField;
    public Label codeLabel;
    public Label purchasePriceLabel;
    public Label salePriceLabel;
    public Label packageLabel;
    public Label quantityLabel;
    public Label totalLabel;
    public final Button addStockToListButton;

    public final Button addButton;
    public final Button deleteButton;
    public final Button clearButton;
    public FXDialogDelete dialogDelete;

    public PurchasePointView(Stage stage) {
        FXSurface surface0 = new FXSurface();
        surface0.addTitle(LocaleService.getKey("purchase-data"));

        codeTextField = surface0.addRowWithTextField(LocaleService.getKey("code"));
        surface0.addSeparator();
        purchasePriceTextField = surface0.addRowWithTextField(LocaleService.getKey("purchase-price"));
        surface0.addSeparator();
        salePriceTextField = surface0.addRowWithTextField(LocaleService.getKey("sale-price"));
        surface0.addSeparator();
        packageTextField = surface0.addRowWithTextField(LocaleService.getKey("package"));
        surface0.addSeparator();
        quantityTextField = surface0.addRowWithTextField(LocaleService.getKey("quantity"));
        addStockToListButton = surface0.addButton(LocaleService.getKey("add"));

        FXSurface surface1 = new FXSurface();
        surface1.addTitle(LocaleService.getKey("purchases-list"));
        stockListView = new ListView<>();
        ImageView placeholder2 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder2.setFitWidth(100);
        placeholder2.setFitHeight(100);
        stockListView.setPlaceholder(placeholder2);
        surface1.addRow(stockListView);

        FXSurface surface2 = new FXSurface();
        surface2.addTitle(LocaleService.getKey("stock-data"));
        codeLabel = surface2.addRowWithLabel(LocaleService.getKey("code"));
        surface2.addSeparator();
        purchasePriceLabel = surface2.addRowWithLabel(LocaleService.getKey("purchase-price"));
        surface2.addSeparator();
        salePriceLabel = surface2.addRowWithLabel(LocaleService.getKey("sale-price"));
        surface2.addSeparator();
        packageLabel = surface2.addRowWithLabel(LocaleService.getKey("package"));
        surface2.addSeparator();
        quantityLabel = surface2.addRowWithLabel(LocaleService.getKey("quantity"));
        surface2.addSeparator();
        totalLabel = surface2.addRowWithLabel(LocaleService.getKey("total"));
        totalLabel.setText("0.00");

        FXSurface surface3 = new FXSurface();
        checkoutButton = new Button("0.00");
        checkoutButton.getStyleClass().add("primary-button");
        checkoutButton.getStyleClass().add("total-amount");
        surface3.addRow(checkoutButton);
        VBox vBox = new VBox(surface2, surface3);

        HBox hBox = new HBox(surface0, surface1, vBox);
        hBox.setSpacing(25);
        hBox.setAlignment(Pos.TOP_CENTER);

        FXToolBar fxToolBar = new FXToolBar();
        settingsButton = fxToolBar.addButton(Constants.GENERAL_ICON.getValue());
        addButton = fxToolBar.addButton(Constants.ADD_ICON.getValue());
        deleteButton = fxToolBar.addButton(Constants.DELETE_ICON.getValue());
        clearButton = fxToolBar.addButton(Constants.UPDATE_ICON.getValue());

        dialogSurface = new FXSurface();
        dialogTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("name"));
        dialogSurface.addSeparator();
        dialogListView = new ListView<>();
        dialogSurface.addRow(dialogListView);

        settingsDialogSurface = new FXSurface();
        settingsDialogSurface.addTitle(LocaleService.getKey("bill-settings"));
        storeComboBox = new ComboBox<>();
        settingsDialogSurface.addRowWithComboBox(storeComboBox, LocaleService.getKey("store"));
        settingsDialogSurface.addSeparator();
        treasuryComboBox = new ComboBox<>();
        settingsDialogSurface.addRowWithComboBox(treasuryComboBox, LocaleService.getKey("treasury"));
        settingsDialogSurface.addSeparator();
        discountTextField = settingsDialogSurface.addRowWithTextField(LocaleService.getKey("discount"));
        discountTextField.setText("0");
        settingsDialogSurface.addSeparator();
        paymentMethodComboBox = new ComboBox<>();
        settingsDialogSurface.addRowWithComboBox(paymentMethodComboBox, LocaleService.getKey("payment-method"));
        settingsDialogSurface.addSeparator();
        supplierComboBox = new ComboBox<>();
        settingsDialogSurface.addRowWithComboBox(supplierComboBox, LocaleService.getKey("supplier"));

        root = new VBox(fxToolBar, hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");
        root.setAlignment(Pos.CENTER);
    }
}
