package org.softwareengine.modules.finance.view;

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
import org.softwareengine.modules.finance.model.Treasury;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXDialogDelete;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.FXToolBar;

import java.util.Objects;
public abstract class TreasuryView {
    public final VBox root;
    public Label expensesLable ;
    public Label debitsLable ;
    public Label salesLable ;

    public TextField nameTextField;
    public final ListView<Treasury> treasuryListView;
    public final Button editButton;
    public       Button addButton ;

    public FXSurface dialogSurface;
    public FXDialogContent dialogContent;
    public FXDialogDelete dialogDelete;
    public TreasuryView() {
        FXSurface surfaceProductsList = new FXSurface();
        surfaceProductsList.addTitle(LocaleService.getKey("treasuries-list"));
        treasuryListView = new ListView<>();
        ImageView placeholder = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder.setFitWidth(100);
        placeholder.setFitHeight(100);
        treasuryListView.setPlaceholder(placeholder);
        surfaceProductsList.addRow(treasuryListView);

        FXSurface surfaceProductData = new FXSurface();
        surfaceProductData.addTitle(LocaleService.getKey("treasury-data"));

        expensesLable = surfaceProductData.addRowWithLabel(LocaleService.getKey("expensess")) ;
        debitsLable   = surfaceProductData.addRowWithLabel(LocaleService.getKey("debits")) ;
        salesLable    = surfaceProductData.addRowWithLabel(LocaleService.getKey("sales")) ;

        HBox hBox = new HBox(surfaceProductsList,surfaceProductData);
        hBox.setSpacing(25);
        hBox.setAlignment(Pos.TOP_CENTER);

        FXToolBar fxToolBar = new FXToolBar();
        editButton = fxToolBar.addButton(Constants.EDIT_ICON.getValue());
        addButton  = fxToolBar.addButton(Constants.ADD_ICON.getValue());

        root = new VBox(fxToolBar, hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");
        root.setAlignment(Pos.CENTER);

        dialogSurface = new FXSurface();
        dialogSurface.addTitle(LocaleService.getKey("add-product"));
        nameTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("name"));
    }
}
