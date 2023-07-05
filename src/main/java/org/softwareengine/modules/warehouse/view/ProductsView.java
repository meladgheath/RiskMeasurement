package org.softwareengine.modules.warehouse.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.softwareengine.config.Constants;
import org.softwareengine.config.Paths;
import org.softwareengine.modules.warehouse.model.Product;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.FXToolBar;

import java.util.Objects;

public abstract class ProductsView {
    public final VBox root;
    public TextField codeTextField;
    public TextField nameTextField;
    public final ListView<Product> productListView;
    public final Label codeLabel;
    public final Label nameLabel;
    public final Button addButton;
    public final Button editButton;
    public final Button deleteButton;

    public Button printButton ;
    public FXSurface dialogSurface;
    public FXDialogContent dialogContent;
    public FXDialogDelete dialogDelete;

    public ProductsView() {
        FXSurface surfaceProductsList = new FXSurface();
        surfaceProductsList.addTitle(LocaleService.getKey("products-list"));
        productListView = new ListView<>();
        ImageView placeholder = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder.setFitWidth(100);
        placeholder.setFitHeight(100);
        productListView.setPlaceholder(placeholder);
        surfaceProductsList.addRow(productListView);

        FXSurface surfaceProductData = new FXSurface();
        surfaceProductData.addTitle(LocaleService.getKey("product-data"));
        codeLabel = surfaceProductData.addRowWithLabel(LocaleService.getKey("code"));
        surfaceProductData.addSeparator();
        nameLabel = surfaceProductData.addRowWithLabel(LocaleService.getKey("name"));

      /*  FXSurface surfacePrintButton = new FXSurface();
        printButton = surfacePrintButton.addButton(LocaleService.getKey("print")) ;

        VBox v = new VBox();
        v.getChildren().add(surfaceProductData) ;
        v.getChildren().add(surfacePrintButton);
        v.setSpacing(25);
*/
        HBox hBox = new HBox(surfaceProductsList, surfaceProductData);
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
        dialogSurface.addTitle(LocaleService.getKey("add-product"));
        codeTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("code"));
        dialogSurface.addSeparator();
        nameTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("name"));
    }
}
