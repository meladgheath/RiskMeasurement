package org.softwareengine.modules.purchases.view;

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
import org.softwareengine.modules.purchases.model.Supplier;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.FXToolBar;

import java.util.Objects;

public abstract class SuppliersView {
    public final VBox root;
    public TextField nameTextField;
    public TextField phoneTextField;
    public final ListView<Supplier> supplierListView;
    public final Label nameLabel;
    public final Label phoneLabel;
    public final Button addButton;
    public final Button editButton;
    public final Button deleteButton;
    public FXSurface dialogSurface;
    public FXDialogContent dialogContent;
    public FXDialogDelete dialogDelete;

    public SuppliersView() {
        FXSurface surfaceSuppliersList = new FXSurface();
        surfaceSuppliersList.addTitle(LocaleService.getKey("suppliers-list"));
        supplierListView = new ListView<>();
        ImageView placeholder = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder.setFitWidth(100);
        placeholder.setFitHeight(100);
        supplierListView.setPlaceholder(placeholder);
        surfaceSuppliersList.addRow(supplierListView);

        FXSurface surfaceSupplierData = new FXSurface();
        surfaceSupplierData.addTitle(LocaleService.getKey("supplier-data"));
        nameLabel = surfaceSupplierData.addRowWithLabel(LocaleService.getKey("name"));
        surfaceSupplierData.addSeparator();
        phoneLabel = surfaceSupplierData.addRowWithLabel(LocaleService.getKey("phone"));

        HBox hBox = new HBox(surfaceSuppliersList, surfaceSupplierData);
        hBox.setSpacing(25);
        hBox.setAlignment(Pos.TOP_CENTER);

        FXToolBar fxToolBar = new FXToolBar();
        deleteButton = fxToolBar.addButton(Constants.DELETE_ICON.getValue());
        editButton = fxToolBar.addButton(Constants.EDIT_ICON.getValue());
        addButton = fxToolBar.addButton(Constants.ADD_ICON.getValue());

        root = new VBox(fxToolBar, hBox);
        root.getStyleClass().add("home-center");
        VBox.setMargin(hBox, new Insets(25));
        root.setAlignment(Pos.CENTER);

        dialogSurface = new FXSurface();
        dialogSurface.addTitle(LocaleService.getKey("add-supplier"));
        nameTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("name"));
        dialogSurface.addSeparator();
        phoneTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("phone"));
    }
}
