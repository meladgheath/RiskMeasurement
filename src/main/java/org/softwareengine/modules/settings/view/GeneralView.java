package org.softwareengine.modules.settings.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import org.softwareengine.config.Constants;
import org.softwareengine.utils.service.LocaleService;
import org.softwareengine.utils.ui.FXSurface;
import org.softwareengine.utils.ui.ToggleSwitch;

public abstract class GeneralView {
    public final VBox root;
    public final ToggleButton arabicToggleButton;
    public final ToggleButton englishToggleButton;
    public final ToggleSwitch darkModeSwitch;
    public final SVGPath lightModeIcon;
    public final SVGPath darkModeIcon;
    public final SVGPath updateIcon;
    public final Button updateButton;

    public GeneralView() {
        FXSurface surface0 = new FXSurface();
        surface0.setMinWidth(500);
        ToggleGroup toggleGroup = new ToggleGroup();
        arabicToggleButton = new ToggleButton();
        arabicToggleButton.textProperty().bind(LocaleService.getKey("arabic"));
        englishToggleButton = new ToggleButton();
        englishToggleButton.textProperty().bind(LocaleService.getKey("english"));
        arabicToggleButton.setToggleGroup(toggleGroup);
        englishToggleButton.setToggleGroup(toggleGroup);
        HBox hBox = new HBox(arabicToggleButton, englishToggleButton);
        surface0.addRow(hBox, LocaleService.getKey("language"));
        surface0.addSeparator();

        lightModeIcon = new SVGPath();
        lightModeIcon.setContent(Constants.LIGHT_MODE_ICON_SELECTED.getValue());
        lightModeIcon.setScaleX(.5);
        lightModeIcon.setScaleY(.5);

        darkModeIcon = new SVGPath();
        darkModeIcon.setContent(Constants.DARK_MODE_ICON_SELECTED.getValue());
        darkModeIcon.setScaleX(.5);
        darkModeIcon.setScaleY(.5);

        darkModeSwitch = new ToggleSwitch();
        darkModeSwitch.setPadding(new Insets(5));
//        darkModeSwitch.setMinSize(100, 25);
//        darkModeSwitch.setMaxSize(100, 25);
        surface0.addRow(darkModeSwitch, LocaleService.getKey("dark-mode"));
        surface0.addSeparator();

        updateIcon = new SVGPath();
        updateIcon.setContent(Constants.UPDATE_ICON.getValue());
        updateIcon.setScaleX(.5);
        updateIcon.setScaleY(.5);

        updateButton = new Button();
        updateButton.getStyleClass().add("primary-button");
        updateButton.setMinSize(50, 25);
        updateButton.setMaxSize(50, 25);
        updateButton.setGraphic(updateIcon);
        surface0.addRow(updateButton, LocaleService.getKey("system-update"));

        root = new VBox(surface0);
        root.getStyleClass().add("home-center");
        VBox.setMargin(surface0, new Insets(25));
        root.setAlignment(Pos.CENTER);

    }
}
