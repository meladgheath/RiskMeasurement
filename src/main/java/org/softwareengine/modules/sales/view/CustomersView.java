package org.softwareengine.modules.sales.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.softwareengine.config.Constants;
import org.softwareengine.config.Paths;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.FXToolBar;

import java.util.Objects;

public abstract class CustomersView {

    public final VBox root;
    public TextField nameTextField;
    public TextField phoneTextField;
    public final ListView<Customer> customerListView;
    public final Label nameLabel;
    public final Label phoneLabel;
    public final Button addButton;
    public final Button editButton;
    public final Button deleteButton;
    public FXSurface dialogSurface;
    public FXDialogContent dialogContent;
    public FXDialogDelete dialogDelete;

    public CustomersView() {
        FXSurface surfaceCustomersList = new FXSurface();
        surfaceCustomersList.addTitle(LocaleService.getKey("customers-list"));
        customerListView = new ListView<>();
        ImageView placeholder = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder.setFitWidth(100);
        placeholder.setFitHeight(100);
        customerListView.setPlaceholder(placeholder);
        surfaceCustomersList.addRow(customerListView);

        FXSurface surfaceCustomerData = new FXSurface();
        surfaceCustomerData.addTitle(LocaleService.getKey("customer-data"));
        nameLabel = surfaceCustomerData.addRowWithLabel(LocaleService.getKey("name"));
        surfaceCustomerData.addSeparator();
        phoneLabel = surfaceCustomerData.addRowWithLabel(LocaleService.getKey("phone"));

        HBox hBox = new HBox(surfaceCustomersList, surfaceCustomerData);
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.TOP_CENTER);

        FXToolBar fxToolBar = new FXToolBar();
        deleteButton = fxToolBar.addButton(Constants.DELETE_ICON.getValue());
        editButton = fxToolBar.addButton(Constants.EDIT_ICON.getValue());
        addButton = fxToolBar.addButton(Constants.ADD_ICON.getValue());

        ScrollBar bar = new ScrollBar();

        root = new VBox(fxToolBar,hBox);

        VBox.setMargin(hBox, new Insets(25));
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("home-center");


        dialogSurface = new FXSurface();
        dialogSurface.addTitle(LocaleService.getKey("add-customer"));
        nameTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("name"));
        dialogSurface.addSeparator();
        phoneTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("phone"));
    }
}
