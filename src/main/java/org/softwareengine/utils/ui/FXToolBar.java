package org.softwareengine.utils.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;

public class FXToolBar extends HBox {

    public FXToolBar() {
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(0));
        setSpacing(3);
        getStyleClass().add("fx-toolbar");
    }

    public Button addButton(String svgContent) {
        SVGPath svgPath = new SVGPath();
        svgPath.setContent(svgContent);
        svgPath.setScaleX(.5);
        svgPath.setScaleY(.5);

        Button button = new Button();
        button.setGraphic(svgPath);
        button.getStyleClass().add("toolbar-button");
        button.setMinSize(40, 40);
        button.setMaxSize(40, 40);

        getChildren().add(button);
        return button;
    }
}
