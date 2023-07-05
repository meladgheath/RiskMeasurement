package org.softwareengine.modules.finance.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.softwareengine.config.Constants;
import org.softwareengine.config.Paths;
import org.softwareengine.modules.finance.model.DebtBook;
import org.softwareengine.modules.sales.model.Customer;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXDialogContent;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.FXToolBar;

import java.util.Objects;

public abstract class DebtBookView {

    public final VBox root;
    public ComboBox<Customer> customerComboBox;
    public TextField debitTextField;
    public TextField creditTextField;
    public final ListView<DebtBook> debtBookListView;
    public final Label nameLabel;
    public final Label totalDebitLabel;
    public final Label creditLabel;
    public final Label debitLabel;
    public final Label totalCreditLabel;
    public final Button addButton;
    public FXSurface dialogSurface;
    public FXDialogContent dialogContent;

    public DebtBookView() {
        FXSurface surface = new FXSurface();
        surface.addTitle(LocaleService.getKey("debt-list"));
        debtBookListView = new ListView<>();
        ImageView placeholder = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.PLACEHOLDER_IMAGE.getPath()))));
        placeholder.setFitWidth(100);
        placeholder.setFitHeight(100);
        debtBookListView.setPlaceholder(placeholder);
        surface.addRow(debtBookListView);

        FXSurface surface1 = new FXSurface();
        surface1.addTitle(LocaleService.getKey("debt-data"));
        nameLabel = surface1.addRowWithLabel(LocaleService.getKey("name"));
        surface1.addSeparator();
        totalDebitLabel = surface1.addRowWithLabel(LocaleService.getKey("total-debit"));
        surface1.addSeparator();
        totalCreditLabel = surface1.addRowWithLabel(LocaleService.getKey("total-credit"));
        surface1.addSeparator();
        creditLabel = surface1.addRowWithLabel(LocaleService.getKey("credit"));
        surface1.addSeparator();
        debitLabel = surface1.addRowWithLabel(LocaleService.getKey("remaining"));

        HBox hBox = new HBox(surface, surface1);
        hBox.setSpacing(25);
        hBox.setAlignment(Pos.TOP_CENTER);

        FXToolBar fxToolBar = new FXToolBar();
        addButton = fxToolBar.addButton(Constants.ADD_ICON.getValue());

        root = new VBox(fxToolBar, hBox);
        VBox.setMargin(hBox, new Insets(25));
        root.getStyleClass().add("home-center");
        root.setAlignment(Pos.CENTER);

        dialogSurface = new FXSurface();
        dialogSurface.addTitle(LocaleService.getKey("add-debt"));

        customerComboBox = new ComboBox<>();
        dialogSurface.addRowWithComboBox(customerComboBox, LocaleService.getKey("name"));
        dialogSurface.addSeparator();
        debitTextField = new TextField();
        dialogSurface.addRow(debitTextField, LocaleService.getKey("debit"));
        dialogSurface.addSeparator();
        creditTextField = dialogSurface.addRowWithTextField(LocaleService.getKey("credit"));
    }
}
